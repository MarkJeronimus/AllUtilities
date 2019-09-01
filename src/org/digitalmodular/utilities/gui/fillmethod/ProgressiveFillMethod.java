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
package org.digitalmodular.utilities.gui.fillmethod;

import java.awt.Rectangle;

import org.digitalmodular.utilities.gui.swing.window.PixelImage;

/**
 * @author Mark Jeronimus
 */
// Created 2007-11-23
public class ProgressiveFillMethod extends FillMethod {
	private int initialBlockSize = 8;

	private int x;
	private int y;

	private int smallStep;
	private int largeStep;
	private int mask;

	private int blockWidth;
	private int blockHeight;

	public void setInitialBlockSize(int initialBlockSize) {
		this.initialBlockSize = initialBlockSize;
	}

	@Override
	public void init(PixelImage img) {
		super.init(img);

		int blockSize = initialBlockSize;
		while (blockSize > img.width && blockSize > img.height) {
			blockSize >>= 1;
		}

		smallStep = largeStep = blockSize;
		mask = blockSize >> 1;

		blockWidth = blockHeight = smallStep;

		x = smallStep == largeStep ? 0 : smallStep;
		y = 0;

		rect.setSize(blockWidth, blockHeight);
	}

	@Override
	public Rectangle nextBlock() {
		rect.setBounds(x, y, blockWidth, blockHeight);

		if ((y & mask) == 0) {
			x += largeStep;
		} else {
			x += smallStep;
		}

		// New line?
		if (x >= img.width) {
			x = (y & mask) == 0 ? 0 : smallStep;
			blockWidth = smallStep;

			y += smallStep;

			// New frame?
			if (y >= img.height) {
				if (smallStep == 0) {
					return null;
				}

				largeStep = smallStep;
				mask = smallStep >>= 1;

				if (smallStep == 0) {
					// Trigger new frame next call.
					x = img.width;
					y = img.height;
				} else {
					x = smallStep;
					y = 0;
					blockWidth = blockHeight = smallStep;
				}
			}
		}

		return rect;
	}
}
