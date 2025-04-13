/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.digitalmodular.utilities.graphics.pixelsorter;

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

		return List.of(pixels);
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
