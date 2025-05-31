package nl.airsupplies.utilities.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Mark Jeronimus
 */
// Created 2009-06-?6
public class BitInputStream extends FilterInputStream {
	int pos    = 8;
	int buffer = 0;

	public BitInputStream(InputStream in) {
		super(in);
	}

	@Override
	public int read() throws IOException {
		if (pos == 8) {
			buffer = in.read();

			if (buffer == -1) {
				return -1;
			}

			pos = 0;
		}

		int bit = buffer & 1;
		buffer >>>= 1;
		pos++;

		return bit;
	}
}
