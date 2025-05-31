package nl.airsupplies.utilities.graphics.color;

import java.awt.Color;

import nl.airsupplies.utilities.NumberUtilities;

/**
 * @author Mark Jeronimus
 */
// Created 2005-10-03
public class Color3f implements Comparable<Color3f> {
	public static final Color3f BLACK = new Color3f(0.0f, 0.0f, 0.0f);
	public static final Color3f WHITE = new Color3f(1.0f, 1.0f, 1.0f);

	public final float r;
	public final float g;
	public final float b;

	/**
	 * Construct from an AWT {@link Color}
	 */
	public Color3f(Color color) {
		this(color.getRGB());
	}

	/**
	 * Construct from packed RGB triplet
	 */
	public Color3f(int rgb) {
		this(rgb >> 16 & 0xFF,
		     rgb >> 8 & 0xFF,
		     rgb & 0xFF);
	}

	public Color3f(int r, int g, int b) {
		this(r / 255.0f,
		     g / 255.0f,
		     b / 255.0f);
	}

	public Color3f(Color4f other) {
		this.r = other.r;
		this.g = other.g;
		this.b = other.b;
	}

	public Color3f(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public float getGrayValue() {
		return 0.299f * r + 0.587f * g + 0.114f * b;
	}

	public Color3f setR(float r) {
		return new Color3f(r, g, b);
	}

	public Color3f setG(float g) {
		return new Color3f(r, g, b);
	}

	public Color3f setB(float b) {
		return new Color3f(r, g, b);
	}

	public Color3f negative() {
		return new Color3f(1 - r,
		                   1 - g,
		                   1 - b);
	}

	public Color3f add(int r, int g, int b) {
		return new Color3f(this.r + r / 255.0f,
		                   this.g + g / 255.0f,
		                   this.b + b / 255.0f);
	}

	public Color3f add(float r, float g, float b) {
		return new Color3f(this.r + r,
		                   this.g + g,
		                   this.b + b);
	}

	public Color3f add(int rgb) {
		return new Color3f(r + (rgb >> 16 & 0xFF) / 255.0f,
		                   g + (rgb >> 8 & 0xFF) / 255.0f,
		                   b + (rgb & 0xFF) / 255.0f);
	}

	public Color3f add(Color3f c) {
		return new Color3f(r + c.r,
		                   g + c.g,
		                   b + c.b);
	}

	public Color3f sub(Color3f c) {
		return new Color3f(r - c.r,
		                   g - c.g,
		                   b - c.b);
	}

	public Color3f mul(float f) {
		return new Color3f(r * f,
		                   g * f,
		                   b * f);
	}

	public Color3f mul(float rf, float gf, float bf) {
		return new Color3f(r * rf,
		                   g * gf,
		                   b * bf);
	}

	public Color3f div(float f) {
		return new Color3f(r / f,
		                   g / f,
		                   b / f);
	}

	public Color3f div(Color3f f) {
		return new Color3f(r / f.r,
		                   g / f.g,
		                   b / f.b);
	}

	public Color3f lerp(Color3f other, float f) {
		return new Color3f(r + (other.r - r) * f,
		                   g + (other.g - g) * f,
		                   b + (other.b - b) * f);
	}

	public Color3f lerp(Color3f other, Color3f f) {
		return new Color3f(r + (other.r - r) * f.r,
		                   g + (other.g - g) * f.g,
		                   b + (other.b - b) * f.b);
	}

	public Color3f pow(double power) {
		return new Color3f((float)Math.pow(r, power),
		                   (float)Math.pow(g, power),
		                   (float)Math.pow(b, power));
	}

	public float getMax() {
		return Math.max(Math.max(r, g), b);
	}

	public float getMin() {
		return Math.min(Math.min(r, g), b);
	}

	public Color3f clip() {
		return new Color3f(NumberUtilities.clamp(r, 0.0f, 1.0f),
		                   NumberUtilities.clamp(g, 0.0f, 1.0f),
		                   NumberUtilities.clamp(b, 0.0f, 1.0f));
	}

	public Color3f clamp(float min, float max) {
		return new Color3f(NumberUtilities.clamp(r, min, max),
		                   NumberUtilities.clamp(g, min, max),
		                   NumberUtilities.clamp(b, min, max));
	}

	public Color3f min(float min) {
		return new Color3f(Math.min(r, min),
		                   Math.min(g, min),
		                   Math.min(b, min));
	}

	public Color3f min(Color3f other) {
		return new Color3f(Math.min(r, other.r),
		                   Math.min(g, other.g),
		                   Math.min(b, other.b));
	}

	public Color3f max(float max) {
		return new Color3f(Math.max(r, max),
		                   Math.max(g, max),
		                   Math.max(b, max));
	}

	public Color3f max(Color3f other) {
		return new Color3f(Math.max(r, other.r),
		                   Math.max(g, other.g),
		                   Math.max(b, other.b));
	}

	public boolean isClipping() {
		return r < 0.0f || r > 1.0f ||
		       g < 0.0f || g > 1.0f ||
		       b < 0.0f || b > 1.0f;
	}

	public int toInteger() {
		return NumberUtilities.clamp((int)(r * 255.0f + 0.5f), 0, 255) << 16 |
		       NumberUtilities.clamp((int)(g * 255.0f + 0.5f), 0, 255) << 8 |
		       NumberUtilities.clamp((int)(b * 255.0f + 0.5f), 0, 255);
	}

	public Color toColor() {
		return new Color(toInteger());
	}

	@Override
	public int compareTo(Color3f o) {
		int diff = Float.compare(r, o.r);
		if (diff != 0) {
			return diff;
		}

		diff = Float.compare(g, o.g);
		if (diff != 0) {
			return diff;
		}

		return Float.compare(b, o.b);
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof Color3f col &&
		       col.r == r &&
		       col.g == g &&
		       col.b == b;
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
		return hash;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '(' + r + ", " + g + ", " + b + ')';
	}
}
