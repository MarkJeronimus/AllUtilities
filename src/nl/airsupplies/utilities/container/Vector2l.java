package nl.airsupplies.utilities.container;

import java.io.Serializable;

/**
 * @author Mark Jeronimus
 */
// Created 2005-11-29
public class Vector2l implements Comparable<Vector2l>, Serializable {
	/**
	 * The <i>x </i> coordinate. If no <i>x </i> coordinate is set it will default to 0.
	 */
	public long x;

	/**
	 * The <i>y </i> coordinate. If no <i>y </i> coordinate is set it will default to 0.
	 */
	public long y;

	/**
	 * Constructs and initializes a Vector2i at the origin (0,&nbsp;0) of the coordinate space.
	 */
	public Vector2l() {
		x = 0;
		y = 0;
	}

	/**
	 * Constructs and initializes a Vector2l with the same location as the specified {@code Vector2i} object.
	 *
	 * @param v a Vector2i
	 */
	public Vector2l(Vector2l v) {
		x = v.x;
		y = v.y;
	}

	/**
	 * Constructs and initializes a Vector2l at the specified ( <i>x </i>,&nbsp; <i>y </i>) location in the coordinate
	 * space.
	 *
	 * @param x the <i>x </i> coordinate
	 * @param y the <i>y </i> coordinate
	 */
	public Vector2l(long x, long y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int compareTo(Vector2l o) {
		int diff = Long.compare(y, o.y);
		if (diff != 0) {
			return diff;
		}
		return Long.compare(x, o.x);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vector2l v) {
			return x == v.x && y == v.y;
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		int hash = 0x811C9DC5;
		hash ^= Long.hashCode(x);
		hash *= 0x01000193;
		hash ^= Long.hashCode(y);
		hash *= 0x01000193;
		return hash;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '(' + x + ", " + y + ')';
	}
}
