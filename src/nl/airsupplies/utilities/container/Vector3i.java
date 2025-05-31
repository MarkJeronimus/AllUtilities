package nl.airsupplies.utilities.container;

/**
 * @author Mark Jeronimus
 */
// Created 2005-12-06
public class Vector3i implements Comparable<Vector3i> {
	public int x;
	public int y;
	public int z;

	public Vector3i() {
		x = y = z = 0;
	}

	public Vector3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3i(Vector3i o) {
		x = o.x;
		y = o.y;
		z = o.z;
	}

	public void set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void set(Vector3i o) {
		x = o.x;
		y = o.y;
		z = o.z;
	}

	@Override
	public int compareTo(Vector3i o) {
		int diff = Integer.compare(z, o.z);
		if (diff != 0) {
			return diff;
		}
		diff = Integer.compare(y, o.y);
		if (diff != 0) {
			return diff;
		}
		return Integer.compare(x, o.x);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vector3i v) {
			return x == v.x && y == v.y && z == v.z;
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		int hash = 0x811C9DC5;
		hash ^= Integer.hashCode(x);
		hash *= 0x01000193;
		hash ^= Integer.hashCode(y);
		hash *= 0x01000193;
		hash ^= Integer.hashCode(z);
		hash *= 0x01000193;
		return hash;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '(' + x + ", " + y + ", " + z + ')';
	}
}
