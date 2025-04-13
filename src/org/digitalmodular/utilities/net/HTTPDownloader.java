/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.digitalmodular.utilities.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;
import static java.util.logging.Level.FINER;
import static java.util.logging.Level.WARNING;

import org.jetbrains.annotations.Nullable;

import org.digitalmodular.utilities.graphics.swing.progress.ProgressListener;
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
public class HTTPDownloader {
	/** Equals to: {@code "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:87.0) Gecko/20100101 Firefox/87.0"}. */
	public static final String USER_AGENT_FIREFOX_UBUNTU =
			"Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:93.0) Gecko/20100101 Firefox/93.0";

	/** Similar to: {@code "Mozilla/5.0 (compatible; Java/1.8.0_144)"}. */
	public static final String USER_AGENT_JAVA =
			"Mozilla/5.0 (compatible; Java/" + System.getProperty("java.version") + ')';

	private static final int DEFAULT_TIMEOUT = 10_000;

	private       String                       userAgent         = USER_AGENT_FIREFOX_UBUNTU;
	private final Map<String, String>          cookies           = new LinkedHashMap<>(16);
	private       boolean                      doReferer         = true;
	private       int                          timeout           = DEFAULT_TIMEOUT;
	private final Collection<ProgressListener> progressListeners = new CopyOnWriteArrayList<>();
//	private final IndirectProgressListener     indirectListener  = new IndirectProgressListener();

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = requireNonNull(userAgent, "userAgent");
	}

	public final void setCookie(String cookieName, @Nullable String cookieValue) {
		requireNonNull(cookieName, "cookieName");

		if (cookieValue == null) {
			cookies.remove(cookieName);
		} else {
			cookies.put(cookieName, cookieValue);
		}
	}

	public Map<String, String> getCookies() {
		return Collections.unmodifiableMap(cookies);
	}

	public boolean isDoReferer() {
		return doReferer;
	}

	public void setDoReferer(boolean doReferer) {
		this.doReferer = doReferer;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getTimeout() {
		return timeout;
	}

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
	public HTTPResponseStream openConnection(URI uri,
	                                         byte @Nullable [] postData,
	                                         String... extraRequestProperties) throws IOException {
		return openConnection(uri.toURL(), postData, extraRequestProperties);
	}

	/**
	 * Requests a URL and if successful (response 200), returns the open stream. Closing this stream closes the
	 * connection.
	 */
	public HTTPResponseStream openConnection(URL url,
	                                         byte @Nullable [] postData,
	                                         String... extraRequestProperties) throws IOException {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)url.openConnection();
			connection.addRequestProperty("User-Agent", userAgent);
			connection.addRequestProperty("Accept", "*.*");
			connection.addRequestProperty("Accept-Language", "en-US,en;q=0.5");
			connection.addRequestProperty("Accept-Encoding", "gzip, deflate");
			addExtraRequestProperty(connection, getCookieString(cookies));
			addExtraRequestProperties(connection, extraRequestProperties);
			connection.addRequestProperty("Connection", "keep-alive");
			connection.addRequestProperty("DNT", "1");
			addPostDataHeader(postData, connection);

			if (doReferer) {
				connection.addRequestProperty("Referer", url.toString() + '/');
			}

			connection.setInstanceFollowRedirects(false);

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setConnectTimeout(timeout);
			connection.setReadTimeout(timeout);
			addPostData(postData, connection);

//			Map<String, List<String>>        requestProperties = connection.getRequestProperties();
//			Set<Entry<String, List<String>>> entries           = requestProperties.entrySet();
//			for (Entry<String, List<String>> entry : entries) {
//				System.out.println(entry.getKey() + ": " + String.join(", ", entry.getValue()));
//			}

			int responseCode = connection.getResponseCode();
//			if (responseCode >= 400)
//				throw new EOFException("HTTP response code " + connection.getResponseCode());

			InputStream in;
			try {
				in = connection.getInputStream();
			} catch (IOException ex) {
				in = connection.getErrorStream();
				if (in == null) {
					throw ex;
				}
			}

			in = getDecompressedStream(connection, in);

			int                       contentLength   = attemptGetStreamLength(connection);
			Map<String, List<String>> responseHeaders = connection.getHeaderFields();

			HTTPResponseStream stream = new HTTPResponseStream(url, in, responseCode, responseHeaders, contentLength);
			progressListeners.forEach(stream::addProgressListener);

			return stream;
		} catch (IOException ex) {
			if (connection != null) {
				connection.disconnect();
			}

			throw ex;
		}
	}

	private static void addExtraRequestProperties(URLConnection connection, String[] extraRequestProperties) {
		for (String extraRequestProperty : extraRequestProperties) {
			addExtraRequestProperty(connection, extraRequestProperty);
		}
	}

	private static void addExtraRequestProperty(URLConnection connection, @Nullable String s) {
		if (s == null) {
			return;
		}

		int i = s.indexOf(": ");
		if (i == -1) {
			throw new IllegalArgumentException("Not a request property (must contain a \": \"): " + s);
		}

		connection.addRequestProperty(s.substring(0, i), s.substring(i + 2));
	}

	private static @Nullable String getCookieString(Map<String, String> cookies) {
		if (cookies.isEmpty()) {
			return null;
		}

		StringBuilder cookieProperty = new StringBuilder(256);
		for (Entry<String, String> cookie : cookies.entrySet()) {
			if (cookieProperty.length() == 0) {
				cookieProperty.append("Cookie:");
			} else {
				cookieProperty.append(';');
			}

			cookieProperty.append(' ');
			cookieProperty.append(cookie.getKey());
			cookieProperty.append('=');
			cookieProperty.append(cookie.getValue());
		}
		return cookieProperty.toString();
	}

	private static void addPostDataHeader(byte @Nullable [] postData, HttpURLConnection connection)
			throws ProtocolException {
		if (postData != null) {
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.addRequestProperty("Content-length", Integer.toString(postData.length));
//			connection.setRequestProperty("Content-Language", "en-US");
		}
	}

	@SuppressWarnings("TypeMayBeWeakened")
	private static void addPostData(byte @Nullable [] postData, HttpURLConnection connection) throws IOException {
		if (postData != null) {
			connection.setDoOutput(true);
			try (OutputStream os = connection.getOutputStream()) {
				os.write(postData);
			}
		}
	}

	@SuppressWarnings("IOResourceOpenedButNotSafelyClosed")
	private static InputStream getDecompressedStream(URLConnection connection, InputStream in) throws IOException {
		if ("gzip".equals(connection.getHeaderField("Content-Encoding"))) {
			in = new GZIPInputStream(in);
		} else if ("deflate".equals(connection.getHeaderField("Content-Encoding"))) {
			in = new DeflaterInputStream(in);
		}
		return in;
	}

	private static int attemptGetStreamLength(URLConnection connection) {
		try {
			return Integer.parseInt(connection.getHeaderField("Content-Length"));
		} catch (NumberFormatException ignored) {
			return -1;
		}
	}

	public HTTPResponseStream openPage(URI uri,
	                                   byte @Nullable [] postData,
	                                   String... extraRequestProperties) throws IOException {
		return openPage(uri.toURL(), postData, extraRequestProperties);
	}

	public HTTPResponseStream openPage(URL url,
	                                   byte @Nullable [] postData,
	                                   String... extraRequestProperties) throws IOException {
		HTTPResponseStream stream = openConnection(url, postData, extraRequestProperties);

		int responseCode = stream.getResponseCode();
		if (responseCode == 301 || responseCode == 302) { // 301 = Moved permanently, 302 = Redirect
			List<String> locationHeaderResponse = stream.getResponseHeaders().get("Location");
			if (locationHeaderResponse == null) {
				throw new IOException("\"HTTP/" + responseCode + "\" without \"Location\" response header: " +
				                      stream.getResponseHeaders());
			}

			String newLocation = locationHeaderResponse.get(0);
			URL    redirect    = new URL(url, newLocation);

			if (Logger.getGlobal().isLoggable(FINER)) {
				Logger.getGlobal().log(FINER, "Redirecting to: " + redirect);
			}

			return openPage(redirect, postData, extraRequestProperties);
		}

		return stream;
	}

	public void downloadToFile(URI uri, byte @Nullable [] postData, Path file, String... extraRequestProperties)
			throws IOException {
		downloadToFile(uri.toURL(), postData, file);
	}

	public void downloadToFile(URL url, byte @Nullable [] postData, Path file, String... extraRequestProperties)
			throws IOException {
		try (HTTPResponseStream stream = openConnection(url, postData, extraRequestProperties);
		     OutputStream out = Files.newOutputStream(file)) {
			int responseCode = stream.getResponseCode();
			if (responseCode == 301 || responseCode == 302) { // 301 = Moved permanently, 302 = Redirect
				List<String> locationHeaderResponse = stream.getResponseHeaders().get("Location");
				if (locationHeaderResponse == null) {
					throw new IOException("\"HTTP/" + responseCode + "\" without \"Location\" response header: " +
					                      stream.getResponseHeaders());
				}

				String newLocation = locationHeaderResponse.get(0);
				URL    redirect    = new URL(url, newLocation);

				if (Logger.getGlobal().isLoggable(FINER)) {
					Logger.getGlobal().log(FINER, "Redirecting to: " + redirect);
				}

				downloadToFile(redirect, null, file, extraRequestProperties);
				return;
			}

			if (stream.getResponseCode() / 100 != 2) {
				throw new IOException("Received " + stream.getResponseHeaders().get(null) + " for " + url);
			}

			stream.transferTo(out);
		} catch (IOException ex) {
			if (Files.exists(file)) {
				Logger.getGlobal().log(WARNING, "Removing partially downloaded file because of exception");
				Files.delete(file);
			}

			throw ex;
		}

//		indirectListener.delayedFireCompletedEvent();
	}
//
//	private final class IndirectProgressListener implements ProgressListener {
//		private @Nullable ProgressEvent completedEvent = null;
//
//		@Override
//		public void progressUpdated(ProgressEvent e) {
//			progressListeners.forEach(listener -> listener.progressUpdated(e));
//		}
//
//		@Override
//		public void progressCompleted(ProgressEvent e) {
//			completedEvent = e;
//		}
//
//		public void delayedFireCompletedEvent() {
//			if (completedEvent == null)
//				throw new IllegalStateException("progressCompleted not called yet");
//
//			progressListeners.forEach(listener -> listener.progressCompleted(completedEvent));
//		}
//	}
}
