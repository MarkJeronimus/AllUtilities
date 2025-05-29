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

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.digitalmodular.utilities.graphics.swing.progress.ProgressEvent;
import org.digitalmodular.utilities.graphics.swing.progress.ProgressListener;
import org.digitalmodular.utilities.io.UnbufferedReader;

/**
 * @author Mark Jeronimus
 */
// Created 2005-11-03
public class TCPSocket {
	private static final int defaultExpectedBodyLength = 4096;

	private Socket socket = null;

	private DataOutputStream  dataOutputStream;
	private FilterInputStream inputStream;
	private UnbufferedReader  inputReader;

	public void connect(URL url) throws IOException {
		int port = url.getPort();
		if (port == -1) {
			port = 80;
		}
		socket = new Socket(url.getHost(), port);

		socket.setSoTimeout(10000);

		dataOutputStream = new DataOutputStream(socket.getOutputStream());
		inputStream      = new BufferedInputStream(socket.getInputStream());
		inputReader      = new UnbufferedReader(inputStream);
	}

	public void request(String message) throws IOException {
		// this.dataOutputStream.writeUTF(message);
		dataOutputStream.write(message.getBytes(StandardCharsets.UTF_8));
		// this.dataOutputStream.write(new String("GET " + path + "
		// HTTP/1.0\n").getBytes());
		// this.dataOutputStream.write(new String("Host: " + host +
		// "\n").getBytes());
		// this.dataOutputStream.write(new String("Connection:
		// close\n\n").getBytes());
		// this.dataOutputStream.flush();
		// return this.dataInputStream.readUTF();
	}

	public int read(byte[] b) throws IOException {
		return inputStream.read(b);
	}

	public String readLine() throws IOException {
		return inputReader.readLine();
	}

	public String readFully(int size, ProgressListener listener) throws IOException {
		return readFully(size, listener, false);
	}

	public String readFully(int size, ProgressListener listener, boolean chunked) throws IOException {
		StringBuilder b = new StringBuilder(size < 0 ? defaultExpectedBodyLength : size);

		if (listener != null) {
			listener.progressUpdated(new ProgressEvent(this, 0, size, ""));
		}

		int chunkSize = -1;

		if (chunked) {
			chunkSize = Integer.parseInt(readLine(), 16);
		}

		int i = 0;
		int c;
		while (true) {
			if (chunkSize == 0) {
				readLine();
				chunkSize = Integer.parseInt(readLine(), 16);
				if (chunkSize == 0) {
					break;
				}
			}

			if (chunkSize != -1) {
				chunkSize--;
			}

			try {
				c = inputStream.read();

				if (c == -1) {
					break;
				}

				b.append((char)c);

				++i;
				if ((i & 1023) == 0) {
					if (listener != null) {
						listener.progressUpdated(new ProgressEvent(this, i, size, ""));
					}
				}
			} catch (SocketTimeoutException ex) {
				ex.printStackTrace();
				break;
			}
		}

		if (listener != null) {
			listener.progressUpdated(new ProgressEvent(this, size, size, ""));
		}

		return b.toString();
	}

	public void streamTo(int size, OutputStream stream, ProgressListener listener, boolean chunked) throws
	                                                                                                IOException {
		if (listener != null) {
			listener.progressUpdated(new ProgressEvent(this, 0, size, ""));
		}

		int chunkSize = -1;

		if (chunked) {
			chunkSize = Integer.parseInt(readLine(), 16);
		}

		if (size <= 0) {
			int i = 0;
			int c;
			while ((c = inputStream.read()) != -1) {
				if (chunkSize == 0) {
					readLine();
					chunkSize = Integer.parseInt(readLine(), 16);
					if (chunkSize == 0) {
						break;
					}
				}

				if (chunkSize != -1) {
					chunkSize--;
				}

				stream.write(c);

				++i;
				if ((i & 1023) == 0) {
					if (listener != null) {
						listener.progressUpdated(new ProgressEvent(this, i, size, ""));
					}
				}
			}
			if (listener != null) {
				listener.progressUpdated(new ProgressEvent(this, i, size, ""));
			}
		} else {
			for (int i = 0; i < size; i++) {
				stream.write(inputStream.read());

				if ((i & 1023) == 0) {
					if (listener != null) {
						listener.progressUpdated(new ProgressEvent(this, i, size, ""));
					}
				}
			}
			if (listener != null) {
				listener.progressUpdated(new ProgressEvent(this, size, size, ""));
			}
		}

		if (listener != null) {
			listener.progressUpdated(new ProgressEvent(this, size, size, ""));
		}
	}

	public void close() throws IOException {
		socket.close();
	}
}
