package nl.airsupplies.utilities.graphics.image.converter;

import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;

/**
 * Make grayscale
 *
 * @author Mark Jeronimus
 */
// Created 2011-11-22
public class ImageGrayScaleConverter extends ImageConverter {
	@Override
	public void convertToArray(ImageMatrixFloat image, int[] array, int offset, int lineStride) {
		// Inner loop
		int     x;
		int     endX = image.endX;
		int     p    = offset + (image.numColumns + 1) * image.border;
		float[] row0;
		float[] row1;
		float[] row2;
		float[] row3;

		switch (image.numComponents) {
			case 1:
				for (int y = image.border; y < image.endY; y++) {
					row0 = image.matrix[0][y];
					for (x = image.border; x < endX; x++) {
						array[p] = 0xFF000000 | 0x10101 * (int)(row0[x] * 255 + 0.5);
						p++;
					}
					p += lineStride - image.numColumns;
				}
				break;
			case 2:
				for (int y = image.border; y < image.endY; y++) {
					row0 = image.matrix[0][y];
					row1 = image.matrix[1][y];
					for (x = image.border; x < endX; x++) {
						array[p] = (int)(row1[x] * 255 + 0.5) << 24 | 0x10101 * (int)(row0[x] * 255 + 0.5);
						p++;
					}
					p += lineStride - image.numColumns;
				}
				break;
			case 3:
				for (int y = image.border; y < image.endY; y++) {
					row0 = image.matrix[0][y];
					row1 = image.matrix[1][y];
					row2 = image.matrix[2][y];
					for (x = image.border; x < endX; x++) {
						array[p] = 0xFF000000 | 0x10101 *
						                        (int)((row0[x] * 0.299f + row1[x] * 0.587f + row2[x] * 0.114f) *
						                              255 + 0.5);
						p++;
					}
					p += lineStride - image.numColumns;
				}
				break;
			case 4:
				for (int y = image.border; y < image.endY; y++) {
					row0 = image.matrix[0][y];
					row1 = image.matrix[1][y];
					row2 = image.matrix[2][y];
					row3 = image.matrix[3][y];
					for (x = image.border; x < endX; x++) {
						array[p] = (int)(row3[x] * 255 + 0.5) << 24 | 0x10101
						                                              * (int)((row0[x] * 0.299f + row1[x] * 0.587f +
						                                                       row2[x] * 0.114f) * 255 + 0.5);
						p++;
					}
					p += lineStride - image.numColumns;
				}
				break;
			default:
				throw new IllegalArgumentException("Unsupported number of components: " + image.numComponents);
		}
	}

	@Override
	public void convert(ImageMatrixFloat inImage, ImageMatrixFloat outImage) {
		// Inner loop
		int     x;
		int     endX = inImage.endX;
		float[] rowIn0;
		float[] rowIn1;
		float[] rowIn2;
		float[] rowOut0;
		float[] rowIn3;
		float[] rowOut1;

		switch (inImage.numComponents) {
			case 1:
				for (int y = inImage.border; y < inImage.endY; y++) {
					rowIn0  = inImage.matrix[0][y];
					rowOut0 = outImage.matrix[0][y];
					for (x = inImage.border; x < endX; x++) {
						rowOut0[x] = rowIn0[x];
					}
				}
				break;
			case 2:
				for (int y = inImage.border; y < inImage.endY; y++) {
					rowIn0  = inImage.matrix[0][y];
					rowIn1  = inImage.matrix[1][y];
					rowOut0 = outImage.matrix[0][y];
					rowOut1 = outImage.matrix[1][y];
					for (x = inImage.border; x < endX; x++) {
						rowOut0[x] = rowIn0[x];
						rowOut1[x] = rowIn1[x];
					}
				}
				break;
			case 3:
				for (int y = inImage.border; y < inImage.endY; y++) {
					rowIn0  = inImage.matrix[0][y];
					rowIn1  = inImage.matrix[1][y];
					rowIn2  = inImage.matrix[2][y];
					rowOut0 = outImage.matrix[0][y];
					for (x = inImage.border; x < endX; x++) {
						rowOut0[x] = rowIn0[x] * 0.299f + rowIn1[x] * 0.587f + rowIn2[x] * 0.114f;
					}
				}
				break;
			case 4:
				for (int y = inImage.border; y < inImage.endY; y++) {
					rowIn0  = inImage.matrix[0][y];
					rowIn1  = inImage.matrix[1][y];
					rowIn2  = inImage.matrix[2][y];
					rowIn3  = inImage.matrix[3][y];
					rowOut0 = outImage.matrix[0][y];
					rowOut1 = outImage.matrix[1][y];
					for (x = inImage.border; x < endX; x++) {
						rowOut0[x] = rowIn0[x] * 0.299f + rowIn1[x] * 0.587f + rowIn2[x] * 0.114f;
						rowOut1[x] = rowIn3[x];
					}
				}
				break;
			default:
				throw new IllegalArgumentException("Unsupported number of components: " + inImage.numComponents);
		}
	}
}
