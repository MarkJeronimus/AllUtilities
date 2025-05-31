package nl.airsupplies.utilities.math.polynomial;

import nl.airsupplies.utilities.container.Vector2d;

/**
 * @author Mark Jeronimus
 */
// Created 2007-12-03
public abstract class PolynomialInterpolation {
	protected int      degree;
	protected double[] coefficients;

	public double[] makeDifferentialOf(PolynomialInterpolation poly) {
		degree       = poly.degree - 1;
		coefficients = new double[poly.degree];
		for (int i = 0; i <= degree; i++) {
			coefficients[i] = poly.coefficients[i + 1] * (i + 1);
		}

		return coefficients;
	}

	public abstract double[] makeCoefficients(Vector2d[] points);
}
