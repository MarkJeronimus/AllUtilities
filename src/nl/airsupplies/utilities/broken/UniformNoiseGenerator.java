package nl.airsupplies.utilities.broken;

import java.util.concurrent.ThreadLocalRandom;

import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;
import nl.airsupplies.utilities.graphics.image.generator.ImageGenerator;

/**
 * @author Mark Jeronimus
 */
// Created 2012-04-03
public class UniformNoiseGenerator extends ImageGenerator {
	public float amount;

	public UniformNoiseGenerator(float amount) {
		this.amount = amount;
	}

	@Override
	public ImageMatrixFloat generate() {
		// Inner loop
		int               x; // 3
		float[]           row; // 1
		float             amount     = this.amount; // 1
		ThreadLocalRandom rnd2       = ThreadLocalRandom.current();
		int               numColumns = image.numColumns; // 1

		for (int y = 0; y < image.numRows; y++) {
			row = image.matrix[0][y];
			for (x = 0; x < numColumns; x++) {
				row[x] = amount * rnd2.nextFloat();
			}
		}

		return image;
	}
}
