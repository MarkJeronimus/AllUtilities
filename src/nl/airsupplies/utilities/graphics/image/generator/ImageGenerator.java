package nl.airsupplies.utilities.graphics.image.generator;

import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;
import nl.airsupplies.utilities.nodes.Generator;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2012-04-04
// Updated 2016-04-03 Converted to Node
public abstract class ImageGenerator implements Generator<ImageMatrixFloat, ImageMatrixFloat> {
	protected ImageMatrixFloat image = null;

	@Override
	public void initialize(ImageMatrixFloat input) {
		image = requireNonNull(input, "input");
	}
}
