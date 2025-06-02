package nl.airsupplies.utilities.graphics.pixelsorter;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Mark Jeronimus
 */
// Created 2009-05-02
public class RandomPermutationPixelSorter extends PixelSorter {
	@Override
	protected double getSortOrder(int x, int y, int width, int height) {
		return ThreadLocalRandom.current().nextDouble();
	}
}
