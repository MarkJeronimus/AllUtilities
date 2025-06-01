package nl.airsupplies.utilities;

import java.util.Arrays;

import nl.airsupplies.utilities.annotation.UtilityClass;
import static nl.airsupplies.utilities.NumberUtilities.RADIX_DIGITS;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireBetween;

/**
 * @author Mark Jeronimus
 */
// Created 2018-05-17
@UtilityClass
public final class HexUtilities {
	public static String toUnsignedHex(byte b) {
		int i = b & 0xFF;
		char[] out = {RADIX_DIGITS.charAt(i >>> 4),
		              RADIX_DIGITS.charAt(i & 0xF)};
		return new String(out);
	}

	public static String toUnsignedHex(short sh) {
		int i = sh & 0xFFFF;
		return new String(new char[]{RADIX_DIGITS.charAt(i >>> 12),
		                             RADIX_DIGITS.charAt(i >>> 8 & 0xF),
		                             RADIX_DIGITS.charAt(i >>> 4 & 0xF),
		                             RADIX_DIGITS.charAt(i & 0xF)});
	}

	public static String toColorString(int i) {
		return new String(new char[]{RADIX_DIGITS.charAt(i >>> 20 & 0xF),
		                             RADIX_DIGITS.charAt(i >>> 16 & 0xF),
		                             RADIX_DIGITS.charAt(i >>> 12 & 0xF),
		                             RADIX_DIGITS.charAt(i >>> 8 & 0xF),
		                             RADIX_DIGITS.charAt(i >>> 4 & 0xF),
		                             RADIX_DIGITS.charAt(i & 0xF)});
	}

	public static String toUnsignedHex(int i) {
		return new String(new char[]{RADIX_DIGITS.charAt(i >>> 28),
		                             RADIX_DIGITS.charAt(i >>> 24 & 0xF),
		                             RADIX_DIGITS.charAt(i >>> 20 & 0xF),
		                             RADIX_DIGITS.charAt(i >>> 16 & 0xF),
		                             RADIX_DIGITS.charAt(i >>> 12 & 0xF),
		                             RADIX_DIGITS.charAt(i >>> 8 & 0xF),
		                             RADIX_DIGITS.charAt(i >>> 4 & 0xF),
		                             RADIX_DIGITS.charAt(i & 0xF)});
	}

	public static String toUnsignedHexWithSpaces(int value) {
		return toUnsignedHexWithSpaces(value, 4);
	}

	/**
	 * Converts N least significant octets of an int to hexadecimal, separated by spaces.
	 */
	public static String toUnsignedHexWithSpaces(int value, int len) {
		requireBetween(1, 4, len, "len");

		StringBuilder sb = new StringBuilder(len * 3 - 1);
		for (int i = len - 1; i >= 0; i--) {
			if (i > 0) {
				sb.append(' ');
			}

			appendHexByte(sb, value >>> i * 8);
		}

		return sb.toString();
	}

	public static String toUnsignedHex(long l) {
		int hi = (int)(l >>> 32);
		int lo = (int)l;
		return new String(new char[]{RADIX_DIGITS.charAt(hi >>> 28),
		                             RADIX_DIGITS.charAt(hi >>> 24 & 0xF),
		                             RADIX_DIGITS.charAt(hi >>> 20 & 0xF),
		                             RADIX_DIGITS.charAt(hi >>> 16 & 0xF),
		                             RADIX_DIGITS.charAt(hi >>> 12 & 0xF),
		                             RADIX_DIGITS.charAt(hi >>> 8 & 0xF),
		                             RADIX_DIGITS.charAt(hi >>> 4 & 0xF),
		                             RADIX_DIGITS.charAt(hi & 0xF),
		                             RADIX_DIGITS.charAt(lo >>> 28),
		                             RADIX_DIGITS.charAt(lo >>> 24 & 0xF),
		                             RADIX_DIGITS.charAt(lo >>> 20 & 0xF),
		                             RADIX_DIGITS.charAt(lo >>> 16 & 0xF),
		                             RADIX_DIGITS.charAt(lo >>> 12 & 0xF),
		                             RADIX_DIGITS.charAt(lo >>> 8 & 0xF),
		                             RADIX_DIGITS.charAt(lo >>> 4 & 0xF),
		                             RADIX_DIGITS.charAt(lo & 0xF)});
	}

	public static String toUnsignedHexWithSpaces(long value) {
		return toUnsignedHexWithSpaces(value, 8);
	}

	/**
	 * Converts N least significant octets of a long to hexadecimal, separated by spaces.
	 */
	public static String toUnsignedHexWithSpaces(long value, int len) {
		requireBetween(1, 8, len, "len");

		StringBuilder sb = new StringBuilder(len * 3 - 1);

		for (int i = len - 1; i >= 0; i--) {
			if (i > 0) {
				sb.append(' ');
			}

			appendHexByte(sb, (int)(value >>> i * 8));
		}

		return sb.toString();
	}

	public static String toUnsignedHex(byte[] array) {
		StringBuilder out = new StringBuilder(array.length * 2);

		for (byte b : array) {
			appendHexByte(out, b);
		}

		return out.toString().toUpperCase();
	}

	public static String toUnsignedHexWithSpaces(byte[] array) {
		return toUnsignedHexWithSpaces(array, array.length);
	}

	public static String toUnsignedHexWithSpaces(byte[] array, int len) {
		StringBuilder sb = new StringBuilder(len * 3 + 2);

		for (int i = 0; i < len; i++) {
			if (i > 0) {
				sb.append(' ');
			}

			appendHexByte(sb, array[i]);
		}

		return sb.toString();
	}

	/**
	 * Converts N least significant octets of a long to hexadecimal, separated by spaces.
	 * <p>
	 * If the array is longer than the specified length, a string like " (1234 total)" is appended.
	 */
	public static String toUnsignedHexWithSpacesTruncated(byte[] array, int len) {
		requireAtLeast(1, len, "len");

		// 20 assumes at most 2**31-1 = 2147483647 more elements: "xx, yy, (2147483647 total)"
		StringBuilder sb = new StringBuilder(len * 4 + 18);

		for (int i = 0; i < array.length && i < len; i++) {
			if (i > 0) {
				sb.append(' ');
			}

			appendHexByte(sb, array[i]);
		}

		if (array.length > len) {
			sb.append(" (").append(array.length).append(" total)");
		}

		return sb.toString();
	}

	private static void appendHexByte(StringBuilder sb, int b) {
		sb.append(RADIX_DIGITS.charAt(b >>> 4 & 0xF));
		sb.append(RADIX_DIGITS.charAt(b & 0xF));
	}

	public static byte[] parseHexString(String s) {
		byte[] bytes = new byte[s.length() / 2];
		int    count = 0;

		int     upper     = 0;
		boolean upperRead = false;

		for (int i = 0; i < s.length(); i++) {
			int digit = Character.digit(s.charAt(i), 16);
			if (digit < 0) {
				continue;
			}

			if (!upperRead) {
				upper = digit;
			} else {
				bytes[count] = (byte)(upper << 4 | digit);
				count++;
			}

			upperRead = !upperRead;
		}

		if (upperRead) {
			throw new AssertionError("Odd number of hexadecimal digits: " + s);
		}

		return Arrays.copyOf(bytes, count);
	}
}
