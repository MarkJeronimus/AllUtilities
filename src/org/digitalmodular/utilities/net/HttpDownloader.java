/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2018 Mark Jeronimus. All Rights Reversed.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AllUtilities. If not, see <http://www.gnu.org/licenses/>.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.digitalmodular.utilities.net;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.logging.Level.*;

import org.jetbrains.annotations.Nullable;

import org.digitalmodular.utilities.gui.swing.progress.ProgressListener;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;

/**
 * Wrapper for non-persistent http connections.
 * <ul><li>Protocols: http, https</li>
 * <li>Methods: GET, POST</li>
 * <li>Cookies</li>
 * <li>User agent spoofing (default = {@code "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:55.0) Gecko/20100101
 * Firefox/55.0"})</li>
 * <li>Compressions: gzip, deflate</li>
 * <li>Additional header fields: yes (but not removal of existing header fields)</li>
 * <li>Timeout (default 30s)</li>
 * <li>Progress listeners</li>
 * <li>Immutable header fields include {@code Accept} and {@code Accept-Language}</li></ul>
 *
 * @author Mark Jeronimus
 */
// Created 2015-10-17
public class HttpDownloader {
	/** Equals to: {@code "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:55.0) Gecko/20100101 Firefox/55.0"}. */
	public static final String USER_AGENT_FIREFOX_UBUNTU =
			"Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:55.0) Gecko/20100101 Firefox/55.0";

	/** Similar to: {@code "Mozilla/5.0 (compatible; Java/1.8.0_144)"}. */
	public static final String USER_AGENT_JAVA =
			"Mozilla/5.0 (compatible; Java/" + System.getProperty("java.version") + ')';

	private static final int DEFAULT_TIMEOUT = 10_000;

	private       String                       userAgent         = USER_AGENT_FIREFOX_UBUNTU;
	private final Map<String, String>          cookies           = new LinkedHashMap<>(16);
	private       int                          timeout           = DEFAULT_TIMEOUT;
	private final Collection<ProgressListener> progressListeners = new CopyOnWriteArrayList<>();

	public String getUserAgent() { return userAgent; }

	public void setUserAgent(String userAgent) {
		this.userAgent = requireNonNull(userAgent, "userAgent");
	}

	public final void setCookie(String cookieName, @Nullable String cookieValue) {
		requireNonNull(cookieName, "cookieName");

		if (cookieValue == null)
			cookies.remove(cookieName);
		else
			cookies.put(cookieName, cookieValue);
	}

	public Map<String, String> getCookies() { return Collections.unmodifiableMap(cookies); }

	public void setTimeout(int timeout)     { this.timeout = timeout; }

	public int getTimeout()                 { return timeout; }

	public void addProgressListener(ProgressListener progressListener) {
		progressListeners.add(progressListener);
	}

	public void removeProgressListener(ProgressListener progressListener) {
		progressListeners.remove(progressListener);
	}

	/**
	 * Requests a URL and if successful (response 200), returns the open stream. Closing this stream closes the
	 * connection.
	 */
	public HttpResponse openConnection(URL url,
	                                   @Nullable byte[] postData,
	                                   String... extraRequestProperties) throws IOException {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)url.openConnection();
			connection.addRequestProperty("Host", url.getHost());
			connection.addRequestProperty("User-Agent", userAgent);
			connection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			connection.addRequestProperty("Accept-Language", "en-US,en;q=0.5");
			connection.addRequestProperty("Accept-Encoding", "gzip, deflate");
			addExtraRequestProperty(connection, getCookieString(cookies));
			addExtraRequestProperties(connection, extraRequestProperties);
			connection.addRequestProperty("Connection", "close");
			addPostDataHeader(postData, connection);

			connection.setInstanceFollowRedirects(false);

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setReadTimeout(timeout);
			addPostData(postData, connection);

			int responseCode = connection.getResponseCode();
			if (responseCode >= 400)
				throw new EOFException("HTTP response code " + connection.getResponseCode());

			InputStream in;
			try {
				in = connection.getInputStream();
			} catch (IOException ex) {
				in = connection.getErrorStream();
				if (in == null)
					throw ex;
			}

			in = getDecompressedStream(connection, in);

			int                       contentLength   = attemptGetStreamLength(connection);
			Map<String, List<String>> responseHeaders = connection.getHeaderFields();

			HttpResponse stream = new HttpResponse(url, in, responseCode, responseHeaders, contentLength);
			progressListeners.forEach(stream::addProgressListener);

			return stream;
		} catch (IOException e) {
			if (connection != null)
				connection.disconnect();

			throw e;
		}
	}

	private static void addExtraRequestProperties(HttpURLConnection connection, String[] extraRequestProperties) {
		for (String extraRequestProperty : extraRequestProperties)
			addExtraRequestProperty(connection, extraRequestProperty);
	}

	private static void addExtraRequestProperty(HttpURLConnection connection, @Nullable String s) {
		if (s == null)
			return;

		int i = s.indexOf(": ");
		if (i == -1)
			throw new IllegalArgumentException("Not a request property (must contain a \": \"): " + s);

		connection.addRequestProperty(s.substring(0, i), s.substring(i + 2));
	}

	private static @Nullable String getCookieString(Map<String, String> cookies) {
		if (cookies.isEmpty())
			return null;

		StringBuilder cookieProperty = new StringBuilder(256);
		for (Entry<String, String> cookie : cookies.entrySet()) {
			if (cookieProperty.length() == 0)
				cookieProperty.append("Cookie:");
			else
				cookieProperty.append(';');

			cookieProperty.append(' ');
			cookieProperty.append(cookie.getKey());
			cookieProperty.append('=');
			cookieProperty.append(cookie.getValue());
		}
		return cookieProperty.toString();
	}

	private static void addPostDataHeader(@Nullable byte[] postData, HttpURLConnection connection)
			throws ProtocolException {
		if (postData != null) {
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.addRequestProperty("Content-length", Integer.toString(postData.length));
			connection.setRequestProperty("Content-Language", "en-US");
		}
	}

	private static void addPostData(@Nullable byte[] postData, HttpURLConnection connection) throws IOException {
		if (postData != null) {
			connection.setDoOutput(true);
			try (OutputStream os = connection.getOutputStream()) {
				os.write(postData);
			}
		}
	}

	private static InputStream getDecompressedStream(HttpURLConnection connection, InputStream in) throws IOException {
		if ("gzip".equals(connection.getHeaderField("Content-Encoding")))
			in = new GZIPInputStream(in);
		else if ("deflate".equals(connection.getHeaderField("Content-Encoding")))
			in = new DeflaterInputStream(in);
		return in;
	}

	private static int attemptGetStreamLength(HttpURLConnection connection) {
		try {
			return Integer.parseInt(connection.getHeaderField("Content-Length"));
		} catch (NumberFormatException ignored) {
			return -1;
		}
	}

	public void downloadToFile(URL url, @Nullable byte[] postData, Path file) throws IOException {
		Logger.getGlobal().info("Downloading: " + file + " from " + url);

		try (HttpResponse stream = openConnection(url, postData)) {
			if (stream.getResponseCode() / 100 != 2)
				throw new IOException("Received " + String.valueOf(stream.getResponseHeaders().get(null)) +
				                      " for " + url);

			Files.copy(stream, file, REPLACE_EXISTING);
		} catch (IOException ex) {
			if (Files.exists(file)) {
				Logger.getGlobal().log(WARNING, "Removing partially downloaded file because of exception");
				Files.delete(file);
			}

			throw ex;
		}
	}
}
