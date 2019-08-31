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
package org.digitalmodular.utilities.gui.swing.tablelayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import static org.digitalmodular.utilities.gui.swing.tablelayout.TableLayout.MINIMUM;
import static org.digitalmodular.utilities.gui.swing.tablelayout.TableLayout.PREFERRED;
import static org.digitalmodular.utilities.gui.swing.tablelayout.TableLayout.weightToString;

/**
 * @author Mark Jeronimus
 */
// Created 2017-02-12
final class TableLayoutTest {
	public static void main(String... args) {
		SwingUtilities.invokeLater(TableLayoutTest::new);
	}

	private TableLayoutTest() {
		Number[] widths = {40, 10, MINIMUM, MINIMUM, PREFERRED, PREFERRED, 1.0, 1.0};
//		Number[] widths  = {40.0, 10.0, 50.0, 70.0, 100.0, 120.0, 190.0, 210.0};
//		Number[] widths  = {10, 20, 30, 40, 50, 60, 70, 80};
		Number[] heights = {16, PREFERRED, 1.0, 2.0, MINIMUM, 1.5};
		JPanel   p       = new JPanel(new TableLayout(8, widths, heights));

		for (int i = 0; i < 100; i++) {
			if (i < widths.length)
				p.add(new JButton(weightToString(widths[i])));
			else if (i % widths.length == 0)
				p.add(new JButton(weightToString(heights[Math.min(i / widths.length, heights.length - 1)])));
			else if (i == 23)
				p.add(new JButton("<html>Foo bar<br>baz quux"));
			else if (((i % widths.length) & 1) == 0)
				p.add(new JButton("l"));
			else
				p.add(new JButton("Foo bar baz quux"));
		}

		p.setBorder(new TitledBorder("TitledBorder"));

		JFrame f = new JFrame();
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		f.setContentPane(p);

		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
