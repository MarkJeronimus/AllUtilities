package nl.airsupplies.utilities.graphics.color.colormodel;

import nl.airsupplies.utilities.graphics.color.Color3f;

/**
 * This color model is a cube with the grays at the volume-diagonal and the primaries and secondaries at vertices.
 * <p>
 * Components:
 * r = Red in [0, 1];
 * g = Green in [0, 1];
 * b = Blue in [0, 1]
 *
 * @author Mark Jeronimus
 */
// Created 2025-06-01
public class RGBColorModel extends ColorModel {
	@Override
	public Color3f fromRGB(Color3f color) {
		return color;
	}

	@Override
	public Color3f toRGB(Color3f color) {
		return color;
	}
}
