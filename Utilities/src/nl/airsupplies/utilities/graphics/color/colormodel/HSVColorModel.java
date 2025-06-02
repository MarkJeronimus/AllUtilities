package nl.airsupplies.utilities.graphics.color.colormodel;

import nl.airsupplies.utilities.graphics.color.Color3f;

/**
 * This color model is a hexagonal cylinder with the grays at the central axis and the primary and
 * secondary colors on a hexagonal plane at the top.
 * <p>
 * Components:
 * r = Hue in [0, 1];
 * g = Saturation in [0, 1];
 * b = Value in [0, 1]
 *
 * @author Mark Jeronimus
 */
// Created 2025-06-01
public class HSVColorModel extends ColorModel {
	@Override
	public Color3f fromRGB(Color3f color) {
		float min = Math.min(Math.min(color.r, color.g), color.b);
		float max = Math.max(Math.max(color.r, color.g), color.b);

		float chroma = max - min;

		if (chroma < 1.0e-7f) {
			return new Color3f(0.0f, 0, max);
		}

		float sat = max < 1.0e-7f ? 0.0f : chroma / max;

		return new Color3f(rgb2hueInternal(color.r, color.g, color.b, min, max, chroma), sat, max);
	}

	@Override
	public Color3f toRGB(Color3f color) {
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
}
