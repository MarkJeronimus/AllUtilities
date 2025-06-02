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
