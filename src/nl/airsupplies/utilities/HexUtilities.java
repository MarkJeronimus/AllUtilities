package nl.airsupplies.utilities;

import nl.airsupplies.utilities.annotation.UtilityClass;
import static nl.airsupplies.utilities.NumberUtilities.DIGITS;

/**
 * @author Mark Jeronimus
 */
// Created 2019-08-08
@UtilityClass
public final class HexUtilities {
	public static String toString(int i) {
		char[] buf     = new char[33];
		int    charPos = 32;

		boolean negative = i < 0;
		if (!negative) {
			i = -i;
		}

		while (i <= -16) {
			buf[charPos] = DIGITS[-(i % 16)];
			charPos--;
			i /= 16;
		}
		buf[charPos] = DIGITS[-i];

		if (negative) {
			--charPos;
			buf[charPos] = '-';
		}

		return new String(buf, charPos, 33 - charPos);
	}

	public static String toUnsignedString(int v) {
		char[] buf = new char[8];

		for (int i = 7; i >= 0; i--) {
			buf[i] = DIGITS[v & 0xF];
			v >>>= 4;
		}

		return new String(buf);
	}

	public static String toString(long l) {
		char[] buf     = new char[65];
		int    charPos = 64;

		boolean negative = l < 0;
		if (!negative) {
			l = -l;
		}

		while (l <= -16) {
			buf[charPos] = DIGITS[(int)-(l % 16)];
			charPos--;
			l /= 16;
		}
		buf[charPos] = DIGITS[(int)-l];

		if (negative) {
			--charPos;
			buf[charPos] = '-';
		}

		return new String(buf, charPos, 65 - charPos);
	}

	public static String toUnsignedString(long l) {
		char[] buf     = new char[64];
		int    charPos = 63;

		while (l != 0) {
			buf[charPos] = DIGITS[(int)l & 0xF];
			charPos--;
			l >>>= 4;
		}

		return new String(buf, charPos, 64 - charPos);
	}

	public static String toUnsignedWordString(byte b) {
		int i = b & 0xFF;
		char[] out = {DIGITS[i >>> 4],
		              DIGITS[i & 0xF]};
		return new String(out);
	}

	public static String toUnsignedWordString(short sh) {
		int i = sh & 0xFFFF;
		char[] out = {DIGITS[i >>> 12],
		              DIGITS[i >>> 8 & 0xF],
		              DIGITS[i >>> 4 & 0xF],
		              DIGITS[i & 0xF]};
		return new String(out);
	}

	public static String toColorString(int i) {
		char[] out = {DIGITS[i >>> 20 & 0xF],
		              DIGITS[i >>> 16 & 0xF],
		              DIGITS[i >>> 12 & 0xF],
		              DIGITS[i >>> 8 & 0xF],
		              DIGITS[i >>> 4 & 0xF],
		              DIGITS[i & 0xF]};
		return new String(out);
	}

	public static String toColorStringWithAlpha(int i) {
		char[] out = {DIGITS[i >>> 28 & 0xF],
		              DIGITS[i >>> 24 & 0xF],
		              DIGITS[i >>> 20 & 0xF],
		              DIGITS[i >>> 16 & 0xF],
		              DIGITS[i >>> 12 & 0xF],
		              DIGITS[i >>> 8 & 0xF],
		              DIGITS[i >>> 4 & 0xF],
		              DIGITS[i & 0xF]};
		return new String(out);
	}

	public static String toUnsignedWordString(int i) {
		char[] out = {DIGITS[i >>> 28],
		              DIGITS[i >>> 24 & 0xF],
		              DIGITS[i >>> 20 & 0xF],
		              DIGITS[i >>> 16 & 0xF],
		              DIGITS[i >>> 12 & 0xF],
		              DIGITS[i >>> 8 & 0xF],
		              DIGITS[i >>> 4 & 0xF],
		              DIGITS[i & 0xF]};
		return new String(out);
	}

	public static String toUnsignedWordString(long l) {
		int hi = (int)(l >>> 32);
		int lo = (int)l;
		char[] out = {DIGITS[hi >>> 28],
		              DIGITS[hi >>> 24 & 0xF],
		              DIGITS[hi >>> 20 & 0xF],
		              DIGITS[hi >>> 16 & 0xF],
		              DIGITS[hi >>> 12 & 0xF],
		              DIGITS[hi >>> 8 & 0xF],
		              DIGITS[hi >>> 4 & 0xF],
		              DIGITS[hi & 0xF],
		              DIGITS[lo >>> 28],
		              DIGITS[lo >>> 24 & 0xF],
		              DIGITS[lo >>> 20 & 0xF],
		              DIGITS[lo >>> 16 & 0xF],
		              DIGITS[lo >>> 12 & 0xF],
		              DIGITS[lo >>> 8 & 0xF],
		              DIGITS[lo >>> 4 & 0xF],
		              DIGITS[lo & 0xF]};
		return new String(out);
	}

	public static String byteArrayToHexString(byte[] array) {
		StringBuilder out = new StringBuilder(array.length * 2);
		for (byte b : array) {
			out.append(toUnsignedWordString(b));
		}
		return out.toString().toUpperCase();
	}

	public static String toHexFloat(double value) {
		long bits = Double.doubleToRawLongBits(value);

		boolean negative = bits < 0;
		bits &= 0x7FFFFFFFFFFFFFFFL;

		int exponent = (int)(bits >>> 52);
		bits &= 0xFFFFFFFFFFFFFL;

		if (exponent == 2047) {
			return bits != 0 ? "NaN" : negative ? "-Infinity" : "Infinity";
		}

		if (exponent == 0) {
			if (bits == 0) {
				return negative ? "-0" : "0";
			}
			exponent = -1022;
			while (bits <= 0xFFFFFFFFFFFFFL) {
				bits <<= 1;
				exponent--;
			}
		} else {
			exponent += -1023;
			bits |= 0x10000000000000L;
		}
		bits <<= exponent & 3;
		exponent >>= 2;

		String out = Long.toHexString(bits).toUpperCase();

		if (exponent < -3 || exponent > 6) {
			out = removeTrailingZeroes(out);
			if (out.length() > 1) {
				out = out.charAt(0) + '.' + out.substring(1);
			}
			out += "e" + exponent;
		} else if (exponent >= 0) {
			if (out.length() > 1) {
				out = out.substring(0, exponent + 1) + '.' + out.substring(exponent + 1);
			}
			out = removeTrailingZeroes(out);
		} else {
			out = removeTrailingZeroes(out);
			for (int i = exponent + 1; i < 0; i++) {
				out = '0' + out;
			}
			out = "0." + out;
		}

		return negative ? '-' + out : out;
	}

	private static String removeTrailingZeroes(String s) {
		for (int i = s.length() - 1; i >= 0; i--) {
			if (s.charAt(i) != '0') {
				if (s.charAt(i) == '.') {
					return s.substring(0, i + 2);
				}
				return s.substring(0, i + 1);
			}
		}
		return s.substring(0, 1);
	}
}
