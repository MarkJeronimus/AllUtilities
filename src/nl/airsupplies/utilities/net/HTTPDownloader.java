package nl.airsupplies.utilities.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
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
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Logger;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;
import static java.util.logging.Level.FINER;
import static java.util.logging.Level.WARNING;

import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.FileUtilities;
import nl.airsupplies.utilities.gui.progress.ProgressListener;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * Wrapper for non-persistent http connections.
 *
 * <ul><li>Protocols: http, https</li>
 * <li>Methods: GET, POST</li>
 * <li>Cookies</li>
 * <li>User agent spoofing (default = {@code "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:55.0) Gecko/20100101
 * Firefox/55.0"})</li>
 * <li>Compressions: gzip, deflate</li>
 * <li>Additional header fields: yes (but not removal of existing header fields)</li>
 * <li>Timeout (default 30s)</li>
 * <li>Progress listeners</li>
 * <li>Hard-coded header fields include {@code Accept} and {@code Accept-Language}</li></ul>
 *
 * @author Mark Jeronimus
 */
// Created 2015-10-17
public class HTTPDownloader {
	/**
	 * Equals to: {@code "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:93.0) Gecko/20100101 Firefox/93.0"}.
	 */
	public static final String USER_AGENT_FIREFOX_UBUNTU =
			"Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:93.0) Gecko/20100101 Firefox/93.0";

	/**
	 * Similar to: {@code "Mozilla/5.0 (compatible; Java/1.8.0_144)"}.
	 */
	public static final String USER_AGENT_JAVA =
			"Mozilla/5.0 (compatible; Java/" + System.getProperty("java.version") + ')';

	private static final int DEFAULT_TIMEOUT = 10_000;

	private       String                       userAgent         = USER_AGENT_FIREFOX_UBUNTU;
	private final Map<String, String>          cookies           = new LinkedHashMap<>(16);
	private       boolean                      doReferer         = true;
	private       int                          timeout           = DEFAULT_TIMEOUT;
	private final Collection<ProgressListener> progressListeners = new CopyOnWriteArraySet<>();

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
		requireNonNull(progressListener, "progressListener");

		progressListeners.add(progressListener);
	}

	public void removeProgressListener(ProgressListener progressListener) {
		requireNonNull(progressListener, "progressListener");

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
	public HTTPResponseStream openConnection(URL url, byte @Nullable [] postData, String... extraRequestProperties)
			throws IOException {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)url.openConnection();
			connection.addRequestProperty("Host", url.getHost());
			connection.addRequestProperty("User-Agent", userAgent);
			connection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			connection.addRequestProperty("Accept-Language", "en-US,en;q=0.5");
			connection.addRequestProperty("Accept-Encoding", "gzip, deflate");
			addExtraRequestProperty(connection, getCookieString(cookies));
			boolean containsConnectionHeader = addExtraRequestProperties(connection, extraRequestProperties);
			connection.addRequestProperty("Connection", "keep-alive");
			connection.addRequestProperty("DNT", "1");
			addPostDataHeader(postData, connection);

			if (doReferer) {
				connection.addRequestProperty("Referer", url.toString() + '/');
			}

			if (!containsConnectionHeader) {
				connection.addRequestProperty("Connection", "keep-alive");
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

	private static boolean addExtraRequestProperties(URLConnection connection, String[] extraRequestProperties) {
		boolean containsConnectionHeader = false;

		for (String extraRequestProperty : extraRequestProperties) {
			containsConnectionHeader |= addExtraRequestProperty(connection, extraRequestProperty);
		}

		return containsConnectionHeader;
	}

	private static boolean addExtraRequestProperty(URLConnection connection, @Nullable String s) {
		if (s == null) {
			return false;
		}

		int i = s.indexOf(": ");
		if (i == -1) {
			throw new IllegalArgumentException("Not a request property (must contain a \": \"): " + s);
		}

		String header = s.substring(0, i);
		String value  = s.substring(i + 2);

		connection.addRequestProperty(header, value);

		return header.equalsIgnoreCase("connection");
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
//			connection.setRequestProperty("Content-Type", "application/json");
			connection.addRequestProperty("Content-length", Integer.toString(postData.length));
//			connection.setRequestProperty("Content-Language", "en-US");
		}
	}

	private static void addPostData(byte @Nullable [] postData, HttpURLConnection connection) throws IOException {
		if (postData != null) {
			connection.setDoOutput(true);
			try (OutputStream os = connection.getOutputStream()) {
				os.write(postData);
			}
		}
	}

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
			throws IOException, InterruptedException {
		downloadToFile(uri.toURL(), postData, file, extraRequestProperties);
	}

	public void downloadToFile(URL url, byte @Nullable [] postData, Path file, String... extraRequestProperties)
			throws IOException, InterruptedException {
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

				downloadToFile(redirect, postData, file, extraRequestProperties);
				return;
			}

			if (stream.getResponseCode() / 100 != 2) {
				throw new IOException("Received " + stream.getResponseHeaders().get(null) + " for " + url);
			}

			FileUtilities.streamToStream(stream, out);
		} catch (IOException ex) {
			if (Files.exists(file)) {
				Logger.getGlobal().log(WARNING, "Removing partially downloaded file because of exception");
				Files.delete(file);
			}

			throw ex;
		}
	}
}
