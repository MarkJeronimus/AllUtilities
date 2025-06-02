package nl.airsupplies.utilities.graphics.image;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferShort;
import java.awt.image.WritableRaster;

import nl.airsupplies.utilities.container.Vector2f;
import nl.airsupplies.utilities.graphics.image.function.ImageMatrixImageImageFunctions;
import nl.airsupplies.utilities.graphics.image.function.ImageMatrixImageImageFunctionsComponent;
import nl.airsupplies.utilities.graphics.image.function.ImageMatrixImageImageImageFunctions;
import nl.airsupplies.utilities.graphics.image.function.ImageMatrixImageImageImageFunctionsComponent;
import nl.airsupplies.utilities.graphics.image.function.ImageMatrixImageImageVectorFunctions;
import nl.airsupplies.utilities.graphics.image.function.ImageMatrixImageScalarFunctions;
import nl.airsupplies.utilities.graphics.image.function.ImageMatrixImageScalarFunctionsComponent;
import nl.airsupplies.utilities.graphics.image.function.ImageMatrixImageVectorFunctions;
import nl.airsupplies.utilities.graphics.image.function.ImageMatrixQueryFunctions;
import nl.airsupplies.utilities.graphics.image.function.ImageMatrixQueryFunctionsComponent;
import nl.airsupplies.utilities.graphics.image.function.ImageMatrixUnaryFunctions;
import nl.airsupplies.utilities.graphics.image.function.ImageMatrixUnaryFunctionsComponent;

/**
 * The image matrix, with color component sub-images consisting of rows consisting of pixels. For example, an image of
 * 1024x768x24 will have an array of int[3][768][1024].
 *
 * @author Mark Jeronimus
 */
// Created 2008-02-08
public class ImageMatrixFloat extends AbstractImageMatrix {

	public final float[][][] matrix;

	/**
	 * Create an empty  object with a given size and number of color components.
	 *
	 * @param border the size of the border in pixels. The border determines the radius of the operations that
	 *               can be performed on the image. For example, to convolve the image with a 5x7 kernel, the
	 *               border size has to be 3.
	 */
	public ImageMatrixFloat(int width, int height, int numComponents, int border) {
		super(width, height, numComponents, border);

		matrix = new float[numComponents][numRows][numColumns];
	}

	/**
	 * Create an  from a {@link BufferedImage}.
	 *
	 * @param border the size of the border in pixels. The border determines the radius of the operations that can be
	 *               performed on the image. For example, to convolve the image with a 5x7 kernel, the border size has
	 *               to be 3.
	 */
	public ImageMatrixFloat(BufferedImage image, int border) {
		super(image.getWidth(), image.getHeight(), image.getColorModel().getNumColorComponents(), border);

		matrix = new float[numComponents][numRows][numColumns];

		set(image);
	}

	/**
	 * Create a copy (clone) of an , including it's data.
	 */
	public ImageMatrixFloat(ImageMatrixFloat image) {
		this(image, true);
	}

	/**
	 * Create an ImageMatrix of the same dimensions as a given ImageMatrix, and optionally copy the image data.
	 */
	public ImageMatrixFloat(ImageMatrixFloat image, boolean copyData) {
		super(image.width, image.height, image.numComponents, image.border);

		matrix = new float[numComponents][numRows][numColumns];

		if (copyData) {
			set(image);
		}
	}

	// ## Query methods
	public BufferedImage toBufferedImage(BufferedImage image) {
		WritableRaster raster = image.getRaster();
		int            bands  = raster.getNumBands();

		// Inner loop
		int     x;
		int     u;
		int     v;
		int     endX = this.endX;
		float[] row0;
		float[] row1;
		float[] row2;
		float[] row3;
		int     c;

		switch (numComponents * 10 + bands) {
			case 11:
				for (int y = border; y < endY; y++) {
					row0 = matrix[0][y];
					v    = y - border;
					u    = 0;
					for (x = border; x < endX; x++) {
						raster.setSample(u, v, 0, (int)(row0[x] * 255 + 0.499999) & 0xFF);
						u++;
					}
				}
				break;
			case 13:
			case 14:
				for (int y = border; y < endY; y++) {
					row0 = matrix[0][y];
					v    = y - border;
					u    = 0;
					for (x = border; x < endX; x++) {
						c = (int)(row0[x] * 255 + 0.499999) & 0xFF;
						image.setRGB(u, v, 0xFF000000 | (c << 8 | c) << 8 | c);
						u++;
					}
				}
				break;
			case 34:
			case 33:
				for (int y = border; y < endY; y++) {
					row0 = matrix[0][y];
					row1 = matrix[1][y];
					row2 = matrix[2][y];
					v    = y - border;
					u    = 0;
					for (x = border; x < endX; x++) {
						image.setRGB(u, v, 0xFF000000
						                   | (((int)(row0[x] * 255 + 0.499999) & 0xFF) << 8 |
						                      (int)(row1[x] * 255 + 0.499999) & 0xFF) << 8
						                   | (int)(row2[x] * 255 + 0.499999) & 0xFF);
						u++;
					}
				}
				break;
			case 44:
				for (int y = border; y < endY; y++) {
					row0 = matrix[0][y];
					row1 = matrix[1][y];
					row2 = matrix[2][y];
					row3 = matrix[3][y];
					v    = y - border;
					u    = 0;
					for (x = border; x < endX; x++) {
						image.setRGB(
								u,
								v,
								((((int)(row3[x] * 255 + 0.499999) & 0xFF) << 8 |
								  (int)(row0[x] * 255 + 0.499999) & 0xFF) << 8 |
								 (int)(row1[x] * 255 + 0.499999) & 0xFF) << 8
								| (int)(row2[x] * 255 + 0.499999) & 0xFF);
						u++;
					}
				}
				break;
			default:
				throw new IllegalArgumentException("Unsupported combination of number of components: " + bands + " vs "
				                                   + numComponents);
		}

		return image;
	}

	public void toBufferedImageDebug(BufferedImage image) {
		WritableRaster raster = image.getRaster();

		if (raster.getNumBands() != numComponents) {
			throw new IllegalArgumentException(
					"Number of components differ: " + image.getColorModel().getNumComponents() + " != "
					+ numComponents);
		}

		// Inner loop
		int     x;
		int     numColumns = this.numColumns;
		float[] row0;
		float[] row1;
		float[] row2;
		float[] row3;

		switch (numComponents) {
			case 1:
				for (int y = 0; y < numRows; y++) {
					row0 = matrix[0][y];
					for (x = 0; x < numColumns; x++) {
						raster.setSample(x, y, 0, (int)(row0[x] * 255 + 0.499999) & 0xFF);
					}
				}
				break;
			case 2:
				// for (int y = 0; y < numRows; y++)
				// {
				// row0 = matrix[0][y];
				// row1 = matrix[1][y];
				// for (x = 0; x < numColumns; x++)
				// image.setRGB(x, y, 0xFF000000 | 0x10101 * ((int)(row0[x] *
				// row1[x] * 255 + 0.499999) & 0xFF));
				// }
				// break;
				throw new UnsupportedOperationException("Not supported yet.");
			case 3:
				for (int y = 0; y < numRows; y++) {
					row0 = matrix[0][y];
					row1 = matrix[1][y];
					row2 = matrix[2][y];
					for (x = 0; x < numColumns; x++) {
						int rgb = 0xFF000000
						          | (((int)(row0[x] * 255 + 0.499999) & 0xFF) << 8 |
						             (int)(row1[x] * 255 + 0.499999) & 0xFF) << 8
						          | (int)(row2[x] * 255 + 0.499999) & 0xFF;
						image.setRGB(x, y, rgb);
					}
				}
				break;
			case 4:
				for (int y = 0; y < numRows; y++) {
					row0 = matrix[0][y];
					row1 = matrix[1][y];
					row2 = matrix[2][y];
					row3 = matrix[3][y];
					for (x = 0; x < numColumns; x++) {
						image.setRGB(
								x,
								y,
								((((int)(row3[x] * 255 + 0.499999) & 0xFF) << 8 |
								  (int)(row0[x] * 255 + 0.499999) & 0xFF) << 8 |
								 (int)(row1[x] * 255 + 0.499999) & 0xFF) << 8
								| (int)(row2[x] * 255 + 0.499999) & 0xFF);
					}
				}
				break;
			default:
				throw new IllegalArgumentException("Unsupported number of components: " + numComponents);
		}
	}

	// ## Modification methods
	public void setBorder(float... components) {
		// Inner loop
		int     x;// 3+3+3+3
		float[] row;// 1+1+1+1
		float   c;// 1+1+1+1
		int     border     = this.border;// 1+0+0+0
		int     numColumns = this.numColumns;// 0+1+0+0

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

	public void setBorderUniform(float value) {
		// Inner loop
		int     x;// 3+3+3+3
		float[] row;// 1+1+1+1
		int     border     = this.border;// 1+0+0+0
		int     numColumns = this.numColumns;// 0+1+0+0

		for (int z = 0; z < numComponents; z++) {
			for (int y = border; y < endY; y++) {
				// West border, excluding corners.
				row = matrix[z][y];
				for (x = 0; x < border; x++) {
					row[x] = value;
				}
				// East border, excluding corners.
				row = matrix[z][y];
				for (x = endX; x < numColumns; x++) {
					row[x] = value;
				}
			}
			// North border, including corners.
			for (int y = 0; y < border; y++) {
				row = matrix[z][y];
				for (x = 0; x < numColumns; x++) {
					row[x] = value;
				}
			}
			// South border, including corners.
			for (int y = endY; y < numRows; y++) {
				row = matrix[z][y];
				for (x = 0; x < numColumns; x++) {
					row[x] = value;
				}
			}
		}
	}

	public void setBorderComponent(int component, float value) {
		// Inner loop
		int     x;// 3+3+3+3
		float[] row;// 1+1+1+1
		int     border     = this.border;// 1+0+0+0
		int     numColumns = this.numColumns;// 0+1+0+0

		for (int y = border; y < endY; y++) {
			// West border, excluding corners.
			row = matrix[component][y];
			for (x = 0; x < border; x++) {
				row[x] = value;
			}
			// East border, excluding corners.
			row = matrix[component][y];
			for (x = endX; x < numColumns; x++) {
				row[x] = value;
			}
		}
		// North border, including corners.
		for (int y = 0; y < border; y++) {
			row = matrix[component][y];
			for (x = 0; x < numColumns; x++) {
				row[x] = value;
			}
		}
		// South border, including corners.
		for (int y = endY; y < numRows; y++) {
			row = matrix[component][y];
			for (x = 0; x < numColumns; x++) {
				row[x] = value;
			}
		}
	}

	/**
	 * Extends the edges of the image into the border, ie. fills every border pixel with the color of the closest image
	 * pixel.
	 */
	public void extendBorder() {
		// Inner loop
		int     x; // 3+3
		float[] row;// 1+1
		float   c; // 1+1
		int     border     = this.border; // 1+0
		int     numColumns = this.numColumns; // 0+1

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
				System.arraycopy(row, 0, matrix[z][y], 0, numColumns);
			}

			// South border, including corners.
			row = matrix[z][endY - 1];
			for (int y = endY; y < numRows; y++) {
				System.arraycopy(row, 0, matrix[z][y], 0, numColumns);
			}
		}
	}

	/**
	 * Extends the edges of the image into the upper and lower border.
	 */
	public void extendBorderY() {
		for (int z = 0; z < numComponents; z++) {
			// North border, including corners.
			float[] row = matrix[z][border];
			for (int y = 0; y < border; y++) {
				System.arraycopy(row, border, matrix[z][y], 0, width);
			}

			// South border, including corners.
			row = matrix[z][endY - 1];
			for (int y = endY; y < numRows; y++) {
				System.arraycopy(row, border, matrix[z][y], 0, width);
			}
		}
	}

	public void set(BufferedImage image) {
		WritableRaster raster = image.getRaster();

		if (width != image.getWidth() || height != image.getHeight() || numComponents != raster.getNumBands()) {
			throw new IllegalArgumentException("Incompatible type");
		}

		// Inner loop
		int     x;
		int     endX = this.endX;
		float[] row0;
		float[] row1;
		float[] row2;
		float[] row3;
		int     p    = 0;

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
						row0[x] = (c >> 16 & 0xFF) / 255.0f;
						row1[x] = (c >> 8 & 0xFF) / 255.0f;
						row2[x] = (c & 0xFF) / 255.0f;
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
						row3[x] = (c >> 24 & 0xFF) / 255.0f;
						row0[x] = (c >> 16 & 0xFF) / 255.0f;
						row1[x] = (c >> 8 & 0xFF) / 255.0f;
						row2[x] = (c & 0xFF) / 255.0f;
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
						row0[x] = (c & 0xFF) / 255.0f;
						row1[x] = (c >> 8 & 0xFF) / 255.0f;
						row2[x] = (c >> 16 & 0xFF) / 255.0f;
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
						row2[x] = (byteRaster[p] & 0xFF) / 255.0f;
						p++;
						row1[x] = (byteRaster[p] & 0xFF) / 255.0f;
						p++;
						row0[x] = (byteRaster[p] & 0xFF) / 255.0f;
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
						row0[x] = (byteRaster[p] & 0xFF) / 255.0f;
						p++;
						row1[x] = (byteRaster[p] & 0xFF) / 255.0f;
						p++;
						row2[x] = (byteRaster[p] & 0xFF) / 255.0f;
						p++;
						row3[x] = (byteRaster[p] & 0xFF) / 255.0f;
						p++;
					}
				}
				break;
			case BufferedImage.TYPE_BYTE_GRAY: // 10
				byteRaster = ((DataBufferByte)raster.getDataBuffer()).getData();
				for (int y = border; y < endY; y++) {
					row0 = matrix[0][y];
					for (x = border; x < endX; x++) {
						row0[x] = (byteRaster[p] & 0xFF) / 255.0f;
						p++;
					}
				}
				break;
			case BufferedImage.TYPE_USHORT_GRAY: // 11
				short[] shortRaster = ((DataBufferShort)raster.getDataBuffer()).getData();
				for (int y = border; y < endY; y++) {
					row0 = matrix[0][y];
					for (x = border; x < endX; x++) {
						row0[x] = (int)((shortRaster[p] & 0xFFFF) / 65535.0f);
						p++;
					}
				}
				break;
			default:
				throw new IllegalArgumentException("Unsupported type");
		}
	}

	@SuppressWarnings("unused")
	public void set(byte[] data, int offset, int planeStride, int pixelStride, int lineStride, int startX, int startY,
	                int width,
	                int height) {
		int p = offset - planeStride;
		switch (numComponents) {
			case 1:
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						matrix[0][border + y][border + x] = (data[p += planeStride] & 0xFF) / 255.0f;

						p += pixelStride - planeStride;
					}
					p += lineStride - pixelStride * width;
				}
				break;
			case 2:
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						matrix[0][border + y][border + x] = (data[p += planeStride] & 0xFF) / 255.0f;
						matrix[1][border + y][border + x] = (data[p += planeStride] & 0xFF) / 255.0f;

						p += pixelStride - 2 * planeStride;
					}
					p += lineStride - pixelStride * width;
				}
				break;
			case 3:
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						matrix[0][border + y][border + x] = (data[p += planeStride] & 0xFF) / 255.0f;
						matrix[1][border + y][border + x] = (data[p += planeStride] & 0xFF) / 255.0f;
						matrix[2][border + y][border + x] = (data[p += planeStride] & 0xFF) / 255.0f;

						p += pixelStride - 3 * planeStride;
					}
					p += lineStride - pixelStride * width;
				}
				break;
			case 4:
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						matrix[0][border + y][border + x] = (data[p += planeStride] & 0xFF) / 255.0f;
						matrix[1][border + y][border + x] = (data[p += planeStride] & 0xFF) / 255.0f;
						matrix[2][border + y][border + x] = (data[p += planeStride] & 0xFF) / 255.0f;
						matrix[3][border + y][border + x] = (data[p += planeStride] & 0xFF) / 255.0f;

						p += pixelStride - 4 * planeStride;
					}
					p += lineStride - pixelStride * width;
				}
				break;
			default:
				throw new IllegalArgumentException("Unimplemented");
		}
	}

	// // ### ImageMatrixImageImageFunctions

	/**
	 * this = other
	 */
	public ImageMatrixFloat set(ImageMatrixFloat other) {
		ImageMatrixImageImageFunctions.set(this, other);
		return this;
	}

	/**
	 * this += other
	 */
	public void add(ImageMatrixFloat other) {
		ImageMatrixImageImageFunctions.add(this, other);
	}

	/**
	 * this -= other
	 */
	public void sub(ImageMatrixFloat other) {
		ImageMatrixImageImageFunctions.sub(this, other);
	}

	/**
	 * this = other - this
	 */
	public void subR(ImageMatrixFloat other) {
		ImageMatrixImageImageFunctions.subR(this, other);
	}

	/**
	 * this *= other
	 */
	public void mul(ImageMatrixFloat other) {
		ImageMatrixImageImageFunctions.mul(this, other);
	}

	/**
	 * this /= other
	 */
	public void div(ImageMatrixFloat other) {
		ImageMatrixImageImageFunctions.div(this, other);
	}

	/**
	 * this = other / this
	 */
	public void divR(ImageMatrixFloat other) {
		ImageMatrixImageImageFunctions.divR(this, other);
	}

	/**
	 * this = this<sup>powerImage</sup>
	 */
	public void pow(ImageMatrixFloat powerImage) {
		ImageMatrixImageImageFunctions.pow(this, powerImage);
	}

	/**
	 * this = baseImage<sup>this</sup>
	 */
	public void powR(ImageMatrixFloat baseImage) {
		ImageMatrixImageImageFunctions.powR(this, baseImage);
	}

	/**
	 * this = hypot(this, other) = sqrt(this<sup>2</sup> + other<sup>2</sup>)
	 */
	public void cabs(ImageMatrixFloat other) {
		ImageMatrixImageImageFunctions.cabs(this, other);
	}

	/**
	 * this = hypot(this, other) = sqrt(this<sup>2</sup> + other<sup>2</sup>)
	 */
	public void hypot(ImageMatrixFloat other) {
		ImageMatrixImageImageFunctions.hypot(this, other);
	}

	/**
	 * this = atan2(other, this)
	 */
	public void atan2R(ImageMatrixFloat other) {
		ImageMatrixImageImageFunctions.atan2R(this, other);
	}

	/**
	 * this = min(this, other)
	 */
	public void min(ImageMatrixFloat other) {
		ImageMatrixImageImageFunctions.min(this, other);
	}

	/**
	 * this = max(this, other)
	 */
	public void max(ImageMatrixFloat other) {
		ImageMatrixImageImageFunctions.max(this, other);
	}

	/**
	 * this += offset + offset2
	 */
	public void addAddUniform(ImageMatrixFloat offset, float offset2) {
		ImageMatrixImageImageFunctions.addAdd(this, offset, offset2);
	}

	/**
	 * this = (this + add) * mul
	 */
	public void addMulUniform(ImageMatrixFloat add, float mul) {
		ImageMatrixImageImageFunctions.addMul(this, add, mul);
	}

	/**
	 * this = (this + add) * mul
	 */
	public void addMulUniform(float add, ImageMatrixFloat mul) {
		ImageMatrixImageImageFunctions.addMul(this, add, mul);
	}

	/**
	 * this = this * mul + add
	 */
	public void mulAddUniform(ImageMatrixFloat mul, float add) {
		ImageMatrixImageImageFunctions.mulAdd(this, mul, add);
	}

	/**
	 * this = this * mul + add
	 */
	public void mulAddUniform(float mul, ImageMatrixFloat add) {
		ImageMatrixImageImageFunctions.mulAdd(this, mul, add);
	}

	/**
	 * this = this * (mul + offset)
	 */
	public void mulOffsetUniform(ImageMatrixFloat mul, float offset) {
		ImageMatrixImageImageFunctions.mulOffset(this, mul, offset);
	}

	/**
	 * this += add * scale
	 */
	public void addScaledUniform(ImageMatrixFloat add, float scale) {
		ImageMatrixImageImageFunctions.addScaled(this, add, scale);
	}

	// ### ImageMatrixImageImageFunctionsComponent

	/**
	 * this[component] = other[otherComponent]
	 */
	public void setComponent(int component, ImageMatrixFloat other, int otherComponent) {
		if (!isCompatibleByBorderAndSize(other)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		ImageMatrixImageImageFunctionsComponent.set(this, matrix[component], other.matrix[otherComponent]);
	}

	/**
	 * this[component] += other[otherComponent]
	 */
	public void addComponent(int component, ImageMatrixFloat other, int otherComponent) {
		if (!isCompatibleByBorderAndSize(other)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		ImageMatrixImageImageFunctionsComponent.add(this, matrix[component], other.matrix[otherComponent]);
	}

	/**
	 * this[component] -= other[otherComponent]
	 */
	public void subComponent(int component, ImageMatrixFloat other, int otherComponent) {
		if (!isCompatibleByBorderAndSize(other)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		ImageMatrixImageImageFunctionsComponent.sub(this, matrix[component], other.matrix[otherComponent]);
	}

	/**
	 * this[component] = other[otherComponent] - this[component]
	 */
	public void subRComponent(int component, ImageMatrixFloat other, int otherComponent) {
		if (!isCompatibleByBorderAndSize(other)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		ImageMatrixImageImageFunctionsComponent.subR(this, matrix[component], other.matrix[otherComponent]);
	}

	/**
	 * this[component] *= other[otherComponent]
	 */
	public void mulComponent(int component, ImageMatrixFloat other, int otherComponent) {
		if (!isCompatibleByBorderAndSize(other)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		ImageMatrixImageImageFunctionsComponent.mul(this, matrix[component], other.matrix[otherComponent]);
	}

	/**
	 * this[component] /= other[otherComponent]
	 */
	public void divComponent(int component, ImageMatrixFloat other, int otherComponent) {
		if (!isCompatibleByBorderAndSize(other)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		ImageMatrixImageImageFunctionsComponent.div(this, matrix[component], other.matrix[otherComponent]);
	}

	/**
	 * this[component] = other[otherComponent] / this[component]
	 */
	public void divRComponent(int component, ImageMatrixFloat other, int otherComponent) {
		if (!isCompatibleByBorderAndSize(other)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		ImageMatrixImageImageFunctionsComponent.divR(this, matrix[component], other.matrix[otherComponent]);
	}

	/**
	 * this[component] = this[component]<sup>powerImage[powerComponent]</sup>
	 */
	public void powComponent(int component, ImageMatrixFloat powerImage, int powerComponent) {
		if (!isCompatibleByBorderAndSize(powerImage)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		ImageMatrixImageImageFunctionsComponent.pow(this, matrix[component], powerImage.matrix[powerComponent]);
	}

	/**
	 * this[component] = baseImage[baseComponent]<sup>this[component]</sup>
	 */
	public void powRComponent(int component, ImageMatrixFloat baseImage, int baseComponent) {
		if (!isCompatibleByBorderAndSize(baseImage)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		ImageMatrixImageImageFunctionsComponent.powR(this, matrix[component], baseImage.matrix[baseComponent]);
	}

	/**
	 * this[component] = hypot(this[component], other[otherComponent]) = sqrt(this[component]<sup>2</sup> +
	 * other[otherComponent]<sup>2</sup>)
	 */
	public void hypotComponent(int component, ImageMatrixFloat other, int otherComponent) {
		if (!isCompatibleByBorderAndSize(other)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		ImageMatrixImageImageFunctionsComponent.hypot(this, matrix[component], other.matrix[otherComponent]);
	}

	/**
	 * this[component] = atan2(this[component], other[otherComponent])
	 */
	public void atan2Component(int component, ImageMatrixFloat other, int otherComponent) {
		if (!isCompatibleByBorderAndSize(other)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		ImageMatrixImageImageFunctionsComponent.atan2(this, matrix[component], other.matrix[otherComponent]);
	}

	/**
	 * this[component] = atan2(other[otherComponent], this[component])
	 */
	public void atan2RComponent(int component, ImageMatrixFloat other, int otherComponent) {
		if (!isCompatibleByBorderAndSize(other)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		ImageMatrixImageImageFunctionsComponent.atan2R(this, matrix[component], other.matrix[otherComponent]);
	}

	/**
	 * this[component] += offset1Image[offset1Component] + offset2
	 */
	public void addAddComponent(int component, ImageMatrixFloat offset1Image, int offset1Component, float offset2) {
		ImageMatrixImageImageFunctionsComponent
				.addAdd(this, matrix[component], offset1Image.matrix[offset1Component], offset2);
	}

	/**
	 * this[component] = (this[component] + addImage[addComponent]) * mul
	 */
	public void addMulComponent(int component, ImageMatrixFloat addImage, int addComponent, float mul) {
		if (!isCompatibleByBorderAndSize(addImage)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		ImageMatrixImageImageFunctionsComponent.addMul(this, matrix[component], addImage.matrix[addComponent], mul);
	}

	/**
	 * this[component] = (this[component] + add) * mulImage[mulComponent]
	 */
	public void addMulComponent(int component, float add, ImageMatrixFloat mulImage, int mulComponent) {
		if (!isCompatibleByBorderAndSize(mulImage)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		ImageMatrixImageImageFunctionsComponent.addMul(this, matrix[component], add, mulImage.matrix[mulComponent]);
	}

	/**
	 * this[component] = this[component] * mulImage[mulComponent] + add
	 */
	public void mulAddComponent(int component, ImageMatrixFloat mulImage, int mulComponent, float add) {
		if (!isCompatibleByBorderAndSize(mulImage)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		ImageMatrixImageImageFunctionsComponent.mulAdd(this, matrix[component], mulImage.matrix[mulComponent], add);
	}

	/**
	 * this[component] = this[component] * (mulImage[mulComponent] + add)
	 */
	public void mulOffsetComponent(int component, ImageMatrixFloat mulImage, int mulComponent, float offset) {
		if (!isCompatibleByBorderAndSize(mulImage)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		ImageMatrixImageImageFunctionsComponent
				.mulOffset(this, matrix[component], mulImage.matrix[mulComponent], offset);
	}

	/**
	 * this[component] = this[component] * scale + add[offsetComponent]
	 */
	public void mulAddComponent(int component, float scale, ImageMatrixFloat addImage, int addComponent) {
		if (!isCompatibleByBorderAndSize(addImage)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		ImageMatrixImageImageFunctionsComponent.mulAdd(this, matrix[component], scale, addImage.matrix[addComponent]);
	}

	/**
	 * this[component] += addImage[addComponent] * scale
	 */
	public void addScaledComponent(int component, ImageMatrixFloat addImage, int addComponent, float scale) {
		if (!isCompatibleByBorderAndSize(addImage)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		ImageMatrixImageImageFunctionsComponent
				.addScaled(this, matrix[component], addImage.matrix[addComponent], scale);
	}

	// ### ImageMatrixImageImageImageFunctions

	/**
	 * this += add1 + add2
	 */
	public void addAdd(ImageMatrixFloat add1, ImageMatrixFloat add2) {
		ImageMatrixImageImageImageFunctions.addAdd(this, add1, add2);
	}

	/**
	 * this += add * scale
	 */
	public void addScaled(ImageMatrixFloat add, ImageMatrixFloat scale) {
		ImageMatrixImageImageImageFunctions.addScaled(this, add, scale);
	}

	/**
	 * this *= mul + offset
	 */
	public void mulOffset(ImageMatrixFloat mul, ImageMatrixFloat offset) {
		ImageMatrixImageImageImageFunctions.mulOffset(this, mul, offset);
	}

	/**
	 * this *= add1 * add2
	 */
	public void mulMul(ImageMatrixFloat mul1, ImageMatrixFloat mul2) {
		ImageMatrixImageImageImageFunctions.mulMul(this, mul1, mul2);
	}

	// ### ImageMatrixImageImageImageFunctionsComponent

	/**
	 * this[component] = (this[component] + addImage[addComponent]) * mulImage[mulComponent]
	 */
	public void addMulComponent(int component, ImageMatrixFloat addImage, int addComponent, ImageMatrixFloat mulImage,
	                            int mulComponent) {
		if (!(isCompatibleByBorderAndSize(addImage) && isCompatibleByBorderAndSize(mulImage))) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		ImageMatrixImageImageImageFunctionsComponent.addMul(this, matrix[component], addImage.matrix[addComponent],
		                                                    mulImage.matrix[mulComponent]);
	}

	/**
	 * this[component] = this[component] * mulImage[mulComponent] + addImage[addComponent]
	 */
	public void mulAddComponent(int component, ImageMatrixFloat mulImage, int mulComponent, ImageMatrixFloat addImage,
	                            int addComponent) {
		if (!(isCompatibleByBorderAndSize(mulImage) && isCompatibleByBorderAndSize(addImage))) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		ImageMatrixImageImageImageFunctionsComponent.mulAdd(this, matrix[component], mulImage.matrix[mulComponent],
		                                                    addImage.matrix[addComponent]);
	}

	/**
	 * this[component] += addImage[addComponent] * scaleImage[scaleComponent]
	 */
	public void addScaledComponent(int component,
	                               ImageMatrixFloat addImage,
	                               int addComponent,
	                               ImageMatrixFloat scaleImage,
	                               int scaleComponent) {
		if (!(isCompatibleByBorderAndSize(addImage) && isCompatibleByBorderAndSize(scaleImage))) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		ImageMatrixImageImageImageFunctionsComponent.addScaled(this, matrix[component], addImage.matrix[addComponent],
		                                                       scaleImage.matrix[scaleComponent]);
	}

	// ### ImageMatrixImageImageVectorFunctions /** this += add1 + add2 */
	public void addAdd(ImageMatrixFloat add, float... scale) {
		ImageMatrixImageImageVectorFunctions.addAdd(this, add, scale);
	}

	/**
	 * this = (this + add) * mul
	 */
	public void addMul(ImageMatrixFloat add, float... mul) {
		ImageMatrixImageImageVectorFunctions.addMul(this, add, mul);
	}

	/**
	 * this = mul * (this + add)
	 */
	public void addMulR(ImageMatrixFloat mul, float... add) {
		ImageMatrixImageImageVectorFunctions.addMulR(this, mul, add);
	}

	/**
	 * this += add * scale
	 */
	public void addScaled(ImageMatrixFloat add, float... scale) {
		ImageMatrixImageImageVectorFunctions.addScaled(this, add, scale);
	}

	/**
	 * this *= (mul + offset)
	 */
	public void mulOffset(ImageMatrixFloat mul, float... offset) {
		ImageMatrixImageImageVectorFunctions.mulOffset(this, mul, offset);
	}

	// ### ImageMatrixImageScalarFunctions

	/**
	 * this = value
	 */
	public void setUniform(float value) {
		ImageMatrixImageScalarFunctions.set(this, value);
	}

	/**
	 * this += value
	 */
	public void addUniform(float value) {
		ImageMatrixImageScalarFunctions.add(this, value);
	}

	/**
	 * this -= value
	 */
	public void subUniform(float value) {
		ImageMatrixImageScalarFunctions.sub(this, value);
	}

	/**
	 * this = value - this
	 */
	public void subRUniform(float value) {
		ImageMatrixImageScalarFunctions.subR(this, value);
	}

	/**
	 * this *= value
	 */
	public void mulUniform(float value) {
		ImageMatrixImageScalarFunctions.mul(this, value);
	}

	/**
	 * this /= value
	 */
	public void divUniform(float value) {
		ImageMatrixImageScalarFunctions.div(this, value);
	}

	/**
	 * this = value / this
	 */
	public void divRUniform(float value) {
		ImageMatrixImageScalarFunctions.divR(this, value);
	}

	/**
	 * this = this<sup>power</sup>
	 */
	public void powUniform(float power) {
		ImageMatrixImageScalarFunctions.pow(this, power);
	}

	/**
	 * this = base<sup>this</sup>
	 */
	public void powRUniform(float base) {
		ImageMatrixImageScalarFunctions.powR(this, base);
	}

	/**
	 * this = (this + add) * mul
	 */
	public void addMulUniform(float add, float mul) {
		ImageMatrixImageScalarFunctions.addMul(this, add, mul);
	}

	/**
	 * this = this * mul + add
	 */
	public ImageMatrixFloat mulAddUniform(float mul, float add) {
		ImageMatrixImageScalarFunctions.mulAdd(this, mul, add);
		return this;
	}

	// ### ImageMatrixImageScalarFunctionsComponent

	/**
	 * this[component] = value
	 */
	public void setComponent(int component, float value) {
		ImageMatrixImageScalarFunctionsComponent.set(this, matrix[component], value);
	}

	/**
	 * this[component] += value
	 */
	public void addComponent(int component, float value) {
		ImageMatrixImageScalarFunctionsComponent.add(this, matrix[component], value);
	}

	/**
	 * this[component] -= value
	 */
	public void subComponent(int component, float value) {
		ImageMatrixImageScalarFunctionsComponent.sub(this, matrix[component], value);
	}

	/**
	 * this[component] = value - this[component]
	 */
	public void subRComponent(int component, float value) {
		ImageMatrixImageScalarFunctionsComponent.subR(this, matrix[component], value);
	}

	/**
	 * this[component] *= value
	 */
	public void mulComponent(int component, float value) {
		ImageMatrixImageScalarFunctionsComponent.mul(this, matrix[component], value);
	}

	/**
	 * this[component] /= value
	 */
	public void divComponent(int component, float value) {
		ImageMatrixImageScalarFunctionsComponent.div(this, matrix[component], value);
	}

	/**
	 * this[component] = value / this[component]
	 */
	public void divRComponent(int component, float value) {
		ImageMatrixImageScalarFunctionsComponent.divR(this, matrix[component], value);
	}

	/**
	 * this[component] = this[component]<sup>power</sup>
	 */
	public void powComponent(int component, float power) {
		ImageMatrixImageScalarFunctionsComponent.pow(this, matrix[component], power);
	}

	/**
	 * this[component] = base<sup>this[component]</sup>
	 */
	public void powRComponent(int component, float base) {
		ImageMatrixImageScalarFunctionsComponent.powR(this, matrix[component], base);
	}

	/**
	 * this[component] = this[component] * mul + add
	 */
	public void addMulComponent(int component, float mul, float add) {
		ImageMatrixImageScalarFunctionsComponent.addMul(this, matrix[component], mul, add);
	}

	/**
	 * this[component] = this[component] * mul + add
	 */
	public void mulAddComponent(int component, float mul, float add) {
		ImageMatrixImageScalarFunctionsComponent.mulAdd(this, matrix[component], mul, add);
	}

	// ### ImageMatrixImageVectorFunctions

	/**
	 * this = value
	 */
	public void set(float... value) {
		ImageMatrixImageVectorFunctions.set(this, value);
	}

	/**
	 * this += value
	 */
	public ImageMatrixFloat add(float... value) {
		ImageMatrixImageVectorFunctions.add(this, value);
		return this;
	}

	/**
	 * this -= value
	 */
	public void sub(float... value) {
		ImageMatrixImageVectorFunctions.sub(this, value);
	}

	/**
	 * this = value - this
	 */
	public void subR(float... value) {
		ImageMatrixImageVectorFunctions.subR(this, value);
	}

	/**
	 * this *= value
	 */
	public ImageMatrixFloat mul(float... value) {
		ImageMatrixImageVectorFunctions.mul(this, value);
		return this;
	}

	/**
	 * this /= value
	 */
	public void div(float... value) {
		ImageMatrixImageVectorFunctions.div(this, value);
	}

	/**
	 * this = value / this
	 */
	public void divR(float... value) {
		ImageMatrixImageVectorFunctions.divR(this, value);
	}

	/**
	 * this = mod(this, dividend) (different from % as in that the result has the same sign as dividend)
	 */
	public ImageMatrixFloat mod(float... dividend) {
		ImageMatrixImageVectorFunctions.mod(this, dividend);
		return this;
	}

	/**
	 * this = mod(divisor, this) (different from % as in that the result has the same sign as dividend)
	 */
	public void modR(float... divisor) {
		ImageMatrixImageVectorFunctions.modR(this, divisor);
	}

	/**
	 * this = this<sup>power</sup>
	 */
	public void pow(float... power) {
		ImageMatrixImageVectorFunctions.pow(this, power);
	}

	/**
	 * this = base<sup>this</sup>
	 */
	public void powR(float... base) {
		ImageMatrixImageVectorFunctions.powR(this, base);
	}

	/**
	 * this = (this < BLACK)? value : this
	 */
	public void showUnderflow(float... value) {
		ImageMatrixImageVectorFunctions.showUnderflow(this, value);
	}

	/**
	 * this = (this > WHITE)? value : this
	 */
	public void showOverflow(float... value) {
		ImageMatrixImageVectorFunctions.showOverflow(this, value);
	}

	/**
	 * this = (this <= BLACK)? value : this
	 */
	public void showBlacks(float... value) {
		ImageMatrixImageVectorFunctions.showBlacks(this, value);
	}

	/**
	 * this = (this >= WHITE)? value : this
	 */
	public void showWhites(float... value) {
		ImageMatrixImageVectorFunctions.showWhites(this, value);
	}

	/**
	 * this = (this > value)? 1 : 0
	 */
	public void threshold(float... value) {
		ImageMatrixImageVectorFunctions.threshold(this, value);
	}

	// ### ImageMatrixQueryComponentFunctions

	/**
	 * returns min(img[component])
	 */
	public float findMinComponent(int component) {
		return ImageMatrixQueryFunctionsComponent.findMin(this, matrix[component]);
	}

	/**
	 * returns max(img[component])
	 */
	public float findMaxComponent(int component) {
		return ImageMatrixQueryFunctionsComponent.findMax(this, matrix[component]);
	}

	/**
	 * returns {min(img[component]), max(img[component])}
	 */
	public Vector2f findMinMaxComponent(int component) {
		return ImageMatrixQueryFunctionsComponent.findMinMax(this, matrix[component]);
	}

	/**
	 * array[component] = average(img[component])
	 */
	public float findAverageComponent(int component) {
		return ImageMatrixQueryFunctionsComponent.findAverage(this, matrix[component]);
	}

	/**
	 * histogram[i] = count(round(img[component] * (histogram.length - 1)) == i)
	 */
	public void makeHistogramComponent(int[] hist, int component) {
		ImageMatrixQueryFunctionsComponent.makeHistogram(this, matrix[component], hist);
	}

	// ### ImageMatrixQueryFunctions

	/**
	 * returns min(img)
	 */
	public float findMin() {
		return ImageMatrixQueryFunctions.findMin(this);
	}

	/**
	 * returns max(img)
	 */
	public float findMax() {
		return ImageMatrixQueryFunctions.findMax(this);
	}

	/**
	 * returns {min(img), max(img)}
	 */
	public Vector2f findMinMax() {
		return ImageMatrixQueryFunctions.findMinMax(this);
	}

	/**
	 * returns {average(this), amplitude(this)}
	 */
	public Vector2f findBiasAmplitude() {
		return ImageMatrixQueryFunctions.findBiasAmplitude(this);
	}

	/**
	 * returns average(img[component])
	 */
	public float findAverage() {
		return ImageMatrixQueryFunctions.findAverage(this);
	}

	/**
	 * histogram[i] = count(round(img * (histogram.length - 1)) == i)
	 */
	public void makeHistogram(int[] hist) {
		ImageMatrixQueryFunctions.makeHistogram(this, hist);
	}

	// ### ImageMatrixUnaryFunctions

	/**
	 * this = -this
	 */
	public void invert() {
		ImageMatrixUnaryFunctions.invert(this);
	}

	/**
	 * this = WHITE - this<br> (where WHITE = 255/256)
	 */
	public ImageMatrixFloat negative() {
		ImageMatrixUnaryFunctions.negative(this);
		return this;
	}

	/**
	 * this = BLACK, if this < BLACK<br> this = WHITE, if this > WHITE<br> this = this, if BLACK <= this <= WHITE<br>
	 * (where BLACK = 0 and WHITE = 255/256)
	 */
	public ImageMatrixFloat clamp() {
		ImageMatrixUnaryFunctions.clamp(this);
		return this;
	}

	/**
	 * this = black, if this < black<br> this = white, if this > white<br> this = this, if black <= this <= white<br>
	 */
	public void clamp(float black, float white) {
		ImageMatrixUnaryFunctions.clamp(this, black, white);
	}

	/**
	 * this = black, if this < black<br> this = this, if this >= black<br>
	 */
	public void clampLower(float black) {
		ImageMatrixUnaryFunctions.clampLower(this, black);
	}

	/**
	 * this = white, if this > white<br> this = this, if this <= white<br>
	 */
	public void clampUpper(float white) {
		ImageMatrixUnaryFunctions.clampUpper(this, white);
	}

	/**
	 * this /= max(this)
	 */
	public void normalize() {
		ImageMatrixUnaryFunctions.normalize(this);
	}

	/**
	 * this = (this - average(this)) / (0.5 * amplitude(this)) + 0.5
	 */
	public void stretchHistogram() {
		ImageMatrixUnaryFunctions.stretchHistogram(this);
	}

	/**
	 * this = |this|
	 */
	public void abs() {
		ImageMatrixUnaryFunctions.abs(this);
	}

	/**
	 * this = round(this) (round towards closest int)
	 */
	public ImageMatrixFloat round() {
		ImageMatrixUnaryFunctions.round(this);
		return this;
	}

	/**
	 * this = round(this) (round towards infinity)
	 */
	public ImageMatrixFloat floor() {
		ImageMatrixUnaryFunctions.floor(this);
		return this;
	}

	/**
	 * this = round(this) (round towards -infinity)
	 */
	public void ceil() {
		ImageMatrixUnaryFunctions.ceil(this);
	}

	/**
	 * this = round(this) (round towards 0)
	 */
	public void trunc() {
		ImageMatrixUnaryFunctions.trunc(this);
	}

	/**
	 * this = 1 / this
	 */
	public void recip() {
		ImageMatrixUnaryFunctions.recip(this);
	}

	/**
	 * this = sqr(this)
	 */
	public void sqr() {
		ImageMatrixUnaryFunctions.sqr(this);
	}

	/**
	 * this = sqrt(this)
	 */
	public void sqrt() {
		ImageMatrixUnaryFunctions.sqrt(this);
	}

	/**
	 * this = cube(this)
	 */
	public void cube() {
		ImageMatrixUnaryFunctions.cube(this);
	}

	/**
	 * this = cbrt(this)
	 */
	public void cbrt() {
		ImageMatrixUnaryFunctions.cbrt(this);
	}

	/**
	 * this = exp(this)
	 */
	public void exp() {
		ImageMatrixUnaryFunctions.exp(this);
	}

	/**
	 * this = log(this)
	 */
	public void log() {
		ImageMatrixUnaryFunctions.log(this);
	}

	/**
	 * this = this<sup>base</sup>
	 */
	public ImageMatrixFloat pow(float power) {
		ImageMatrixUnaryFunctions.pow(this, power);
		return this;
	}

	/**
	 * this = base<sup>this</sup>
	 */
	public void powR(float base) {
		ImageMatrixUnaryFunctions.powR(this, base);
	}

	/**
	 * this = sin(this)
	 */
	public void sin() {
		ImageMatrixUnaryFunctions.sin(this);
	}

	/**
	 * this = cos(this)
	 */
	public void cos() {
		ImageMatrixUnaryFunctions.cos(this);
	}

	/**
	 * this = tan(this)
	 */
	public void tan() {
		ImageMatrixUnaryFunctions.tan(this);
	}

	/**
	 * this = atan(this)
	 */
	public void atan() {
		ImageMatrixUnaryFunctions.atan(this);
	}

	// ### ImageMatrixUnaryFunctionsComponent

	/**
	 * this[component] = -this[component]
	 */
	public void invertComponent(int component) {
		ImageMatrixUnaryFunctionsComponent.invert(this, component);
	}

	/**
	 * this[component] = WHITE - this[component]<br> (where WHITE = 255/256)
	 */
	public void neagtiveComponent(int component) {
		ImageMatrixUnaryFunctionsComponent.negative(this, component);
	}

	/**
	 * this[component] = BLACK, if this[component] < BLACK<br> this[component] = WHITE, if this[component] > WHITE<br>
	 * this[component] = this[component], if BLACK <= this[component] <= WHITE<br> (where BLACK = 0 and WHITE =
	 * 255/256)
	 */
	public void clampComponent(int component) {
		ImageMatrixUnaryFunctionsComponent.clamp(this, component);
	}

	/**
	 * this[component] = black, if this[component] < black<br> this[component] = white, if this[component] > white<br>
	 * this[component] = this[component], if black <= this[component] <= white<br>
	 */
	public void clampComponent(int component, float black, float white) {
		ImageMatrixUnaryFunctionsComponent.clamp(this, component, black, white);
	}

	/**
	 * this[component] = black, if this[component] < black<br> this[component] = this[component], if black <=
	 * this[component]<br>
	 */
	public void clampLowerComponent(int component, float black) {
		ImageMatrixUnaryFunctionsComponent.clampLower(this, component, black);
	}

	/**
	 * this[component] = white, if this[component] > white<br> this[component] = this[component], if this[component] <=
	 * white<br>
	 */
	public void clampUpperComponent(int component, float white) {
		ImageMatrixUnaryFunctionsComponent.clampUpper(this, component, white);
	}

	/**
	 * this[component] /= max(this[component])
	 */
	public void normalizeComponent(int component) {
		ImageMatrixUnaryFunctionsComponent.normalize(this, component);
	}

	/**
	 * this[component] = this[component] / (max(this[component]) - min(this[component])) + min(this[component])
	 */
	public void stretchHistogramComponent(int component) {
		ImageMatrixUnaryFunctionsComponent.stretchHistogram(this, component);
	}

	/**
	 * this[component] = |this[component]|
	 */
	public void absComponent(int component) {
		ImageMatrixUnaryFunctionsComponent.abs(this, component);
	}

	/**
	 * this[component] = 1 / this[component]
	 */
	public void recipComponent(int component) {
		ImageMatrixUnaryFunctionsComponent.recip(this, component);
	}

	/**
	 * this[component] = sqr(this[component])
	 */
	public void sqrComponent(int component) {
		ImageMatrixUnaryFunctionsComponent.sqr(this, component);
	}

	/**
	 * this[component] = sqrt(this[component])
	 */
	public void sqrtComponent(int component) {
		ImageMatrixUnaryFunctionsComponent.sqrt(this, component);
	}

	/**
	 * this[component] = cube(this[component])
	 */
	public void cubeComponent(int component) {
		ImageMatrixUnaryFunctionsComponent.cube(this, component);
	}

	/**
	 * this[component] = cbrt(this[component])
	 */
	public void cbrtComponent(int component) {
		ImageMatrixUnaryFunctionsComponent.cbrt(this, component);
	}

	/**
	 * this[component] = exp(this[component])
	 */
	public void expComponent(int component) {
		ImageMatrixUnaryFunctionsComponent.exp(this, component);
	}

	/**
	 * this[component] = sin(this[component])
	 */
	public void sinComponent(int component) {
		ImageMatrixUnaryFunctionsComponent.sin(this, component);
	}

	/**
	 * this[component][component] = cos(this[component][component])
	 */
	public void cosComponent(int component) {
		ImageMatrixUnaryFunctionsComponent.cos(this, component);
	}

	/**
	 * this[component][component] = tan(this[component][component])
	 */
	public void tanComponent(int component) {
		ImageMatrixUnaryFunctionsComponent.tan(this, component);
	}

	/**
	 * this[component][component] = atan(this[component][component])
	 */
	public void atanComponent(int component) {
		ImageMatrixUnaryFunctionsComponent.atan(this, component);
	}

	public void mixChannels(float[]... table) {
		if (table.length != numComponents) {
			throw new IllegalArgumentException("Number of channels mismatch: " + table.length + " != " +
			                                   numComponents);
		}
		if (table[0].length != numComponents) {
			throw new IllegalArgumentException(
					"Number of channels mismatch: " + table[0].length + " != " + numComponents);
		}

		// Inner loop
		int     x;
		float[] row0;
		float[] row1;
		float[] row2;
		float   r;
		float   g;
		float   b;

		if (numComponents == 3) {
			for (int y = 0; y < numRows; y++) {
				row0 = matrix[0][y];
				row1 = matrix[1][y];
				row2 = matrix[2][y];
				for (x = 0; x < numColumns; x++) {
					r = row0[x];
					g = row1[x];
					b = row2[x];

					row0[x] = table[0][0] * r + table[0][1] * g + table[0][2] * b;
					row1[x] = table[1][0] * r + table[1][1] * g + table[1][2] * b;
					row2[x] = table[2][0] * r + table[2][1] * g + table[2][2] * b;
				}
			}
		} else {
			throw new IllegalArgumentException("Unimplemented: " + numComponents);
		}
	}

	public void mixChannels(ImageMatrixFloat outImage, float[]... table) {
		if (table.length != outImage.numComponents) {
			throw new IllegalArgumentException(
					"Number of channels mismatch: " + table.length + " != " + outImage.numComponents);
		}
		if (table[0].length != numComponents) {
			throw new IllegalArgumentException(
					"Number of channels mismatch: " + table[0].length + " != " + numComponents);
		}

		// Inner loop
		int     x;
		float[] rowIn0;
		float[] rowIn1;
		float[] rowIn2;
		float[] rowOut0;
		float[] rowOut1;
		float[] rowOut2;
		float   r;
		float   g;
		float   b;

		switch (outImage.numComponents * 10 + numComponents) {
			case 13:
				for (int y = 0; y < numRows; y++) {
					rowIn0  = matrix[0][y];
					rowIn1  = matrix[1][y];
					rowIn2  = matrix[2][y];
					rowOut0 = outImage.matrix[0][y];
					for (x = 0; x < numColumns; x++) {
						r = rowIn0[x];
						g = rowIn1[x];
						b = rowIn2[x];

						rowOut0[x] = table[0][0] * r + table[0][1] * g + table[0][2] * b;
					}
				}
				break;
			case 33:
				for (int y = 0; y < numRows; y++) {
					rowIn0  = matrix[0][y];
					rowIn1  = matrix[1][y];
					rowIn2  = matrix[2][y];
					rowOut0 = outImage.matrix[0][y];
					rowOut1 = outImage.matrix[1][y];
					rowOut2 = outImage.matrix[2][y];
					for (x = 0; x < numColumns; x++) {
						r = rowIn0[x];
						g = rowIn1[x];
						b = rowIn2[x];

						rowOut0[x] = table[0][0] * r + table[0][1] * g + table[0][2] * b;
						rowOut1[x] = table[1][0] * r + table[1][1] * g + table[1][2] * b;
						rowOut2[x] = table[2][0] * r + table[2][1] * g + table[2][2] * b;
					}
				}
				break;
			default:
				throw new IllegalArgumentException("Unimplemented: " + numComponents + " -> " + outImage
						.numComponents);
		}
	}
}
