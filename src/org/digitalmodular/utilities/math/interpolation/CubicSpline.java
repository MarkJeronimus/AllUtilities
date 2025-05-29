package org.digitalmodular.utilities.math.interpolation;

import java.util.function.DoubleUnaryOperator;

/**
 * @author Mark Jeronimus
 */
// Created 2017-10-30
public class CubicSpline implements DoubleUnaryOperator {
	private final double[] values;

	private final double firstPointDerivative;
	private final double lastPointDerivative;

	private final double[] coefficients;

	public CubicSpline(double[] values, double firstPointDerivative, double lastPointDerivative) {
		this.values               = values.clone();
		this.firstPointDerivative = firstPointDerivative;
		this.lastPointDerivative  = lastPointDerivative;

		coefficients = new double[values.length];

		init();
	}

	private void init() {
		if (values.length <= 1) {
			return;
		}

		int n = values.length - 1;

		double[] u = new double[n];

		if (Double.isNaN(firstPointDerivative)) {
			coefficients[0] = 0;
			u[0]            = 0;
		} else {
			coefficients[0] = -0.5;
			u[0]            = 3 * ((values[1] - values[0]) - firstPointDerivative);
		}

		for (int i = 1; i < n; ++i) {
			coefficients[i] = 1 / (-4 - coefficients[i - 1]);
			u[i]            = values[i + 1] + values[i - 1] - 2 * values[i];
			u[i]            = (3 * u[i] - 0.5 * u[i - 1]) / (0.5 * coefficients[i - 1] + 2.0);
		}

		// Initialize to values for natural splines (used for lastPointDerivative equal to infinity)
		double qn = 0;
		double un = 0;
		if (!Double.isNaN(lastPointDerivative)) {
			qn = 0.5;
			un = 3 * (lastPointDerivative - (values[n] - values[n - 1]));
		}

		coefficients[n] = (un - qn * u[n - 1]) * (qn * coefficients[n - 1] + 1.0);

		// Note: the algorithm in [1] iterates from n-1 to 0, but as size_type
		// may be (usually is) an unsigned type, we can not write k >= 0, as this
		// is always true.
		for (int i = n; i > 0; --i) {
			coefficients[i - 1] *= coefficients[i];
			coefficients[i - 1] += u[i - 1];
		}

		// Precompute "coefficients[i] / 6"
		for (int i = 0; i < values.length; i++) {
			coefficients[i] /= 6;
		}
	}

	@Override
	public double applyAsDouble(double x) {
		int left  = (int)x;
		int right = left + 1;

		double leftF  = right - x;
		double rightF = x - left;

		right = Math.min(right, values.length - 1);

		double a = leftF * values[left];
		double b = (leftF * leftF * leftF - leftF) * coefficients[left];
		double c = (rightF * rightF * rightF - rightF) * coefficients[right];
		double d = rightF * values[right];

		return a + b + c + d;
	}

	public double reverseValue(double y) {
		double min      = 0;
		double position = 0;
		double max      = values.length - 1;

		boolean descending = values[0] > values[values.length - 1];

		// 30 iterations should be enough because interpolation is approximate by definition anyway. This is an
		// equivalent precision of 1/1000th on 1000000 points or 1/1000000th on 1000 points.
		for (int i = 0; i < 30; i++) {
			position = (min + max) / 2;
			double interpolatedY = applyAsDouble(position);
			if (interpolatedY > y != descending) {
				max = position;
			} else {
				min = position;
			}
		}

		return position;
	}

	public double[] getValues() {
		return values.clone();
	}
}
