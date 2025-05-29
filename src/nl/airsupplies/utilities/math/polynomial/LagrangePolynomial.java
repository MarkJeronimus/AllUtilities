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

package nl.airsupplies.utilities.math.polynomial;

import java.util.Arrays;

import nl.airsupplies.utilities.container.Vector2d;

/**
 * @author Mark Jeronimus
 */
// Created 2007-12-02
public class LagrangePolynomial extends PolynomialInterpolation {
	@Override
	public double[] makeCoefficients(Vector2d[] points) {
		degree       = points.length - 1;
		coefficients = new double[points.length];
		Arrays.fill(coefficients, 0);

		for (int p = 0; p <= degree; p++) {
			double   det  = 1;
			double[] c;
			double[] oldc = new double[2];
			oldc[0] = 0;
			oldc[1] = 1;
			int whichX = 0;
			for (int j = 1; j <= degree; j++) {
				if (whichX == p) {
					whichX++;
				}

				det *= (points[p].x - points[whichX].x);

				c    = new double[j + 2];
				c[0] = 0;
				for (int i = 1; i <= j; i++) {
					c[i] = oldc[i - 1] + oldc[i] * points[whichX].x;
				}
				c[j + 1] = 1;
				oldc     = null;
				oldc     = c;

				whichX++;
			}

			int sign = 1;
			for (int i = degree; i >= 0; i--) {
				coefficients[i] += points[p].y * oldc[i + 1] / det * sign;
				sign = -sign;
			}
		}

		return coefficients;
	}
}
