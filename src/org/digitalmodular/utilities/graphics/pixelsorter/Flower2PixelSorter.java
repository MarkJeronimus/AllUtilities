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

package org.digitalmodular.utilities.graphics.pixelsorter;

import static org.digitalmodular.utilities.constant.NumberConstants.TAU;

/**
 * @author Mark Jeronimus
 */
// Created 2009-05-02
public class Flower2PixelSorter extends PixelSorter {
	private final int numPetals;

	public Flower2PixelSorter(int numPetals) {
		this.numPetals = numPetals;
	}

	@Override
	protected double getSortOrder(int x, int y, int width, int height) {
		double dx = x - (width - 0.75) * 0.5;
		double dy = y - (height - 0.875) * 0.5;

		double a = StrictMath.atan2(dx, dy) / TAU + 0.5;
		double r = StrictMath.hypot(dx, dy);

		return -StrictMath.abs(a * numPetals % 1 - 0.5) + r / 1000;
	}
}
