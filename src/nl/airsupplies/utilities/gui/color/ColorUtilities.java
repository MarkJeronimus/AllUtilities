package nl.airsupplies.utilities.gui.color;

import java.awt.Color;

import nl.airsupplies.utilities.NumberUtilities;
import nl.airsupplies.utilities.annotation.UtilityClass;

/**
 * Functions for easily manipulating RGB or ARGB int colors.
 *
 * @author Mark Jeronimus
 */
@UtilityClass
public final class ColorUtilities {
	private static final int     SRGB_PRECISION  = 4096;
	private static final float[] TO_SRGB_TABLE   = new float[SRGB_PRECISION + 2];
	private static final float[] FROM_SRGB_TABLE = new float[SRGB_PRECISION + 2];

	static {
		for (int i = 0; i <= SRGB_PRECISION; i++) {
			float f = i / (float)SRGB_PRECISION;
			TO_SRGB_TABLE[i]   = f < 0.0031308f ? f * 12.92f : (float)Math.pow(f, 1 / 2.4) * 1.055f - 0.055f;
			FROM_SRGB_TABLE[i] = f < 0.04045f ? f / 12.92f : (float)Math.pow((f + 0.055f) / 1.055f, 2.4);
		}

		TO_SRGB_TABLE[0]                    = 0;
		FROM_SRGB_TABLE[0]                  = 0;
		TO_SRGB_TABLE[SRGB_PRECISION + 1]   = 1;
		FROM_SRGB_TABLE[SRGB_PRECISION + 1] = 1;
	}

	public static Color setAlpha(Color original, int newAlpha) {
		newAlpha = newAlpha << 24 & 0xFF000000;
		int rgb = original.getRGB() & 0x00FFFFFF;
		return new Color(newAlpha | rgb, true);
	}

	public static int toColor(double r, double g, double b) {
		return NumberUtilities.clamp((int)Math.round(r * 255), 0, 255) << 16 |
		       NumberUtilities.clamp((int)Math.round(g * 255), 0, 255) << 8 |
		       NumberUtilities.clamp((int)Math.round(b * 255), 0, 255);
	}

	public static int lerpRGB(int first, int second, float position) {
		return Math.round(NumberUtilities.lerp((first >> 16) & 0xFF, (second >> 16) & 0xFF, position)) << 16 |
		       Math.round(NumberUtilities.lerp((first >> 8) & 0xFF, (second >> 8) & 0xFF, position)) << 8 |
		       Math.round(NumberUtilities.lerp(first & 0xFF, second & 0xFF, position));
	}

	/**
	 * Convert from sRGB colors (the normal 0..255 range computers use) to linear values.
	 */
	public static float fromSRGB(float f) {
		f = NumberUtilities.clamp(f, 0.0f, 1.0f) * SRGB_PRECISION;
		int   fInt = (int)f;
		float fPos = f - fInt;
		return NumberUtilities.lerp(FROM_SRGB_TABLE[fInt], FROM_SRGB_TABLE[fInt + 1], fPos);
	}

	/**
	 * Convert from linear values to sRGB colors (the normal 0..255 range computers use).
	 */
	public static float toSRGB(float f) {
		f = NumberUtilities.clamp(f, 0.0f, 1.0f) * SRGB_PRECISION;
		int   fInt = (int)f;
		float fPos = f - fInt;
		return NumberUtilities.lerp(TO_SRGB_TABLE[fInt], TO_SRGB_TABLE[fInt + 1], fPos);
	}

	/**
	 * Calculate linear perceptual luminosity from a color in sRGB color space.
	 */
	public static float getPerceptualLuminosity(int color) {
		return getPerceptualLuminosity((color >> 16 & 0xFF) / 255.0f,
		                               (color >> 8 & 0xFF) / 255.0f,
		                               (color & 0xFF) / 255.0f);
	}

	/**
	 * Calculate linear perceptual luminosity from a color in sRGB color space.
	 */
	public static float getPerceptualLuminosity(Color color) {
		return getPerceptualLuminosity(color.getRed() / 255.0f,
		                               color.getGreen() / 255.0f,
		                               color.getBlue() / 255.0f);
	}

	/**
	 * Calculate linear perceptual luminosity from a color in sRGB color space.
	 */
	public static float getPerceptualLuminosity(float r, float g, float b) {
		return fromSRGB(r) * 0.2126f + fromSRGB(g) * 0.7152f + fromSRGB(b) * 0.0722f;
	}

	public static double[] stretch(double r, double g, double b) {
		double max = Math.max(Math.max(r, g), b);
		double min = Math.min(Math.min(r, g), b);
		if (max == min) {
			min = 0;
			max = 1;
		}

		return new double[]{(r - min) / (max - min),
		                    (g - min) / (max - min),
		                    (b - min) / (max - min)};
	}
}
