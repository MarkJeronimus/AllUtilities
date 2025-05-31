package nl.airsupplies.utilities.container;

/**
 * @author Mark Jeronimus
 */
// Created 2005-10-03
public class Vector3d implements Comparable<Vector3d> {
	public double x;
	public double y;
	public double z;

	/**
	 * Create a zero-length vector.
	 */
	public Vector3d() {
		x = 0;
		y = 0;
		z = 0;
	}

	/**
	 * Create a new Vector3d
	 */
	public Vector3d(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Create a new Vector3d
	 */
	public Vector3d(Vector3d other) {
		x = other.x;
		y = other.y;
		z = other.z;
	}

	public void set(Vector3d other) {
		x = other.x;
		y = other.y;
		z = other.z;
	}

	public void set(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Returns a Vector added to this Vector3d
	 */
	public Vector3d add(Vector3d Vector3d) {
		return new Vector3d(x + Vector3d.x, y + Vector3d.y, z + Vector3d.z);
	}

	public void addSelf(Vector3d Vector3d) {
		x += Vector3d.x;
		y += Vector3d.y;
		z += Vector3d.z;
	}

	/**
	 * Returns the subtraction of a supplied Vector3d from this Vector3d
	 */
	public Vector3d sub(Vector3d Vector3d) {
		return new Vector3d(x - Vector3d.x, y - Vector3d.y, z - Vector3d.z);
	}

	public void subSelf(Vector3d Vector3d) {
		x -= Vector3d.x;
		y -= Vector3d.y;
		z -= Vector3d.z;
	}

	/**
	 * Returns this Vector3d multiplied by a factor
	 */
	public Vector3d mul(double factor) {
		return new Vector3d(x * factor, y * factor, z * factor);
	}

	public void mulMatrix(Matrix4d matrix) {
		double x = matrix.x0 * this.x + matrix.x1 * y + matrix.x2 * z + matrix.x3;
		double y = matrix.y0 * this.x + matrix.y1 * this.y + matrix.y2 * z + matrix.y3;
		z      = matrix.z0 * this.x + matrix.z1 * this.y + matrix.z2 * z + matrix.z3;
		this.x = x;
		this.y = y;
	}

	/**
	 * Multiply this Vector3d with a factor
	 */
	public void scaleSelf(double factor) {
		x *= factor;
		y *= factor;
		z *= factor;
	}

	/**
	 * Returns the addition of this Vector3d and a scaled Vector3d
	 */
	public Vector3d addScaled(Vector3d Vector3d, double scale) {
		return new Vector3d(x + Vector3d.x * scale, y + Vector3d.y * scale, z + Vector3d.z * scale);
	}

	/**
	 * Returns the inner product (sometimes called the dot product) of this Vector3d and the supplied Vector3d
	 */
	public double dotProduct(Vector3d vector) {
		return x * vector.x + y * vector.y + z * vector.z;
	}

	/**
	 * Returns the outer product (sometimes called the cross product) of this Vector3d and the given Vector3d
	 *
	 * @return the Vector3d of the outer product
	 */
	public Vector3d crossProduct(Vector3d Vector3d) {
		return new Vector3d(y * Vector3d.z - z * Vector3d.y, z * Vector3d.x - x * Vector3d.z,
		                    x * Vector3d.y - y * Vector3d.x);
	}

	/**
	 * Returns the vectorized multiplication of this Vector3d and the supplied Vector3d
	 */
	public Vector3d vectorProduct(Vector3d vector) {
		return new Vector3d(x * vector.x, y * vector.y, z * vector.z);
	}

	public double length() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public double distanceTo(double x, double y, double z) {
		x -= this.x;
		y -= this.y;
		z -= this.z;
		return Math.sqrt(x * x + y * y + z * z);
	}

	public double distanceTo(Vector3d o) {
		double x = this.x - o.x;
		double y = this.y - o.y;
		double z = this.z - o.z;
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Returns the distance squared of the Vector3d
	 */
	public double mod() {
		return x * x + y * y + z * z;
	}

	public Vector3d normalize() {
		double determinant = Math.sqrt(x * x + y * y + z * z);
		if (determinant == 0) {
			return new Vector3d();
		}
		return new Vector3d(x / determinant, y / determinant, z / determinant);
	}

	public void normalizeSelf() {
		double det = Math.sqrt(x * x + y * y + z * z);
		if (det == 0) {
			x = 0;
			y = 0;
			z = 0;
		} else {
			x /= det;
			y /= det;
			z /= det;
		}
	}

	public Vector3d invert() {
		return new Vector3d(-x, -y, -z);
	}

	public void minimumSelf(Vector3d p) {
		if (x > p.x) {
			x = p.x;
		}
		if (y > p.y) {
			y = p.y;
		}
		if (z > p.z) {
			z = p.z;
		}
	}

	public void maximumSelf(Vector3d p) {
		if (x < p.x) {
			x = p.x;
		}
		if (y < p.y) {
			y = p.y;
		}
		if (z < p.z) {
			z = p.z;
		}
	}

	@Override
	public int compareTo(Vector3d o) {
		int diff = Double.compare(z, o.z);
		if (diff != 0) {
			return diff;
		}
		diff = Double.compare(y, o.y);
		if (diff != 0) {
			return diff;
		}
		return Double.compare(x, o.x);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Vector3d other = (Vector3d)obj;
		return x == other.x && y == other.y && z == other.z;
	}

	@Override
	public int hashCode() {
		int hash = 0x811C9DC5;
		hash ^= Double.hashCode(x);
		hash *= 0x01000193;
		hash ^= Double.hashCode(y);
		hash *= 0x01000193;
		hash ^= Double.hashCode(z);
		hash *= 0x01000193;
		return hash;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ')';
	}
}
