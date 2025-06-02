package nl.airsupplies.utilities.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * @author Mark Jeronimus
 */
// Created 2009-05-?6
public class UnbufferedReader extends Reader {
	private static final int defaultExpectedLineLength = 80;

	private InputStream in;

	public UnbufferedReader(InputStream in) {
		super(in);

		if (!in.markSupported()) {
			throw new IllegalArgumentException(
					"Mark not supported on given stream. Suggest wrapping it with BufferedInputStream.");
		}

		this.in = in;
	}

	/**
	 * Checks to make sure that the stream has not been closed
	 */
	private void ensureOpen() throws IOException {
		if (in == null) {
			throw new IOException("Stream closed");
		}
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		while (true) {

		}
	}

	/**
	 * Reads a line of text. A line is considered to be terminated by any one of a line feed ('\n'), a carriage return
	 * ('\r'), or a carriage return followed immediately by a linefeed.
	 *
	 * @return A String containing the contents of the line, not including any line-termination characters, or null if
	 * the end of the stream has been reached
	 * @throws IOException If an I/O error occurs
	 */
	public String readLine() throws IOException {
		StringBuilder s = new StringBuilder(defaultExpectedLineLength);

		synchronized (lock) {
			ensureOpen();

			int c;
			while (true) {
				c = in.read();
				if (c == -1) {
					return s.length() == 0 ? null : s.toString();
				}

				if (c == '\r' || c == '\n') {
					break;
				}

				s.append((char)c);
			}

			if (c == '\r') {
				in.mark(1);
				if (in.read() != '\n') {
					in.reset();
				}
			}

			return s.toString();
		}
	}

	@Override
	public void close() throws IOException {
		synchronized (lock) {
			if (in == null) {
				return;
			}
			in.close();
			in = null;
		}
	}
}
