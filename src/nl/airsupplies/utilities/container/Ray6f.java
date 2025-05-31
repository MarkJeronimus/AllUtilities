package nl.airsupplies.utilities.container;

/**
 * @author Mark Jeronimus
 */
// Created 2005-10-03
public class Ray6f {
	public Vector3f origin;
	public Vector3f direction;

	private Vector3f directionSquared = null;

	public Ray6f() {
		origin    = null;
		direction = null;
	}

	public Ray6f(Vector3f origin, Vector3f direction) {
		this.origin    = origin;
		this.direction = direction;
	}

	public Ray6f(Ray6f other) {
		origin           = other.origin;
		direction        = other.direction;
		directionSquared = other.directionSquared;
	}

	public Vector3f getDirectionSquared() {
		if (directionSquared == null && direction != null) {
			directionSquared.set(direction);
			directionSquared.sqr();
		}

		return directionSquared;
	}

	@Override
	public String toString() {
		return origin + "+r*" + direction;
	}
}
