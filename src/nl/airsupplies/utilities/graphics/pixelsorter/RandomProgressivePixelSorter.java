package nl.airsupplies.utilities.graphics.pixelsorter;

import java.awt.Point;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import nl.airsupplies.utilities.graphics.pixelsorter.SpiralPixelSorter.SpiralType;

/**
 * @author Mark Jeronimus
 */
// Created 2016-02-06
public class RandomProgressivePixelSorter extends PixelSorter {
	PixelSorter[] pixelSorters = {
			new CRTPixelSorter(),
			new DitherPixelSorter(),
			new RadialPixelSorter(),
			new RandomPermutationPixelSorter(),
			new SpiralPixelSorter(0.02, SpiralType.ARCHIMEDEAN),
			new SpiralPixelSorter(0.05, SpiralType.FERMAT),
			new SquareSpiralPixelSorter(),
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
