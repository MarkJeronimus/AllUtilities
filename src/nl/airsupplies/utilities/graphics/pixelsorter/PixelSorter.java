package nl.airsupplies.utilities.graphics.pixelsorter;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mark Jeronimus
 */
// Created 2009-04-17
// Changed 2016-02-04
public abstract class PixelSorter {
	public static List<Point> getSortedPixels(int width, int height, PixelSorter sorter) {
		ScheduledPoint[] pixels = new ScheduledPoint[width * height];

		int i = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				double z = sorter.getSortOrder(x, y, width, height);
				pixels[i] = new ScheduledPoint(x, y, z);
				i++;
			}
		}

		Arrays.parallelSort(pixels);

		return Arrays.asList(pixels);
	}

	public List<Point> getSortedPixels(int width, int height) {
		return getSortedPixels(width, height, this);
	}

	/**
	 * Note: this class has a natural ordering that is inconsistent with equals.
	 */
	private static final class ScheduledPoint extends Point implements Comparable<ScheduledPoint> {
		private final double sortOrder;

		private ScheduledPoint(int x, int y, double sortOrder) {
			super(x, y);
			this.sortOrder = sortOrder;
		}

		@Override
		public int compareTo(ScheduledPoint o) {
			return java.lang.Double.compare(sortOrder, o.sortOrder);
		}
	}

	protected abstract double getSortOrder(int x, int y, int width, int height);
}
