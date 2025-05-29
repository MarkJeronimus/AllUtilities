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

package nl.airsupplies.utilities.graphics.image;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferShort;
import java.awt.image.WritableRaster;

/**
 * The image matrix, with color component sub-images consisting of rows consisting of pixels. For example, an image of
 * 1024x768x24 will have an array of int[3][768][1024].
 *
 * @author Mark Jeronimus
 */
// Created 2008-02-08
public class ImageMatrixInt extends AbstractImageMatrix {
	// NetBeans bug 244841
	private static final int[] DEFAULT_TYPES = {BufferedImage.TYPE_BYTE_GRAY, // 1 component
	                                            BufferedImage.TYPE_USHORT_GRAY, // 1 component (but 16-bit values)
	                                            BufferedImage.TYPE_INT_RGB, // 3 components
	                                            BufferedImage.TYPE_INT_ARGB};        // 4 components

	public final int[][][] matrix;

	/**
	 * The number of level values from black to maximum intensity. Black is always 0 and white is 1 less than this
	 * value.
	 */
	public int fullRange = 256;

	/**
	 * Create an empty  object with a given size and number of color components.
	 *
	 * @param border the size of the border in pixels. The border determines the radius of the operations that
	 *               can be performed on the image. For example, to convolve the image with a 5x7 kernel, the
	 *               border size has to be 3.
	 */
	public ImageMatrixInt(int width, int height, int numComponents, int border) {
		super(width, height, numComponents, border);

		matrix = new int[numComponents][numRows][numColumns];
	}

	/**
	 * Create an  from a {@link BufferedImage}.
	 *
	 * @param border the size of the border in pixels. The border determines the radius of the operations that can be
	 *               performed on the image. For example, to convolve the image with a 5x7 kernel, the border size has
	 *               to be 3.
	 */
	public ImageMatrixInt(BufferedImage image, int border, boolean copyData) {
		super(image.getWidth(), image.getHeight(), image.getRaster().getNumBands(), border);

		matrix = new int[numComponents][numRows][numColumns];

		if (copyData) {
			set(image);
		}
	}

	/**
	 * Create an  from a {@link BufferedImage}.
	 */
	public ImageMatrixInt(ImageMatrixInt image, boolean copyData) {
		super(image.width, image.height, image.numComponents, image.border);

		// Copy metadata.
		fullRange = image.fullRange;

		matrix = new int[numComponents][numRows][numColumns];

		if (copyData) {
			set(image);
		}
	}

	// ## Query methods
	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getBorderSize() {
		return border;
	}

	// NetBeans bug 244841
	public void set(BufferedImage image) {
		WritableRaster raster = image.getRaster();

		if (width != image.getWidth() || height != image.getHeight() || numComponents != raster.getNumBands()) {
			throw new IllegalArgumentException("Incompatible type");
		}

		// Inner loop
		int   x;
		int   endX = this.endX;
		int[] row0;
		int[] row1;
		int[] row2;
		int[] row3;
		int   p    = 0;

		switch (image.getType()) {
			case BufferedImage.TYPE_INT_RGB: // 1
				int[] intRaster = ((DataBufferInt)raster.getDataBuffer()).getData();
				for (int y = border; y < endY; y++) {
					row0 = matrix[0][y];
					row1 = matrix[1][y];
					row2 = matrix[2][y];
					for (x = border; x < endX; x++) {
						int c = intRaster[p];
						p++;
						row0[x] = (c >> 16 & 0xFF) * fullRange / 256;
						row1[x] = (c >> 8 & 0xFF) * fullRange / 256;
						row2[x] = (c & 0xFF) * fullRange / 256;
					}
				}
				break;
			case BufferedImage.TYPE_INT_ARGB: // 2
				intRaster = ((DataBufferInt)raster.getDataBuffer()).getData();
				for (int y = border; y < endY; y++) {
					row0 = matrix[0][y];
					row1 = matrix[1][y];
					row2 = matrix[2][y];
					row3 = matrix[3][y];
					for (x = border; x < endX; x++) {
						int c = intRaster[p];
						p++;
						row3[x] = (c >> 24 & 0xFF) * fullRange / 256;
						row0[x] = (c >> 16 & 0xFF) * fullRange / 256;
						row1[x] = (c >> 8 & 0xFF) * fullRange / 256;
						row2[x] = (c & 0xFF) * fullRange / 256;
					}
				}
				break;
			case BufferedImage.TYPE_INT_BGR: // 4
				intRaster = ((DataBufferInt)raster.getDataBuffer()).getData();
				for (int y = border; y < endY; y++) {
					row0 = matrix[0][y];
					row1 = matrix[1][y];
					row2 = matrix[2][y];
					for (x = border; x < endX; x++) {
						int c = intRaster[p];
						p++;
						row0[x] = (c & 0xFF) * fullRange / 256;
						row1[x] = (c >> 8 & 0xFF) * fullRange / 256;
						row2[x] = (c >> 16 & 0xFF) * fullRange / 256;
					}
				}
				break;
			case BufferedImage.TYPE_3BYTE_BGR: // 5
				byte[] byteRaster = ((DataBufferByte)raster.getDataBuffer()).getData();
				for (int y = border; y < endY; y++) {
					row0 = matrix[0][y];
					row1 = matrix[1][y];
					row2 = matrix[2][y];
					for (x = border; x < endX; x++) {
						row2[x] = (byteRaster[p] & 0xFF) * fullRange / 256;
						p++;
						row1[x] = (byteRaster[p] & 0xFF) * fullRange / 256;
						p++;
						row0[x] = (byteRaster[p] & 0xFF) * fullRange / 256;
						p++;
					}
				}
				break;
			case BufferedImage.TYPE_4BYTE_ABGR: // 6
				byteRaster = ((DataBufferByte)raster.getDataBuffer()).getData();
				for (int y = border; y < endY; y++) {
					row0 = matrix[0][y];
					row1 = matrix[1][y];
					row2 = matrix[2][y];
					row3 = matrix[3][y];
					for (x = border; x < endX; x++) {
						row0[x] = (byteRaster[p] & 0xFF) * fullRange / 256;
						p++;
						row1[x] = (byteRaster[p] & 0xFF) * fullRange / 256;
						p++;
						row2[x] = (byteRaster[p] & 0xFF) * fullRange / 256;
						p++;
						row3[x] = (byteRaster[p] & 0xFF) * fullRange / 256;
						p++;
					}
				}
				break;
			case BufferedImage.TYPE_BYTE_GRAY: // 10
				byteRaster = ((DataBufferByte)raster.getDataBuffer()).getData();
				for (int y = border; y < endY; y++) {
					row0 = matrix[0][y];
					for (x = border; x < endX; x++) {
						row0[x] = (byteRaster[p] & 0xFF) * fullRange / 256;
						p++;
					}
				}
				break;
			case BufferedImage.TYPE_USHORT_GRAY: // 11
				fullRange = 65536;
				short[] shortRaster = ((DataBufferShort)raster.getDataBuffer()).getData();
				for (int y = border; y < endY; y++) {
					row0 = matrix[0][y];
					for (x = border; x < endX; x++) {
						row0[x] = (int)((shortRaster[p] & 0xFFFF) * (long)fullRange / 65536);
						p++;
					}
				}
				break;
			default:
				throw new IllegalArgumentException("Unsupported type");
		}
	}

	// NetBeans bug 244841
	public void set(ImageMatrixInt other) {
		if (width != other.getWidth() || height != other.getHeight() || numComponents != other.numComponents
		    || border != other.border) {
			throw new IllegalArgumentException("Incompatible type");
		}

		fullRange = other.fullRange;

		for (int z = 0; z < other.numComponents; z++) {
			for (int y = other.border; y < other.endY; y++) {
				System.arraycopy(other.matrix[z][y], 0, matrix[z][y], 0, numColumns);
			}
		}
	}

	// NetBeans bug 244841
	public BufferedImage toBufferedImage(BufferedImage image) {
		// Create new image?
		if (image == null) {
			image = new BufferedImage(width, height, DEFAULT_TYPES[numComponents - 1]);
		}

		WritableRaster raster = image.getRaster();

		if (width != image.getWidth() || height != image.getHeight() || numComponents != raster.getNumBands()) {
			throw new IllegalArgumentException("Incompatible type");
		}

		// Inner loop
		int   x;
		int   endX = this.endX;
		int[] row0;
		int[] row1;
		int[] row2;
		int[] row3;
		int   p    = 0;

		switch (image.getType()) {
			case BufferedImage.TYPE_INT_RGB: // 1
				int[] intRaster = ((DataBufferInt)raster.getDataBuffer()).getData();
				for (int y = border; y < endY; y++) {
					row0 = matrix[0][y];
					row1 = matrix[1][y];
					row2 = matrix[2][y];
					for (x = border; x < endX; x++) {
						int r = row0[x] * 256 / fullRange;
						int g = row1[x] * 256 / fullRange;
						int b = row2[x] * 256 / fullRange;
						intRaster[p] = (r << 8 | g) << 8 | b;
						p++;
					}
				}
				break;
			case BufferedImage.TYPE_INT_ARGB: // 2
				intRaster = ((DataBufferInt)raster.getDataBuffer()).getData();
				for (int y = border; y < endY; y++) {
					row0 = matrix[0][y];
					row1 = matrix[1][y];
					row2 = matrix[2][y];
					row3 = matrix[3][y];
					for (x = border; x < endX; x++) {
						int r = row0[x] * 256 / fullRange;
						int g = row1[x] * 256 / fullRange;
						int b = row2[x] * 256 / fullRange;
						int a = row3[x] * 256 / fullRange;
						intRaster[p] = ((a << 8 | r) << 8 | g) << 8 | b;
						p++;
					}
				}
				break;
			case BufferedImage.TYPE_INT_BGR: // 4
				intRaster = ((DataBufferInt)raster.getDataBuffer()).getData();
				for (int y = border; y < endY; y++) {
					row0 = matrix[0][y];
					row1 = matrix[1][y];
					row2 = matrix[2][y];
					for (x = border; x < endX; x++) {
						int r = row0[x] * 256 / fullRange;
						int g = row1[x] * 256 / fullRange;
						int b = row2[x] * 256 / fullRange;
						intRaster[p] = (b << 8 | g) << 8 | r;
						p++;
					}
				}
				break;
			case BufferedImage.TYPE_3BYTE_BGR: // 5
				byte[] byteRaster = ((DataBufferByte)raster.getDataBuffer()).getData();
				for (int y = border; y < endY; y++) {
					row0 = matrix[0][y];
					row1 = matrix[1][y];
					row2 = matrix[2][y];
					for (x = border; x < endX; x++) {
						byteRaster[p] = (byte)(row2[x] * 256 / fullRange);
						p++;
						byteRaster[p] = (byte)(row1[x] * 256 / fullRange);
						p++;
						byteRaster[p] = (byte)(row0[x] * 256 / fullRange);
						p++;
					}
				}
				break;
			case BufferedImage.TYPE_4BYTE_ABGR: // 6
				byteRaster = ((DataBufferByte)raster.getDataBuffer()).getData();
				for (int y = border; y < endY; y++) {
					row0 = matrix[0][y];
					row1 = matrix[1][y];
					row2 = matrix[2][y];
					row3 = matrix[3][y];
					for (x = border; x < endX; x++) {
						byteRaster[p] = (byte)(row0[x] * 256 / fullRange);
						p++;
						byteRaster[p] = (byte)(row1[x] * 256 / fullRange);
						p++;
						byteRaster[p] = (byte)(row2[x] * 256 / fullRange);
						p++;
						byteRaster[p] = (byte)(row3[x] * 256 / fullRange);
						p++;
					}
				}
				break;
			case BufferedImage.TYPE_BYTE_GRAY: // 10
				byteRaster = ((DataBufferByte)raster.getDataBuffer()).getData();
				for (int y = border; y < endY; y++) {
					row0 = matrix[0][y];
					for (x = border; x < endX; x++) {
						byteRaster[p] = (byte)(row0[x] * 256 / fullRange);
						p++;
					}
				}
				break;
			case BufferedImage.TYPE_USHORT_GRAY: // 11
				short[] shortRaster = ((DataBufferShort)raster.getDataBuffer()).getData();
				for (int y = border; y < endY; y++) {
					row0 = matrix[0][y];
					for (x = border; x < endX; x++) {
						shortRaster[p] = (short)(row0[x] * 65536L / fullRange);
						p++;
					}
				}
				break;
			default:
				throw new IllegalArgumentException("Unsupported type");
		}

		return image;
	}

	// ## Modification methods
	// NetBeans bug 244841
	public void setBorder(int... components) {
		// Inner loop
		int   x; // 3+3+3+3
		int[] row; // 1+1+1+1
		int   c; // 1+1+1+1
		int   border     = this.border; // 1+0+0+0
		int   numColumns = this.numColumns; // 0+1+0+0

		for (int z = 0; z < numComponents; z++) {
			c = components[z];
			for (int y = border; y < endY; y++) {
				// West border, excluding corners.
				row = matrix[z][y];
				for (x = 0; x < border; x++) {
					row[x] = c;
				}
				// East border, excluding corners.
				row = matrix[z][y];
				for (x = endX; x < numColumns; x++) {
					row[x] = c;
				}
			}
			// North border, including corners.
			for (int y = 0; y < border; y++) {
				row = matrix[z][y];
				for (x = 0; x < numColumns; x++) {
					row[x] = c;
				}
			}
			// South border, including corners.
			for (int y = endY; y < numRows; y++) {
				row = matrix[z][y];
				for (x = 0; x < numColumns; x++) {
					row[x] = c;
				}
			}
		}
	}

	/**
	 * Extends the edges of the image into the border, ie. fills every border pixel with the color of the closest image
	 * pixel.
	 */
	public void extendBorder() {
		// Inner loop
		int   x; // 3+3
		int[] row; // 1+1
		int   c; // 1+1
		int   border     = this.border; // 1+0
		int   numColumns = this.numColumns; // 0+1

		for (int z = 0; z < numComponents; z++) {
			for (int y1 = border; y1 < endY; y1++) {
				row = matrix[z][y1];

				// West border, excluding corners.
				c = row[border];
				for (x = 0; x < border; x++) {
					row[x] = c;
				}

				// East border, excluding corners.
				c = row[endX - 1];
				for (x = endX; x < numColumns; x++) {
					row[x] = c;
				}
			}

			// North border, including corners.
			row = matrix[z][border];
			for (int y = 0; y < border; y++) {
				System.arraycopy(row, 0, matrix[y], 0, numColumns);
			}

			// South border, including corners.
			row = matrix[z][endY - 1];
			for (int y = endY; y < numRows; y++) {
				System.arraycopy(row, 0, matrix[y], 0, numColumns);
			}
		}
	}

	/**
	 * Changes the {@link #fullRange} value, and resamples the image values to match this.
	 */
	public void changeFullRange(int newFullRange) {
		// Inner loop
		int   x;
		int   endX = this.endX;
		int[] row;

		for (int z = 0; z < numComponents; z++) {
			for (int y = border; y < endY; y++) {
				row = matrix[z][y];
				for (x = border; x < endX; x++) {
					row[x] = row[x] * newFullRange / fullRange;
				}
			}
		}

		fullRange = newFullRange;
	}

	public void set(int... color) {
		// Inner loop
		int   x;
		int   endX = this.endX;
		int   c;
		int[] row;

		for (int z = 0; z < numComponents; z++) {
			c = color[z];
			for (int y = border; y < endY; y++) {
				row = matrix[z][y];
				for (x = border; x < endX; x++) {
					row[x] = c;
				}
			}
		}
	}

	public void setMasked(ImageMatrixInt mask, int maskColor, int fgColor, int bgColor) {
		if (!isCompatibleByBorderAndSize(mask)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int   x;
		int   endX = this.endX;
		int[] rowImg;
		int[] row;

		for (int z = 0; z < numComponents; z++) {
			for (int y = border; y < endY; y++) {
				rowImg = mask.matrix[z][y];
				row    = matrix[z][y];
				for (x = border; x < endX; x++) {
					row[x] = rowImg[x] == maskColor ? fgColor : bgColor;
				}
			}
		}
	}

	// public void setMasked(ImageMatrixInt mask, int maskColor, int color) {
	// if (!isCompatibleByBorderAndSize(mask))
	// throw new IllegalArgumentException("Other ImageMatrix not compatible");
	//
	// int x;
	// int endX = this.endX;
	// int[] rowImg;
	// int[] row;
	//
	// for (int y = border; y < endY; y++) {
	// rowImg = mask.matrix[y];
	// row = matrix[y];
	// for (x = border; x < endX; x++)
	// if (rowImg[x] == maskColor)
	// row[x] = color;
	// }
	// }
	//
	// public Vector2i[] countColors() {
	// int x;
	// int endX = this.endX;
	// int[] rowImg;
	// int[] imgColorsSorted = new int[width * height];
	// int pixels = 0;
	//
	// // Copy pixels to a linear list.
	// for (int y = border; y < endY; y++) {
	// rowImg = matrix[y];
	// for (x = border; x < endX; x++)
	// imgColorsSorted[pixels++] = rowImg[x];
	// }
	//
	// return countImpl(imgColorsSorted, pixels);
	// }
	//
	// public Vector2i[] countColorsMasked(ImageMatrixInt mask, int maskColor) {
	// if (!isCompatibleByBorderAndSize(mask))
	// throw new IllegalArgumentException("Other ImageMatrix not compatible");
	//
	// int x;
	// int endX = this.endX;
	// int[] rowMask;
	// int[] rowImg;
	// int[] imgPixels = new int[width * height];
	// int pixels = 0;
	//
	// // Copy pixels to a linear list.
	// for (int y = border; y < endY; y++) {
	// rowMask = mask.matrix[y];
	// rowImg = matrix[y];
	// for (x = border; x < endX; x++)
	// if (rowMask[x] == maskColor)
	// imgPixels[pixels++] = rowImg[x];
	// }
	//
	// return countImpl(imgPixels, pixels);
	// }
	//
	// private Vector2i[] countImpl(int[] imgPixels, int pixels) {
	// // Sort pixels by color.
	// Arrays.sort(imgPixels, 0, pixels);
	//
	// // Quickly count the colors.
	// int colorCount = 0;
	// int lastColor = imgPixels[pixels - 1] ^ 1; // Make sure it's different on the first iteration.
	// for (int i = pixels - 1; i >= 0; i--) {
	// if (lastColor != imgPixels[i]) {
	// lastColor = imgPixels[i];
	// colorCount++;
	// }
	// }
	//
	// Vector2i[] sortedColorCounts = new Vector2i[colorCount];
	//
	// // Create a sortable list of unique colors.
	// colorCount = -1;
	// lastColor = imgPixels[pixels - 1] ^ 1; // Make sure it's different on the first iteration.
	// for (int i = pixels - 1; i >= 0; i--) {
	// if (lastColor != imgPixels[i]) {
	// lastColor = imgPixels[i];
	// colorCount++;
	//
	// sortedColorCounts[colorCount] = new Vector2i(lastColor, 0);
	// }
	// sortedColorCounts[colorCount].y--; // Count negative to sort descending.
	// }
	//
	// Arrays.sort(sortedColorCounts);
	//
	// // Make counts positive again.
	// for (int i = 0; i < colorCount; i++)
	// sortedColorCounts[i].y = -sortedColorCounts[i].y;
	//
	// return sortedColorCounts;
	// }
	public void clip() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
