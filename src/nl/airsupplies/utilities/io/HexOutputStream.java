package nl.airsupplies.utilities.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Mark Jeronimus
 */
// Created 2009-06-?7
public class HexOutputStream extends FilterOutputStream {
	public HexOutputStream(OutputStream out) {
		super(out);
	}

	@Override
	public void write(int b) throws IOException {
		int a = b >>> 4;
		b &= 15;

		out.write(a + (a >= 10 ? 55 : 48));
		out.write(b + (b >= 10 ? 55 : 48));
	}
}
