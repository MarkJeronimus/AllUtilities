package nl.airsupplies.utilities.graphics.pixelsorter;

/**
 * @author Mark Jeronimus
 */
// Created 2009-05-04
public class SquareSpiralPixelSorter extends PixelSorter {
	@Override
	protected double getSortOrder(int x, int y, int width, int height) {
		int dx = x - (width >> 1);
		int dy = y - (height >> 1);

		if (width > height)
		// pattern:
		// 2 3
		// 1 0
		{
			if (dx > dy) {
				if (dx < -dy) {
					return 4 * dy * dy + dy + dx; // Top
				} else {
					return 4 * dx * dx + dx + dy; // Right
				}
			} else {
				if (dx < -dy) {
					return 4 * dx * dx + 3 * dx - dy; // Left
				} else {
					return 4 * dy * dy + 3 * dy - dx; // Bottom
				}
			}
		} else {
			// pattern:
			// 2 1
			// 3 0
			if (dx > dy) {
				if (dx < -dy) {
					return 4 * dy * dy + 3 * dy - dx; // Top
				} else {
					return 4 * dx * dx + 3 * dx - dy; // Right
				}
			} else {
				if (dx < -dy) {
					return 4 * dx * dx + dx + dy; // Left
				} else {
					return 4 * dy * dy + dy + dx; // Bottom
				}
			}
		}
	}
}
