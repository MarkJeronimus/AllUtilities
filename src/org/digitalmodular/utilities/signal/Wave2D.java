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

package org.digitalmodular.utilities.signal;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.digitalmodular.utilities.graphics.image.ImageMatrixFloat;

/**
 * @author Mark Jeronimus
 * @deprecated Use {@link ImageMatrixFloat} instead
 */
// Created 2006-01-14
@Deprecated
public class Wave2D {
	public final double[][] samples;
	public final int        numSamplesX;
	public final int        numSamplesY;

	public Wave2D(int numSamplesX, int numSamplesY) {
		this.numSamplesX = numSamplesX;
		this.numSamplesY = numSamplesY;

		samples = new double[numSamplesY][numSamplesX];

		for (int y = 0; y < this.numSamplesY; y++) {
			Arrays.fill(samples[y], 0);
		}
	}

	public Wave2D(BufferedImage image) {
		this(image.getWidth(), image.getHeight());

		int b      = image.getColorModel().getNumColorComponents();
		int source = 0;
		for (int y = 0; y < numSamplesY; y++) {
			for (int x = 0; x < numSamplesX; x++) {
				if (b == 1) {
					samples[y][x] = image.getRaster().getDataBuffer().getElem(0, source);
					source++;
				} else {
					samples[y][x] = 0.299 * image.getRaster().getDataBuffer().getElem(0, source) //
					                + 0.587 * image.getRaster().getDataBuffer().getElem(0, source) //
					                + 0.114 * image.getRaster().getDataBuffer().getElem(0, source);
					source++;
					source++;
					source++;
				}
			}
		}
	}

	/**
	 * Create a  instance with a rectangular part of a specified image as the source for the samples. The
	 * size of the 2-dimensional audio array is always equal to the size of the image slice. When the specified image
	 * slice lies partly outside the bounds of the image, the remaining samples are set to {@code 0}. The bounds
	 * array is always altered to reflect the bounds of the image relative to the 's coordinates.
	 *
	 * @param image  the image to extract a rectangle from
	 * @param bounds initially, the bounds of the part of the image to extract, and after returning, the bounds within
	 *               this {@code Audio2D} that actually contains samples from the image
	 */
	public Wave2D(BufferedImage image, Rectangle bounds) {
		this(bounds.width, bounds.height);

		int sourceWidth  = image.getWidth();
		int sourceHeight = image.getHeight();

		// Extraction point.
		int sourceX = bounds.x;
		int sourceY = bounds.y;

		bounds.x = 0;
		bounds.y = 0;

		// Clipping...
		if (sourceX + bounds.width >= sourceWidth) {
			bounds.width = sourceWidth - sourceX;
		}
		if (sourceX < 0) {
			bounds.x = -sourceX;
			bounds.width += sourceX;
			sourceX = 0;
		}
		if (sourceY + bounds.height >= sourceHeight) {
			bounds.height = sourceHeight - sourceY;
		}
		if (sourceY < 0) {
			bounds.y = -sourceY;
			bounds.height += sourceY;
			sourceY = 0;
		}

		int stopX = bounds.width + bounds.x;
		int stopY = bounds.height + bounds.y;

		int b      = image.getColorModel().getNumColorComponents();
		int source = b * (sourceX + sourceY * sourceWidth);
		for (int y = bounds.y; y < stopY; y++) {
			for (int x = bounds.x; x < stopX; x++) {
				if (b == 1) {
					samples[y][x] = image.getRaster().getDataBuffer().getElem(0, source);
					source++;
				} else {
					samples[y][x] = 0.299 * image.getRaster().getDataBuffer().getElem(0, source) //
					                + 0.587 * image.getRaster().getDataBuffer().getElem(0, source) //
					                + 0.114 * image.getRaster().getDataBuffer().getElem(0, source);
					source++;
					source++;
					source++;
				}
			}
			source += b * (sourceWidth - bounds.width);
		}
	}

	public void setImage(BufferedImage image, int x1, int y1) {
		int b = image.getRaster().getNumBands();
		for (int y = 0; y < numSamplesY; y++) {
			int source = 3 * (x1 + (y1 + y) * image.getWidth());
			for (int x = 0; x < numSamplesX; x++) {
				if (b == 1) {
					// this.samples[y][x] =
					// image.getRaster().getDataBuffer().getElem(0, source++);
					int c = image.getRGB(x1 + x, y1 + y);
					samples[y][x] = //
							0.299 * (c & 0xFF) //
							+ 0.587 * (c >> 8 & 0xFF) //
							+ 0.114 * (c >> 16 & 0xFF);
				} else {
					samples[y][x] = //
							0.299 * image.getRaster().getDataBuffer().getElem(0, source) //
							+ 0.587 * image.getRaster().getDataBuffer().getElem(0, source) //
							+ 0.114 * image.getRaster().getDataBuffer().getElem(0, source);
					source++;
					source++;
					source++;
				}
			}
		}
	}

	public Wave2D getRegion(Rectangle bounds) {
		Wave2D out = new Wave2D(bounds.width, bounds.height);

		// Extraction point.
		int sourceX = bounds.x;
		int sourceY = bounds.y;

		bounds.x = 0;
		bounds.y = 0;

		// Clipping...
		if (sourceX + bounds.width >= numSamplesX) {
			bounds.width = numSamplesX - sourceX;
		}
		if (sourceX < 0) {
			bounds.x = -sourceX;
			bounds.width += sourceX;
			sourceX = 0;
		}
		if (sourceY + bounds.height >= numSamplesY) {
			bounds.height = numSamplesY - sourceY;
		}
		if (sourceY < 0) {
			bounds.y = -sourceY;
			bounds.height += sourceY;
			sourceY = 0;
		}

		int stopX = bounds.width + bounds.x;
		int stopY = bounds.height + bounds.y;

		int j = sourceY;
		for (int y = bounds.y; y < stopY; y++) {
			int i = sourceX;
			for (int x = bounds.x; x < stopX; x++) {
				out.samples[y][x] = samples[j][i];
				i++;
			}
			j++;
		}

		return out;
	}

	/**
	 * Normalize the samples to create a maximum absolute sample value.
	 */
	public void normalize(double amplitude) {
		double max = 0;
		for (int y = 0; y < numSamplesY; y++) {
			for (int x = 0; x < numSamplesX; x++) {
				// This condition is rarely true, so optimize the condition
				// instead of the body.
				if (max < Math.abs(samples[y][x])) {
					max = Math.abs(samples[y][x]);
				}
			}
		}
		max /= amplitude;
		for (int y = 0; y < numSamplesY; y++) {
			for (int x = 0; x < numSamplesX; x++) {
				samples[y][x] /= max;
			}
		}
	}

	public void add(Wave2D audio, int targetX, int targetY) {
		int sourceX = 0;
		int sourceY = 0;
		int width   = audio.numSamplesX;
		int height  = audio.numSamplesY;

		// Clipping...
		if (targetX + width >= numSamplesX) {
			width = numSamplesX - targetX;
		}
		if (targetX < 0) {
			sourceX = -targetX;
			width += targetX;
			targetX = 0;
		}
		if (targetY + height >= numSamplesY) {
			height = numSamplesY - targetY;
		}
		if (targetY < 0) {
			sourceY = -targetY;
			height += targetY;
			targetY = 0;
		}

		int stopX = width + targetX;
		int stopY = height + targetY;

		int j = sourceY;
		for (int v = targetY; v < stopY; v++) {
			int i = sourceX;
			for (int u = targetX; u < stopX; u++) {
				samples[v][u] += audio.samples[j][i];
				i++;
			}
			j++;
		}
	}

	public void addFileRAW8(String filename, int offset, double level, int skip) {
		try (BufferedInputStream file1 = new BufferedInputStream(new FileInputStream(filename))) {
			// skip bytes
			for (int a = 0; a < offset; a++) {
				file1.read();
			}
			// add samples to the audio buffer.
			for (int y = 0; y < numSamplesY; y++) {
				for (int x = 0; x < numSamplesX; x++) {
					samples[y][x] += file1.read() * level;
					// skip bytes to downsample the audio
					for (int b = 0; b < skip; b++) {
						file1.read();
					}
				}
			}
		} catch (IOException ignored) {
		}
	}

	public void addNoise(double amplitude) {
		double magnitude2 = 2 * amplitude;

		for (int y = 0; y < numSamplesY; y++) {
			for (int x = 0; x < numSamplesX; x++) {
				samples[y][x] += Math.random() * magnitude2 - amplitude;
			}
		}
	}

	/**
	 * Scale the audio to a specified factor
	 *
	 * @param volume the factor with which all samples should be multiplied.
	 */
	public void mul(double volume) {
		for (int y = 0; y < numSamplesY; y++) {
			for (int x = 0; x < numSamplesX; x++) {
				samples[y][x] *= volume;
			}
		}
	}

	/**
	 * Bias the audio to remove the DC component.
	 */
	public void correctDC() {
		double sum;

		sum = 0;
		for (int y = 0; y < numSamplesY; y++) {
			for (int x = 0; x < numSamplesX; x++) {
				sum += samples[y][x];
			}
		}

		sum /= numSamplesX * numSamplesY;

		for (int y = 0; y < numSamplesY; y++) {
			for (int x = 0; x < numSamplesX; x++) {
				samples[y][x] -= sum;
			}
		}
	}

	public void threshold(double threshold, int lower, int higher) {
		for (int y = 0; y < numSamplesY; y++) {
			for (int x = 0; x < numSamplesX; x++) {
				samples[y][x] = samples[y][x] > threshold ? higher : lower;
			}
		}
	}

	public int[] toRGBArray() {
		int[] rgbArray = new int[numSamplesX * numSamplesY];
		int   i        = 0;
		for (int y = 0; y < numSamplesY; y++) {
			for (int x = 0; x < numSamplesX; x++) {
				int c = (int)samples[y][x];
				if (c < 0) {
					c = 0;
				} else if (c > 0xFF) {
					c = 0xFF;
				}
				rgbArray[i] = 0xFF000000 | 0x00010101 * c;
				i++;
			}
		}
		return rgbArray;
	}

	public Image toImage() {
		return Toolkit.getDefaultToolkit().createImage(
				new MemoryImageSource(numSamplesX, numSamplesY, toRGBArray(), 0, numSamplesX));
	}
}
