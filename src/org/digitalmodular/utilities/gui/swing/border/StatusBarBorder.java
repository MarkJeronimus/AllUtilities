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
package org.digitalmodular.utilities.gui.swing.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.AbstractBorder;

/**
 * @author Mark Jeronimus
 */
public class StatusBarBorder extends AbstractBorder {
	public static Insets getBorderInsets() {
		return new Insets(1, 3, 1, 1);
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return StatusBarBorder.getBorderInsets();
	}

	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		insets = StatusBarBorder.getBorderInsets();
		return insets;
	}

	@Override
	public void paintBorder(Component component, Graphics g, int x, int y, int width, int height) {
		Graphics copy = g.create();
		if (copy != null) {
			copy.translate(x, y);
			copy.setColor(Color.gray);
			copy.fillRect(0, 0, width - 1, 1);
			copy.fillRect(0, 0, 1, height - 1);
			copy.setColor(Color.white);
			copy.fillRect(width - 1, 0, 1, height - 1);
			copy.fillRect(0, height - 1, width - 1, 1);
			copy.dispose();
		}
	}
}
