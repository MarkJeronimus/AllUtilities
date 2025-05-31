package nl.airsupplies.utilities.math;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireThat;

/**
 * @author Mark Jeronimus
 */
public class SIUnits {
	public static final int      MIN_MAGNITUDE = -24;
	public static final int      MAX_MAGNITUDE = 24;
	public static final String[] PREFIXES      =
			{"y", "z", "a", "f", "p", "n", "µ", "m", "", "k", "M", "G", "T", "P", "E", "Z", "Y"};

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
	 * <p>
	 *
	 * @param value The value to calculate the magnitude of
	 */
	public static int calculateMagnitude(double value) {
		int log10 = (int)Math.floor(Math.log10(Math.abs(value)));

		int magnitude = (int)Math.floor(log10 / 3.0) * 3;

		// Integer overflow?
		if (magnitude > log10) {
			return 0;
		}

		return Math.max(MIN_MAGNITUDE, Math.min(MAX_MAGNITUDE, magnitude));
	}

	public static String getPrefix(int magnitude) {
		requireThat(Math.floorMod(magnitude, 3) == 0, () -> "Invalid SI magnitude: " + magnitude);

		if (magnitude < MIN_MAGNITUDE) {
			return "⋘";
		} else if (magnitude > MAX_MAGNITUDE) {
			return "⋙";
		} else {
			return PREFIXES[(magnitude - MIN_MAGNITUDE) / 3];
		}
	}

	public static double calculateMantissa(double value, double magnitude) {
		return value * Math.pow(0.1, magnitude);
	}
}
