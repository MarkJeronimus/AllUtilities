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

package org.digitalmodular.utilities.io;

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
