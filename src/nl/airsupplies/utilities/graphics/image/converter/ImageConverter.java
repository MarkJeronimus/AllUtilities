package nl.airsupplies.utilities.graphics.image.converter;

import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;

/**
 * @author Mark Jeronimus
 */
// Created 2011-11-22
@SuppressWarnings("unused")
public abstract class ImageConverter {
	/**
	 * Writes an image (but not the border) to a 1-dimensional array. The format of the array and what is done to the
	 * image data depends on the specific implementation of the .
	 *
	 * @param array      the array to store the image in.
	 * @param offset     the pointer in the array to start putting the image
	 * @param lineStride the increment value of the array index between the first pixels of two consecutive lines. This
	 *                   should be equal to or higher than the image width otherwise values of previous rows will be
	 *                   overwritten by subsequent rows.
	 */
	public void convertToArray(ImageMatrixFloat image, int[] array, int offset, int lineStride) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void convert(ImageMatrixFloat inImage, ImageMatrixFloat outImage) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void convertSelf(ImageMatrixFloat image) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void reverseToArray(ImageMatrixFloat image, int[] array, int offset, int lineStride) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void reverse(ImageMatrixFloat inImage, ImageMatrixFloat outImage) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void reverseSelf(ImageMatrixFloat image) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
