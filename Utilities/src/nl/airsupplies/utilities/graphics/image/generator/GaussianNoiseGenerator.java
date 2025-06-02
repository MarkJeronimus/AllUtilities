package nl.airsupplies.utilities.graphics.image.generator;

import java.util.concurrent.ThreadLocalRandom;

import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;
import nl.airsupplies.utilities.math.FastRandom;
import nl.airsupplies.utilities.nodes.DoubleParam;
import nl.airsupplies.utilities.nodes.Node;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAbove;

/**
 * @author Mark Jeronimus
 */
// Created 2012-04-03
// Updated 2016-04-03 Converted to Node
@Node(name = "Gaussian Noise", description = "Gaussian Noise Generator")
public class GaussianNoiseGenerator extends ImageGenerator {
	private static final ThreadLocal<FastRandom> fastRand =
			ThreadLocal.withInitial(() -> new FastRandom(ThreadLocalRandom.current().nextLong()));

	@DoubleParam(description = "", min = 0, minIsInclusive = false, maxIsInclusive = false)
	private double amount = 1.0;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = requireAbove(0, amount, "amount");
	}

	@Override
	public ImageMatrixFloat generate() {
		// Inner loop
		int        x;                               // 3
		FastRandom rnd        = fastRand.get();     // 3
		float      amount     = (float)getAmount(); // 1
		int        numColumns = image.numColumns;   // 1

		for (float[] row : image.matrix[0]) {
			for (x = 0; x < numColumns; x++) {
				row[x] = amount * rnd.nextBell();
			}
		}

		return image;
	}
}
