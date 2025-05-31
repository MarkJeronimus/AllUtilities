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
