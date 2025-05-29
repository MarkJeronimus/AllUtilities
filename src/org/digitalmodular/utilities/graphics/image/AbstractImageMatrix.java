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

package org.digitalmodular.utilities.graphics.image;

import static org.digitalmodular.utilities.ValidatorUtilities.requireAtLeast;

/**
 * @author Mark Jeronimus
 */
// Created 2013-02-04
public abstract class AbstractImageMatrix {
	/** Width of usable image area */
	public final int width;
	/** Height of usable image area */
	public final int height;

	/** Width of image matrix, always equal to {@link #width}+2 {@link #border}. */
	public final int numColumns;
	/** Height of image matrix, always equal to {@link #height}+2 {@link #border}. */
	public final int numRows;
	/** Color components per pixel */
	public final int numComponents;

	// Borders, required for certain operations, like convolve.
	public int border;
	public int border2;
	public int endX;
	public int endY;

	protected AbstractImageMatrix(int width, int height, int numComponents, int border) {
		requireAtLeast(1, numComponents, "numComponents");

		this.width         = width;
		this.height        = height;
		this.border        = border;
		this.numComponents = numComponents;

		border2    = border * 2;
		numColumns = width + border2;
		numRows    = height + border2;
		endX       = width + border;
		endY       = height + border;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getBorderSize() {
		return border;
	}

	public int getNumComponents() {
		return numComponents;
	}

	public boolean isCompatibleByBorderAndSize(AbstractImageMatrix other) {
		return border == other.border && other.width >= width && other.height >= height;
	}

	public boolean isCompatibleBySize(AbstractImageMatrix other) {
		return other.width >= width && other.height >= height;
	}
}
