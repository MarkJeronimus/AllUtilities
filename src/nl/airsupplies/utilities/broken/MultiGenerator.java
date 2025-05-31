package nl.airsupplies.utilities.broken;

import java.util.ArrayList;
import java.util.List;

import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;
import nl.airsupplies.utilities.graphics.image.generator.ImageGenerator;

/**
 * @author Mark Jeronimus
 */
// Created 2012-04-05
public class MultiGenerator extends ImageGenerator {
	private List<ImageGenerator> generators = new ArrayList<>();

	public void add(ImageGenerator generator) {
		generators.add(generator);
	}

	@Override
	public ImageMatrixFloat generate() {
		image.set(0);

		for (ImageGenerator generator : generators) {
			image.add(generator.generate());
		}

		return image;
	}
}
