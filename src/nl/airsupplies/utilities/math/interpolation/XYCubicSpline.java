package nl.airsupplies.utilities.math.interpolation;

import java.util.function.DoubleUnaryOperator;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireThat;

/**
 * @author Mark Jeronimus
 */
// Created 2017-11-16
public class XYCubicSpline implements DoubleUnaryOperator {
	private final CubicSpline splineX;
	private final CubicSpline splineY;

	public XYCubicSpline(double[] valuesX, double[] valuesY, double firstPointDerivative, double lastPointDerivative) {
		requireThat(valuesX.length == valuesY.length, () ->
				"'" + valuesX.length + "' and '" + valuesY.length + "' must have the same length: " +
				valuesX.length + " vs " + valuesY.length);

		splineX = new CubicSpline(valuesX, Double.NaN, Double.NaN);
		splineY = new CubicSpline(valuesY, firstPointDerivative, lastPointDerivative);
	}

	public CubicSpline getSplineX() {
		return splineX;
	}

	public CubicSpline getSplineY() {
		return splineY;
	}

	@Override
	public double applyAsDouble(double x) {
		// Both the splineX and splineY are interpolated with a cubic function with a position as parameter. Find the
		// position at which the splineX interpolates to the requested X, then interpolate the Y for that position.
		@SuppressWarnings("SuspiciousNameCombination")
		double position = splineX.reverseValue(x);

		return splineY.applyAsDouble(position);
	}

	public double reverseValue(double y) {
		double position = splineY.reverseValue(y);

		return splineX.applyAsDouble(position);
	}
}
