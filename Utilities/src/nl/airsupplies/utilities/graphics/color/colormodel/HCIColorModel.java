package nl.airsupplies.utilities.graphics.color.colormodel;

import nl.airsupplies.utilities.graphics.color.Color3f;
import nl.airsupplies.utilities.gui.color.ColorUtilities;

/**
 * This color model is a hexagonal bipyramid with the grays at the central axis
 * and the primary and secondary colors on a hexagonal plane at the center.
 * <p>
 * Compared to HSL, Saturation is replaced with Chroma my multiplying by Lightness,
 * and Lightness is then replaced by perceptual Intensity.
 * <p>
 * The input is assumed to be sRGB, and the output Intensity is linear.
 * <p>
 * Components:
 * r = Hue in [0, 1];
 * g = Chroma in [0, 1];
 * b = Intensity in [0, 1]
 *
 * @author Mark Jeronimus
 */
// Created 2025-06-01
public class HCIColorModel extends ColorModel {
	@Override
	public Color3f fromRGB(Color3f color) {
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

	@Override
	public Color3f toRGB(Color3f color) {
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

	/**
	 * Calculate linear perceptual luminosity from a color in sRGB color space.
	 */
	public static float getPerceptualLuminosity(Color3f color) {
		return ColorUtilities.getPerceptualLuminosity(color.r, color.g, color.b);
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
}
