package nl.airsupplies.utilities.graphics.color.colormodel;

import nl.airsupplies.utilities.graphics.color.Color3f;

/**
 * The XYZ color model from the CIE 1931 color space.
 * <p>
 * Components:
 * r = X in [0, 1];
 * g = Y in [0, 1];
 * b = Z in [0, 1]
 *
 * @author Mark Jeronimus
 */
// Created 2025-06-01
@SuppressWarnings("UnaryPlus")
public class CIEXYZColorModel extends ColorModel {
	@Override
	public Color3f fromRGB(Color3f color) {
		return new Color3f(0.43030295f * color.r + 0.34163640f * color.g + 0.17822778f * color.b,
		                   0.22187495f * color.r + 0.70683396f * color.g + 0.07129111f * color.b,
		                   0.02017045f * color.r + 0.12958622f * color.g + 0.93866630f * color.b);
	}

	@Override
	public Color3f toRGB(Color3f color) {
		return new Color3f(+2.06084660f * color.r - 0.93738980f * color.g - 0.32010582f * color.b,
		                   -1.14153440f * color.r + 2.20943570f * color.g + 0.04894180f * color.b,
		                   +0.08068783f * color.r - 0.27204585f * color.g + 1.27116410f * color.b);
	}
}
