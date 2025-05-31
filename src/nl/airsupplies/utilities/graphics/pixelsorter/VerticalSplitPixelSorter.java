package nl.airsupplies.utilities.graphics.pixelsorter;

/**
 * @author Mark Jeronimus
 */
// Created 2009-05-03
public class VerticalSplitPixelSorter extends PixelSorter {
	@Override
	protected double getSortOrder(int x, int y, int width, int height) {
		double dx = StrictMath.abs(x - width * 0.5) * width;
		double dy = StrictMath.abs(y - height * 0.5);

		return dx + dy;
	}
}
