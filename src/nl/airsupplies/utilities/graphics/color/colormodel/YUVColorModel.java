package nl.airsupplies.utilities.graphics.color.colormodel;

import nl.airsupplies.utilities.graphics.color.Color3f;

/**
 * The color model used by PAL TV.
 * <p>
 * Components:
 * r = Y in [0, 1];
 * g = U in [-0.436, 0.436];
 * b = V in [-0.615, 0.615]
 *
 * @author Mark Jeronimus
 */
// Created 2025-06-01
@SuppressWarnings("UnaryPlus")
public class YUVColorModel extends ColorModel {
	@Override
	public Color3f fromRGB(Color3f color) {
		return new Color3f(+0.2990000f * color.r + 0.58700000f * color.g + 0.11400000f * color.b,
		                   -0.1471377f * color.r - 0.28886230f * color.g + 0.43600000f * color.b,
		                   +0.6150000f * color.r - 0.51498574f * color.g - 0.10001426f * color.b);
	}

	@Override
	public Color3f toRGB(Color3f color) {
		return new Color3f(color.r + 0.00000000f * color.g + 1.13983740f * color.b,
		                   color.r - 0.39465170f * color.g - 0.58059860f * color.b,
		                   color.r + 2.03211000f * color.g + 0.00000000f * color.b);
	}
}
