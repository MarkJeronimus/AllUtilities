/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2018 Mark Jeronimus. All Rights Reversed.
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
 * along with AllUtilities. If not, see <http://www.gnu.org/licenses/>.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.digitalmodular.utilities;

/**
 * @author Mark Jeronimus
 */
// Created 2016-04-04
public enum NumberConversionUtilities {
	;

	/**
	 * Returns the {@code long} value of the source {@code byte[]} in little endian byte order. Only the first
	 * 8 bytes of the source are used.
	 */
	public static long rawBytesToLong(byte[] src) {
		return src[0] & 0xFF | (src[1] & 0xFF) << 8 | (src[2] & 0xFF) << 16 | (long)(src[3] & 0xFF) << 24
		       | (long)(src[4] & 0xFF) << 32 | (long)(src[5] & 0xFF) << 40 | (long)(src[6] & 0xFF) << 48
		       | (long)(src[7] & 0xFF) << 56;
	}

	/**
	 * Returns the {@code long} value of the source {@code byte[]} in little endian byte order. Only 8 bytes
	 * of the source are used starting at the given position.
	 */
	public static long rawBytesToLong(byte[] src, int srcPos) {
		return src[srcPos] & 0xFF | (src[srcPos + 1] & 0xFF) << 8 | (src[srcPos + 2] & 0xFF) << 16
		       | (long)(src[srcPos + 3] & 0xFF) << 24 | (long)(src[srcPos + 4] & 0xFF) << 32
		       | (long)(src[srcPos + 5] & 0xFF) << 40 | (long)(src[srcPos + 6] & 0xFF) << 48
		       | (long)(src[srcPos + 7] & 0xFF) << 56;
	}

	/**
	 * Converts a {@code byte[]} to a {@code long[]} in little endian byte order. Only multiples of 8 bytes of
	 * the source are used.
	 */
	public static void rawBytesToLongs(byte[] src, long[] dst) {
		for (int i = 0; i < dst.length; i++) {
			dst[i] = rawBytesToLong(src, i * 8);
		}
	}

	/**
	 * Converts a {@code byte[]} to a {@code long[]} in little endian byte order. Only multiples of 8 bytes of
	 * the source are used starting at the given position.
	 */
	public static void rawBytesToLongs(byte[] src, int srcPos, long[] dst, int dstPos, int count) {
		for (int i = 0; i < count; i++) {
			dst[i + dstPos] = rawBytesToLong(src, i * 8 + srcPos);
		}
	}

	/**
	 * Converts a {@code long} to a {@code byte[]} in little endian byte order. Only the first 8 bytes of the
	 * destination are written to.
	 */
	public static void longToRawBytes(long src, byte[] dst) {
		dst[0] = (byte)src;
		dst[1] = (byte)(src >> 8);
		dst[2] = (byte)(src >> 16);
		dst[3] = (byte)(src >> 24);
		dst[4] = (byte)(src >> 32);
		dst[5] = (byte)(src >> 40);
		dst[6] = (byte)(src >> 48);
		dst[7] = (byte)(src >> 56);
	}

	/**
	 * Converts a {@code long} to a {@code byte[]} in little endian byte order. Only the first 8 bytes of the
	 * destination are written to starting at the given position.
	 */
	public static void longToRawBytes(long src, byte[] dst, int srcPos) {
		dst[srcPos + 0] = (byte)src;
		dst[srcPos + 1] = (byte)(src >> 8);
		dst[srcPos + 2] = (byte)(src >> 16);
		dst[srcPos + 3] = (byte)(src >> 24);
		dst[srcPos + 4] = (byte)(src >> 32);
		dst[srcPos + 5] = (byte)(src >> 40);
		dst[srcPos + 6] = (byte)(src >> 48);
		dst[srcPos + 7] = (byte)(src >> 56);
	}

	/**
	 * Converts a {@code long[]} to a {@code byte[]} in little endian byte order. Only multiples of 8 bytes of
	 * the destination are written to.
	 */
	public static void longsToRawBytes(long[] src, byte[] dst) {
		for (int i = 0; i < src.length; i++) {
			longToRawBytes(src[i], dst, i * 8);
		}
	}

	/**
	 * Converts a {@code long[]} to a {@code byte[]} in little endian byte order. Only multiples of 8 bytes of
	 * the destination are written to starting at the given position.
	 */
	public static void longsToRawBytes(long[] src, int srcPos, byte[] dst, int dstPos, int count) {
		for (int i = 0; i < count; i++) {
			longToRawBytes(src[i + srcPos], dst, i * 8 + dstPos);
		}
	}

	/**
	 * Returns the {@code int} value of the source {@code byte[]} in little endian byte order. Only the first
	 * 4 bytes of the source are used.
	 */
	public static int rawBytesToInt(byte[] src) {
		return src[0] & 0xFF | (src[1] & 0xFF) << 8 | (src[2] & 0xFF) << 16 | (src[3] & 0xFF) << 24;
	}

	/**
	 * Returns the {@code int} value of the source {@code byte[]} in little endian byte order. Only 4 bytes of
	 * the source are used starting at the given position.
	 */
	public static int rawBytesToInt(byte[] src, int srcPos) {
		return src[srcPos] & 0xFF | (src[srcPos + 1] & 0xFF) << 8 | (src[srcPos + 2] & 0xFF) << 16
		       | (src[srcPos + 3] & 0xFF) << 24;
	}

	/**
	 * Converts a {@code byte[]} to an {@code int[]} in little endian byte order. Only multiples of 4 bytes of
	 * the source are used.
	 */
	public static void rawBytesToInts(byte[] src, int[] dst) {
		for (int i = 0; i < dst.length; i++) {
			dst[i] = rawBytesToInt(src, i * 4);
		}
	}

	/**
	 * Converts a {@code byte[]} to an {@code int[]} in little endian byte order. Only multiples of 4 bytes of
	 * the source are used starting at the given position.
	 */
	public static void rawBytesToInts(byte[] src, int srcPos, int[] dst, int dstPos, int count) {
		for (int i = 0; i < count; i++) {
			dst[i + dstPos] = rawBytesToInt(src, i * 4 + srcPos);
		}
	}

	/**
	 * Converts an {@code int} to a {@code byte[]} in little endian byte order. Only the first 4 bytes of the
	 * destination are written to.
	 */
	public static void intToRawBytes(int src, byte[] dst) {
		dst[0] = (byte)src;
		dst[1] = (byte)(src >> 8);
		dst[2] = (byte)(src >> 16);
		dst[3] = (byte)(src >> 24);
	}

	/**
	 * Converts an {@code int} to a {@code byte[]} in little endian byte order. Only the first 4 bytes of the
	 * destination are written to starting at the given position.
	 */
	public static void intToRawBytes(int src, byte[] dst, int srcPos) {
		dst[srcPos + 0] = (byte)src;
		dst[srcPos + 1] = (byte)(src >> 8);
		dst[srcPos + 2] = (byte)(src >> 16);
		dst[srcPos + 3] = (byte)(src >> 24);
	}

	/**
	 * Converts an {@code int[]} to a {@code byte[]} in little endian byte order. Only multiples of 4 bytes of
	 * the destination are written to.
	 */
	public static void intsToRawBytes(int[] src, byte[] dst) {
		for (int i = 0; i < src.length; i++) {
			intToRawBytes(src[i], dst, i * 4);
		}
	}

	/**
	 * Converts an {@code int[]} to a {@code byte[]} in little endian byte order. Only multiples of 4 bytes of
	 * the destination are written to starting at the given position.
	 */
	public static void intsToRawBytes(int[] src, int srcPos, byte[] dst, int dstPos, int count) {
		for (int i = 0; i < count; i++) {
			intToRawBytes(src[i + srcPos], dst, i * 4 + dstPos);
		}
	}

	/**
	 * Returns the {@code short} value of the source {@code byte[]} in little endian byte order. Only the
	 * first 2 bytes of the source are used.
	 */
	public static short rawBytesToShort(byte[] src) {
		return (short)(src[0] & 0xFF | (src[1] & 0xFF) << 8);
	}

	/**
	 * Returns the {@code short} value of the source {@code byte[]} in little endian byte order. Only 2 bytes
	 * of the source are used starting at the given position.
	 */
	public static short rawBytesToShort(byte[] src, int srcPos) {
		return (short)(src[srcPos] & 0xFF | (src[srcPos + 1] & 0xFF) << 8);
	}

	/**
	 * Converts a {@code byte[]} to a {@code short[]} in little endian byte order. Only multiples of 2 bytes
	 * of the source are used.
	 */
	public static void rawBytesToShorts(byte[] src, short[] dst) {
		for (int i = 0; i < dst.length; i++) {
			dst[i] = rawBytesToShort(src, i * 2);
		}
	}

	/**
	 * Converts a {@code byte[]} to a {@code short[]} in little endian byte order. Only multiples of 2 bytes
	 * of the source are used starting at the given position.
	 */
	public static void rawBytesToShorts(byte[] src, int srcPos, short[] dst, int dstPos, int count) {
		for (int i = 0; i < count; i++) {
			dst[i + dstPos] = rawBytesToShort(src, i * 2 + srcPos);
		}
	}

	/**
	 * Converts a {@code short} to a {@code byte[]} in little endian byte order. Only the first 2 bytes of the
	 * destination are written to.
	 */
	public static void shortToRawBytes(short src, byte[] dst) {
		dst[0] = (byte)src;
		dst[1] = (byte)(src >> 8);
	}

	/**
	 * Converts a {@code short} to a {@code byte[]} in little endian byte order. Only the first 2 bytes of the
	 * destination are written to starting at the given position.
	 */
	public static void shortToRawBytes(short src, byte[] dst, int srcPos) {
		dst[srcPos + 0] = (byte)src;
		dst[srcPos + 1] = (byte)(src >> 8);
	}

	/**
	 * Converts a {@code short[]} to a {@code byte[]} in little endian byte order. Only multiples of 2 bytes
	 * of the destination are written to.
	 */
	public static void shortsToRawBytes(short[] src, byte[] dst) {
		for (int i = 0; i < src.length; i++) {
			shortToRawBytes(src[i], dst, i * 2);
		}
	}

	/**
	 * Converts a {@code short[]} to a {@code byte[]} in little endian byte order. Only multiples of 2 bytes
	 * of the destination are written to starting at the given position.
	 */
	public static void shortsToRawBytes(short[] src, int srcPos, byte[] dst, int dstPos, int count) {
		for (int i = 0; i < count; i++) {
			shortToRawBytes(src[i + srcPos], dst, i * 2 + dstPos);
		}
	}

	/**
	 * Returns the {@code float} value of the source {@code byte[]} in little endian byte order. Only the
	 * first 4 bytes of the source are used.
	 */
	public static float rawBytesToFloat(byte[] src) {
		return Float.intBitsToFloat(rawBytesToInt(src));
	}

	/**
	 * Returns the {@code float} value of the source {@code byte[]} in little endian byte order. Only 4 bytes
	 * of the source are used starting at the given position.
	 */
	public static float rawBytesToFloat(byte[] src, int srcPos) {
		return Float.intBitsToFloat(rawBytesToInt(src, srcPos));
	}

	/**
	 * Converts a {@code byte[]} to a {@code float[]} in little endian byte order. Only multiples of 4 bytes
	 * of the source are used.
	 */
	public static void rawBytesToFloats(byte[] src, float[] dst) {
		for (int i = 0; i < dst.length; i++) {
			dst[i] = rawBytesToFloat(src, i * 4);
		}
	}

	/**
	 * Converts a {@code byte[]} to a {@code float[]} in little endian byte order. Only multiples of 4 bytes
	 * of the source are used starting at the given position.
	 */
	public static void rawBytesToFloats(byte[] src, int srcPos, float[] dst, int dstPos, int count) {
		for (int i = 0; i < count; i++) {
			dst[i + dstPos] = rawBytesToFloat(src, i * 4 + srcPos);
		}
	}

	/**
	 * Converts a {@code float} to a {@code byte[]} in little endian byte order. Only the first 4 bytes of the
	 * destination are written to.
	 */
	public static void floatToRawBytes(float src, byte[] dst) {
		intToRawBytes(Float.floatToRawIntBits(src), dst);
	}

	/**
	 * Converts a {@code float} to a {@code byte[]} in little endian byte order. Only the first 4 bytes of the
	 * destination are written to starting at the given position.
	 */
	public static void floatToRawBytes(float src, byte[] dst, int srcPos) {
		intToRawBytes(Float.floatToRawIntBits(src), dst, srcPos);
	}

	/**
	 * Converts a {@code float[]} to a {@code byte[]} in little endian byte order. Only multiples of 4 bytes
	 * of the destination are written to.
	 */
	public static void floatsToRawBytes(float[] src, byte[] dst) {
		for (int i = 0; i < src.length; i++) {
			floatToRawBytes(src[i], dst, i * 4);
		}
	}

	/**
	 * Converts a {@code float[]} to a {@code byte[]} in little endian byte order. Only multiples of 4 bytes
	 * of the destination are written to starting at the given position.
	 */
	public static void floatsToRawBytes(float[] src, int srcPos, byte[] dst, int dstPos, int count) {
		for (int i = 0; i < count; i++) {
			floatToRawBytes(src[i + srcPos], dst, i * 4 + dstPos);
		}
	}

	/**
	 * Returns the {@code double} value of the source {@code byte[]} in little endian byte order. Only the
	 * first 8 bytes of the source are used.
	 */
	public static double rawBytesToDouble(byte[] src) {
		return Double.longBitsToDouble(rawBytesToLong(src));
	}

	/**
	 * Returns the {@code double} value of the source {@code byte[]} in little endian byte order. Only 8 bytes
	 * of the source are used starting at the given position.
	 */
	public static double rawBytesToDouble(byte[] src, int srcPos) {
		return Double.longBitsToDouble(rawBytesToLong(src, srcPos));
	}

	/**
	 * Converts a {@code byte[]} to a {@code double[]} in little endian byte order. Only multiples of 8 bytes
	 * of the source are used.
	 */
	public static void rawBytesToDoubles(byte[] src, double[] dst) {
		for (int i = 0; i < dst.length; i++) {
			dst[i] = rawBytesToDouble(src, i * 8);
		}
	}

	/**
	 * Converts a {@code byte[]} to a {@code double[]} in little endian byte order. Only multiples of 8 bytes
	 * of the source are used starting at the given position.
	 */
	public static void rawBytesToDoubles(byte[] src, int srcPos, double[] dst, int dstPos, int count) {
		for (int i = 0; i < count; i++) {
			dst[i + dstPos] = rawBytesToDouble(src, i * 8 + srcPos);
		}
	}

	/**
	 * Converts a {@code double} to a {@code byte[]} in little endian byte order. Only the first 8 bytes of
	 * the destination are written to.
	 */
	public static void doubleToRawBytes(double src, byte[] dst) {
		longToRawBytes(Double.doubleToRawLongBits(src), dst);
	}

	/**
	 * Converts a {@code double} to a {@code byte[]} in little endian byte order. Only the first 8 bytes of
	 * the destination are written to starting at the given position.
	 */
	public static void doubleToRawBytes(double src, byte[] dst, int srcPos) {
		longToRawBytes(Double.doubleToRawLongBits(src), dst, srcPos);
	}

	/**
	 * Converts a {@code double[]} to a {@code byte[]} in little endian byte order. Only multiples of 8 bytes
	 * of the destination are written to.
	 */
	public static void doublesToRawBytes(double[] src, byte[] dst) {
		for (int i = 0; i < src.length; i++) {
			doubleToRawBytes(src[i], dst, i * 8);
		}
	}

	/**
	 * Converts a {@code double[]} to a {@code byte[]} in little endian byte order. Only multiples of 8 bytes
	 * of the destination are written to starting at the given position.
	 */
	public static void doublesToRawBytes(double[] src, int srcPos, byte[] dst, int dstPos, int count) {
		for (int i = 0; i < count; i++) {
			doubleToRawBytes(src[i + srcPos], dst, i * 8 + dstPos);
		}
	}
}
