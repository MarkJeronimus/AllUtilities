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

package org.digitalmodular.utilities.math.rangeconverter;

import static org.digitalmodular.utilities.ValidatorUtilities.requireAtLeast;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;
import static org.digitalmodular.utilities.ValidatorUtilities.requireRange;
import static org.digitalmodular.utilities.ValidatorUtilities.requireThat;

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
		this.scale = scale;
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
			scale = mid - min;
			offset = mid - 2 * min;
		} else {
			throw new UnsupportedOperationException("sigmoid: " + sigmoid);
		}

		return new InverseHalfSigmoidRangeConverter(scale, offset);
	}

	@Override
	public double toDomain(double value) {
		requireRange(0, 1, value, "value");

		return scale / (1 - value) - offset;
	}

	@Override
	public double fromDomain(double domain) {
		requireAtLeast(0, domain, "domain");

		return 1 - scale / (domain + offset);
	}
}
