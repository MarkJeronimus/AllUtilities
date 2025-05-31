package nl.airsupplies.utilities.io;

import java.io.FilterInputStream;
import java.io.InputStream;

/**
 * @author Mark Jeronimus
 */
// Created 2015-10-17
public class InputStreamWithLength extends FilterInputStream {
	private final int length;

	public InputStreamWithLength(InputStream in, int length) {
		super(in);
		this.length = length;
	}

	public int getLength() {
		return length;
	}
}
