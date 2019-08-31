/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2018 Mark Jeronimus. All Rights Reversed.
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
package org.digitalmodular.utilities.gui.image.converter;

import org.digitalmodular.utilities.gui.image.ImageMatrixFloat;

/**
 * @author Mark Jeronimus
 */
// Created 2011-11-22
@SuppressWarnings("unused")
public abstract class ImageConverter {
	/**
	 * Writes an image (but not the border) to a 1-dimensional array. The format of the array and what is done to the
	 * image data depends on the specific implementation of the {@link ImageConverter}.
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
