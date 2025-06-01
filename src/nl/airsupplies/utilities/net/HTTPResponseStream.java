package nl.airsupplies.utilities.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import nl.airsupplies.utilities.gui.progress.ProgressEvent;
import nl.airsupplies.utilities.gui.progress.ProgressListener;
import nl.airsupplies.utilities.io.InputStreamWithLength;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireBetween;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2015-10-17
public class HTTPResponseStream extends InputStreamWithLength {
	private final Collection<ProgressListener> listeners = new CopyOnWriteArraySet<>();

	private final int                       responseCode;
	private final Map<String, List<String>> responseHeaders;

	private int position = 0;

	public HTTPResponseStream(URL url,
	                          InputStream in,
	                          int responseCode,
	                          Map<String, List<String>> responseHeaders,
	                          int length) {
		super(in, length);
		this.responseCode    = requireBetween(100, 599, responseCode, "responseCode");
		this.responseHeaders = requireNonNull(responseHeaders, "responseHeaders");
	}

	public int getResponseCode() {
		return responseCode;
	}

	@SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
	public Map<String, List<String>> getResponseHeaders() {
		return responseHeaders;
	}

	@Override
	public synchronized int read() throws IOException {
		int read = super.read();

		if (read > 0) {
			position++;
			fireUpdated();
		}

		return read;
	}

	@Override
	public synchronized int read(byte[] b) throws IOException {
		return read(b, 0, b.length);
	}

	@Override
	public synchronized int read(byte[] b, int off, int len) throws IOException {
		int read = super.read(b, off, len);

		if (read > 0) {
			position += read;
			fireUpdated();
		}

		return read;
	}

	@Override
	public synchronized long skip(long n) throws IOException {
		long skipped = super.skip(n);

		position += skipped;
		fireUpdated();

		return skipped;
	}

	@Override
	public synchronized void close() throws IOException {
		super.close();

		if (getLength() == -1) {
			fireCompleted();
		}
	}

	@Override
	public synchronized void reset() throws IOException {
		throw new IOException("mark/reset not supported");
	}

	public synchronized void addProgressListener(ProgressListener listener) {
		requireNonNull(listener, "listener");

		listeners.add(listener);
	}

	public synchronized void removeProgressListener(ProgressListener listener) {
		requireNonNull(listener, "listener");

		listeners.remove(listener);
	}

	private void fireUpdated() {
		String message = "Downloading... " + (int)(100.0f * position / getLength() + 0.5f) + '%';

		ProgressEvent event = new ProgressEvent(this, position, getLength(), message);
		listeners.forEach(listener -> listener.progressUpdated(event));
	}

	private void fireCompleted() {
		ProgressEvent dummyEvent = new ProgressEvent(this, position, getLength(), "");
		String        message    = dummyEvent.isComplete() ? "Download Complete" : "Download Aborted";

		ProgressEvent event = new ProgressEvent(this, position, getLength(), message);
		listeners.forEach(listener -> listener.progressCompleted(event));
	}
}
