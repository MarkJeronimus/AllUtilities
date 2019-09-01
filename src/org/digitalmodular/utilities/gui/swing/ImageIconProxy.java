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
package org.digitalmodular.utilities.gui.swing;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * @author Mark Jeronimus
 */
// Created 2007-05-07
public class ImageIconProxy {
	private static HashMap<Object, ImageIcon> icons = new HashMap<>();

	public static void put(Object key, ImageIcon icon) {
		ImageIconProxy.icons.put(key, icon);
	}

	public static ImageIcon get(Object key) {
		return ImageIconProxy.icons.get(key);
	}

	public static ImageIcon getIcon(String filename) {
		ImageIcon icon = ImageIconProxy.icons.get(filename);

		if (icon == null) {
			icon = new ImageIcon(filename);
			ImageIconProxy.icons.put(filename, icon);
		}

		return icon;
	}

	public static Icon getIcon(BufferedImage image) {
		ImageIcon icon = ImageIconProxy.icons.get(image);

		if (icon == null) {
			icon = new ImageIcon(image);
			ImageIconProxy.icons.put(image, icon);
		}

		return icon;
	}

	public static void remove(Object binaryImage) {
		ImageIconProxy.icons.remove(binaryImage);
	}

	public static void flush() {
		ImageIconProxy.icons.clear();
	}
}
