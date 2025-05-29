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

package nl.airsupplies.utilities.container;

import net.jcip.annotations.Immutable;

/**
 * @author Mark Jeronimus
 */
// Created 2015-08-31
@Immutable
public class Size2D {
	private final int width;
	private final int height;

	public Size2D(int width, int height) {
		this.width  = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Size2D flip() {
		return new Size2D(height, width);
	}

	@Override
	public String toString() {
		return "Size2D[" + width + ", " + height + ']';
	}
}
