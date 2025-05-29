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

package nl.airsupplies.utilities.graphics.pixelsorter;

import java.awt.Point;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import nl.airsupplies.utilities.graphics.pixelsorter.SpiralPixelSorter.SpiralType;

/**
 * @author Mark Jeronimus
 */
// Created 2016-02-04
public class RandomPixelSorter extends PixelSorter {
	PixelSorter[] pixelSorters = {
			new AngularPixelSorter(12),
			new ClockPixelSorter(),
			new CogPixelSorter(12, 1),
			new CRTPixelSorter(),
			new DitherPixelSorter(),
			new FlowerPixelSorter(7),
			new Flower2PixelSorter(4),
			new RadialPixelSorter(),
			new RandomPermutationPixelSorter(),
			new SierpinskyPixelSorter(),
			new SpiralPixelSorter(0.1, SpiralType.ARCHIMEDEAN),
			new SpiralPixelSorter(0.05, SpiralType.FERMAT),
			new SquareSpiralPixelSorter(),
			new XorPixelSorter(),
			};

	@Override
	protected double getSortOrder(int x, int y, int width, int height) {
		return 0;
	}

	@Override
	public List<Point> getSortedPixels(int width, int height) {
		PixelSorter pixelSorter = pixelSorters[ThreadLocalRandom.current().nextInt(pixelSorters.length)];

		return getSortedPixels(width, height, pixelSorter);
	}
}
