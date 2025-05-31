package nl.airsupplies.utilities.container;

/**
 * @author Mark Jeronimus
 */
// Created 2005-10-03
public class Ray4f {
	public Vector2f origin;
	public Vector2f direction;

	private Vector2f directionSquared = null;

	public Ray4f() {
		origin    = new Vector2f();
		direction = new Vector2f();
	}

	public Ray4f(Vector2f origin, Vector2f direction) {
		this.origin    = origin;
		this.direction = direction;
	}

	public Ray4f(Ray4f other) {
		origin           = other.origin;
		direction        = other.direction;
		directionSquared = other.directionSquared;
	}

	public Vector2f getDirectionSquared() {
		if (directionSquared == null && direction != null) {
			directionSquared = direction.scale(direction);
		}
		return directionSquared;
	}

	@Override
	public String toString() {
		return origin + "+r*" + direction;
	}
}
