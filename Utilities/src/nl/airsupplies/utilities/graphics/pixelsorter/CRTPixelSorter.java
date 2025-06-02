package nl.airsupplies.utilities.graphics.pixelsorter;

/**
 * @author Mark Jeronimus
 */
// Created 2009-05-05
public class CRTPixelSorter extends PixelSorter {
	@Override
	protected double getSortOrder(int x, int y, int width, int height) {
		int dy = height - y - 1;
		int dx = width - x - 1;
		return -1.0 * x * y * dx * dy;
	}
}
