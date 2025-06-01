package nl.airsupplies.utilities.graphics.color;

import nl.airsupplies.utilities.graphics.color.colormodel.CIEXYZColorModel;
import nl.airsupplies.utilities.graphics.color.colormodel.ColorModel;
import nl.airsupplies.utilities.graphics.color.colormodel.HCIColorModel;
import nl.airsupplies.utilities.graphics.color.colormodel.HCLColorModel;
import nl.airsupplies.utilities.graphics.color.colormodel.HSLColorModel;
import nl.airsupplies.utilities.graphics.color.colormodel.HSVColorModel;
import nl.airsupplies.utilities.graphics.color.colormodel.RGBColorModel;
import nl.airsupplies.utilities.graphics.color.colormodel.YIQColorModel;
import nl.airsupplies.utilities.graphics.color.colormodel.YPbPrColorModel;
import nl.airsupplies.utilities.graphics.color.colormodel.YUVColorModel;

/**
 * @author Mark Jeronimus
 */
// Created 2016-02-28
public enum ColorModels {
	/**
	 * This color model is a cube with the grays at the volume-diagonal and the primaries and secondaries at vertices.
	 */
	RGB(new RGBColorModel()),
	/**
	 * This color model is a hexagonal cylinder with the grays at the central axis and the primary and
	 * secondary colors on a hexagonal plane at the top.
	 */
	HSV(new HSVColorModel()),
	/**
	 * This color model is a hexagonal cylinder with the grays at the central axis, white at the top,
	 * and the primary and secondary colors on a hexagonal plane at the center.
	 */
	HSL(new HSLColorModel()),
	/**
	 * This color model is a hexagonal bipyramid with the grays at the central axis
	 * and the primary and secondary colors on a hexagonal plane at the center.
	 * <p>
	 * Compared to HSL, Saturation is replaced with Chroma my multiplying by Lightness.
	 * <p>
	 * The input is assumed to be sRGB, and the output Intensity is linear.
	 */
	HCL(new HCLColorModel()),
	/**
	 * This color model is a hexagonal bipyramid with the grays at the central axis
	 * and the primary and secondary colors on a hexagonal plane at the center.
	 * <p>
	 * Compared to HSL, Saturation is replaced with Chroma my multiplying by Lightness,
	 * and Lightness is then replaced by perceptual Intensity.
	 * <p>
	 * The input is assumed to be sRGB, and the output Intensity is linear.
	 */
	HCI(new HCIColorModel()),
	/** The color model used by PAL TV. */
	YUV(new YUVColorModel()),
	/** The color model used by NTSC TV. */
	YIQ(new YIQColorModel()),
	/** The digital version of YCbCr, the color model used by JPEG and DVD. */
	YPBPR(new YPbPrColorModel()),
	/** The XYZ color model from the CIE 1931 color space. */
	CIEXYZ(new CIEXYZColorModel());

	private final ColorModel colorModel;

	ColorModels(ColorModel colorModel) {
		this.colorModel = colorModel;
	}

	public Color3f fromRGB(Color3f color) {
		return colorModel.fromRGB(color);
	}

	public Color3f toRGB(Color3f color) {
		return colorModel.toRGB(color);
	}
}
