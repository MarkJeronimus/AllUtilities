package nl.airsupplies.utilities.graphics.pixelsorter;

/**
 * @author Mark Jeronimus
 */
// Created 2016-02-04
public class FlowerPixelSorter extends PixelSorter {
	private final double petals;

	public FlowerPixelSorter(int numPetals) {
		petals = numPetals * 0.5;
	}

	@Override
	protected double getSortOrder(int x, int y, int width, int height) {
		double dx = x - (width - 0.75) * 0.5;
		double dy = y - (height - 0.875) * 0.5;

		double a = StrictMath.atan2(dx, -dy) * petals;
		double r = StrictMath.hypot(dx, dy);

		return r / Math.abs(StrictMath.cos(a));
	}
}
