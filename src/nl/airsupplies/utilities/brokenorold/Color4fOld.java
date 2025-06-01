package nl.airsupplies.utilities.brokenorold;

import java.awt.Color;

/**
 * @author Mark Jeronimus
 */
// Created 2005-10-03
public class Color4fOld extends Color3fOld {
	public float a;

	/**
	 * Create a black
	 */
	public Color4fOld() {
		r = 0;
		g = 0;
		b = 0;
		a = 0;
	}

	/**
	 * Create a specified
	 */
	public Color4fOld(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	/**
	 * Create a specified
	 */
	public Color4fOld(int r, int g, int b, int a) {
		this.r = r / 255.0f;
		this.g = g / 255.0f;
		this.b = b / 255.0f;
		this.a = a / 255.0f;
	}

	/**
	 * Create a  from packed ARGB quadruplet
	 */
	public Color4fOld(int argb) {
		a = (argb >> 24 & 0xFF) / 255.0f;
		r = (argb >> 16 & 0xFF) / 255.0f;
		g = (argb >> 8 & 0xFF) / 255.0f;
		b = (argb & 0xFF) / 255.0f;
	}

	/**
	 * Create a  from an awt {@link Color}
	 */
	public Color4fOld(Color color) {
		int argb = color.getRGB();
		a = (argb >> 24 & 0xFF) / 255.0f;
		r = (argb >> 16 & 0xFF) / 255.0f;
		g = (argb >> 8 & 0xFF) / 255.0f;
		b = (argb & 0xFF) / 255.0f;
	}

	/**
	 * Create a copy of another
	 */
	public Color4fOld(Color4fOld color) {
		r = color.r;
		g = color.g;
		b = color.b;
		a = color.a;
	}

	public void set(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public void set(int r, int g, int b, int a) {
		this.r = r / 255.0f;
		this.g = g / 255.0f;
		this.b = b / 255.0f;
		this.a = a / 255.0f;
	}

	@Override
	public void set(int argb) {
		a = (argb >> 24 & 0xFF) / 255.0f;
		r = (argb >> 16 & 0xFF) / 255.0f;
		g = (argb >> 8 & 0xFF) / 255.0f;
		b = (argb & 0xFF) / 255.0f;
	}

	public void set(Color4fOld color) {
		r = color.r;
		g = color.g;
		b = color.b;
		a = color.a;
	}

	@Override
	public void set(Color color) {
		int argb = color.getRGB();
		a = (argb >> 24 & 0xFF) / 255.0f;
		r = (argb >> 16 & 0xFF) / 255.0f;
		g = (argb >> 8 & 0xFF) / 255.0f;
		b = (argb & 0xFF) / 255.0f;
	}

	@Override
	public void clip() {
		r = Math.max(0, Math.min(r, 1));
		g = Math.max(0, Math.min(g, 1));
		b = Math.max(0, Math.min(b, 1));
		a = Math.max(0, Math.min(a, 1));
	}

	@Override
	public void clip(float min, float max) {
		r = Math.max(min, Math.min(r, max));
		g = Math.max(min, Math.min(g, max));
		b = Math.max(min, Math.min(b, max));
		a = Math.max(min, Math.min(a, max));
	}

	@Override
	public void clipMin(float min) {
		r = Math.max(min, r);
		g = Math.max(min, g);
		b = Math.max(min, b);
		a = Math.max(min, a);
	}

	@Override
	public void clipMax(float max) {
		r = Math.min(r, max);
		g = Math.min(g, max);
		b = Math.min(b, max);
		a = Math.min(a, max);
	}

	@Override
	public boolean isClipping() {
		return r < 0 || r > 1 || g < 0 || g > 1 || b < 0 || b > 1 || a < 0 || a > 1;
	}

	@Override
	public int toInteger() {
		int ra = (int)(r * 255 + 0.5);
		int ga = (int)(g * 255 + 0.5);
		int ba = (int)(b * 255 + 0.5);
		int aa = (int)(a * 255 + 0.5);

		ra = Math.max(0, Math.min(ra, 255));
		ga = Math.max(0, Math.min(ga, 255));
		ba = Math.max(0, Math.min(ba, 255));
		aa = Math.max(0, Math.min(aa, 255));

		return (((aa << 8) + ra << 8) + ga << 8) + ba;
	}

	@Override
	public int compareTo(Color3fOld o) {
		int diff = Float.compare(r, o.r);
		if (diff != 0) {
			return diff;
		}
		diff = Float.compare(g, o.g);
		if (diff != 0) {
			return diff;
		}
		diff = Float.compare(b, o.b);
		if (!(o instanceof Color4fOld) || diff != 0) {
			return diff;
		}
		return Float.compare(a, ((Color4fOld)o).a);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Color4fOld) {
			Color4fOld col = (Color4fOld)other;
			return col.r == r && col.g == g && col.b == b && col.a == a;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hash = 0x811C9DC5;
		hash ^= Float.hashCode(r);
		hash *= 0x01000193;
		hash ^= Float.hashCode(g);
		hash *= 0x01000193;
		hash ^= Float.hashCode(b);
		hash *= 0x01000193;
		hash ^= Float.hashCode(a);
		hash *= 0x01000193;
		return hash;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '[' + r + ", " + g + ", " + b + ", " + a + ']';
	}
}
