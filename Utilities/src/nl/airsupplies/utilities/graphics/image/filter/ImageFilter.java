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
