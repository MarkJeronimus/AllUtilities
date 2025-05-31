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
