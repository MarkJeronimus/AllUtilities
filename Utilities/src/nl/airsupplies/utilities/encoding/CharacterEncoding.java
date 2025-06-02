package nl.airsupplies.utilities.encoding;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * @author Mark Jeronimus
 */
// Created 2012-07-30
public abstract class CharacterEncoding {
	protected static char[]     surrogatePair = new char[2];
	protected static CharBuffer charBuffer    = CharBuffer.allocate(1);
	protected static ByteBuffer byteBuffer    = ByteBuffer.allocate(2);

	public static final CharacterEncoding jisEncoding = new JISEncoding();

	public abstract int encode(int codePoint);

	public abstract int decode(int codePoint);
}
