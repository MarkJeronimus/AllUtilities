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

package org.digitalmodular.utilities.math.polynomial;

import org.digitalmodular.utilities.container.Vector2d;

/**
 * @author Mark Jeronimus
 */
// Created 2007-12-03
public abstract class PolynomialInterpolation {
	protected int      degree;
	protected double[] coefficients;

	public double[] makeDifferentialOf(PolynomialInterpolation poly) {
		degree = poly.degree - 1;
		coefficients = new double[poly.degree];
		for (int i = 0; i <= degree; i++) {
			coefficients[i] = poly.coefficients[i + 1] * (i + 1);
		}

		return coefficients;
	}

	public abstract double[] makeCoefficients(Vector2d[] points);
}
