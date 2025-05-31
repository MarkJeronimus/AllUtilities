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
			return Color3fUtilities.rgb2hsv(color);
		}

		@Override
		public Color3f toRGB(Color3f color) {
			return Color3fUtilities.hsv2rgb(color);
		}
	},
	/**
	 * This color model is a hexagonal cylinder with the grays at the central axis, white at the top,
	 * and the primary and secondary colors on a hexagonal plane at the center.
	 */
	HSL {
		@Override
		public Color3f fromRGB(Color3f color) {
			return Color3fUtilities.rgb2hsl(color);
		}

		@Override
		public Color3f toRGB(Color3f color) {
			return Color3fUtilities.hsl2rgb(color);
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
			return Color3fUtilities.rgb2hcl(color);
		}

		@Override
		public Color3f toRGB(Color3f color) {
			return Color3fUtilities.hcl2rgb(color);
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
			return Color3fUtilities.rgb2hci(color);
		}

		@Override
		public Color3f toRGB(Color3f color) {
			return Color3fUtilities.hci2rgb(color);
		}
	},
	/** Parallelepiped color model with components: Luminosity, chroma, chroma. */
	YUV {
		@Override
		public Color3f fromRGB(Color3f color) {
			return Color3fUtilities.rgb2yuv(color);
		}

		@Override
		public Color3f toRGB(Color3f color) {
			return Color3fUtilities.yuv2rgb(color);
		}
	},
	/** Parallelepiped color model with components: Luminosity, chroma, chroma. */
	YIQ {
		@Override
		public Color3f fromRGB(Color3f color) {
			return Color3fUtilities.rgb2yiq(color);
		}

		@Override
		public Color3f toRGB(Color3f color) {
			return Color3fUtilities.yiq2rgb(color);
		}
	},
	/** Parallelepiped color model with components: Luminosity, chroma, chroma. */
	YPbPr {
		@Override
		public Color3f fromRGB(Color3f color) {
			return Color3fUtilities.rgb2ypbpr(color);
		}

		@Override
		public Color3f toRGB(Color3f color) {
			return Color3fUtilities.ypbpr2rgb(color);
		}
	},
	/**
	 *
	 */
	CIEXYZ {
		@Override
		public Color3f fromRGB(Color3f color) {
			return Color3fUtilities.rgb2xyz(color);
		}

		@Override
		public Color3f toRGB(Color3f color) {
			return Color3fUtilities.xyz2rgb(color);
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
