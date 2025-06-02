package nl.airsupplies.utilities.graphics.pixelsorter;

/**
 * @author Mark Jeronimus
 */
// Created 2009-05-03
public class XorPixelSorter extends PixelSorter {
	@Override
	protected double getSortOrder(int x, int y, int width, int height) {
		int dx = StrictMath.abs(x - (width >> 1));
		int dy = StrictMath.abs(y - (height >> 1));

		return dx ^ dy;
	}
}
