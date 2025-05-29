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

//package org.digitalmodular.utilities.graphics.image.generator;
//
//import org.digitalmodular.utilities.math.RandomUtilities;
//import org.digitalmodular.utilities.math.RandomUtilities.FastRandom;
//import org.digitalmodular.utilities.graphics.image.ImageMatrix;
//
// /**
//  * @author Mark Jeronimus
//  */
//// Created 2012-04-03
//public class UniformNoiseGenerator extends ImageGenerator {
//	private static final FastRandom RND = RandomUtilities.FAST_RND;
//
//	public float amount;
//
//	public UniformNoiseGenerator(int width, int height, int border, float amount) {
//		super(width, height, border);
//
//		this.amount = amount;
//	}
//
//	@Override
//	public ImageMatrix generate() {
//		// Inner loop
//		int        x; // 3
//		float[]    row; // 1
//		float      amount     = this.amount; // 1
//		FastRandom rnd2       = UniformNoiseGenerator.RND; // 1
//		int        numColumns = image.numColumns; // 1
//
//		for (int y = 0; y < image.numRows; y++) {
//			row = image.matrix[0][y];
//			for (x = 0; x < numColumns; x++) {
//				row[x] = amount * rnd2.nextFloat();
//			}
//		}
//
//		return image;
//	}
//}
