package nl.airsupplies.utilities.graphics.pixelsorter;

/**
 * @author Mark Jeronimus
 */
// Created 2009-05-03
public class DitherPixelSorter extends PixelSorter {
	@Override
	protected double getSortOrder(int x, int y, int width, int height) {
		int dy = Integer.reverse(y) >>> 1;
		int dx = Integer.reverse(x) >>> 1;

		return (double)dx + dy;
	}
}
