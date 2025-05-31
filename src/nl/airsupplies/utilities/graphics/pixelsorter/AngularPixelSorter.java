package nl.airsupplies.utilities.graphics.pixelsorter;

import static nl.airsupplies.utilities.constant.NumberConstants.TAU;

/**
 * @author Mark Jeronimus
 */
// Created 2009-05-02
public class AngularPixelSorter extends PixelSorter {
	private final int bands;

	public AngularPixelSorter(int bands) {
		this.bands = bands;
	}

	@Override
	protected double getSortOrder(int x, int y, int width, int height) {
		double dx = x - (width - 0.75) * 0.5;
		double dy = y - (height - 0.875) * 0.5;

		double a = StrictMath.atan2(dx, dy) / TAU + 0.5;

		return StrictMath.abs(a * bands % 1 - 0.5);
	}
}
