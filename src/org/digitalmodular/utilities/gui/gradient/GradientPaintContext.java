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
package org.digitalmodular.utilities.gui.gradient;

import java.awt.PaintContext;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;

/**
 * @author Mark Jeronimus
 */
// Created 2009-04-04
abstract class GradientPaintContext implements PaintContext {
	private static ColorModel COLOR_MODEL_24 = new DirectColorModel(24, 16711680, 65280, 255);
	private static ColorModel COLOR_MODEL_32 = ColorModel.getRGBdefault();

	private static ColorModel     PersistentColorModel;
	private static WritableRaster PersistentRaster;

	protected Gradient gradient;

	private ColorModel colorModel;

	protected GradientPaintContext() {}

	public void setGradient(Gradient gradient) {
		this.gradient = gradient;

		colorModel = gradient instanceof TransparentGradient ? GradientPaintContext.COLOR_MODEL_32
		                                                     : GradientPaintContext.COLOR_MODEL_24;
	}

	@Override
	public final Raster getRaster(int x, int y, int w, int h) {
		ensureRaster(w, h);

		DataBufferInt dataBuffer = (DataBufferInt)GradientPaintContext.PersistentRaster.getDataBuffer();

		int[] pixels = dataBuffer.getData();
		int   offset = dataBuffer.getOffset();
		int skip = ((SinglePixelPackedSampleModel)GradientPaintContext.PersistentRaster.getSampleModel())
				           .getScanlineStride() - w;

		fillRaster(x, y, w, h, pixels, offset, skip);

		return GradientPaintContext.PersistentRaster;
	}

	protected abstract void fillRaster(int x, int y, int w, int h, int pixels[], int offset, int skip);

	private final synchronized void ensureRaster(int w, int h) {
		if (colorModel != GradientPaintContext.PersistentColorModel || GradientPaintContext.PersistentRaster == null
		    || GradientPaintContext.PersistentRaster.getWidth() < w ||
		    GradientPaintContext.PersistentRaster.getHeight() < h) {
			GradientPaintContext.PersistentColorModel = colorModel;
			GradientPaintContext.PersistentRaster = colorModel.createCompatibleWritableRaster(w, h);
		}
	}

	@Override
	public synchronized final void dispose() {
		GradientPaintContext.PersistentColorModel = null;
		GradientPaintContext.PersistentRaster = null;
	}

	@Override
	public final ColorModel getColorModel() {
		return colorModel;
	}
}
