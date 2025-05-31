package nl.airsupplies.utilities.graphics.color;

import nl.airsupplies.utilities.annotation.UtilityClass;
import nl.airsupplies.utilities.gui.color.ColorUtilities;

/**
 * @author Mark Jeronimus
 */
// Created 2025-05-31 Split from ColorUtilities
@UtilityClass
public final class Color3fUtilities {
	public static Color3f fromSRGB(Color3f color) {
		return new Color3f(ColorUtilities.fromSRGB(color.r),
		                   ColorUtilities.fromSRGB(color.g),
		                   ColorUtilities.fromSRGB(color.b));
	}

	public static Color3f toSRGB(Color3f color) {
		return new Color3f(ColorUtilities.toSRGB(color.r),
		                   ColorUtilities.toSRGB(color.g),
		                   ColorUtilities.toSRGB(color.b));
	}

	/**
	 * Calculate linear perceptual luminosity from a color in sRGB color space.
	 */
	public static float getPerceptualLuminosity(Color3f color) {
		return ColorUtilities.getPerceptualLuminosity(color.r, color.g, color.b);
	}

	@Deprecated
	public static Color3f toPerceptualHSL(Color3f color) {
		return toPerceptualHSL(color.r, color.g, color.b);
	}

	@Deprecated
	public static Color3f toPerceptualHSL(float r, float g, float b) {
		float min = Math.min(Math.min(r, g), b);
		float max = Math.max(Math.max(r, g), b);

		float lum    = ColorUtilities.toSRGB(ColorUtilities.getPerceptualLuminosity(r, g, b));
		float chroma = max - min;

		if (chroma < 1.0e-7f) {
			return new Color3f(0.0f, 0.0f, lum);
		}

		float hue = rgb2hueInternal(r, g, b, min, max, chroma);
		float sat = chroma / (1.0f - Math.abs(min + max - 1.0f));

		return new Color3f(hue, sat, lum);
	}

	private static float rgb2hueInternal(float r, float g, float b, float min, float max, float chroma) {
		assert chroma != 0.0f;

		if (max == r) {
			if (min == b) {
				return ((g - b) / chroma) / 6.0f;
			} else {
				return ((g - b) / chroma + 6.0f) / 6.0f;
			}
		} else {
			if (max == g) {
				return ((b - r) / chroma + 2.0f) / 6.0f;
			} else {
				return ((r - g) / chroma + 4.0f) / 6.0f;
			}
		}
	}

	// HSV ////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * This color space is a hexagonal cylinder with the grays at the central axis and the primary and
	 * secondary colors on a hexagonal plane at the top.
	 */
	public static Color3f rgb2hsv(Color3f color) {
		float min = Math.min(Math.min(color.r, color.g), color.b);
		float max = Math.max(Math.max(color.r, color.g), color.b);

		float chroma = max - min;

		if (chroma < 1.0e-7f) {
			return new Color3f(0.0f, 0, max);
		}

		float sat = max < 1.0e-7f ? 0.0f : chroma / max;

		return new Color3f(rgb2hueInternal(color.r, color.g, color.b, min, max, chroma), sat, max);
	}

	public static Color3f hsv2rgb(Color3f color) {
		float hue6 = (color.r - (float)Math.floor(color.r)) * 6.0f;

		// Hue coding
		float r = Math.max(-1.0f, Math.min(0.0f, Math.abs(hue6 - 3.0f) - 2.0f));
		float g = Math.max(-1.0f, Math.min(0.0f, 1.0f - Math.abs(hue6 - 2.0f)));
		float b = Math.max(-1.0f, Math.min(0.0f, 1.0f - Math.abs(hue6 - 4.0f)));

		// Sat & val coding
		float c = color.g * color.b;
		return new Color3f(r * c + color.b,
		                   g * c + color.b,
		                   b * c + color.b);
	}

	// HSL /////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * This color space is a hexagonal cylinder with the grays at the central axis, white at the top,
	 * and the primary and secondary colors on a hexagonal plane at the center.
	 */
	public static Color3f rgb2hsl(Color3f color) {
		float min = Math.min(Math.min(color.r, color.g), color.b);
		float max = Math.max(Math.max(color.r, color.g), color.b);

		float lum    = (min + max) / 2.0f;
		float chroma = max - min;

		if (chroma < 1.0e-7f) {
			return new Color3f(0.0f, 0.0f, lum);
		}

		float maxChroma = 1 - Math.abs(min + max - 1.0f);
		float hue       = rgb2hueInternal(color.r, color.g, color.b, min, max, chroma);
		float sat       = chroma / maxChroma;

		return new Color3f(hue, sat, lum);
	}

	public static Color3f hsl2rgb(Color3f color) {
		float hue6 = (color.r - (float)Math.floor(color.r)) * 6.0f;

		// Hue coding
		float r = Math.max(0.0f, Math.min(1.0f, Math.abs(hue6 - 3.0f) - 1.0f));
		float g = Math.max(0.0f, Math.min(1.0f, 2.0f - Math.abs(hue6 - 2.0f)));
		float b = Math.max(0.0f, Math.min(1.0f, 2.0f - Math.abs(hue6 - 4.0f)));

		// Sat & val coding
		float c = color.g * (1.0f - Math.abs(2.0f * color.b - 1.0f));
		float m = color.b - c / 2.0f;
		return new Color3f(r * c + m,
		                   g * c + m,
		                   b * c + m);
	}

	// HCL /////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * This color space is a hexagonal bipyramid with the grays at the central axis
	 * and the primary and secondary colors on a hexagonal plane at the center.
	 * <p>
	 * Compared to HSL, Saturation is replaced with Chroma my multiplying by Lightness.
	 * <p>
	 * The input is assumed to be sRGB, and the output Intensity is linear.
	 */
	public static Color3f rgb2hcl(Color3f color) {
		float min = Math.min(Math.min(color.r, color.g), color.b);
		float max = Math.max(Math.max(color.r, color.g), color.b);

		float lum    = (min + max) / 2.0f;
		float chroma = max - min;

		if (chroma < 1.0e-7f) {
			return new Color3f(0.0f, 0.0f, lum);
		}

		float hue = rgb2hueInternal(color.r, color.g, color.b, min, max, chroma);

		return new Color3f(hue, chroma, lum);
	}

	public static Color3f hcl2rgb(Color3f color) {
		float hue6 = (color.r - (float)Math.floor(color.r)) * 6.0f;

		// Hue coding
		float r = Math.max(0.0f, Math.min(1.0f, Math.abs(hue6 - 3.0f) - 1.0f));
		float g = Math.max(0.0f, Math.min(1.0f, 2.0f - Math.abs(hue6 - 2.0f)));
		float b = Math.max(0.0f, Math.min(1.0f, 2.0f - Math.abs(hue6 - 4.0f)));

		// Sat & val coding
		float m = color.b - color.g / 2.0f;
		return new Color3f(r * color.g + m,
		                   g * color.g + m,
		                   b * color.g + m);
	}

	// HCI /////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * This color space is a hexagonal bipyramid with the grays at the central axis
	 * and the primary and secondary colors on a hexagonal plane at the center.
	 * <p>
	 * Compared to HSL, Saturation is replaced with Chroma my multiplying by Lightness,
	 * and Lightness is replaced by perceptual Intensity.
	 * <p>
	 * The input is assumed to be sRGB, and the output Intensity is linear.
	 */
	public static Color3f rgb2hci(Color3f color) {
		float min = Math.min(Math.min(color.r, color.g), color.b);
		float max = Math.max(Math.max(color.r, color.g), color.b);

		float chroma = max - min;

		if (chroma < 1.0e-7f) {
			float intensity = ColorUtilities.fromSRGB(max);
			return new Color3f(0.0f, 0.0f, intensity);
		}

		float hue = rgb2hueInternal(color.r, color.g, color.b, min, max, chroma);

		float intensity = ColorUtilities.getPerceptualLuminosity(color.r, color.g, color.b);
		return new Color3f(hue, chroma, intensity);
	}

	public static Color3f hci2rgb(Color3f color) {
		float hue6 = (color.r - (float)Math.floor(color.r)) * 6;

		// Hue & sat coding
		float r = Math.max(0.0f, Math.min(1.0f, Math.abs(hue6 - 3.0f) - 1.0f)) * color.g;
		float g = Math.max(0.0f, Math.min(1.0f, 2.0f - Math.abs(hue6 - 2.0f))) * color.g;
		float b = Math.max(0.0f, Math.min(1.0f, 2.0f - Math.abs(hue6 - 4.0f))) * color.g;

		// Luma searching
		if (color.b < ColorUtilities.getPerceptualLuminosity(r, g, b)) {
			return findDarkerColor(r, g, b, color.b);
		} else {
			return findBrighterColor(r, g, b, color.b);
		}
	}

	private static Color3f findDarkerColor(float r, float g, float b, float targetIntensity) {
		float minMul = 0.0f;
		float maxMul = 1.0f;
		float mul    = 0.0f;
		float intensity;

		while (maxMul - minMul > 1.0e-7f) {
			mul       = (maxMul + minMul) / 2;
			intensity = ColorUtilities.getPerceptualLuminosity(r * mul, g * mul, b * mul);
			if (intensity < targetIntensity) {
				minMul = mul;
			} else {
				maxMul = mul;
			}
		}

		return new Color3f(r * mul, g * mul, b * mul);
	}

	private static Color3f findBrighterColor(float r, float g, float b, float targetIntensity) {
		float minAdd = 0.0f;
		float maxAdd = 1.0f;
		float add    = 0.0f;
		float intensity;

		while (maxAdd - minAdd > 1.0e-7f) {
			add       = (maxAdd + minAdd) / 2;
			intensity = ColorUtilities.getPerceptualLuminosity(Math.min(r + add, 1.0f),
			                                                   Math.min(g + add, 1.0f),
			                                                   Math.min(b + add, 1.0f));

			if (intensity < targetIntensity) {
				minAdd = add;
			} else {
				maxAdd = add;
			}
		}

		return new Color3f(Math.min(r + add, 1.0f),
		                   Math.min(g + add, 1.0f),
		                   Math.min(b + add, 1.0f));
	}

	// YUV /////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Convert RGB to YUV (the color space used by PAL TV). Outputs: Y in [0, 1]; G=U in [-0.436, 0.436]; B=V in
	 * [-0.615, 0.615]
	 */
	public static Color3f rgb2yuv(Color3f color) {
		return new Color3f(+0.2990000f * color.r + 0.58700000f * color.g + 0.11400000f * color.b,
		                   -0.1471377f * color.r - 0.28886230f * color.g + 0.43600000f * color.b,
		                   +0.6150000f * color.r - 0.51498574f * color.g - 0.10001426f * color.b);
	}

	/**
	 * Convert YUV (the color space used by PAL TV) to RGB. Inputs: Y in [0, 1]; G=U in [-0.436, 0.436]; B=V in [-0.615,
	 * 0.615]
	 */
	public static Color3f yuv2rgb(Color3f color) {
		return new Color3f(color.r + 0.00000000f * color.g + 1.13983740f * color.b,
		                   color.r - 0.39465170f * color.g - 0.58059860f * color.b,
		                   color.r + 2.03211000f * color.g + 0.00000000f * color.b);
	}

	// YUV /////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Convert RGB to YIQ (the color space used in NTSC TV). Outputs: R=Y in [0, 1]; G=I in [-0.5, 0.5]; B=Q in [-0.5,
	 * 0.5]
	 */
	public static Color3f rgb2yiq(Color3f color) {
		return new Color3f(0.29900000f * color.r + 0.58700000f * color.g + 0.11400000f * color.b,
		                   0.59571610f * color.r - 0.27445284f * color.g - 0.32126328f * color.b,
		                   0.21145640f * color.r - 0.52259105f * color.g + 0.31113464f * color.b);
	}

	/**
	 * Convert YIQ (the color space used in NTSC TV) to RGB. Inputs: R=Y in [0 1]; G=I in [-0.5 0.5]; B=Q in [-0.5 0.5]
	 */
	public static Color3f yiq2rgb(Color3f color) {
		return new Color3f(color.r + 0.95629483f * color.g + 0.62102515f * color.b,
		                   color.r - 0.27212146f * color.g - 0.64738095f * color.b,
		                   color.r - 1.10698990f * color.g + 1.70461500f * color.b);
	}

	// YPbPr / yCbCr ///////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Convert RGB to YPbPr (the digital version of YCbCr, the color space used by JPEG/DVD). Outputs: R=Y in [0, 1];
	 * G=Pb in [-0.5, 0.5]; B=Pr in [-0.5, 0.5]
	 */
	public static Color3f rgb2ypbpr(Color3f color) {
		return new Color3f(+0.29900000f * color.r + 0.58700000f * color.g + 0.11400000f * color.b,
		                   -0.16873589f * color.r - 0.33126410f * color.g + 0.50000000f * color.b,
		                   +0.50000000f * color.r - 0.41868758f * color.g - 0.08131241f * color.b);
	}

	/**
	 * Convert YPbPr (the digital version of YCbCr, the color space used by JPEG/DVD) to RGB. Inputs: R=Y in [0, 1];
	 * G=Pb
	 * in [-0.5, 0.5]; B=Pr in [-0.5, 0.5]
	 */
	public static Color3f ypbpr2rgb(Color3f color) {
		return new Color3f(color.r + 0.00000000f * color.g + 1.40200000f * color.b,
		                   color.r - 0.34413630f * color.g - 0.71413630f * color.b,
		                   color.r + 1.77200000f * color.g + 0.00000000f * color.b);
	}

	// XYZ /////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Convert RGB to XYZ (the CIE 1931 color space). Outputs: R=Y in [0, 1]; G=U in [0, 1]; B=V in [0, 1]
	 */
	public static Color3f rgb2xyz(Color3f color) {
		return new Color3f(0.43030295f * color.r + 0.34163640f * color.g + 0.17822778f * color.b,
		                   0.22187495f * color.r + 0.70683396f * color.g + 0.07129111f * color.b,
		                   0.02017045f * color.r + 0.12958622f * color.g + 0.93866630f * color.b);
	}

	/**
	 * Convert XYZ (the CIE 1931 color space) to RGB. Inputs: R=X in [0, 1]; G=Y in [0, 1]; B=z in [0, 1]
	 */
	public static Color3f xyz2rgb(Color3f color) {
		return new Color3f(+2.06084660f * color.r - 0.93738980f * color.g - 0.32010582f * color.b,
		                   -1.14153440f * color.r + 2.20943570f * color.g + 0.04894180f * color.b,
		                   +0.08068783f * color.r - 0.27204585f * color.g + 1.27116410f * color.b);
	}
}
