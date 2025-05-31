package nl.airsupplies.utilities.graphics.pixelsorter;

import static nl.airsupplies.utilities.constant.NumberConstants.TAU;

/**
 * @author Mark Jeronimus
 */
// Created 2009-05-02
public class SpiralPixelSorter extends PixelSorter {
	public enum SpiralType {
		ARCHIMEDEAN,
		FERMAT
	}

	private final double     thickness;
	private final SpiralType type;

	public SpiralPixelSorter(double thickness) {
		this(thickness, SpiralType.ARCHIMEDEAN);
	}

	public SpiralPixelSorter(double thickness, SpiralType type) {
		this.thickness = thickness;
		this.type      = type;
	}

	@Override
	protected double getSortOrder(int x, int y, int width, int height) {
		double dx = x - (width - 0.75) * 0.5;
		double dy = y - (height - 0.875) * 0.5;

		double a = StrictMath.atan2(dx, dy) / TAU;
		double r = StrictMath.hypot(dx, dy);

		double t = Math.hypot(width, height) * 0.5 * thickness;

		if (type == SpiralType.FERMAT) {
			r *= r / 100;
			t *= t / 10;
		}

		return Math.rint(a + r / t) * t - a;
	}
}
