package nl.airsupplies.utilities.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireBetween;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * An input stream wrapper where even {@link #read(byte[], int, int)} blocks.
 *
 * @author Mark Jeronimus
 */
// Created 2017-07-04
public class BlockingInputStream extends FilterInputStream {
	public BlockingInputStream(InputStream in) {
		super(in);
	}

	// Copied and modified from PipedInoutStream.read(byte[], off, len)
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		requireNonNull(b, "b");
		requireBetween(0, b.length - 1, off, "off");
		requireBetween(0, b.length - off, len, "len");

		if (len == 0) {
			return 0;
		}

		/* possibly wait on the first character */
		int c = read();
		if (c < 0) {
			return -1;
		}

		int ptr = off;

		b[ptr] = (byte)c;
		ptr++;

		int end = off + len;
		while (ptr < end) {
			c = read();
			if (c < 0) {
				break;
			}

			b[ptr] = (byte)c;
			ptr++;
		}

		return ptr - off;
	}

	@Override
	public String toString() {
		return in.toString();
	}
}
