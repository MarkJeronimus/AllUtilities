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

package nl.airsupplies.utilities.graphics.color;

import nl.airsupplies.utilities.gui.color.ColorUtilities;

/**
 * @author Mark Jeronimus
 */
// Created 2016-02-28
public enum ColorModel {
	/**
	 * This color model is a cube with the grays at the volume-diagonal and the primaries and secondaries at vertices.
	 */
	RGB {
		@Override
		public Color3f fromRGB(Color3f color) {
			return color;
		}

		@Override
		public Color3f toRGB(Color3f color) {
			return color;
		}
	},
	/**
	 * This color model is a hexagonal cylinder with the grays at the central axis and the primary and
	 * secondary colors on a hexagonal plane at the top.
	 */
	HSV {
		@Override
		public Color3f fromRGB(Color3f color) {
			return ColorUtilities.rgb2hsv(color);
		}

		@Override
		public Color3f toRGB(Color3f color) {
			return ColorUtilities.hsv2rgb(color);
		}
	},
	/**
	 * This color model is a hexagonal cylinder with the grays at the central axis, white at the top,
	 * and the primary and secondary colors on a hexagonal plane at the center.
	 */
	HSL {
		@Override
		public Color3f fromRGB(Color3f color) {
			return ColorUtilities.rgb2hsl(color);
		}

		@Override
		public Color3f toRGB(Color3f color) {
			return ColorUtilities.hsl2rgb(color);
		}
	},
	/**
	 * This color model is a hexagonal bipyramid with the grays at the central axis
	 * and the primary and secondary colors on a hexagonal plane at the center.
	 * <p>
	 * Compared to HSL, Saturation is replaced with Chroma my multiplying by Lightness.
	 * <p>
	 * The input is assumed to be sRGB, and the output Intensity is linear.
	 */
	HCL {
		@Override
		public Color3f fromRGB(Color3f color) {
			return ColorUtilities.rgb2hcl(color);
		}

		@Override
		public Color3f toRGB(Color3f color) {
			return ColorUtilities.hcl2rgb(color);
		}
	},
	/**
	 * This color model is a hexagonal bipyramid with the grays at the central axis
	 * and the primary and secondary colors on a hexagonal plane at the center.
	 * <p>
	 * Compared to HSL, Saturation is replaced with Chroma my multiplying by Lightness,
	 * and Lightness is then replaced by perceptual Intensity.
	 * <p>
	 * The input is assumed to be sRGB, and the output Intensity is linear.
	 */
	HCI {
		@Override
		public Color3f fromRGB(Color3f color) {
			return ColorUtilities.rgb2hci(color);
		}

		@Override
		public Color3f toRGB(Color3f color) {
			return ColorUtilities.hci2rgb(color);
		}
	},
	/** Parallelepiped color model with components: Luminosity, chroma, chroma. */
	YUV {
		@Override
		public Color3f fromRGB(Color3f color) {
			return ColorUtilities.rgb2yuv(color);
		}

		@Override
		public Color3f toRGB(Color3f color) {
			return ColorUtilities.yuv2rgb(color);
		}
	},
	/** Parallelepiped color model with components: Luminosity, chroma, chroma. */
	YIQ {
		@Override
		public Color3f fromRGB(Color3f color) {
			return ColorUtilities.rgb2yiq(color);
		}

		@Override
		public Color3f toRGB(Color3f color) {
			return ColorUtilities.yiq2rgb(color);
		}
	},
	/** Parallelepiped color model with components: Luminosity, chroma, chroma. */
	YPbPr {
		@Override
		public Color3f fromRGB(Color3f color) {
			return ColorUtilities.rgb2ypbpr(color);
		}

		@Override
		public Color3f toRGB(Color3f color) {
			return ColorUtilities.ypbpr2rgb(color);
		}
	},
	/**
	 *
	 */
	CIEXYZ {
		@Override
		public Color3f fromRGB(Color3f color) {
			return ColorUtilities.rgb2xyz(color);
		}

		@Override
		public Color3f toRGB(Color3f color) {
			return ColorUtilities.xyz2rgb(color);
		}
	},
	/**
	 *
	 */
	LAB {
		@Override
		public Color3f fromRGB(Color3f color) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Color3f toRGB(Color3f color) {
			throw new UnsupportedOperationException();
		}
	};

	public abstract Color3f fromRGB(Color3f color);

	public abstract Color3f toRGB(Color3f color);
}
