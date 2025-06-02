package nl.airsupplies.utilities.encoding;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * @author Mark Jeronimus
 */
// Created 2012-07-30
public class JISEncoding extends CharacterEncoding {
	protected static Charset charset = Charset.forName("x-JIS0208");

	protected JISEncoding() {
	}

	@Override
	public int encode(int codePoint) {
		if (codePoint <= 0x7F) {
			return codePoint;
		}

		if (codePoint >= 0xFF61 && codePoint <= 0xFF9F) {
			return codePoint - 0xFEC0;
		}

		charBuffer.clear();
		if (Character.isHighSurrogate((char)codePoint)) {
			Character.toChars(codePoint, surrogatePair, 0);
			charBuffer.put(surrogatePair);
		} else {
			charBuffer.put((char)codePoint);
		}
		charBuffer.rewind();

		ByteBuffer out = charset.encode(charBuffer);

		int result = 0;
		while (out.hasRemaining()) {
			result = result << 8 | out.get() & 0xFF; // Big endian.
		}

		return result;
	}

	@Override
	public int decode(int codePoint) {
		if (codePoint <= 0x7F) {
			return codePoint;
		}

		if (codePoint >= 0xA1 && codePoint <= 0xDF) {
			return codePoint + 0xFEC0;
		}

		byteBuffer.clear();
		byteBuffer.put((byte)(codePoint >> 8 & 0xFF));
		byteBuffer.put((byte)(codePoint & 0xFF));
		byteBuffer.rewind();

		CharBuffer out = charset.decode(byteBuffer);

		int result = 0;
		while (out.hasRemaining()) {
			result = result << 16 | out.get(); // Big endian.
		}

		return result;
	}
}
