package nl.airsupplies.utilities.math.rangeconverter;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireBetween;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireThat;

/**
 * Converts a value (in the range 0..1) to a value smoothly ranging from 0 to infinity.
 * <p>
 * Useful for multipliers and other values where the adjustment precision or derivative should linearly depend on the
 * domain value.
 *
 * @author Mark Jeronimus
 */
// Created 2016-03-25
public class InverseHalfSigmoidRangeConverter implements RangeConverter {
	public enum HalfSigmoidFunction {
		RECIPROCAL
	}

	private final double scale;
	private final double offset;

	private InverseHalfSigmoidRangeConverter(double scale, double offset) {
		this.scale  = scale;
		this.offset = offset;
	}

	public static InverseHalfSigmoidRangeConverter fromMid(HalfSigmoidFunction sigmoid, double mid) {
		return fromMinMid(sigmoid, 0, mid);
	}

	public static InverseHalfSigmoidRangeConverter fromMinMid(HalfSigmoidFunction sigmoid, double min, double mid) {
		requireNonNull(sigmoid, "sigmoid");
		requireThat(min < mid, () -> "min should be less than mid: " + min + ", " + mid);

		double scale;
		double offset;
		if (sigmoid == HalfSigmoidFunction.RECIPROCAL) {
			scale  = mid - min;
			offset = mid - 2 * min;
		} else {
			throw new UnsupportedOperationException("sigmoid: " + sigmoid);
		}

		return new InverseHalfSigmoidRangeConverter(scale, offset);
	}

	@Override
	public double toDomain(double value) {
		requireBetween(0, 1, value, "value");

		return scale / (1 - value) - offset;
	}

	@Override
	public double fromDomain(double domain) {
		requireAtLeast(0, domain, "domain");

		return 1 - scale / (domain + offset);
	}
}
