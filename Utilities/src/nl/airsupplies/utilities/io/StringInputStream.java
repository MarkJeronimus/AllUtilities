package nl.airsupplies.utilities.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Mark Jeronimus
 */
// Created 2007-05-18
public class StringInputStream extends InputStream {
	private final char[] data;

	private int pos  = 0;
	private int mark = -1;

	public StringInputStream(char[] data) {
		this.data = data;
	}

	/**
	 * Convenience method. Equal to calling
	 *
	 * <pre>
	 * new StringInputStream(data.toCharArray())
	 * </pre>
	 */
	public StringInputStream(String data) {
		this(data.toCharArray());
	}

	public int getPosition() {
		return pos;
	}

	public int getMark() {
		return mark;
	}

	public String getSubString(int start, int end) {
		return new String(data, start, end - start);
	}

	@Override
	public int read() {
		if (pos == data.length) {
			return -1;
		}
		char c = data[pos];
		pos++;
		return c;
	}

	@Override
	public int available() {
		return data.length - pos;
	}

	@Override
	public synchronized void mark(int readLimit) {
		mark = pos;
	}

	@Override
	public boolean markSupported() {
		return true;
	}

	@Override
	public synchronized void reset() throws IOException {
		if (pos > data.length) {
			throw new IOException("Mark is invalidated");
		}
		pos = mark;
	}

	@Override
	public long skip(long n) {
		if (n < -pos) {
			n = -pos;
		} else if (n > available()) {
			n = available();
		}
		pos += n;
		return n;
	}
}
