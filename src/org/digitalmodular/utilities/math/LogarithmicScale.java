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

package org.digitalmodular.utilities.math;

import static org.digitalmodular.utilities.ValidatorUtilities.requireAbove;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNotDegenerate;

/**
 * Converter to map a linear value to a logarithmic value and back.
 * It maps numbers in the closed interval [min, 1] to the closed interval [-1, 0] in a logarithmic
 * fashion.
 * This also extends to numbers outside of this range: it maps the values {0, min, 1, &infin;} to the values {
 * -&infin;, -1, 0, &infin;}.
 * <p>
 * Parameter {@code min} can be set directly, or with helper methods for a fixed number octaves, decades, or decibels.
 * The calculations are as follows: <code>min = 2<sup>-octaves</sup> = 10<sup>-octaves</sup> =
 * 10<sup>-decibels/20</sup></code>.
 * For example, the interval <code>[min=2<sup>-10</sup>, 1]</code> spans exactly 10 octaves and spans approximately 3
 * decades or 60 dB.
 * <p>
 * The formula of the logarithmic mapping function is equivalent to {@code ln(value) / -ln(min)}.
 * <p>
 * Another interpretation of a logarithmic mapping function is that each doubling or halving of the input value will
 * add or subtract a fixed amount from the output value, respectively.
 * The number of halvings needed starting at {@code 1} to span the entire output range depends on the {@code min}
 * parameter and is equal to the number of octaves.
 * <p>
 * There's a hack to extend the output range without an additional multiplication of the result.
 * If you want the output span the range [-N, 0] then set {@code min} to {@code Math.pow(min, N)}.
 * Equivalently, if you're using the octaves, decades or decibels method, multiply the value like this: {@code
 * setOctaves(octaves * N)}.
 *
 * @author Mark Jeronimus
 */
// Created 2014-02-12
public class LogarithmicScale {
	private double min;
	private double logFactor;

	public LogarithmicScale() {
		min = 0.125;
		logFactor = -1 / Math.log(min);
	}

	public LogarithmicScale setMin(double min) {
		requireNotDegenerate(min, "min");
		if (min <= 0 || min >= 1) {
			throw new IllegalArgumentException("'min' should be in the open range (0, 1).");
		}

		this.min = min;
		logFactor = -1 / Math.log(min);

		return this;
	}

	public double getMin() {
		return min;
	}

	public LogarithmicScale setOctaves(double octaves) {
		requireAbove(0, octaves, "octaves");

		setMin(Math.pow(0.5, octaves));

		return this;
	}

	public LogarithmicScale setDecades(double decades) {
		requireAbove(0, decades, "decades");

		setMin(Math.pow(0.1, decades));

		return this;
	}

	public LogarithmicScale setDecibels(double decibels) {
		requireAbove(0, decibels, "decibels");

		setMin(Math.pow(0.1, decibels / 20));

		return this;
	}

	public double get(double value) {
		return value == 0 ? Double.NEGATIVE_INFINITY : Math.log(value) * logFactor;
	}

	public double reverse(double log) {
		return Math.exp(log / logFactor);
	}
}
