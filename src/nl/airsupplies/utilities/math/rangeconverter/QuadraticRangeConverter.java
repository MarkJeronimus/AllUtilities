package nl.airsupplies.utilities.math.rangeconverter;

import nl.airsupplies.utilities.NumberUtilities;

/**
 * Converts a value (in the range 0..1) to a quadratic domain.
 * <p>
 * Useful where the adjustment precision or derivative should linearly depend on the input value.
 * <p>
 * {@code Domain} is a linear function of {@code value}; regular increments in {@code value} cause regular increments in
 * {@code domain}:
 * <pre>
 * domain = a * value * value + b * value + c;
 * value = (-b + sqrt(b * b + 4 * a * (domain - c))) / (2 * a);
 * </pre>
 *
 * @author Mark Jeronimus
 */
// Created 2016-03-26
public class QuadraticRangeConverter implements RangeConverter {
	private final double a;
	private final double b;
	private final double c;

	private final double max;

	private QuadraticRangeConverter(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;

		max = a + b + c;
	}

	public static QuadraticRangeConverter fromABC(double a, double b, double c) {
		if (NumberUtilities.isDegenerate(a)) {
			throw new IllegalArgumentException("a is degenerate:" + a);
		}
		if (NumberUtilities.isDegenerate(b)) {
			throw new IllegalArgumentException("b is degenerate:" + b);
		}
		if (NumberUtilities.isDegenerate(c)) {
			throw new IllegalArgumentException("c is degenerate:" + c);
		}
		if (a <= 0) {
			throw new IllegalArgumentException("a must be positive: " + a);
		}
		if (b < 0) {
			throw new IllegalArgumentException("b must be non-negative: " + b);
		}
		if (a == 0 && b == 0) {
			throw new IllegalArgumentException("One of a or b must be non-negative: " + 0 + ", " + 0);
		}

		return new QuadraticRangeConverter(a, b, c);
	}

	public static QuadraticRangeConverter fromMidMax(double mid, double max) {
		return fromMinMidMax(0, mid, max);
	}

	public static QuadraticRangeConverter fromMinMidMax(double min, double mid, double max) {
		if (NumberUtilities.isDegenerate(min)) {
			throw new IllegalArgumentException("min is degenerate:" + min);
		}
		if (NumberUtilities.isDegenerate(mid)) {
			throw new IllegalArgumentException("mid is degenerate:" + mid);
		}
		if (NumberUtilities.isDegenerate(max)) {
			throw new IllegalArgumentException("max is degenerate:" + max);
		}
		if (min >= mid) {
			throw new IllegalArgumentException("min should be less than mid: " + min + ", " + mid);
		}
		if (mid >= max) {
			throw new IllegalArgumentException("mid should be less than max: " + mid + ", " + max);
		}
		if (mid - min >= max - mid) {
			throw new IllegalArgumentException("mid-min should be less than max-mid: " + min + ", " + mid + ", " +
			                                   max);
		}

		double a = 2 * (min + max - 2 * mid);
		double b = 2 * (mid - min - a / 4);

		if (b < 0) {
			throw new IllegalArgumentException("This combination causes negative b: " + b);
		}

		return new QuadraticRangeConverter(a, b, min);
	}

	@Override
	public double toDomain(double value) {
		if (value < 0 || value > 1) {
			new IllegalArgumentException("value must be in the range [0, 1]: " + value);
		}
		return (a * value + b) * value + c;
	}

	@Override
	public double fromDomain(double domain) {
		if (domain < b || domain > max) {
			new IllegalArgumentException("value must be in the range [" + c + ", " + max + "]: " + domain);
		}
		return (Math.sqrt(b * b + 4 * a * (domain - c)) - b) / (2 * a);
	}
}
