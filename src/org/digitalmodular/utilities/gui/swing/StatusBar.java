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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.digitalmodular.utilities.Debug;

/**
 * @author Mark Jeronimus
 */
// Created 2005-04-23
public class StatusBar extends JPanel {
	private JPanel bodyPanel = new JPanel();

	public StatusBar() {
		super(new BorderLayout());

		Debug.gui(this, "this");

		setPreferredSize(new Dimension(0, 18));

		bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.X_AXIS));
		Debug.gui(bodyPanel, "bodyPanel");
		this.add(bodyPanel, BorderLayout.CENTER);
	}

	public Component addStatusBarItem(StatusBarItem c) {
		if (bodyPanel.getComponentCount() > 0) {
			bodyPanel.add(Box.createRigidArea(new Dimension(2, 0)));
		}
		{
			JPanel p5 = new JPanel(new BorderLayout());
			Debug.gui(p5, "p5");
			p5.add(c, BorderLayout.CENTER);
			bodyPanel.add(p5);
		}
		return c;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAll() {
		super.removeAll();
		bodyPanel.removeAll();
	}
}
