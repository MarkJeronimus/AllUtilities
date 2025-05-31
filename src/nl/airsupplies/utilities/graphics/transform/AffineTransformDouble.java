package nl.airsupplies.utilities.graphics.transform;

import java.awt.geom.AffineTransform;

/**
 * Java's AffineTransform doesn't have any useful interface for translating individual coordinates, and the inside are
 * inaccessible when inheriting, so here's my fixed version. I only added some useful methods. If you find some missing,
 * add them yourself.
 *
 * @author Mark Jeronimus
 */
// Created 2009-10-02
public class AffineTransformDouble implements TransformDouble {
	public double x0;
	public double y0;
	public double x1;
	public double y1;
	public double x2;
	public double y2;
	public double rx0;
	public double ry0;
	public double rx1;
	public double ry1;
	public double rx2;
	public double ry2;

	// These methods define the transform.
	public AffineTransformDouble() {
		setIdentity();
		initializeReverse();
	}

	public AffineTransformDouble(double x0, double y0, double x1, double y1, double x2, double y2) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		initializeReverse();
	}

	public AffineTransformDouble(AffineTransformDouble tr) {
		x0 = tr.x0;
		y0 = tr.y0;
		x1 = tr.x1;
		y1 = tr.y1;
		x2 = tr.x2;
		y2 = tr.y2;
		initializeReverse();
	}

	@Override
	public void setIdentity() {
		x0 = 1;
		y0 = 0;
		x1 = 0;
		y1 = 1;
		x2 = 0;
		y2 = 0;
	}

	public void set(double x0, double y0, double x1, double y1, double x2, double y2) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public void set(TransformDouble tr) {
		if (tr instanceof AffineTransformDouble) {
			AffineTransformDouble t = (AffineTransformDouble)tr;
			x0 = t.x0;
			y0 = t.y0;
			x1 = t.x1;
			y1 = t.y1;
			x2 = t.x2;
			y2 = t.y2;
		} else if (tr instanceof HomotheticTransformDouble) {
			HomotheticTransformDouble t = (HomotheticTransformDouble)tr;
			x0 = t.sx;
			y0 = 0;
			x1 = 0;
			y1 = t.sy;
			x2 = t.tx;
			y2 = t.ty;
		} else {
			throw new UnsupportedOperationException("Not supported yet");
		}
	}

	public void setRotation(double theta) {
		double s = Math.sin(theta);
		double c = Math.cos(theta);
		x0 = c;
		x1 = -s;
		y0 = s;
		y1 = c;
	}

	public void concatenate(TransformDouble tr) {
		if (tr instanceof AffineTransformDouble) {
			AffineTransformDouble t = (AffineTransformDouble)tr;

			double z0 = x0;
			double z1 = x1;
			x2 += z0 * t.x2 + z1 * t.y2;
			x0 = z0 * t.x0 + z1 * t.y0;
			x1 = z0 * t.x1 + z1 * t.y1;

			z0 = y0;
			z1 = y1;
			y2 += z0 * t.x2 + z1 * t.y2;
			y0 = z0 * t.x0 + z1 * t.y0;
			y1 = z0 * t.x1 + z1 * t.y1;
		} else if (tr instanceof HomotheticTransformDouble) {
			HomotheticTransformDouble t = (HomotheticTransformDouble)tr;
			x2 += t.tx * x0 + t.ty * x1;
			x0 *= t.sx;
			x1 *= t.sy;

			y2 += t.tx * y0 + t.ty * y1;
			y0 *= t.sx;
			y1 *= t.sy;
		} else {
			throw new UnsupportedOperationException("Not supported yet");
		}
	}

	public void translateWorld(double dx, double dy) {
		x2 += dx;
		y2 += dy;
	}

	public void translateLocal(double dx, double dy) {
		x2 += dx * x0 + dy * x1;
		y2 += dx * y0 + dy * y1;
	}

	public void scaleWorld(double sx, double sy) {
		x0 *= sx;
		y0 *= sy;
		x1 *= sx;
		y1 *= sy;
		x2 *= sx;
		y2 *= sy;
	}

	public void scaleLocal(double sx, double sy) {
		x0 *= sx;
		y0 *= sy;
		x1 *= sx;
		y1 *= sy;
	}

	public void rotateWorld(double theta) {
		double c = Math.cos(theta);
		double s = Math.sin(theta);
		double x = x0;
		double y = y0;
		x0 = c * x - s * y;
		y0 = s * x + c * y;
		x  = x1;
		y  = y1;
		x1 = c * x - s * y;
		y1 = s * x + c * y;
		x  = x2;
		y  = y2;
		x2 = c * x - s * y;
		y2 = s * x + c * y;
	}

	public void rotateLocal(double theta) {
		double c = Math.cos(theta);
		double s = Math.sin(theta);
		double x = x0;
		double u = x1;
		x0 = s * u + c * x;
		x1 = c * u - s * x;
		x  = y0;
		u  = y1;
		y0 = s * u + c * x;
		y1 = c * u - s * x;
	}

	/**
	 * Transforms relative to the real space.
	 */
	public void concatPre(AffineTransformDouble t) {
		double x = x0;
		double y = x1;
		x0 = t.x0 * x + t.y0 * y;
		x1 = t.x1 * x + t.y1 * y;
		x2 += t.x2 * x + t.y2 * y;

		x  = y0;
		y  = y1;
		y0 = t.x0 * x + t.y0 * y;
		y1 = t.x2 * x + t.y1 * y;
		y2 += t.x2 * x + t.y2 * y;
	}

	/**
	 * Transforms relative to the transformed space.
	 */
	public void concat(AffineTransformDouble t) {
		double x = x0;
		double y = y0;
		x0 = x * t.x0 + y * t.x1;
		y0 = x * t.y0 + y * t.y1;

		x  = x1;
		y  = y1;
		x1 = x * t.x0 + y * t.x1;
		y1 = x * t.y0 + y * t.y1;

		x  = x2;
		y  = y2;
		x2 = x * t.x0 + y * t.x1 + t.x2;
		y2 = x * t.y0 + y * t.y1 + t.y2;
	}

	/**
	 * Calculates the reverse of the matrix representation of this affine transformation. This always needs to be
	 * called
	 * before calls to reverse$() functions when the transform have changed. No need to call directly after a
	 * constructor.
	 */
	public void initializeReverse() {
		double det = x0 * y1 - x1 * y0;
		if (det == 0) {
			throw new IllegalStateException(
					"Transformation is not invertible because it is singular. (it projects all points to a single " +
					"line" +
					" or " +
					"point)");
		}

		det = 1 / det;

		rx0 = y1 * det;
		rx1 = -x1 * det;
		ry0 = -y0 * det;
		ry1 = x0 * det;
		rx2 = -(rx0 * x2 + rx1 * y2);
		ry2 = -(ry0 * x2 + ry1 * y2);
	}

	// These methods transform coordinates.
	@Override
	public double transformX(double x, double y) {
		return x0 * x + x1 * y + x2;
	}

	@Override
	public double transformY(double x, double y) {
		return y0 * x + y1 * y + y2;
	}

	@Override
	public double transformRelativeX(double x, double y) {
		return x0 * x + x1 * y;
	}

	@Override
	public double transformRelativeY(double x, double y) {
		return y0 * x + y1 * y;
	}

	@Override
	public double transformR(double r) {
		return r * Math.hypot(x0, x1);
	}

	@Override
	public double reverseX(double x, double y) {
		return rx0 * x + rx1 * y + rx2;
	}

	@Override
	public double reverseY(double x, double y) {
		return ry0 * x + ry1 * y + ry2;
	}

	@Override
	public double reverseRelativeX(double x, double y) {
		return rx0 * x + rx1 * y;
	}

	@Override
	public double reverseRelativeY(double x, double y) {
		return ry0 * x + ry1 * y;
	}

	@Override
	public double reverseR(double r) {
		return r * Math.hypot(rx0, rx1);
	}

	@Override
	public AffineTransform getSwingTransform() {
		return new AffineTransform(x0, y0, x1, y1, x2, y2);
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append('[');
		out.append(x0);
		out.append(' ');
		out.append(x1);
		out.append(' ');
		out.append(x2);
		out.append(']');
		out.append('\n');
		out.append('[');
		out.append(y0);
		out.append(' ');
		out.append(y1);
		out.append(' ');
		out.append(y2);
		out.append(']');
		return out.toString();
	}
}
