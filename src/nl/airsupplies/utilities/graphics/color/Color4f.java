
package nl.airsupplies.utilities.graphics.color;

import java.awt.Color;

import nl.airsupplies.utilities.NumberUtilities;

/**
 * @author Mark Jeronimus
 */
// Created 2005-10-03
public class Color4f {
	public static final Color4f BLACK_TRANSPARENT = new Color4f(0.0f, 0.0f, 0.0f, 0.0f);
	public static final Color4f BLACK             = new Color4f(0.0f, 0.0f, 0.0f, 1.0f);
	public static final Color4f WHITE             = new Color4f(1.0f, 1.0f, 1.0f, 1.0f);

	public final float r;
	public final float g;
	public final float b;
	public final float a;

	/**
	 * Construct from an AWT {@link Color}
	 */
	public Color4f(Color color) {
		this(color.getRGB());
	}

	/**
	 * Construct from packed ARGB quadruplet
	 */
	public Color4f(int argb) {
		this(argb >> 24 & 0xFF,
		     argb >> 16 & 0xFF,
		     argb >> 8 & 0xFF,
		     argb & 0xFF);
	}

	public Color4f(int r, int g, int b, int a) {
		this(r / 255.0f,
		     g / 255.0f,
		     b / 255.0f,
		     a / 255.0f);
	}

	public Color4f(Color3f other, float a) {
		this.r = other.r;
		this.g = other.g;
		this.b = other.b;
		this.a = a;
	}

	public Color4f(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public float getGrayValue() {
		return 0.299f * r + 0.587f * g + 0.114f * b;
	}

	/**
	 * Inverts the color but not the alpha channel.
	 */
	public Color4f negative() {
		return new Color4f(1 - r,
		                   1 - g,
		                   1 - b,
		                   a);
	}

	public Color4f lerp(Color4f other, float f) {
		return new Color4f(r + (other.r - r) * f,
		                   g + (other.g - g) * f,
		                   b + (other.b - b) * f,
		                   a + (other.a - a) * f);
	}

	public Color4f lerp(Color4f other, Color4f f) {
		return new Color4f(r + (other.r - r) * f.r,
		                   g + (other.g - g) * f.g,
		                   b + (other.b - b) * f.b,
		                   a + (other.a - a) * f.a);
	}

	public float getMax() {
		return Math.max(Math.max(r, g), b);
	}

	public float getMin() {
		return Math.min(Math.min(r, g), b);
	}

	public Color4f clip() {
		return new Color4f(NumberUtilities.clamp(r, 0.0f, 1.0f),
		                   NumberUtilities.clamp(g, 0.0f, 1.0f),
		                   NumberUtilities.clamp(b, 0.0f, 1.0f),
		                   NumberUtilities.clamp(a, 0.0f, 1.0f));
	}

	public Color4f clamp(float min, float max) {
		return new Color4f(NumberUtilities.clamp(r, min, max),
		                   NumberUtilities.clamp(g, min, max),
		                   NumberUtilities.clamp(b, min, max),
		                   NumberUtilities.clamp(a, min, max));
	}

	public boolean isClipping() {
		return r < 0.0f || r > 1.0f ||
		       g < 0.0f || g > 1.0f ||
		       b < 0.0f || b > 1.0f ||
		       a < 0.0f || a > 1.0f;
	}

	public int toInteger() {
		return NumberUtilities.clamp((int)(a * 255.0f + 0.5f), 0, 255) << 24 |
		       NumberUtilities.clamp((int)(r * 255.0f + 0.5f), 0, 255) << 16 |
		       NumberUtilities.clamp((int)(g * 255.0f + 0.5f), 0, 255) << 8 |
		       NumberUtilities.clamp((int)(b * 255.0f + 0.5f), 0, 255);
	}

	public Color toColor() {
		return new Color(toInteger());
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof Color4f col &&
		       col.r == r &&
		       col.g == g &&
		       col.b == b &&
		       col.a == a;

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
		return getClass().getSimpleName() + '(' + r + ", " + g + ", " + b + ", " + a + ')';
	}
}
