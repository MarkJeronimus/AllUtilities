package nl.airsupplies.utilities.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Mark Jeronimus
 */
// Created 2009-06-?7
public class BitOutputStream extends FilterOutputStream {
	private int accu     = 0;
	private int bitCount = 0;

	public BitOutputStream(OutputStream out) {
		super(out);
	}

	public void write(int datagram, int numBits) throws IOException {
		accu |= datagram << bitCount;
		bitCount += numBits;

		while (bitCount >= 8) {
			out.write(accu & 0xFF);
			accu >>>= 8;
			bitCount -= 8;
		}
	}

	@Override
	public void flush() throws IOException {
		if (bitCount > 0) {
			out.write(accu & 0xFF);
			accu >>>= 8;
			bitCount -= 8;
		}
		super.flush();
	}
}
