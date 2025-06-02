package nl.airsupplies.utilities.graphics.pixelsorter;

/**
 * @author Mark Jeronimus
 */
// Created 2009-05-03
public class CogPixelSorter extends PixelSorter {
	private final int    petals;
	private final double toothSize;

	public CogPixelSorter(int petals, double toothSize) {
		this.petals    = petals;
		this.toothSize = 4 / toothSize + 1;
	}

	@Override
	protected double getSortOrder(int x, int y, int width, int height) {
		double dx = x - (width - 0.75) * 0.5;
		double dy = y - (height - 0.875) * 0.5;

		double a = StrictMath.atan2(dx, -dy) * petals;
		double r = StrictMath.hypot(dx, dy);

		return r * (StrictMath.cos(a) + toothSize);
	}
}
