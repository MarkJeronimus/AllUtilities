package nl.airsupplies.utilities.io;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Mark Jeronimus
 */
// Created 2010-06-?0
public class GeneralDataInputStream extends DataInputStream {
	public GeneralDataInputStream(InputStream in) {
		super(in);
	}

	public final short readShortBigEndian() throws IOException {
		int c0 = in.read();
		int c1 = in.read();
		if ((c0 | c1) < 0) {
			throw new EOFException();
		}
		return (short)(c0 << 8 | c1);
	}

	public final short readShortLittleEndian() throws IOException {
		int c0 = in.read();
		int c1 = in.read();
		if ((c0 | c1) < 0) {
			throw new EOFException();
		}
		return (short)(c1 << 8 | c0);
	}

	public final int readIntBigEndian() throws IOException {
		int c0 = in.read();
		int c1 = in.read();
		int c2 = in.read();
		int c3 = in.read();
		if ((c0 | c1 | c2 | c3) < 0) {
			throw new EOFException();
		}
		return ((c0 << 8 | c1) << 8 | c2) << 8 | c3;
	}

	public final int readIntLittleEndian() throws IOException {
		int c0 = in.read();
		int c1 = in.read();
		int c2 = in.read();
		int c3 = in.read();
		if ((c0 | c1 | c2 | c3) < 0) {
			throw new EOFException();
		}
		return ((c3 << 8 | c2) << 8 | c1) << 8 | c0;
	}

	public final long readLongBigEndian() throws IOException {
		int c0 = in.read();
		int c1 = in.read();
		int c2 = in.read();
		int c3 = in.read();
		int c4 = in.read();
		int c5 = in.read();
		int c6 = in.read();
		int c7 = in.read();
		if ((c0 | c1 | c2 | c3 | c4 | c5 | c6 | c7) < 0) {
			throw new EOFException();
		}
		return ((((((long)(c0 << 8 | c1) << 8 | c2) << 8 | c3) << 8 | c4) << 8 | c5) << 8 | c6) << 8 | c7;
	}

	public final long readLongLittleEndian() throws IOException {
		int c0 = in.read();
		int c1 = in.read();
		int c2 = in.read();
		int c3 = in.read();
		int c4 = in.read();
		int c5 = in.read();
		int c6 = in.read();
		int c7 = in.read();
		if ((c0 | c1 | c2 | c3 | c4 | c5 | c6 | c7) < 0) {
			throw new EOFException();
		}
		return ((((((long)(c7 << 8 | c6) << 8 | c5) << 8 | c4) << 8 | c3) << 8 | c2) << 8 | c1) << 8 | c0;
	}

	public final double readFloatBigEndian() throws IOException {
		return Float.intBitsToFloat(readIntBigEndian());
	}

	public final double readFloatLittleEndian() throws IOException {
		return Float.intBitsToFloat(readIntLittleEndian());
	}

	public final double readDoubleBigEndian() throws IOException {
		return Double.longBitsToDouble(readLongBigEndian());
	}

	public final double readDoubleLittleEndian() throws IOException {
		return Double.longBitsToDouble(readLongLittleEndian());
	}
}
