package nl.airsupplies.utilities.math.rangeconverter;

import nl.airsupplies.utilities.NumberUtilities;

/**
 * Converts a value (in the range 0..1) to a linear domain.
 * <p>
 * Useful where the domain value should linearly depend on the input value or where the derivative should be constant.
 * <p>
 * {@code Domain} is a linear function of {@code value}; regular increments in {@code value} cause regular increments in
 * {@code domain}:
 * <pre>
 * domain = a * value + b;
 * value = (domain - b) / a;
 * </pre>
 *
 * @author Mark Jeronimus
 */
// Created 2016-03-25
public class LinearRangeConverter implements RangeConverter {
	private final double a;
	private final double b;

	private final double max;

	protected LinearRangeConverter(double a, double b) {
		this.a = a;
		this.b = b;

		max = a + b;
	}

	public static LinearRangeConverter fromAB(double a, double b) {
		if (NumberUtilities.isDegenerate(a)) {
			throw new IllegalArgumentException("a is degenerate:" + a);
		}
		if (NumberUtilities.isDegenerate(b)) {
			throw new IllegalArgumentException("b is degenerate:" + b);
		}
		if (a <= 0) {
			throw new IllegalArgumentException("a must be positive: " + a);
		}

		return new LinearRangeConverter(a, b);
	}

	public static LinearRangeConverter fromMinMax(double max) {
		return fromMinMax(0, max);
	}

	public static LinearRangeConverter fromMinMax(double min, double max) {
		if (NumberUtilities.isDegenerate(min)) {
			throw new IllegalArgumentException("min is degenerate:" + min);
		}
		if (NumberUtilities.isDegenerate(max)) {
			throw new IllegalArgumentException("max is degenerate:" + min);
		}
		if (min >= max) {
			throw new IllegalArgumentException("min should be less than max: " + min + ", " + max);
		}

		double a = max - min;

		if (a == Double.POSITIVE_INFINITY) {
			throw new IllegalArgumentException("difference between min and max too large: " + min + ", " + max);
		}

		return new LinearRangeConverter(a, min);
	}

	@Override
	public double toDomain(double value) {
		if (value < 0 || value > 1) {
			new IllegalArgumentException("value must be in the range [0, 1]: " + value);
		}
		return a * value + b;
	}

	@Override
	public double fromDomain(double domain) {
		if (domain < b || domain > max) {
			new IllegalArgumentException("value must be in the range [" + b + ", " + max + "]: " + domain);
		}
		return (domain - b) / a;
	}
}
