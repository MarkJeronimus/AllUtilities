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

package nl.airsupplies.utilities.net;

import java.awt.Desktop;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;

import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.Debug;
import nl.airsupplies.utilities.annotation.UtilityClass;
import nl.airsupplies.utilities.gui.progress.ProgressEvent;
import nl.airsupplies.utilities.gui.progress.ProgressListener;
import nl.airsupplies.utilities.io.ProgressInputStream;

/**
 * @author Mark Jeronimus
 * @deprecated in favor of {@link HTTPDownloader}
 */
@Deprecated
@UtilityClass
public final class Internet {
	private static final String FIREFOX_17_0 = "Mozilla/5.0 (Windows NT 5.1; rv:17.0) " +
	                                           "Gecko/20100101 Firefox/17.0";

	/**
	 * Requests a URL and if successful (response 200), returns the open stream.
	 * <p>
	 * Can be passed to, for example, the Tidy library, to obtain a DOM model.
	 */
	public static ProgressInputStream httpRequest(URL url,
	                                              byte @Nullable [] postData,
	                                              String... extraRequestProperties) throws IOException {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)url.openConnection();
			connection.addRequestProperty("Host", url.getHost());
			connection.addRequestProperty("User-Agent", FIREFOX_17_0);
			connection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			connection.addRequestProperty("Accept-Language", "en-US,en;q=0.5");
			connection.addRequestProperty("Accept-Encoding", "gzip, deflate");
			addExtraProperties(connection, extraRequestProperties);
			connection.addRequestProperty("Connection", "keep-alive");
			addPostDataHeader(postData, connection);

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setReadTimeout(30000);
			addPostData(postData, connection);

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

			int length = attemptGetStreamLength(connection);

			return new ProgressInputStream(in, length);
		} catch (IOException e) {
			if (connection != null) {
				connection.disconnect();
			}

			throw e;
		}
	}

	private static void addExtraProperties(HttpURLConnection connection, String[] extraRequestProperties) {
		if (extraRequestProperties != null) {
			if (extraRequestProperties.length != 1 || extraRequestProperties[0] != null) {
				for (String s : extraRequestProperties) {
					int i = s.indexOf(": ");
					if (i == -1) {
						throw new IllegalArgumentException("Not a request property (must contain \": \"): " + s);
					}

					connection.addRequestProperty(s.substring(0, i), s.substring(i + 2));
				}
			}
		}
	}

	private static void addPostDataHeader(byte @Nullable [] postData, HttpURLConnection connection)
			throws ProtocolException {
		if (postData != null) {
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.addRequestProperty("Content-length", Integer.toString(postData.length));
			connection.setRequestProperty("Content-Language", "en-US");
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

	private static InputStream getDecompressedStream(HttpURLConnection connection, InputStream in) throws IOException {
		if ("gzip".equals(connection.getHeaderField("Content-Encoding"))) {
			in = new GZIPInputStream(in);
		} else if ("deflate".equals(connection.getHeaderField("Content-Encoding"))) {
			in = new DeflaterInputStream(in);
		}
		return in;
	}

	private static int attemptGetStreamLength(HttpURLConnection connection) {
		try {
			return Integer.parseInt(connection.getHeaderField("Content-Length"));
		} catch (NumberFormatException ignored) {
			return -1;
		}
	}

	/**
	 * Open the specified URL in te default external browser.
	 */
	public static void openURL(String url) throws IOException, URISyntaxException {
		Desktop.getDesktop().browse(new URI(url));
	}

	@Deprecated
	public static @Nullable String getRequest(String host, int port, String path) {
		StringBuilder result = new StringBuilder(2048);
		try (Socket sd = new Socket(host, port)) {
			sd.setSoTimeout(10);
			try (InputStream is = sd.getInputStream()) {
				try (OutputStream os = sd.getOutputStream()) {
					os.write(("GET " + path + " HTTP/1.0\n").getBytes(StandardCharsets.UTF_8));
					os.write(("Host: " + host + '\n').getBytes(StandardCharsets.UTF_8));
					os.write("Connection: close\n\n".getBytes(StandardCharsets.UTF_8));
					os.flush();

					System.out.println("Getting data from " + path);

					byte[] b;
					int    i;
					while (true) {
						i = is.available();
						if (i > 0) {
							b = new byte[i];
							i = is.read(b);
							String s = new String(b, StandardCharsets.UTF_8);
							result.append(s);

							Debug.println(i + " (" + result.length() + ')');

							// Can be made more efficient, right?
							// System.out.println(s);
							if (s.toUpperCase().contains("</HTML") || s.contains("Location: ")) {
								break;
							}
						}

//						Thread.yield();
					}
				}
			}
		} catch (Exception ignored) {
			return null;
		}

		Debug.println("Received " + result.length() + " bytes.");
		return result.toString();
	}

	@Deprecated
	public static String download(URL url, ProgressListener listener) throws IOException {
		return download(url, listener, null, null);
	}

	@Deprecated
	public static String download(URL url,
	                              ProgressListener listener,
	                              Iterable<String> extraRequestProperties) throws IOException {
		return download(url, listener, extraRequestProperties, null);
	}

	@Deprecated
	public static String download(URL url,
	                              ProgressListener listener,
	                              @Nullable Iterable<String> extraRequestProperties,
	                              @Nullable String post) throws IOException {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)url.openConnection();
			makeHeader(url, extraRequestProperties, post, connection);

			if (post != null) {
				connection.setDoOutput(true);
				try (OutputStream os = connection.getOutputStream()) {
					os.write(post.getBytes(StandardCharsets.UTF_8));
					os.flush();
				}
			}

			if (connection.getResponseCode() >= 300) {
				throw new EOFException("HTTP returned code " + connection.getResponseCode());
			}

			if (listener != null) {
				listener.progressUpdated(new ProgressEvent(url, -1, -1, ""));
			}

			InputStream in = connection.getInputStream();

			if ("gzip".equals(connection.getHeaderField("Content-Encoding"))) {
				in = new GZIPInputStream(in);
			}

			int size = -1;
			{
				String length = connection.getHeaderField("Content-Length");
				if (length != null) {
					size = Integer.parseInt(length);
				}
			}

			if (listener != null) {
				listener.progressUpdated(new ProgressEvent(url, 0, size, ""));
			}

			try (InputStreamReader ir = new InputStreamReader(in, StandardCharsets.UTF_8)) {
				StringBuilder b = new StringBuilder(size == -1 ? 4096 : size);
				int           i = 0;
				int           c;
				while ((c = ir.read()) != -1) {
					b.append((char)c);

					if ((++i & 1023) == 0) {
						if (listener != null) {
							listener.progressUpdated(new ProgressEvent(url, i, size, ""));
						}
					}
				}

				if (listener != null) {
					listener.progressUpdated(new ProgressEvent(url, size, size, ""));
				}

				connection.disconnect();

				return b.toString();
			}
		} catch (IOException e) {
			if (connection != null) {
				connection.disconnect();
			}
			throw e;
		}
	}

	@Deprecated
	public static void streamTo(URL url, OutputStream out, ProgressListener listener) throws IOException {
		streamTo(url, out, listener, null);
	}

	@Deprecated
	public static void streamTo(URL url,
	                            OutputStream out,
	                            ProgressListener listener,
	                            @Nullable Iterable<String> extraRequestProperties) throws IOException {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)url.openConnection();
			makeHeader(url, extraRequestProperties, null, connection);

			if (listener != null) {
				listener.progressUpdated(new ProgressEvent(url, -1, -1, ""));
			}

			boolean gzipped = "gzip".equals(connection.getHeaderField("Content-Encoding"));
			try (InputStream in = gzipped
			                      ? new GZIPInputStream(connection.getInputStream())
			                      : connection.getInputStream()) {
				int size = -1;
				{
					String length = connection.getHeaderField("Content-Length");
					if (length != null) {
						size = Integer.parseInt(length);
					}
				}

				if (listener != null) {
					listener.progressUpdated(new ProgressEvent(url, 0, size, ""));
				}

				int i = 0;
				int c;
				while ((c = in.read()) != -1) {
					out.write((char)c);

					if (++i % 10000 == 0) {
						if (listener != null) {
							listener.progressUpdated(new ProgressEvent(url, i, size, ""));
						}
					}
				}

				if (listener != null) {
					listener.progressUpdated(new ProgressEvent(url, size, size, ""));
				}

				connection.disconnect();
			}
		} catch (IOException e) {
			if (connection != null) {
				connection.disconnect();
			}
			throw e;
		}
	}

	private static void makeHeader(URL url,
	                               @Nullable Iterable<String> extraRequestProperties,
	                               @Nullable String post,
	                               HttpURLConnection connection) {
		connection.addRequestProperty("Host", url.getHost());
		connection.addRequestProperty("User-Agent",
		                              "Mozilla/5.0 (Windows NT 5.1; rv:17.0) Gecko/20100101 Firefox/17.0");
		connection.addRequestProperty("Accept",
		                              "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		connection.addRequestProperty("Accept-Language", "en-US,en;q=0.5");
		connection.addRequestProperty("Accept-Encoding", "gzip, deflate");
		connection.addRequestProperty("DNT", "1");
		if (extraRequestProperties != null) {
			for (String s : extraRequestProperties) {
				int i = s.indexOf(": ");
				if (i == -1) {
					continue;
				}
				connection.addRequestProperty(s.substring(0, i), s.substring(i + 2));
			}
		}
		connection.addRequestProperty("Connection", "keep-alive");

		if (post != null) {
			connection.addRequestProperty("Content-length", Integer.toString(post.length()));
		}

		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setReadTimeout(10000);
	}
}
