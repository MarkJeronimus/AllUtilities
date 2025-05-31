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
