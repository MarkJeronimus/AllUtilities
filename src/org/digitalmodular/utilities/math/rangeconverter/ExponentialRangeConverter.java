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

import org.digitalmodular.utilities.NumberUtilities;

/**
 * Converts a value (in the range 0..1) to a logarithmic domain.
 * <p>
 * Useful for multipliers and other values where the adjustment precision or derivative should linearly depend on the
 * domain value.
 * <p>
 * Regular increments in x cause regular scaling factors (relative to {@code offset}) in value:
 * <pre>
 * domain = offset + base * pow(exponent, value);
 * value = log((domain - offset) / base) / log(exponent);
 * </pre>
 *
 * @author Mark Jeronimus
 */
// Created 2016-03-25
public class ExponentialRangeConverter implements RangeConverter {
	private final double offset;
	private final double base;
	private final double exponent;

	private final double logExponent;
	private final double max;

	private ExponentialRangeConverter(double offset, double base, double exponent) {
		this.offset   = offset;
		this.base     = base;
		this.exponent = exponent;

		logExponent = Math.log(exponent);
		max         = offset + base * exponent;
	}

	public static ExponentialRangeConverter fromBaseExponent(double base, double exponent) {
		return fromOffsetBaseExponent(0, base, exponent);
	}

	public static ExponentialRangeConverter fromOffsetBaseExponent(double offset, double base, double exponent) {
		if (NumberUtilities.isDegenerate(offset)) {
			throw new IllegalArgumentException("offset is degenerate:" + offset);
		}
		if (NumberUtilities.isDegenerate(base)) {
			throw new IllegalArgumentException("base is degenerate:" + base);
		}
		if (NumberUtilities.isDegenerate(exponent)) {
			throw new IllegalArgumentException("exponent is degenerate:" + exponent);
		}
		if (base <= 0) {
			throw new IllegalArgumentException("base must be positive: " + base);
		}
		if (exponent <= 0) {
			throw new IllegalArgumentException("exponent must be positive: " + exponent);
		}

		return new ExponentialRangeConverter(offset, base, exponent);
	}

	public static ExponentialRangeConverter fromMidMax(double mid, double max) {
		return fromMinMidMax(0, mid, max);
	}

	public static ExponentialRangeConverter fromMinMidMax(double min, double mid, double max) {
		if (NumberUtilities.isDegenerate(min)) {
			throw new IllegalArgumentException("min is degenerate:" + min);
		}
		if (NumberUtilities.isDegenerate(mid)) {
			throw new IllegalArgumentException("mid is degenerate:" + mid);
		}
		if (NumberUtilities.isDegenerate(max)) {
			throw new IllegalArgumentException("max is degenerate:" + max);
		}
		if (mid <= min) {
			throw new IllegalArgumentException("min should be less than mid: " + min + ", " + mid);
		}
		if (max <= mid) {
			throw new IllegalArgumentException("mid should be less than max: " + mid + ", " + max);
		}
		if (mid - min >= max - mid) {
			throw new IllegalArgumentException("mid-min should be less than max-mid: " + min + ", " + mid + ", " +
			                                   max);
		}

		// W|A: solve((b-x)/(a-x)-(c-x)/(b-x),x)
		double offset   = (min * max - mid * mid) / (min + max - 2 * mid);
		double base     = (min - offset);
		double exponent = (max - offset) / base;

		return new ExponentialRangeConverter(offset, base, exponent);
	}

	@Override
	public double toDomain(double value) {
		if (value < 0 || value > 1) {
			new IllegalArgumentException("value must be in the range [0, 1]: " + value);
		}
		return offset + base * Math.pow(exponent, value);
	}

	@Override
	public double fromDomain(double domain) {
		if (domain < offset || domain > max) {
			new IllegalArgumentException("value must be in the range [" + offset + ", " + max + "]: " + domain);
		}
		return Math.log((domain - offset) / base) / logExponent;
	}
}
