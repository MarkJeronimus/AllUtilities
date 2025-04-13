package org.digitalmodular.utilities.graphics.gradient;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import org.digitalmodular.utilities.math.interpolation.MonotoneInterpolator;
import org.digitalmodular.utilities.math.interpolation.MonotoneInterpolatorReference;
import static org.digitalmodular.utilities.ValidatorUtilities.requireRange;

/**
 * @author Mark Jeronimus
 */
// Created 2023-10-02
public class UFCurve {
	/**
	 * @author Mark Jeronimus
	 */
	// Created 2023-10-02
	public static final class ControlPoint {
		private final double  position;
		private final double  value;
		private final boolean smoothIn;
		private final boolean smoothOut;

		private ControlPoint(double position, double value, boolean smoothIn, boolean smoothOut) {
			this.position = requireRange(0.0, 1.0, position, "position");
			this.value = requireRange(0.0, 1.0, value, "value");
			this.smoothIn = smoothIn;
			this.smoothOut = smoothOut;
		}

		public double getPosition() {
			return position;
		}

		public double getValue() {
			return value;
		}

		public boolean isSmoothIn() {
			return smoothIn;
		}

		public boolean isSmoothOut() {
			return smoothOut;
		}

		public ControlPoint setCoordinate(double position, double value) {
			requireRange(0.0, 1.0, position, "position");
			requireRange(0.0, 1.0, value, "value");
			return new ControlPoint(position, value, smoothIn, smoothOut);
		}

		public ControlPoint setSmoothIn(boolean smoothIn) {
			return new ControlPoint(position, value, smoothIn, smoothOut);
		}

		public ControlPoint setSmoothOut(boolean smoothOut) {
			return new ControlPoint(position, value, smoothIn, smoothOut);
		}

		@Override
		public String toString() {
			return "(" + position + ", " + value + ')';
		}
	}

	private final List<ControlPoint> points = new ArrayList<>(16);

	private @Nullable MonotoneInterpolator          interpolator  = null;
	private @Nullable MonotoneInterpolatorReference interpolator2 = null;

	public int addPoint(double position, double value) {
		return addPoint(new ControlPoint(position, value, true, true));
	}

	public int addPoint(ControlPoint point) {
		int index = -1;
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).getPosition() > point.getPosition()) {
				index = i;
				break;
			}
		}

		if (index < 0) {
			index = points.size();
			points.add(point);
		} else {
			points.add(index, point);
		}

		interpolator = null;
		interpolator2 = null;
		return index;
	}

	public ControlPoint removePoint(int index) {
		interpolator = null;
		interpolator2 = null;
		return points.remove(index);
	}

	public int getNumPoints() {
		return points.size();
	}

	public ControlPoint getPoint(int index) {
		return points.get(index);
	}

	public double get(double position) {
		if (interpolator == null) {
			rebuild();
		}

		return interpolator.applyAsDouble(position);
	}

	public double get2(double position) {
		if (interpolator2 == null) {
			rebuild();
		}

		return interpolator2.applyAsDouble(position);
	}

	private void rebuild() {
		int      n       = points.size();
		double[] pointsX = new double[n];
		double[] pointsY = new double[n];

		for (int i = 0; i < n; i++) {
			ControlPoint point = points.get(i);
			pointsX[i] = point.getPosition();
			pointsY[i] = point.getValue();
		}

		interpolator = new MonotoneInterpolator(pointsX, pointsY, 0.0);
		interpolator2 = new MonotoneInterpolatorReference(pointsX, pointsY, 0.0);
	}
}
