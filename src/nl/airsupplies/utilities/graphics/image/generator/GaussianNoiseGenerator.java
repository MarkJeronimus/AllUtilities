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
