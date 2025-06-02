package nl.airsupplies.utilities.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Mark Jeronimus
 */
// Created 2009-06-?7
public class HexInputStream extends InputStream {
	private final String string;
	int pos = 0;

	public HexInputStream(String string) {
		this.string = string;
	}

	@Override
	public int read() throws IOException {
		if (pos >= string.length()) {
			return -1;
		}

		return Integer.parseInt(string.substring(pos, pos += 2), 16);
	}
}
