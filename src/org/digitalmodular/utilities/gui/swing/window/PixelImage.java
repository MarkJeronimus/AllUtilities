/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2019 Mark Jeronimus. All Rights Reversed.
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
 * along with AllUtilities. If not, see <http://www.gnu.org/licenses/>.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.digitalmodular.utilities.gui.swing.window;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

import org.digitalmodular.utilities.gui.GraphicsUtilities;

/**
 * An image to draw both vector graphics and pixel graphics.
 *
 * @author Mark Jeronimus
 */
// Created 2009-04-04
// Updated 2014-01-07 Split up from {@link PixelPanel}
// Updated 2014-03-01 Made immutable.
public class PixelImage {
	public final int           width;
	public final int           height;
	public final BufferedImage image;
	public final Graphics2D    g;
	public final int[]         pixels;

	public PixelImage(int width, int height) {
		// Clamp to minimum values.
		this.width = Math.max(width, 1);
		this.height = Math.max(height, 1);

		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		image.setAccelerationPriority(0);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		g = image.createGraphics();

		GraphicsUtilities.setAntialiased(g, false);
	}

	/**
	 * Clears the buffer with the specified paint or color.
	 */
	public synchronized void clear(Paint paint) {
		g.setPaint(paint);
		g.fillRect(0, 0, width, height);
	}

	/**
	 * Clears the buffer with the specified RGB triple.
	 */
	public synchronized void clear(int rgb) {
		Arrays.fill(pixels, rgb);
	}

	/**
	 * Clears the buffer with the specified background image. The image will be tiled starting at the upper-left.
	 */
	public synchronized void clear(BufferedImage bgtx) {
		Paint p = new TexturePaint(bgtx, new Rectangle2D.Float(0, 0, bgtx.getWidth(), bgtx.getHeight()));
		g.setPaint(p);
		g.fillRect(0, 0, width, height);
	}

	/**
	 * Fills a rectangular area between (x0, y0) inclusive and (x1, y1) exclusive.
	 *
	 * @param x0 the left side (inclusive)
	 * @param y0 the top side (inclusive)
	 * @param x1 the right side (exclusive)
	 * @param y1 the bottom side (exclusive)
	 */
	public synchronized void fillRect(int x0, int y0, int x1, int y1, int color) {
		if (x0 < 0) {
			x0 = 0;
		}
		if (y0 < 0) {
			y0 = 0;
		}
		if (x1 > width) {
			x1 = width;
		}
		if (y1 > height) {
			y1 = height;
		}

		int p = x0 + y0 * width;
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				pixels[p++] = color;
			}
			p += width + x0 - x1;
		}
	}

	public synchronized BufferedImage getImageCopy() {
		BufferedImage imageCopy = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		imageCopy.setAccelerationPriority(0);
		int[] pixelsCopy = ((DataBufferInt)imageCopy.getRaster().getDataBuffer()).getData();
		System.arraycopy(pixels, 0, pixelsCopy, 0, pixelsCopy.length);

		return imageCopy;
	}
}
