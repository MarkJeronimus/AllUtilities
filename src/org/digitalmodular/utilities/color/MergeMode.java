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

import org.digitalmodular.utilities.math.FastTrig;

/**
 * @author Mark Jeronimus
 */
// Created 2009-05-08
public enum MergeMode {
	NORMAL {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = top.r;
			bot.g = top.g;
			bot.b = top.b;
			bot.a = top.a;
		}
	},
	DARKEN {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = Math.min(bot.r, top.r);
			bot.g = Math.min(bot.g, top.g);
			bot.b = Math.min(bot.b, top.b);
			bot.a = top.a;
		}
	},
	LIGHTEN {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = Math.max(bot.r, top.r);
			bot.g = Math.max(bot.g, top.g);
			bot.b = Math.max(bot.b, top.b);
			bot.a = top.a;
		}
	},
	/** Equivalent to AND in real-valued logic */
	MULTIPLY {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r *= top.r;
			bot.g *= top.g;
			bot.b *= top.b;
			bot.a = top.a;
		}
	},
	/** Equivalent to OR in real-valued logic */
	SCREEN {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = 1 - (1 - bot.r) * (1 - top.r);
			bot.g = 1 - (1 - bot.g) * (1 - top.g);
			bot.b = 1 - (1 - bot.b) * (1 - top.b);
			bot.a = top.a;
		}
	},
	/** a combination of multiply and screen */
	OVERLAY {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = bot.r < 0.5 ? 2 * top.r * bot.r : 1 - 2 * (1 - bot.r) * (1 - top.r);
			bot.g = bot.g < 0.5 ? 2 * top.g * bot.g : 1 - 2 * (1 - bot.g) * (1 - top.g);
			bot.b = bot.b < 0.5 ? 2 * top.b * bot.b : 1 - 2 * (1 - bot.b) * (1 - top.b);
			bot.a = top.a;
		}
	},
	/** a combination of multiply and screen */
	HARD_LIGHT {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = top.r < 0.5 ? 2 * top.r * bot.r : 1 - 2 * (1 - bot.r) * (1 - top.r);
			bot.g = top.g < 0.5 ? 2 * top.g * bot.g : 1 - 2 * (1 - bot.g) * (1 - top.g);
			bot.b = top.b < 0.5 ? 2 * top.b * bot.b : 1 - 2 * (1 - bot.b) * (1 - top.b);
			bot.a = top.a;
		}
	},
	SOFT_LIGHT {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = bot.r * bot.r + 2 * (1 - bot.r) * bot.r * top.r;
			bot.g = bot.g * bot.g + 2 * (1 - bot.g) * bot.g * top.g;
			bot.b = bot.b * bot.b + 2 * (1 - bot.b) * bot.b * top.b;
			bot.a = top.a;
		}
	},
	SOFT_LIGHT_GAMMA {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = (float)Math.pow(bot.r, Math.pow(2, 1 - 2 * top.r));
			bot.g = (float)Math.pow(bot.g, Math.pow(2, 1 - 2 * top.g));
			bot.b = (float)Math.pow(bot.b, Math.pow(2, 1 - 2 * top.b));
			bot.a = top.a;
		}
	},
	SOFT_LIGHT_PS {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = top.r <= 0.5f
			        ? 2 * bot.r * top.r + bot.r * bot.r * (1 - 2 * top.r)
			        : 2 * bot.r * (1 - top.r) + (float)StrictMath.sqrt(bot.r) * (2 * top.r - 1);
			bot.g = top.g <= 0.5f
			        ? 2 * bot.g * top.g + bot.g * bot.g * (1 - 2 * top.g)
			        : 2 * bot.g * (1 - top.g) + (float)StrictMath.sqrt(bot.g) * (2 * top.g - 1);
			bot.b = top.b <= 0.5f
			        ? 2 * bot.b * top.b + bot.b * bot.b * (1 - 2 * top.b)
			        : 2 * bot.b * (1 - top.b) + (float)StrictMath.sqrt(bot.b) * (2 * top.b - 1);
			bot.a = top.a;
		}
	},
	SOFT_LIGHT_W3C {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = top.r <= 0.5f
			        ? bot.r - (1 - 2 * top.r) * bot.r * (1 - bot.r)
			        : bot.r + (2 * top.r - 1) * ((bot.r <= 0.25
			                                      ? ((16 * bot.r - 12) * bot.r + 4) * bot.r
			                                      : (float)Math.sqrt(bot.r)) - bot.r);
			bot.g = top.g <= 0.5f
			        ? bot.g - (1 - 2 * top.g) * bot.g * (1 - bot.g)
			        : bot.g + (2 * top.g - 1) * ((bot.g <= 0.25
			                                      ? ((16 * bot.g - 12) * bot.g + 4) * bot.g
			                                      : (float)Math.sqrt(bot.g)) - bot.g);
			bot.b = top.b <= 0.5f
			        ? bot.b - (1 - 2 * top.b) * bot.b * (1 - bot.b)
			        : bot.b + (2 * top.b - 1) * ((bot.b <= 0.25
			                                      ? ((16 * bot.b - 12) * bot.b + 4) * bot.b
			                                      : (float)Math.sqrt(bot.b)) - bot.b);
			bot.a = top.a;
		}
	},
	SOFT_LIGHT_UF {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = top.r < 0.5f ? (top.r + 0.5f) * bot.r : 1 - (1.5f - top.r) * (1 - bot.r);
			bot.g = top.g < 0.5f ? (top.g + 0.5f) * bot.g : 1 - (1.5f - top.g) * (1 - bot.g);
			bot.b = top.b < 0.5f ? (top.b + 0.5f) * bot.b : 1 - (1.5f - top.b) * (1 - bot.b);
			bot.a = top.a;
		}
	},
	/** a combination of color burn and color dodge */
	VIVID_LIGHT {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = top.r < 0.5f
			        ? top.r == 0 ? -1e10f : 1 - (1 - bot.r) / 2 / top.r
			        : top.r == 1 ? 1e10f : bot.r / 2 / (1 - top.r);
			bot.g = top.g < 0.5f
			        ? top.r == 0 ? -1e10f : 1 - (1 - bot.g) / 2 / top.g
			        : top.r == 1 ? 1e10f : bot.g / 2 / (1 - top.g);
			bot.b = top.b < 0.5f
			        ? top.r == 0 ? -1e10f : 1 - (1 - bot.b) / 2 / top.b
			        : top.r == 1 ? 1e10f : bot.b / 2 / (1 - top.b);
			bot.a = top.a;
		}
	},
	/** a combination of darken and lighten */
	PIN_LIGHT {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = top.r < 0.5f ? Math.min(bot.r, 2 * top.r) : Math.max(bot.r, 2 * top.r - 1);
			bot.g = top.g < 0.5f ? Math.min(bot.g, 2 * top.g) : Math.max(bot.g, 2 * top.g - 1);
			bot.b = top.b < 0.5f ? Math.min(bot.b, 2 * top.b) : Math.max(bot.b, 2 * top.b - 1);
			bot.a = top.a;
		}
	},
	/** a combination of linear burn and linear dodge */
	LINEAR_LIGHT {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = bot.r + 2 * top.r - 1;
			bot.g = bot.g + 2 * top.g - 1;
			bot.b = bot.b + 2 * top.b - 1;
			bot.a = top.a;
		}
	},
	AVERAGE {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = (bot.r + top.r) * 0.5f;
			bot.g = (bot.g + top.g) * 0.5f;
			bot.b = (bot.b + top.b) * 0.5f;
			bot.a = top.a;
		}
	},
	SUBTRACT {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = bot.r - top.r;
			bot.g = bot.g - top.g;
			bot.b = bot.b - top.b;
			bot.a = top.a;
		}
	},
	DIFFERENCE {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = Math.abs(bot.r - top.r);
			bot.g = Math.abs(bot.g - top.g);
			bot.b = Math.abs(bot.b - top.b);
			bot.a = top.a;
		}
	},
	NEGATION {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = 1 - Math.abs(1 - bot.r - top.r);
			bot.g = 1 - Math.abs(1 - bot.g - top.g);
			bot.b = 1 - Math.abs(1 - bot.b - top.b);
			bot.a = top.a;
		}
	},
	INTERPOLATION {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = ((3 - 2 * bot.r) * bot.r * bot.r + (3 - 2 * top.r) * top.r * top.r) * 0.5f;
			bot.g = ((3 - 2 * bot.g) * bot.g * bot.g + (3 - 2 * top.g) * top.g * top.g) * 0.5f;
			bot.b = ((3 - 2 * bot.b) * bot.b * bot.b + (3 - 2 * top.b) * top.b * top.b) * 0.5f;
			bot.a = top.a;
		}
	},
	/** Equivalent to XOR in real-valued logic */
	EXCLUSION {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = bot.r + top.r - 2 * bot.r * top.r;
			bot.g = bot.g + top.g - 2 * bot.g * top.g;
			bot.b = bot.b + top.b - 2 * bot.b * top.b;
			bot.a = top.a;
		}
	},
	GRAIN_EXTRACT {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = bot.r - top.r + 0.5f;
			bot.g = bot.g - top.g + 0.5f;
			bot.b = bot.b - top.b + 0.5f;
			bot.a = top.a;
		}
	},
	GRAIN_MERGE {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = bot.r + top.r - 0.5f;
			bot.g = bot.g + top.g - 0.5f;
			bot.b = bot.b + top.b - 0.5f;
			bot.a = top.a;
		}
	},
	DIVIDE {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = top.r == 0 ? 1e10f : bot.r / top.r;
			bot.g = top.g == 0 ? 1e10f : bot.g / top.g;
			bot.b = top.b == 0 ? 1e10f : bot.b / top.b;
			bot.a = top.a;
		}
	},
	COLOR_DODGE {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = top.r == 1 ? 1e10f : bot.r / (1 - top.r);
			bot.g = top.r == 1 ? 1e10f : bot.g / (1 - top.g);
			bot.b = top.r == 1 ? 1e10f : bot.b / (1 - top.b);
			bot.a = top.a;
		}
	},
	REVERSE_DODGE {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = bot.r == 1 ? 1e10f : top.r / (1 - bot.r);
			bot.g = bot.g == 1 ? 1e10f : top.g / (1 - bot.g);
			bot.b = bot.b == 1 ? 1e10f : top.b / (1 - bot.b);
			bot.a = top.a;
		}
	},
	SMOOTH_DODGE {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = bot.r + top.r > 1
			        ? 1 - 0.5f * (1 - top.r) / bot.r
			        : top.r == 1 ? 1 : 0.5f * bot.r / (1 - top.r);
			bot.g = bot.g + top.g > 1
			        ? 1 - 0.5f * (1 - top.g) / bot.g
			        : top.g == 1 ? 0 : 0.5f * bot.g / (1 - top.g);
			bot.b = bot.b + top.b > 1
			        ? 1 - 0.5f * (1 - top.b) / bot.b
			        : top.b == 1 ? 0 : 0.5f * bot.b / (1 - top.b);
			bot.a = top.a;
		}
	},
	LINEAR_DODGE {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = bot.r + top.r;
			bot.g = bot.g + top.g;
			bot.b = bot.b + top.b;
			bot.a = top.a;
		}
	},
	COLOR_BURN {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = top.r == 0 ? -1e10f : 1 - (1 - bot.r) / top.r;
			bot.g = top.g == 0 ? -1e10f : 1 - (1 - bot.g) / top.g;
			bot.b = top.b == 0 ? -1e10f : 1 - (1 - bot.b) / top.b;
			bot.a = top.a;
		}
	},
	REVERSE_BURN {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = bot.r == 0 ? -1e10f : 1 - (1 - top.r) / bot.r;
			bot.g = bot.g == 0 ? -1e10f : 1 - (1 - top.g) / bot.g;
			bot.b = bot.b == 0 ? -1e10f : 1 - (1 - top.b) / bot.b;
			bot.a = top.a;
		}
	},
	SMOOTH_BURN {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = bot.r + top.r > 1
			        ? 1 - 0.5f * (1 - bot.r) / top.r
			        : bot.r == 1 ? 0 : 0.5f * top.r / (1 - bot.r);
			bot.g = bot.g + top.g > 1
			        ? 1 - 0.5f * (1 - bot.g) / top.g
			        : bot.g == 1 ? 0 : 0.5f * top.g / (1 - bot.b);
			bot.b = bot.b + top.b > 1
			        ? 1 - 0.5f * (1 - bot.b) / top.b
			        : bot.b == 1 ? 0 : 0.5f * top.b / (1 - bot.b);
			bot.a = top.a;
		}
	},
	/** a combination of burn and dodge */
	LINEAR_BURN {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = bot.r + top.r - 1;
			bot.g = bot.g + top.g - 1;
			bot.b = bot.b + top.b - 1;
			bot.a = top.a;
		}
	},
	REFLECT {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = top.r == 1 ? 1e10f : bot.r * bot.r / (1 - top.r);
			bot.g = top.g == 1 ? 1e10f : bot.g * bot.g / (1 - top.g);
			bot.b = top.b == 1 ? 1e10f : bot.b * bot.b / (1 - top.b);
			bot.a = top.a;
		}
	},
	GLOW {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = bot.r == 1 ? 1e10f : top.r * top.r / (1 - bot.r);
			bot.g = bot.g == 1 ? 1e10f : top.g * top.g / (1 - bot.g);
			bot.b = bot.b == 1 ? 1e10f : top.b * top.b / (1 - bot.b);
			bot.a = top.a;
		}
	},
	FREEZE {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = top.r == 0 ? -1e10f : 1 - (1 - bot.r) * (1 - bot.r) / top.r;
			bot.g = top.g == 0 ? -1e10f : 1 - (1 - bot.g) * (1 - bot.g) / top.g;
			bot.b = top.b == 0 ? -1e10f : 1 - (1 - bot.b) * (1 - bot.b) / top.b;
			bot.a = top.a;
		}
	},
	HEAT {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = bot.r == 0 ? -1e10f : 1 - (1 - top.r) * (1 - top.r) / bot.r;
			bot.g = bot.g == 0 ? -1e10f : 1 - (1 - top.g) * (1 - top.g) / bot.g;
			bot.b = bot.b == 0 ? -1e10f : 1 - (1 - top.b) * (1 - top.b) / bot.b;
			bot.a = top.a;
		}
	},
	RED {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.r = top.r;
			bot.a = top.a;
		}
	},
	GREEN {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.g = top.g;
			bot.a = top.a;
		}
	},
	BLUE {
		@Override
		public void apply(Color4f bot, Color4f top) {
			bot.b = top.b;
			bot.a = top.a;
		}
	},
	HUE {
		@Override
		public void apply(Color4f bot, Color4f top) {}
	},
	SATURATION {
		@Override
		public void apply(Color4f bot, Color4f top) {}
	},
	COLOR {
		@Override
		public void apply(Color4f bot, Color4f top) {}
	},
	HSL_LUMINANCE {
		@Override
		public void apply(Color4f bot, Color4f top) {}
	},
	HSV_VALUE {
		@Override
		public void apply(Color4f bot, Color4f top) {}
	},
	HSB_BRIGHTNESS {
		@Override
		public void apply(Color4f bot, Color4f top) {}
	},
	HSL_ADD {
		@Override
		public void apply(Color4f bot, Color4f top) {}
	},
	HSV_ADD {
		@Override
		public void apply(Color4f bot, Color4f top) {}
	},
	HSB_ADD {
		@Override
		public void apply(Color4f bot, Color4f top) {}
	};

	static final FastTrig fastTrig = new FastTrig(524288, 2);

	/**
	 * @param bot premultiplied
	 * @param top not premultiplied
	 */
	public abstract void apply(Color4f bot, Color4f top);

	/**
	 * Linearly interpolate between two colors. Alpha is blended too and doesn't participate in the blending of other
	 * components.
	 */
	public static void lerp(Color4f bot, Color4f top, float factor) {
		float factor1 = 1 - factor;
		bot.r = bot.r * factor1 + top.r * factor;
		bot.g = bot.g * factor1 + top.g * factor;
		bot.b = bot.b * factor1 + top.b * factor;
		bot.a = bot.a * factor1 + top.a * factor;
	}

	/**
	 * Linearly interpolate between two colors. The top layer is considered opaque and the alpha of the top image
	 * controls the blending opacity.
	 */
	public static void blend(Color4f bot, Color4f top) {
		float topA1 = 1 - top.a;
		bot.r = bot.r * topA1 + top.r * top.a;
		bot.g = bot.g * topA1 + top.g * top.a;
		bot.b = bot.b * topA1 + top.b * top.a;
		bot.a = bot.a * topA1 + 1 * top.a;
	}

	/**
	 * Compose the top color on top of the bottom color. The opacity parameter makes the top color more transparent.
	 */
	public static void compose(Color4f bot, Color4f top, float opacity) {
		bot.r *= bot.a;
		bot.g *= bot.a;
		bot.b *= bot.a;

		float topA  = top.a * opacity;
		float topA1 = 1 - topA;

		bot.a = bot.a * topA1 + 1 * topA;
		if (bot.a == 0) {
			bot.r = top.r;
			bot.g = top.g;
			bot.b = top.b;
		} else {
			bot.r = bot.r * topA1 + top.r * topA;
			bot.g = bot.g * topA1 + top.g * topA;
			bot.b = bot.b * topA1 + top.b * topA;

			bot.r /= bot.a;
			bot.g /= bot.a;
			bot.b /= bot.a;
		}
	}

	public void merge(Color4f bot, Color4f top, Color4f temp, float opacity) {
		temp.set(bot);

		apply(temp, top);
		MergeMode.lerp(top, temp, bot.a);
		MergeMode.compose(bot, top, opacity);
	}

	public void merge(Color4f bot, Color4f top, Color4f temp, float opacity, boolean intermediateClamp) {
		temp.set(bot);

		apply(temp, top);

		if (intermediateClamp) {
			temp.clip();
		}

		MergeMode.lerp(top, temp, bot.a);
		MergeMode.compose(bot, top, opacity);
	}
}
