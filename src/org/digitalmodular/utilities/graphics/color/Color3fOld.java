/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.digitalmodular.utilities.graphics.color;

import java.awt.Color;

/**
 * @author Mark Jeronimus
 */
// Created 2005-10-03
public class Color3fOld implements Comparable<Color3fOld> {
	public float r;
	public float g;
	public float b;

	/**
	 * Create a black
	 */
	public Color3fOld() {
		r = 0;
		g = 0;
		b = 0;
	}

	/**
	 * Create a specified
	 */
	public Color3fOld(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	/**
	 * Create a specified
	 */
	public Color3fOld(int r, int g, int b) {
		this.r = r / 255.0f;
		this.g = g / 255.0f;
		this.b = b / 255.0f;
	}

	/**
	 * Create a  from packed RGB triplet
	 */
	public Color3fOld(int rgb) {
		r = (rgb >> 16 & 0xFF) / 255.0f;
		g = (rgb >> 8 & 0xFF) / 255.0f;
		b = (rgb & 0xFF) / 255.0f;
	}

	/**
	 * Create a  from an awt {@link Color}
	 */
	public Color3fOld(Color color) {
		int rgb = color.getRGB();
		r = (rgb >> 16 & 0xFF) / 255.0f;
		g = (rgb >> 8 & 0xFF) / 255.0f;
		b = (rgb & 0xFF) / 255.0f;
	}

	/**
	 * Create a copy of another {@code Color3f}
	 */
	public Color3fOld(Color3fOld color) {
		r = color.r;
		g = color.g;
		b = color.b;
	}

	public void set(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public void set(int r, int g, int b) {
		this.r = r / 255.0f;
		this.g = g / 255.0f;
		this.b = b / 255.0f;
	}

	public void set(int rgb) {
		r = (rgb >> 16 & 0xFF) / 255.0f;
		g = (rgb >> 8 & 0xFF) / 255.0f;
		b = (rgb & 0xFF) / 255.0f;
	}

	public void set(Color3fOld color) {
		r = color.r;
		g = color.g;
		b = color.b;
	}

	public void set(Color color) {
		int rgb = color.getRGB();
		r = (rgb >> 16 & 0xFF) / 255.0f;
		g = (rgb >> 8 & 0xFF) / 255.0f;
		b = (rgb & 0xFF) / 255.0f;
	}

	public float getGrayValue() {
		return 0.299f * r + 0.587f * g + 0.114f * b;
	}

	public void negative() {
		r = 1 - r;
		g = 1 - g;
		b = 1 - b;
	}

	public void add(int r, float g, float b) {
		this.r += r;
		this.g += g;
		this.b += b;
	}

	public void add(int rgb) {
		r += (rgb >> 16 & 0xFF) / 255.0f;
		g += (rgb >> 8 & 0xFF) / 255.0f;
		b += (rgb & 0xFF) / 255.0f;
	}

	public void add(Color3fOld c) {
		r += c.r;
		g += c.g;
		b += c.b;
	}

	public void sub(Color3fOld c) {
		r -= c.r;
		g -= c.g;
		b -= c.b;
	}

	public void mul(float f) {
		r *= f;
		g *= f;
		b *= f;
	}

	public void mul(float rf, float gf, float bf) {
		r *= rf;
		g *= gf;
		b *= bf;
	}

	public void div(Color3fOld f) {
		r /= f.r;
		g /= f.g;
		b /= f.b;
	}

	public void lerp(Color3fOld other, float f) {
		r += (other.r - r) * f;
		g += (other.g - g) * f;
		b += (other.b - b) * f;
	}

	public void lerp(Color3fOld other, Color3fOld f) {
		r += (other.r - r) * f.r;
		g += (other.g - g) * f.g;
		b += (other.b - b) * f.b;
	}

	public void pow(float power) {
		r = (float)Math.pow(r, power);
		g = (float)Math.pow(g, power);
		b = (float)Math.pow(b, power);
	}

	public float getMax() {
		if (r > g) {
			return Math.max(r, b);
		}
		return Math.max(g, b);
	}

	public float getMin() {
		if (r < g) {
			return Math.min(r, b);
		}
		return Math.min(g, b);
	}

	public void clip() {
		if (r > 1) {
			r = 1;
		} else if (r < 0) {
			r = 0;
		}
		if (g > 1) {
			g = 1;
		} else if (g < 0) {
			g = 0;
		}
		if (b > 1) {
			b = 1;
		} else if (b < 0) {
			b = 0;
		}
	}

	public void clip(float min, float max) {
		if (r > max) {
			r = max;
		} else if (r < min) {
			r = min;
		}
		if (g > max) {
			g = max;
		} else if (g < min) {
			g = min;
		}
		if (b > max) {
			b = max;
		} else if (b < min) {
			b = min;
		}
	}

	public void clipMin(float min) {
		if (r < min) {
			r = min;
		}
		if (g < min) {
			g = min;
		}
		if (b < min) {
			b = min;
		}
	}

	public void clipMax(float max) {
		if (r > max) {
			r = max;
		}
		if (g > max) {
			g = max;
		}
		if (b > max) {
			b = max;
		}
	}

	public boolean isClipping() {
		return r < 0 || r > 1 || g < 0 || g > 1 || b < 0 || b > 1;
	}

	public int toInteger() {
		int ra = (int)(r * 255 + 0.5);
		int ga = (int)(g * 255 + 0.5);
		int ba = (int)(b * 255 + 0.5);

		if (ra < 0) {
			ra = 0;
		} else if (ra > 255) {
			ra = 255;
		}

		if (ga < 0) {
			ga = 0;
		} else if (ga > 255) {
			ga = 255;
		}

		if (ba < 0) {
			ba = 0;
		} else if (ba > 255) {
			ba = 255;
		}

		return ba + (ga + (ra << 8) << 8);
	}

	public Color toColor() {
		int ra = (int)(r * 255 + 0.5);
		int ga = (int)(g * 255 + 0.5);
		int ba = (int)(b * 255 + 0.5);

		if (ra < 0) {
			ra = 0;
		} else if (ra > 255) {
			ra = 255;
		}

		if (ga < 0) {
			ga = 0;
		} else if (ga > 255) {
			ga = 255;
		}

		if (ba < 0) {
			ba = 0;
		} else if (ba > 255) {
			ba = 255;
		}

		return new Color(ra, ga, ba);
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
		return Float.compare(b, o.b);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Color3fOld)) {
			return false;
		}

		Color3fOld col = (Color3fOld)other;
		return col.r == r && col.g == g && col.b == b;
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
