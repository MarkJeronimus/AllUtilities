package nl.airsupplies.utilities.container;

/**
 * A homogeneous matrix in 3D.
 *
 * @author Mark Jeronimus
 */
// Created 2005-10-21
public class Matrix4f {
	public float x0;
	public float x1;
	public float x2;
	public float x3;
	public float y0;
	public float y1;
	public float y2;
	public float y3;
	public float z0;
	public float z1;
	public float z2;
	public float z3;

	public Matrix4f() {
		x0 = y1 = z2 = 1;
		x1 = x2 = x3 = y0 = y2 = y3 = z0 = z1 = z3 = 0;
	}

	public Matrix4f(float x0, float x1, float x2, float x3,
	                float y0, float y1, float y2, float y3,
	                float z0, float z1, float z2, float z3) {
		this.x0 = x0;
		this.x1 = x1;
		this.x2 = x2;
		this.x3 = x3;
		this.y0 = y0;
		this.y1 = y1;
		this.y2 = y2;
		this.y3 = y3;
		this.z0 = z0;
		this.z1 = z1;
		this.z2 = z2;
		this.z3 = z3;
	}

	public static Matrix4f makeRotationMatrix(Vector3f v, float a) {
		// Using Rodriques' rotation formula (rewritten).
		float c = (float)Math.cos(a);
		float s = (float)Math.sin(a);
		a = 1 - c;

		float xx = v.x * v.x;
		float xy = v.x * v.y;
		float xz = v.x * v.z;
		float yy = v.y * v.y;
		float yz = v.y * v.z;
		float zz = v.z * v.z;

		return new Matrix4f(xx + (1 - xx) * c,
		                    xy * a - v.z * s,
		                    xz * a + v.y * s,
		                    0,
		                    xy * a + v.z * s,
		                    yy + (1 - yy) * c,
		                    yz * a - v.x * s,
		                    0,
		                    xz * a - v.y * s,
		                    yz * a + v.x * s,
		                    zz + (1 - zz) * c,
		                    0);
	}

	public void setIdentity() {
		x0 = y1 = z2 = 1;
		x1 = x2 = x3 = y0 = y2 = y3 = z0 = z1 = z3 = 0;
	}

	public void set(float x0, float x1, float x2, float x3,
	                float y0, float y1, float y2, float y3,
	                float z0, float z1, float z2, float z3) {
		this.x0 = x0;
		this.x1 = x1;
		this.x2 = x2;
		this.x3 = x3;
		this.y0 = y0;
		this.y1 = y1;
		this.y2 = y2;
		this.y3 = y3;
		this.z0 = z0;
		this.z1 = z1;
		this.z2 = z2;
		this.z3 = z3;
	}

	public void set(Matrix4f m) {
		x0 = m.x0;
		x1 = m.x1;
		x2 = m.x2;
		x3 = m.x3;
		y0 = m.y0;
		y1 = m.y1;
		y2 = m.y2;
		y3 = m.y3;
		z0 = m.z0;
		z1 = m.z1;
		z2 = m.z2;
		z3 = m.z3;
	}

	/**
	 * Translate (move) the non-inverse matrix.
	 */
	public void translate(float x, float y, float z) {
		x3 += x;
		y3 += y;
		z3 += z;
	}

	/**
	 * Scale the non-inverse matrix.
	 */
	public void scale(float f) {
		x0 *= f;
		x1 *= f;
		x2 *= f;
		x3 *= f;
		y0 *= f;
		y1 *= f;
		y2 *= f;
		y3 *= f;
		z0 *= f;
		z1 *= f;
		z2 *= f;
		z3 *= f;
	}

	/**
	 * Scale the non-inverse matrix.
	 */
	public void scale(float x, float y, float z) {
		x0 *= x;
		x1 *= x;
		x2 *= x;
		x3 *= x;
		y0 *= y;
		y1 *= y;
		y2 *= y;
		y3 *= y;
		z0 *= z;
		z1 *= z;
		z2 *= z;
		z3 *= z;
	}

	public Matrix4f recip() {
		float determinant =
				1 / (x0 * y1 * z2 - x0 * z1 * y2 - y0 * x1 * z2 + y0 * z1 * x2 + z0 * x1 * y2 - z0 * y1 * x2);

		float x0b = x0;
		float x1b = x1;
		float x2b = x2;
		float y0b = y0;
		float y1b = y1;
		float y2b = y2;
		float z0b = z0;
		float z1b = z1;
		float z2b = z2;

		float x0 = (y1b * z2b - z1b * y2b) * determinant;
		float x1 = (z1b * x2b - x1b * z2b) * determinant;
		float x2 = (x1b * y2b - y1b * x2b) * determinant;

		float y0 = (y2b * z0b - z2b * y0b) * determinant;
		float y1 = (z2b * x0b - x2b * z0b) * determinant;
		y2b = (x2b * y0b - y2b * x0b) * determinant;

		float z0 = (y0b * z1b - z0b * y1b) * determinant;
		z1b = (z0b * x1b - x0b * z1b) * determinant;
		z2b = (x0b * y1b - y0b * x1b) * determinant;

		return new Matrix4f(x0, x1, x2, -(x0 * x3 + x1 * y3 + x2 * z3),
		                    y0, y1, y2b, -(y0 * x3 + y1 * y3 + y2b * z3),
		                    z0, z1b, z2b, -(z0 * x3 + z1b * y3 + z2b * z3));
	}

	// Speed classification: Frame
	public void recipSelf() {
		float determinant =
				1 / (x0 * y1 * z2 - x0 * z1 * y2 - y0 * x1 * z2 + y0 * z1 * x2 + z0 * x1 * y2 - z0 * y1 * x2);

		float x0b = x0;
		float x1b = x1;
		float x2b = x2;
		float y0b = y0;
		float y1b = y1;
		float y2b = y2;
		float z0b = z0;
		float z1b = z1;
		float z2b = z2;

		x0 = (y1b * z2b - z1b * y2b) * determinant;
		x1 = (z1b * x2b - x1b * z2b) * determinant;
		x2 = (x1b * y2b - y1b * x2b) * determinant;

		y0 = (y2b * z0b - z2b * y0b) * determinant;
		y1 = (z2b * x0b - x2b * z0b) * determinant;
		y2 = (x2b * y0b - y2b * x0b) * determinant;

		z0 = (y0b * z1b - z0b * y1b) * determinant;
		z1 = (z0b * x1b - x0b * z1b) * determinant;
		z2 = (x0b * y1b - y0b * x1b) * determinant;

		x3 = -(x0 * x3 + x1 * y3 + x2 * z3);
		y3 = -(y0 * x3 + y1 * y3 + y2 * z3);
		z3 = -(z0 * x3 + z1 * y3 + z2 * z3);
	}

	public float getX(float u, float v, float w) {
		return x0 * u + x1 * v + x2 * w + x3;
	}

	public float getY(float u, float v, float w) {
		return y0 * u + y1 * v + y2 * w + y3;
	}

	public float getZ(float u, float v, float w) {
		return z0 * u + z1 * v + z2 * w + z3;
	}

	public Vector3f mul(Vector3f v) {
		return new Vector3f(x0 * v.x + x1 * v.y + x2 * v.z + x3,
		                    y0 * v.x + y1 * v.y + y2 * v.z + y3,
		                    z0 * v.x + z1 * v.y + z2 * v.z + z3);
	}

	public Matrix4f mul(Matrix4f m) {
		return new Matrix4f(x0 * m.x0 + x1 * m.y0 + x2 * m.z0,
		                    x0 * m.x1 + x1 * m.y1 + x2 * m.z1,
		                    x0 * m.x2 + x1 * m.y2 + x2 * m.z2,
		                    x0 * m.x3 + x1 * m.y3 + x2 * m.z3 + x3,

		                    y0 * m.x0 + y1 * m.y0 + y2 * m.z0,
		                    y0 * m.x1 + y1 * m.y1 + y2 * m.z1,
		                    y0 * m.x2 + y1 * m.y2 + y2 * m.z2,
		                    y0 * m.x3 + y1 * m.y3 + y2 * m.z3 + y3,

		                    z0 * m.x0 + z1 * m.y0 + z2 * m.z0,
		                    z0 * m.x1 + z1 * m.y1 + z2 * m.z1,
		                    z0 * m.x2 + z1 * m.y2 + z2 * m.z2,
		                    z0 * m.x3 + z1 * m.y3 + z2 * m.z3 + z3);
	}

	public void mulSelf(Matrix4f m) {
		float t0;
		float t1;

		x3 = x0 * m.x3 + x1 * m.y3 + x2 * m.z3 + x3;
		t0 = x0 * m.x0 + x1 * m.y0 + x2 * m.z0;
		t1 = x0 * m.x1 + x1 * m.y1 + x2 * m.z1;
		x2 = x0 * m.x2 + x1 * m.y2 + x2 * m.z2;
		x0 = t0;
		x1 = t1;

		y3 = y0 * m.x3 + y1 * m.y3 + y2 * m.z3 + y3;
		t0 = y0 * m.x0 + y1 * m.y0 + y2 * m.z0;
		t1 = y0 * m.x1 + y1 * m.y1 + y2 * m.z1;
		y2 = y0 * m.x2 + y1 * m.y2 + y2 * m.z2;
		y0 = t0;
		y1 = t1;

		z3 = z0 * m.x3 + z1 * m.y3 + z2 * m.z3 + z3;
		t0 = z0 * m.x0 + z1 * m.y0 + z2 * m.z0;
		t1 = z0 * m.x1 + z1 * m.y1 + z2 * m.z1;
		z2 = z0 * m.x2 + z1 * m.y2 + z2 * m.z2;
		z0 = t0;
		z1 = t1;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[\n[" +
		       x0 + '\t' + x1 + '\t' + x2 + '\t' + x3 + "]\n[" +
		       y0 + '\t' + y1 + '\t' + y2 + '\t' + y3 + "]\n[" +
		       z0 + '\t' + z1 + '\t' + z2 + '\t' + z3 + "]\n[0.0\t0.0\t0.0\t1.0]\n]";
	}
}
