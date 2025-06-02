package nl.airsupplies.utilities.graphics.pixelsorter;

/**
 * @author Mark Jeronimus
 */
// Created 2009-05-02
public class ClockPixelSorter extends PixelSorter {

	public ClockPixelSorter() {
	}

	@Override
	protected double getSortOrder(int x, int y, int width, int height) {
		double dx = x - (width - 0.75) * 0.5;
		double dy = y - (height - 0.875) * 0.5;

		return -StrictMath.atan2(dx, dy);
	}
}
