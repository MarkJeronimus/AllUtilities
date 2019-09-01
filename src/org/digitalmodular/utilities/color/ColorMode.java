/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2019 Mark Jeronimus. All Rights Reversed.
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
 * along with AllUtilities. If not, see <http://www.gnu.org/licenses/>.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.digitalmodular.utilities.color;

import java.awt.Color;

/**
 * @author Mark Jeronimus
 */
// Created 2016-02-28
public enum ColorMode {
	/** Cubic color space with components: Red, Green, Blue. */
	RGB {
		@Override
		public void fromRGB(Color3f src, Color3f dst) {
			dst.set(src);
		}

		@Override
		public void toRGB(Color3f src, Color3f dst) {
			dst.set(src);
		}

		@Override
		public void from(ColorMode srcModel, Color3f src, Color3f dst) {
			throw new UnsupportedOperationException();
		}
	},
	/**
	 * Cylindrical color space with components: Hue, Saturation, Value.
	 * <p>
	 * HSV is synonymous with the later coined HSB (B for Brightness).
	 * <p>
	 * Value is {@code max(r, g, b)}. The primary and secondary colors are in a hexagonal plane at the top of the
	 * hexagonal cylinder.
	 *
	 * @see Color#RGBtoHSB(int, int, int, float[])
	 */
	HSV {
		@Override
		public void fromRGB(Color3f src, Color3f dst) {
			float min    = Math.min(Math.min(src.r, src.g), src.b);
			float max    = Math.max(Math.max(src.r, src.g), src.b);
			float chroma = max - min;

			// Val coding
			dst.b = max;

			// Grayscale?
			if (chroma < 1e-7f || max < 1e-7f) {
				dst.r = 0;
				dst.g = 0;
				return;
			}

			// Sat coding
			dst.g = chroma / max;

			// Hue coding
			if (max == src.r) {
				if (min == src.b) {
					dst.r = (0 + (src.g - src.b) / chroma) / 6f;
				} else {
					dst.r = (6 + (src.g - src.b) / chroma) / 6f;
				}
			} else {
				if (max == src.g) {
					dst.r = (2 + (src.b - src.r) / chroma) / 6f;
				} else {
					dst.r = (4 + (src.r - src.g) / chroma) / 6f;
				}
			}
		}

		@Override
		public void toRGB(Color3f src, Color3f dst) {
			float hue6 = (src.r - (float)Math.floor(src.r)) * 6;
			float sat  = src.g;
			float val  = src.b;

			// Hue coding
			float r = Math.max(0, Math.min(1, Math.abs(hue6 - 3) - 1));
			float g = Math.max(0, Math.min(1, 2 - Math.abs(hue6 - 2)));
			float b = Math.max(0, Math.min(1, 2 - Math.abs(hue6 - 4)));

			// Sat coding
			r = 1 - sat * (1 - r);
			g = 1 - sat * (1 - g);
			b = 1 - sat * (1 - b);

			// Val coding
			dst.r = val * r;
			dst.g = val * g;
			dst.b = val * b;
		}

		@Override
		public void from(ColorMode srcModel, Color3f src, Color3f dst) {
			throw new UnsupportedOperationException();
		}
	},
	/**
	 * Cylindrical color space with components: Hue, Saturation, Lightness.
	 * <p>
	 * Lightness is {@code average(min(r, g, b), max(r, g, b))}. The primary and secondary colors are in a hexagonal
	 * plane at the middle of the hexagonal cylinder.
	 */
	HSL {
		@Override
		public void fromRGB(Color3f src, Color3f dst) {
			float min    = Math.min(Math.min(src.r, src.g), src.b);
			float max    = Math.max(Math.max(src.r, src.g), src.b);
			float chroma = max - min;

			// Lightness
			dst.b = (min + max) / 2;

			// Grayscale?
			if (chroma < 1e-7f || max < 1e-7f || max > 0.9999999f) {
				dst.r = 0;
				dst.g = 0;
				return;
			}

			// Saturation
			dst.g = chroma / (1 - Math.abs(min + max - 1));

			// Hue
			if (max == src.r) {
				if (min == src.b) {
					dst.r = (0 + (src.g - src.b) / chroma) / 6f;
				} else {
					dst.r = (6 + (src.g - src.b) / chroma) / 6f;
				}
			} else {
				if (max == src.g) {
					dst.r = (2 + (src.b - src.r) / chroma) / 6f;
				} else {
					dst.r = (4 + (src.r - src.g) / chroma) / 6f;
				}
			}
		}

		@Override
		public void toRGB(Color3f src, Color3f dst) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void from(ColorMode srcModel, Color3f src, Color3f dst) {
			throw new UnsupportedOperationException();
		}
	},
	/**
	 * Cylindrical color space with components: Hue, Saturation, Intensity.
	 * <p>
	 * Intensity is {@code average(r, g, b)}. The primary and secondary colors are at the corners of the RGB
	 * parallelepiped inscribed in the hexagonal cylinder.
	 */
	HSI {
		@Override
		public void fromRGB(Color3f src, Color3f dst) {
			float min    = Math.min(Math.min(src.r, src.g), src.b);
			float max    = Math.max(Math.max(src.r, src.g), src.b);
			float chroma = max - min;

			// Intensity
			dst.b = (src.r + src.g + src.b) / 3;

			// Grayscale?
			if (chroma < 1e-7f || dst.b < 1e-7f) {
				dst.r = 0;
				dst.g = 0;
				return;
			}

			// Saturation
			dst.g = 1 - min / dst.b;

			// Hue
			if (max == src.r) {
				if (min == src.b) {
					dst.r = (0 + (src.g - src.b) / chroma) / 6f;
				} else {
					dst.r = (6 + (src.g - src.b) / chroma) / 6f;
				}
			} else {
				if (max == src.g) {
					dst.r = (2 + (src.b - src.r) / chroma) / 6f;
				} else {
					dst.r = (4 + (src.r - src.g) / chroma) / 6f;
				}
			}
		}

		@Override
		public void toRGB(Color3f src, Color3f dst) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void from(ColorMode srcModel, Color3f src, Color3f dst) {
			throw new UnsupportedOperationException();
		}
	},
	/**
	 * Cylindrical color space with components: Hue, Saturation, Luminance.
	 * <p>
	 * (Called HSL by w3.org)
	 * <p>
	 * Luminance is the weighted average of R, G, and B, based on their perceived luminance.
	 */
	HSY {
		@Override
		public void fromRGB(Color3f src, Color3f dst) {
			float min    = Math.min(Math.min(src.r, src.g), src.b);
			float max    = Math.max(Math.max(src.r, src.g), src.b);
			float chroma = max - min;

			// Luminance
			dst.b = 0.299f * src.r + 0.587f * src.g + 0.114f * src.b;

			// Grayscale?
			if (chroma < 0.0000001) {
				dst.r = 0;
				dst.g = 0;
				return;
			}

			// Saturation
			dst.g = chroma;

			// Hue
			if (max == src.r) {
				if (min == src.b) {
					dst.r = (0 + (src.g - src.b) / chroma) / 6f;
				} else {
					dst.r = (6 + (src.g - src.b) / chroma) / 6f;
				}
			} else {
				if (max == src.g) {
					dst.r = (2 + (src.b - src.r) / chroma) / 6f;
				} else {
					dst.r = (4 + (src.r - src.g) / chroma) / 6f;
				}
			}
		}

		@Override
		public void toRGB(Color3f src, Color3f dst) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void from(ColorMode srcModel, Color3f src, Color3f dst) {
			throw new UnsupportedOperationException();
		}
	},
	/** Parallelepipoid color space with components: Luminosity, chroma, chroma. */
	YUV {
		@Override
		public void fromRGB(Color3f src, Color3f dst) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void toRGB(Color3f src, Color3f dst) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void from(ColorMode srcModel, Color3f src, Color3f dst) {
			throw new UnsupportedOperationException();
		}
	},
	/** Parallelepipoid color space with components: Luminosity, chroma, chroma. */
	YIQ {
		@Override
		public void fromRGB(Color3f src, Color3f dst) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void toRGB(Color3f src, Color3f dst) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void from(ColorMode srcModel, Color3f src, Color3f dst) {
			throw new UnsupportedOperationException();
		}
	},
	/** Parallelepipoid color space with components: Luminosity, chroma, chroma. */
	YPbPr {
		@Override
		public void fromRGB(Color3f src, Color3f dst) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void toRGB(Color3f src, Color3f dst) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void from(ColorMode srcModel, Color3f src, Color3f dst) {
			throw new UnsupportedOperationException();
		}
	},
	/**
	 *
	 */
	XYZ {
		@Override
		public void fromRGB(Color3f src, Color3f dst) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void toRGB(Color3f src, Color3f dst) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void from(ColorMode srcModel, Color3f src, Color3f dst) {
			throw new UnsupportedOperationException();
		}
	},
	/**
	 *
	 */
	LAB {
		@Override
		public void fromRGB(Color3f src, Color3f dst) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void toRGB(Color3f src, Color3f dst) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void from(ColorMode srcModel, Color3f src, Color3f dst) {
			throw new UnsupportedOperationException();
		}
	};

	public abstract void fromRGB(Color3f src, Color3f dst);

	public abstract void toRGB(Color3f src, Color3f dst);

	public abstract void from(ColorMode srcModel, Color3f src, Color3f dst);
}
