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

package nl.airsupplies.utilities.graphics.image.filter;

import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;

/**
 * @author Mark Jeronimus
 */
// Created 2012-04-19
public abstract class ImageFilter {
	@SuppressWarnings({"unused", "static-method"})
	public void filterSelf(ImageMatrixFloat image) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * The in and out images must be different images of the same dimensions (including border). Beforehand, set,
	 * extend, mirror or wrap the border as you wish. Pro tip: use image swapping to prevent copying an image every
	 * time.
	 */
	public void filter(ImageMatrixFloat in, ImageMatrixFloat out) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Parameters in and out may be the same image, but temp must be a different image of the same dimensions
	 * (including
	 * border). Beforehand, set, extend, mirror or wrap the border as you wish.
	 */
	public void filter(ImageMatrixFloat in, ImageMatrixFloat temp, ImageMatrixFloat out) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
