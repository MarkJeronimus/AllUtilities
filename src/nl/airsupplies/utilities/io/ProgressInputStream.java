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

package nl.airsupplies.utilities.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import static java.util.Objects.requireNonNull;

import nl.airsupplies.utilities.gui.progress.ProgressEvent;
import nl.airsupplies.utilities.gui.progress.ProgressListener;

/**
 * An input stream that supports {@link ProgressListener}s.
 * <p>
 * It is instantiated with another {@link InputStream} and a length value, representing the (expected) number of bytes
 * in the stream, or {@code -1} if the length is unknown.
 *
 * @author Mark Jeronimus
 */
// Created 2015-10-17
public class ProgressInputStream extends FilterInputStream {
	private final List<ProgressListener> listeners = new CopyOnWriteArrayList<>();

	private final int length;
	private       int position = 0;
	private       int mark     = 0;

	public ProgressInputStream(InputStream in, int length) {
		super(in);
		this.length = length;
	}

	public int getLength() {
		return length;
	}

	public void addProgressListener(ProgressListener listener) {
		requireNonNull(listener);

		listeners.add(listener);
	}

	public void removeProgressListener(ProgressListener listener) {
		requireNonNull(listener);

		listeners.remove(listener);
	}

	@Override
	public int read() throws IOException {
		int read = super.read();

		if (read > 0) {
			position++;
			fireProgressUpdated();
		}

		return read;
	}

	@Override
	public int read(byte[] b) throws IOException {
		return read(b, 0, b.length);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int read = super.read(b, off, len);

		if (read > 0) {
			position += read;
			fireProgressUpdated();
		}

		return read;
	}

	@Override
	public long skip(long n) throws IOException {
		long skipped = super.skip(n);

		position += skipped;
		fireProgressUpdated();

		return skipped;
	}

	@Override
	public void close() throws IOException {
		super.close();

		fireProgressUpdated();
	}

	@Override
	public synchronized void mark(int readLimit) {
		super.mark(readLimit);

		mark = position;
	}

	@Override
	public synchronized void reset() throws IOException {
		super.reset();

		position = mark;
		fireProgressUpdated();
	}

	private void fireProgressUpdated() {
		for (ProgressListener listener : listeners) {
			listener.progressUpdated(new ProgressEvent(this, position, length, ""));
		}
	}
}
