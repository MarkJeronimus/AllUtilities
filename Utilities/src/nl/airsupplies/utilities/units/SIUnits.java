package nl.airsupplies.utilities.units;

import nl.airsupplies.utilities.annotation.UtilityClass;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireThat;

/**
 * @author Mark Jeronimus
 */
@UtilityClass
public final class SIUnits {
	private static final String[] PREFIXES            =
			{"y", "z", "a", "f", "p", "n", "µ", "m", "", "k", "M", "G", "T", "P", "E", "Z", "Y"};
	private static final int      PREFIX_ARRAY_OFFSET = 8;

	public static int getMagnitudeFor(char prefix) {
		for (int i = 0; i < PREFIXES.length; i++) {
			if (!PREFIXES[i].isEmpty() && PREFIXES[i].charAt(0) == prefix) {
				return (i - PREFIX_ARRAY_OFFSET) * 3;
			}
		}

		return 0;
	}

	/**
	 * Calculates the magnitude of the specified value, when expressed in
	 * engineering/SI notation. This is always a multiple of three.
	 * <p>
	 * To improve readability, the following rules are added:
	 * <ul><li>If the number is in the range [1000,10000), it will have 4 digits
	 * before the decimal point instead of 1,</li>
	 * <li>If the number is smaller than 1, and would have 3 digits before the
	 * decimal point, it will have 0 digits before the decimal point
	 * instead.</li></ul>
	 *
	 * @param value The value to calculate the magnitude of
	 */
	public static int calculateMagnitude(double value) {
		int magnitude = (int)Math.floor(Math.log10(Math.abs(value)));

		return magnitude;
	}

	/**
	 * To improve readability, if the number is in the range [1000,10000), it will not use the prefix 'k'
	 * Examples: 90k, 9000.
	 * <p>
	 * Useful for electronics such as voltages and farads.
	 */
	public static int adjustForReadabilityOfBigNumbers(int magnitude) {
		if (magnitude == 3) {
			return 2;
		}

		return magnitude;
	}

	/**
	 * To improve readability, if the number is in the range [1000,10000), it will not use the prefix 'k' and if the
	 * number is in the range [0.01, 1) it will not use the prefix 'm'.
	 * <p>
	 * Examples: 90k, 9000, 0.09, 9m.
	 * <p>
	 * Useful for daily units such as seconds or meters.
	 */
	public static int adjustForReadabilityOfSmallNumbers(int magnitude) {
		if (magnitude == 3) {
			return 2;
		} else if (magnitude == -2 || magnitude == -1) {
			return 0;
		}

		return magnitude;
	}

	/**
	 * To improve readability, if the number is in the range [1000,10000), it will not use the prefix 'k' and if the
	 * number is in the range [0.01, 1) it will not use the prefix 'm'.
	 * <p>
	 * Examples: 90k, 9000, 0.09, 9m.
	 */
	public static int adjustForReadabilityPaperVariant(int magnitude) {
		if (magnitude == 3) {
			return 2;
		} else if (magnitude == -2 || magnitude == -1) {
			return 0;
		}

		return magnitude;
	}

	/**
	 * Formats the number using an SI prefix that is suitable for the given magnitude.
	 * <p>
	 * This is the same as {@link #calculateMantissa(double, int) calculateMantissa}{@code (value) +
	 * }{@link #getPrefix(int) getPrefix}{@code (value)}.
	 */
	public static String formatNumber(double value, int magnitude) {
		return formatNumber(value, magnitude, "");
	}

	/**
	 * Formats the number using an SI prefix that is suitable for the given magnitude.
	 * <p>
	 * This is the same as {@link #calculateMantissa(double, int) calculateMantissa}{@code (value) +
	 * }{@link #getPrefix(int) getPrefix}{@code (value) + suffix}.
	 */
	public static String formatNumber(double value, int magnitude, String suffix) {
		int prefixIndex = getPrefixIndex(magnitude);

		return valueToString(value, prefixIndex) + PREFIXES[prefixIndex] + suffix;
	}

	/**
	 * @see #formatNumber(double, int)
	 */
	public static String getPrefix(int magnitude) {
		requireThat(Math.floorMod(magnitude, 3) == 0, () -> "Invalid SI magnitude: " + magnitude);

		int prefixIndex = getPrefixIndex(magnitude);

		return PREFIXES[prefixIndex];
	}

	/**
	 * @see #formatNumber(double, int)
	 */
	public static double calculateMantissa(double value, int magnitude) {
		int prefixIndex = getPrefixIndex(magnitude);

		return value * Math.pow(10, -3 * (prefixIndex - PREFIX_ARRAY_OFFSET));
	}

	/**
	 * If value is an integer, use {@link Long#toString()}.
	 */
	private static String valueToString(double value, int prefixIndex) {
		double adjustedValue = value * Math.pow(10, -3 * (prefixIndex - PREFIX_ARRAY_OFFSET));
		long   longValue     = (long)adjustedValue;

		if (longValue == adjustedValue) {
			return Long.toString(longValue);
		} else {
			return Float.toString((float)adjustedValue);
		}
	}

	private static int getPrefixIndex(int magnitude) {
		int prefixIndex = Math.floorDiv(magnitude, 3) + PREFIX_ARRAY_OFFSET;
		return Math.max(0, Math.min(PREFIXES.length - 1, prefixIndex));
	}
}
