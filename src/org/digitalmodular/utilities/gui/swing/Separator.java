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
package org.digitalmodular.utilities.gui.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Path2D;
import javax.swing.JComponent;
import javax.swing.UIManager;

/**
 * @author Mark Jeronimus
 */
// Created 2005-04-22
public class Separator extends JComponent {
	public Separator() {
		this(7, 7);
	}

	public Separator(int width, int height) {
		super();
		setMinimumSize(new Dimension(7, 7));
		setPreferredSize(new Dimension(width, height));
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;

		int width  = getWidth() - 1;
		int height = getHeight() - 1;
		// g2d.setPaint(Color.green);
		// g2d.fill(new Rectangle2D.Float(-1, -1, width + 3, height + 3));

		Path2D up = new Path2D.Float();
		up.moveTo(0, height - 2.5);
		up.lineTo(-0.5, height - 3);
		up.lineTo(-0.5, 2);
		up.lineTo(1, 0.5);
		up.lineTo(width - 3, 0.5);
		up.lineTo(width - 2, 1.5);

		Path2D dn = new Path2D.Float();
		dn.moveTo(width - 2, 1.5);
		dn.lineTo(width - 1.5, 2);
		dn.lineTo(width - 1.5, height - 3);
		dn.lineTo(width - 3, height - 1.5);
		dn.lineTo(1, height - 1.5);
		dn.lineTo(0, height - 2.5);

		g2d.setPaint(getShadow());
		g2d.draw(dn);

		g2d.setPaint(gethighlight());
		g2d.draw(up);
	}

	private static Paint getShadow() {
		Color c = UIManager.getColor("controlDkShadow");
		if (c != null) {
			return c;
		}
		c = UIManager.getColor("controlDShadow");
		if (c != null) {
			return c;
		}
		c = UIManager.getColor("controlShadow");
		if (c != null) {
			return c;
		}
		return Color.GRAY;
	}

	private static Color gethighlight() {
		Color c = UIManager.getColor("controlLtHighlight");
		if (c != null) {
			return c;
		}
		c = UIManager.getColor("controlLHighlight");
		if (c != null) {
			return c;
		}
		c = UIManager.getColor("controlHighlight");
		if (c != null) {
			return c;
		}
		return Color.WHITE;
	}
}
