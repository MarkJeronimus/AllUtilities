package nl.airsupplies.utilities.graphics.color.colormodel;

import nl.airsupplies.utilities.graphics.color.Color3f;

/**
 * @author Mark Jeronimus
 */
// Created 2025-06-01
public abstract class ColorModel {
	public abstract Color3f fromRGB(Color3f color);

	public abstract Color3f toRGB(Color3f color);

	protected static float rgb2hueInternal(float r, float g, float b, float min, float max, float chroma) {
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
}
