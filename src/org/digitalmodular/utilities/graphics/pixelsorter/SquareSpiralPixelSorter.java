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

/**
 * @author Mark Jeronimus
 */
// Created 2009-05-04
public class SquareSpiralPixelSorter extends PixelSorter {
	@Override
	protected double getSortOrder(int x, int y, int width, int height) {
		int dx = x - (width >> 1);
		int dy = y - (height >> 1);

		if (width > height)
		// pattern:
		// 2 3
		// 1 0
		{
			if (dx > dy) {
				if (dx < -dy) {
					return 4 * dy * dy + dy + dx; // Top
				} else {
					return 4 * dx * dx + dx + dy; // Right
				}
			} else {
				if (dx < -dy) {
					return 4 * dx * dx + 3 * dx - dy; // Left
				} else {
					return 4 * dy * dy + 3 * dy - dx; // Bottom
				}
			}
		} else {
			// pattern:
			// 2 1
			// 3 0
			if (dx > dy) {
				if (dx < -dy) {
					return 4 * dy * dy + 3 * dy - dx; // Top
				} else {
					return 4 * dx * dx + 3 * dx - dy; // Right
				}
			} else {
				if (dx < -dy) {
					return 4 * dx * dx + dx + dy; // Left
				} else {
					return 4 * dy * dy + dy + dx; // Bottom
				}
			}
		}
	}
}
