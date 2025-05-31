package nl.airsupplies.utilities.collection;

import java.awt.geom.Point2D;

/**
 * @author Mark Jeronimus
 */
// Created 2016-03-25
public class Coordinate2D extends Point2D {
	public static final Coordinate2D ZERO = new Coordinate2D(0, 0);

	private final double x;
	private final double y;

	public Coordinate2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public static Coordinate2D valueOf(double x, double y) {
		if (x == 0 & y == 0) {
			return ZERO;
		}
		return new Coordinate2D(x, y);
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public void setLocation(double x, double y) {
		throw new UnsupportedOperationException("Immutable");
	}

	//
	// Calculation functions
	//

	@Override
	public double distance(double x, double y) {
		return Math.hypot(this.x - x, this.y - y);
	}

	public double distance(Coordinate2D other) {
		return Math.hypot(x - other.x, y - other.y);
	}

	public double distanceSquared(Coordinate2D other) {
		double x = this.x - other.x;
		double y = this.y - other.y;
		return x * x + y * y;
	}

	//
	// Binary functions
	//

	public Coordinate2D add(double x, double y) {
		return valueOf(this.x + x,
		               this.y + y);
	}

	public Coordinate2D add(Point2D other) {
		return valueOf(x + other.getX(),
		               y + other.getY());
	}

	public Coordinate2D add(Coordinate2D other) {
		return valueOf(x + other.x,
		               y + other.y);
	}

	//
	// Ternary functions
	//

	public Coordinate2D addScaled(Coordinate2D other, double position) {
		return valueOf(x + other.x * position,
		               y + other.y * position);
	}

	public Coordinate2D lerp(Coordinate2D other, double position) {
		return valueOf(x * (1 - position) + other.x * position,
		               y * (1 - position) + other.y * position);
	}

	//
	//
	//

	@Override
	public String toString() {
		return "(" + x + ", " + y + ')';
	}
}
