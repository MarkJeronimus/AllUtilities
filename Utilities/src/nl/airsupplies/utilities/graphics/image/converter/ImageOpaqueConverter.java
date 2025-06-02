package nl.airsupplies.utilities.graphics.image.converter;

import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;

/**
 * Convert to opaque. Premultiplies opacity, is available.
 *
 * @author Mark Jeronimus
 */
// Created 2011-11-22
public class ImageOpaqueConverter extends ImageConverter {
	@Override
	public void convertToArray(ImageMatrixFloat image, int[] array, int offset, int lineStride) {
		// Inner loop
		int     x;
		int     endX = image.endX;
		int     p    = offset;
		float[] row0;
		float[] row1;
		float[] row2;
		float[] row3;

		switch (image.numComponents) {
			case 1:
				for (int y = image.border; y < image.endY; y++) {
					row0 = image.matrix[0][y];
					for (x = image.border; x < endX; x++) {
						array[p] = 0xFF000000 | 0x10101 * ((int)(row0[x] * 255 + 0.5) & 0xFF);
						p++;
					}
					p += lineStride - image.width;
				}
				break;
			case 2:
				for (int y = image.border; y < image.endY; y++) {
					row0 = image.matrix[0][y];
					row1 = image.matrix[1][y];
					for (x = image.border; x < endX; x++) {
						array[p] = 0xFF000000 | 0x10101 * ((int)(row0[x] * row1[x] * 255 + 0.5) & 0xFF);
						p++;
					}
					p += lineStride - image.width;
				}
				break;
			case 3:
				for (int y = image.border; y < image.endY; y++) {
					row0 = image.matrix[0][y];
					row1 = image.matrix[1][y];
					row2 = image.matrix[2][y];
					for (x = image.border; x < endX; x++) {
						array[p] = 0xFF000000 |
						           (((int)(row0[x] * 255 + 0.5) & 0xFF) << 8 |
						            (int)(row1[x] * 255 + 0.5) & 0xFF) << 8 |
						           (int)(row2[x] * 255 + 0.5) & 0xFF;
						p++;
					}
					p += lineStride - image.width;
				}
				break;
			case 4:
				for (int y = image.border; y < image.endY; y++) {
					row0 = image.matrix[0][y];
					row1 = image.matrix[1][y];
					row2 = image.matrix[2][y];
					row3 = image.matrix[3][y];
					for (x = image.border; x < endX; x++) {
						array[p] = ((((int)(row3[x] * 255 + 0.5) & 0xFF) << 8 |
						             (int)(row0[x] * 255 + 0.5) & 0xFF) << 8 |
						            (int)(row1[x] * 255 + 0.5) & 0xFF) << 8 |
						           (int)(row2[x] * 255 + 0.5) & 0xFF;
						p++;
					}
					p += lineStride - image.width;
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
		float[] rowOut0;
		float[] rowIn1;
		float[] rowOut1;
		float[] rowOut2;
		float[] rowIn3;
		float[] rowIn2;

		ImageMatrixFloat
				out = new ImageMatrixFloat(inImage.width, inImage.height, inImage.numComponents - 1 | 1, inImage
				.border);

		switch (inImage.numComponents) {
			case 1:
				for (int y = inImage.border; y < inImage.endY - 1; y++) {
					rowIn0  = inImage.matrix[0][y];
					rowOut0 = out.matrix[0][y];
					for (x = inImage.border; x < endX; x++) {
						rowOut0[x] = rowIn0[x];
					}
				}
				break;
			case 2:
				for (int y = inImage.border; y < inImage.endY - 1; y++) {
					rowIn0  = inImage.matrix[0][y];
					rowIn1  = inImage.matrix[1][y];
					rowOut0 = out.matrix[0][y];
					for (x = inImage.border; x < endX; x++) {
						rowOut0[x] = rowIn0[x] * rowIn1[x];
					}
				}
				break;
			case 3:
				for (int y = inImage.border; y < inImage.endY - 1; y++) {
					rowIn0  = inImage.matrix[0][y];
					rowIn1  = inImage.matrix[1][y];
					rowIn2  = inImage.matrix[2][y];
					rowOut0 = out.matrix[0][y];
					rowOut1 = out.matrix[1][y];
					rowOut2 = out.matrix[2][y];
					for (x = inImage.border; x < endX; x++) {
						rowOut0[x] = rowIn0[x];
						rowOut1[x] = rowIn1[x];
						rowOut2[x] = rowIn2[x];
					}
				}
				break;
			case 4:
				for (int y = inImage.border; y < inImage.endY - 1; y++) {
					rowIn0  = inImage.matrix[0][y];
					rowIn1  = inImage.matrix[1][y];
					rowIn2  = inImage.matrix[2][y];
					rowIn3  = inImage.matrix[3][y];
					rowOut0 = out.matrix[0][y];
					rowOut1 = out.matrix[1][y];
					rowOut2 = out.matrix[2][y];
					for (x = inImage.border; x < endX; x++) {
						rowOut0[x] = rowIn0[x] * rowIn3[x];
						rowOut1[x] = rowIn1[x] * rowIn3[x];
						rowOut2[x] = rowIn2[x] * rowIn3[x];
					}
				}
				break;
			default:
				throw new IllegalArgumentException("Unsupported number of components: " + inImage.numComponents);
		}
	}
}
