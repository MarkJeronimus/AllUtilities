package nl.airsupplies.utilities.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Lodewijk Pool
 */
// Created 2012-01-17 https://www.codeproject.com/Tips/315892/A-quick-and-easy-way-to-direct-Java-System-out-to
// Changed 2019-07-02 Improved
public class MultiOutputStream extends OutputStream {
	private final OutputStream[] outputStreams;

	public MultiOutputStream(OutputStream... outputStreams) {
		this.outputStreams = outputStreams.clone();
	}

	@Override
	public void write(int b) throws IOException {
		for (OutputStream out : outputStreams) {
			out.write(b);
		}
	}

	@Override
	public void write(byte[] b) throws IOException {
		for (OutputStream out : outputStreams) {
			out.write(b);
		}
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		for (OutputStream out : outputStreams) {
			out.write(b, off, len);
		}
	}

	@Override
	public void flush() throws IOException {
		for (OutputStream out : outputStreams) {
			out.flush();
		}
	}

	@Override
	public void close() throws IOException {
		for (OutputStream out : outputStreams) {
			out.close();
		}
	}
}
