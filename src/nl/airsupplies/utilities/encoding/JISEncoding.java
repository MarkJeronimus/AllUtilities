/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
