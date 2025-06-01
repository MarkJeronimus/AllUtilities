package nl.airsupplies.utilities.graphics.color.colormodel;

import nl.airsupplies.utilities.graphics.color.Color3f;

/**
 * The color model used by NTSC TV.
 * <p>
 * Components:
 * r = Y in [0, 1];
 * g = I in [-0.5, 0.5];
 * b = Q in [-0.5, 0.5]
 *
 * @author Mark Jeronimus
 */
// Created 2025-06-01
public class YIQColorModel extends ColorModel {
	@Override
	public Color3f fromRGB(Color3f color) {
		return new Color3f(0.29900000f * color.r + 0.58700000f * color.g + 0.11400000f * color.b,
		                   0.59571610f * color.r - 0.27445284f * color.g - 0.32126328f * color.b,
		                   0.21145640f * color.r - 0.52259105f * color.g + 0.31113464f * color.b);
	}

	@Override
	public Color3f toRGB(Color3f color) {
		return new Color3f(color.r + 0.95629483f * color.g + 0.62102515f * color.b,
		                   color.r - 0.27212146f * color.g - 0.64738095f * color.b,
		                   color.r - 1.10698990f * color.g + 1.70461500f * color.b);
	}
}
