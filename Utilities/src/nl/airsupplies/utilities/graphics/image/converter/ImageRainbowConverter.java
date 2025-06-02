package nl.airsupplies.utilities.graphics.image.converter;

import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;

/**
 * Make HSL colors, modulate Hue, convert to RGB.
 *
 * @author Mark Jeronimus
 */
// Created 2011-11-22
public class ImageRainbowConverter extends ImageConverter {
	@Override
	public void convertToArray(ImageMatrixFloat image, int[] array, int offset, int lineStride) {
		// Inner loop
		float   f;
		int     endX = image.endX;
		int     p    = offset + (image.numColumns + 1) * image.border;
		float   hue6;
		float   hueF;
		float[] row0;
		float[] row1;

		switch (image.numComponents) {
			case 1:
				for (int y = image.border; y < image.endY; y++) {
					row0 = image.matrix[0][y];
					for (int x = image.border; x < endX; x++) {
						f = row0[x];

						// NaN always becomes black.
						if (f != f) {
							array[p] = 0xFF000000;
							p++;
						} else {
							hue6 = (f - (float)Math.floor(f)) * 6;
							hueF = hue6 - (float)Math.floor(hue6);
							switch ((int)hue6) {
								case 0:
									array[p] = 0xFFFF0000 | (int)(hueF * 255 + 0.5) << 8;
									p++;
									break;
								case 1:
									array[p] = 0xFF00FF00 | ((int)(hueF * 255 + 0.5) ^ 0xFF) << 16;
									p++;
									break;
								case 2:
									array[p] = 0xFF00FF00 | (int)(hueF * 255 + 0.5);
									p++;
									break;
								case 3:
									array[p] = 0xFF0000FF | ((int)(hueF * 255 + 0.5) ^ 0xFF) << 8;
									p++;
									break;
								case 4:
									array[p] = 0xFF0000FF | (int)(hueF * 255 + 0.5) << 16;
									p++;
									break;
								case 5:
									array[p] = 0xFFFF0000 | (int)(hueF * 255 + 0.5) ^ 0xFF;
									p++;
							}
						}
					}
					p += lineStride - image.numColumns;
				}
				break;
			case 2:
				for (int y = image.border; y < image.endY; y++) {
					row0 = image.matrix[0][y];
					row1 = image.matrix[1][y];
					for (int x = image.border; x < endX; x++) {
						f = row0[x];

						// NaN always becomes black.
						if (f != f) {
							array[p] = 0xFF000000;
							p++;
						} else {
							hue6 = (f - (float)Math.floor(f)) * 6;
							hueF = hue6 - (float)Math.floor(hue6);
							switch ((int)hue6) {
								case 0:
									array[p] = (int)(row1[x] * 255 + 0.5) << 24 | 0xFF0000 |
									           (int)(hueF * 255 + 0.5) << 8;
									p++;
									break;
								case 1:
									array[p] = (int)(row1[x] * 255 + 0.5) << 24 | 0x00FF00
									           | ((int)(hueF * 255 + 0.5) ^ 0xFF) << 16;
									p++;
									break;
								case 2:
									array[p] = (int)(row1[x] * 255 + 0.5) << 24 | 0x00FF00 |
									           (int)(hueF * 255 + 0.5);
									p++;
									break;
								case 3:
									array[p] = (int)(row1[x] * 255 + 0.5) << 24 | 0x0000FF
									           | ((int)(hueF * 255 + 0.5) ^ 0xFF) << 8;
									p++;
									break;
								case 4:
									array[p] = (int)(row1[x] * 255 + 0.5) << 24 | 0x0000FF |
									           (int)(hueF * 255 + 0.5) << 16;
									p++;
									break;
								case 5:
									array[p] = (int)(row1[x] * 255 + 0.5) << 24 | 0xFF0000 |
									           (int)(hueF * 255 + 0.5) ^ 0xFF;
									p++;
							}
						}
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
		float   f;
		float   hue6;
		float   hueF;
		float[] rowIn0;
		float[] rowOut0;
		float[] rowOut1;
		float[] rowOut2;
		float[] rowIn1;
		float[] rowOut3;

		switch (inImage.numComponents) {
			case 1:
				for (int y = inImage.border; y < inImage.endY; y++) {
					rowIn0  = inImage.matrix[0][y];
					rowOut0 = outImage.matrix[0][y];
					rowOut1 = outImage.matrix[1][y];
					rowOut2 = outImage.matrix[2][y];
					for (x = inImage.border; x < endX; x++) {
						f = rowIn0[x];

						// NaN always becomes black.
						if (f != f) {
							rowOut0[x] = 0;
							rowOut1[x] = 0;
							rowOut2[x] = 0;
						} else {
							hue6 = (f - (float)Math.floor(f)) * 6;
							hueF = hue6 - (float)Math.floor(hue6);
							switch ((int)hue6) {
								case 0:
									rowOut0[x] = 1;
									rowOut1[x] = hueF;
									rowOut2[x] = 0;
									break;
								case 1:
									rowOut0[x] = 1 - hueF;
									rowOut1[x] = 1;
									rowOut2[x] = 0;
									break;
								case 2:
									rowOut0[x] = 0;
									rowOut1[x] = 1;
									rowOut2[x] = hueF;
									break;
								case 3:
									rowOut0[x] = 0;
									rowOut1[x] = 1 - hueF;
									rowOut2[x] = 1;
									break;
								case 4:
									rowOut0[x] = hueF;
									rowOut1[x] = 0;
									rowOut2[x] = 1;
									break;
								case 5:
									rowOut0[x] = 1;
									rowOut1[x] = 0;
									rowOut2[x] = 1 - hueF;
							}
						}
					}
				}
				break;
			case 2:
				for (int y = inImage.border; y < inImage.endY; y++) {
					rowIn0  = inImage.matrix[0][y];
					rowIn1  = inImage.matrix[1][y];
					rowOut0 = outImage.matrix[0][y];
					rowOut1 = outImage.matrix[1][y];
					rowOut2 = outImage.matrix[2][y];
					rowOut3 = outImage.matrix[3][y];
					for (x = inImage.border; x < endX; x++) {
						f = rowIn0[x];

						// NaN always becomes black.
						if (f != f) {
							rowOut0[x] = 0;
							rowOut1[x] = 0;
							rowOut2[x] = 0;
						} else {
							hue6 = (f - (float)Math.floor(f)) * 6;
							hueF = hue6 - (float)Math.floor(hue6);
							switch ((int)hue6) {
								case 0:
									rowOut0[x] = 1;
									rowOut1[x] = hueF;
									rowOut2[x] = 0;
									break;
								case 1:
									rowOut0[x] = 1 - hueF;
									rowOut1[x] = 1;
									rowOut2[x] = 0;
									break;
								case 2:
									rowOut0[x] = 0;
									rowOut1[x] = 1;
									rowOut2[x] = hueF;
									break;
								case 3:
									rowOut0[x] = 0;
									rowOut1[x] = 1 - hueF;
									rowOut2[x] = 1;
									break;
								case 4:
									rowOut0[x] = hueF;
									rowOut1[x] = 0;
									rowOut2[x] = 1;
									break;
								case 5:
									rowOut0[x] = 1;
									rowOut1[x] = 0;
									rowOut2[x] = 1 - hueF;
							}
						}

						rowOut3[x] = rowIn1[x];
					}
				}
				break;
			default:
				throw new IllegalArgumentException("Unsupported number of components: " + inImage.numComponents);
		}
	}
}
