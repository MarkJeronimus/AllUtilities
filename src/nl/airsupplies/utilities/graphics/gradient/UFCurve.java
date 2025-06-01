package nl.airsupplies.utilities.graphics.gradient;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.math.interpolation.MonotoneInterpolator;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireBetween;

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
			this.position  = requireBetween(0.0, 1.0, position, "position");
			this.value     = requireBetween(0.0, 1.0, value, "value");
			this.smoothIn  = smoothIn;
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
			requireBetween(0.0, 1.0, position, "position");
			requireBetween(0.0, 1.0, value, "value");
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

	private @Nullable MonotoneInterpolator interpolator = null;

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
		return index;
	}

	public ControlPoint removePoint(int index) {
		interpolator = null;
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
	}
}
