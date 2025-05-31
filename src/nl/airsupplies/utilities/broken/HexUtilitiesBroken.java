package nl.airsupplies.utilities.broken;

/**
 * @author Mark Jeronimus
 */
// Created 2025-06-01
public final class HexUtilitiesBroken {
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
			exponent -= 1023;
			bits |= 0x10000000000000L;
		}
		bits <<= exponent & 3;
		exponent >>= 2;

		String out = Long.toHexString(bits).toUpperCase();

		if (exponent < -3 || exponent > 6) {
			out = removeTrailingZeroes(out);
			if (out.length() > 1) {
				out = out.charAt(0) + "." + out.substring(1);
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
