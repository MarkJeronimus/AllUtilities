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

package nl.airsupplies.utilities.graphics.pixelsorter;

/**
 * @author Mark Jeronimus
 */
// Created 2009-05-03
public class CogPixelSorter extends PixelSorter {
	private final int    petals;
	private final double toothSize;

	public CogPixelSorter(int petals, double toothSize) {
		this.petals    = petals;
		this.toothSize = 4 / toothSize + 1;
	}

	@Override
	protected double getSortOrder(int x, int y, int width, int height) {
		double dx = x - (width - 0.75) * 0.5;
		double dy = y - (height - 0.875) * 0.5;

		double a = StrictMath.atan2(dx, -dy) * petals;
		double r = StrictMath.hypot(dx, dy);

		return r * (StrictMath.cos(a) + toothSize);
	}
}
