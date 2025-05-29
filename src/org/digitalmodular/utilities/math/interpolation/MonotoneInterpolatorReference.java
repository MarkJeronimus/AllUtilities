package org.digitalmodular.utilities.math.interpolation;

import java.util.function.DoubleUnaryOperator;

import static org.digitalmodular.utilities.ArrayValidatorUtilities.requireArrayLengthAtLeast;
import static org.digitalmodular.utilities.ArrayValidatorUtilities.requireArrayLengthsMatch;
import static org.digitalmodular.utilities.ArrayValidatorUtilities.requireArrayValuesNotDegenerate;
import static org.digitalmodular.utilities.ValidatorUtilities.requireAtLeast;
import static org.digitalmodular.utilities.ValidatorUtilities.requireRange;

/**
 * @author Mark Jeronimus
 * @since 1.0
 */
public class MonotoneInterpolatorReference implements DoubleUnaryOperator {
	private final double   cycleWidth;
	private final double   slopeFix = 2.0; // Correct = 2.0, UF = 3.0
	private final int      numPoints;
	private final double[] cpx;
	private final double[] cpy;
	private final double[] slopesL;
	private final double[] slopesR;

	private int lastIndexCache = 1;

	public MonotoneInterpolatorReference(double[] pointsX, double[] pointsY) {
		this(pointsX, pointsY, 0.0);
	}

	public MonotoneInterpolatorReference(double[] pointsX, double[] pointsY, double cycleWidth) {
		requireArrayLengthsMatch(pointsX, pointsY, "pointsX", "pointsY");
		requireArrayLengthAtLeast(1, pointsX, "pointsX");
		requireArrayValuesNotDegenerate(pointsX, "pointsX");
		requireArrayValuesNotDegenerate(pointsY, "pointsY");
		this.cycleWidth = requireAtLeast(0.0, cycleWidth, "cycleWidth");

		numPoints = pointsX.length;
		cpx       = new double[numPoints + 2];
		cpy       = new double[numPoints + 2];
		slopesL   = new double[numPoints + 1];
		slopesR   = new double[numPoints + 1];
		System.arraycopy(pointsX, 0, cpx, 1, numPoints);
		System.arraycopy(pointsY, 0, cpy, 1, numPoints);

		init();
	}

	public double getMinX() {
		return cpx[1];
	}

	public double getMaxX() {
		return cpx[numPoints];
	}

	public double getMinY() {
		return cpy[1];
	}

	public double getMaxY() {
		return cpy[numPoints];
	}

	private void init() {
		if (cycleWidth == 0.0) {
			cpx[0]             = 2 * cpx[1] - cpx[2];
			cpy[0]             = 2 * cpy[1] - cpy[2];
			cpx[numPoints + 1] = 2 * cpx[numPoints] - cpx[numPoints - 1];
			cpy[numPoints + 1] = 2 * cpy[numPoints] - cpy[numPoints - 1];
		} else {
			cpx[0]             = cpx[numPoints] - cycleWidth;
			cpy[0]             = cpy[numPoints];
			cpx[numPoints + 1] = cpx[1] + cycleWidth;
			cpy[numPoints + 1] = cpy[1];
		}

		for (int i = numPoints - 2; i >= 0; i--) {
			slopesL[i + 1] = calcSlope(i + 1);
		}

		for (int i = 0; i < numPoints - 1; i++) {
			slopesR[i + 1] = calcSlope(i + 2);
		}
	}

	private double calcSlope(int i) {
		double dxl = cpx[i] - cpx[i - 1];
		double dxr = cpx[i + 1] - cpx[i];
		double dyl = cpy[i] - cpy[i - 1];
		double dyr = cpy[i + 1] - cpy[i];

		if (dxl == 0 || dxr == 0 || dyl == 0 || dyr == 0 || Math.signum(dyl) != Math.signum(dyr)) {
			return 0.0;
		}

		double slopeL = dyl / dxl;
		double slopeR = dyr / dxr;

		if (cycleWidth == 0.0) {
			if (i == 1) {
				return (slopeR * 3 - slopesL[2]) / 2;
			} else if (i == numPoints) {
				return (slopeL * 3 - slopesR[i - 2]) / 2;
			}
		}

//		return slopeR / (1 + slopeR / slopeL) * 3; optimized:
		return slopeL * slopeR / (slopeL + slopeR) * slopeFix;
	}

	public int getNumPoints() {
		return numPoints;
	}

	public double getX(int index) {
		requireRange(0, numPoints, index, "index");

		return cpx[index + 1];
	}

	public double getY(int index) {
		requireRange(0, numPoints, index, "index");

		return cpy[index + 1];
	}

	@Override
	public double applyAsDouble(double x) {
		if (cycleWidth == 0.0 && (x < cpx[1] || x > cpx[numPoints])) {
			return Double.NaN;
		}

		int l = findIndex(x);

		double dx = cpx[l + 1] - cpx[l];
		double dy = cpy[l + 1] - cpy[l];

		double t = (x - cpx[l]) / dx;

		double tt  = t * t;
		double ttt = tt * t;
		double b   = ttt - 2 * tt + t;
		double c   = ttt - tt;
		double d   = 3 * tt - 2 * ttt;

		return cpy[l] +
		       (b * slopesL[l] +
		        c * slopesR[l]) * dx +
		       d * dy;
	}

	private int findIndex(double x) {
		if (x >= cpx[lastIndexCache] && x < cpx[lastIndexCache + 1]) {
			return lastIndexCache;
		}

		int l = 1;
		int r = numPoints - 1;

		while (l <= r) {
			int    mid    = (l + r) >>> 1;
			double midVal = cpx[mid];

			if (midVal < x) {
				l = mid + 1;
			} else if (midVal > x) {
				r = mid - 1;
			} else {
				lastIndexCache = mid;
				return mid;
			}
		}

		lastIndexCache = Math.max(1, l - 1);
		return lastIndexCache;
	}

	public double reverseValue(double y) {
		double min = getMinX();
		double max = getMaxX();

		boolean tooLowBooleanResult = applyAsDouble(min) < applyAsDouble(max);

		// 30 iterations should be enough because interpolation is approximate by definition anyway. This is an
		// equivalent precision of 1/1000th on 1000000 points or 1/1000000th on 1000 points.
		double position = min;
		for (int i = 0; i < 30; i++) {
			position = (min + max) / 2;
			double interpolatedY = applyAsDouble(position);
			if ((interpolatedY > y) == tooLowBooleanResult) {
				max = position;
			} else {
				min = position;
			}
		}

		return position;
	}
}
