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
package org.digitalmodular.utilities.gui.fillmethod;

import java.awt.Rectangle;

import org.digitalmodular.utilities.gui.swing.window.PixelImage;

/**
 * @author Mark Jeronimus
 */
// Created 2007-11-23
public class LinearFillMethod extends FillMethod {
	private int pointer;

	public LinearFillMethod() {
		super();

		rect.width = 1;
		rect.height = 1;
	}

	@Override
	public void init(PixelImage img) {
		super.init(img);

		pointer = 0;
	}

	@Override
	public Rectangle nextBlock() {
		if (pointer == img.width * img.height) {
			return null;
		}

		int p = pointer++;

		rect.x = p % img.width;
		rect.y = p / img.width;

		return rect;
	}
}
