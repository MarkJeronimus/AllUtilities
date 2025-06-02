package nl.airsupplies.utilities.graphics.color.colormodel;

import nl.airsupplies.utilities.graphics.color.Color3f;

/**
 * The digital version of YCbCr, the color model used by JPEG and DVD.
 * <p>
 * Components:
 * r = Y in [0, 1];
 * g = Pb in [-0.5, 0.5];
 * b = Pr in [-0.5, 0.5]
 *
 * @author Mark Jeronimus
 */
// Created 2025-06-01
@SuppressWarnings("UnaryPlus")
public class YPbPrColorModel extends ColorModel {
	@Override
	public Color3f fromRGB(Color3f color) {
		return new Color3f(+0.29900000f * color.r + 0.58700000f * color.g + 0.11400000f * color.b,
		                   -0.16873589f * color.r - 0.33126410f * color.g + 0.50000000f * color.b,
		                   +0.50000000f * color.r - 0.41868758f * color.g - 0.08131241f * color.b);
	}

	@Override
	public Color3f toRGB(Color3f color) {
		return new Color3f(color.r + 0.00000000f * color.g + 1.40200000f * color.b,
		                   color.r - 0.34413630f * color.g - 0.71413630f * color.b,
		                   color.r + 1.77200000f * color.g + 0.00000000f * color.b);
	}
}
