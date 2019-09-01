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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;

import org.digitalmodular.utilities.Debug;

/**
 * @author Mark Jeronimus
 */
// Created 2006-10-15
public class ToggleTabbedPanel extends JComponent implements ActionListener {
	public static final int VERTICAL   = 0;
	public static final int HORIZONTAL = 1;

	private JComponent tabsPanel     = new JPanel();
	private JComponent contentsPanel = new JPanel();

	private Vector<JToggleButton> buttons  = new Vector<>();
	private Vector<JComponent>    contents = new Vector<>();

	public ToggleTabbedPanel(int buttonsDirection) {
		Debug.debuggingGUI = false;
		setLayout(new BorderLayout());

		Debug.gui(this, "this");
		{
			JScrollPane c = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			                                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			Debug.gui(c, "JScrollPane");
			{
				JComponent c2 = new JPanel();
				c2.setLayout(new BorderLayout());
				Debug.gui(c2, "c2 BorderLayout");
				{
					contentsPanel.setLayout(new BoxLayout(contentsPanel, BoxLayout.Y_AXIS));
					Debug.gui(contentsPanel, "contentsPanel BoxLayout");
					c2.add(contentsPanel, BorderLayout.NORTH);
				}
				c.setViewportView(c2);
			}
			this.add(c, BorderLayout.CENTER);
		}

		{
			JComponent c = new JPanel();
			c.setLayout(new BorderLayout());
			Debug.gui(c, "c BorderLayout");
			{
				JComponent c2 = new JPanel();
				c2.setLayout(new BorderLayout());
				c2.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
				Debug.gui(c2, "c2 BorderLayout");
				{
					tabsPanel.setLayout(new BoxLayout(tabsPanel,
					                                  buttonsDirection == ToggleTabbedPanel.HORIZONTAL
					                                  ? BoxLayout.X_AXIS : BoxLayout.Y_AXIS));
					Debug.gui(tabsPanel, "tabsPanel BoxLayout");
					c2.add(tabsPanel,
					       buttonsDirection == ToggleTabbedPanel.HORIZONTAL ? BorderLayout.WEST : BorderLayout.NORTH);
				}
				{
					JComponent c3 = new JPanel();
					c3.setLayout(new BorderLayout());
					Debug.gui(c3, "c3 BorderLayout");
					c3.add(new Separator(), buttonsDirection == ToggleTabbedPanel.HORIZONTAL ? BorderLayout.WEST
					                                                                         : BorderLayout.NORTH);
					c2.add(c3, BorderLayout.CENTER);
				}
				c.add(c2, BorderLayout.CENTER);
			}
			c.add(new Separator(),
			      buttonsDirection == ToggleTabbedPanel.HORIZONTAL ? BorderLayout.SOUTH : BorderLayout.EAST);
			this.add(c, buttonsDirection == ToggleTabbedPanel.HORIZONTAL ? BorderLayout.NORTH : BorderLayout.WEST);
		}
	}

	public void addTab(String name, JComponent content) {
		if (buttons.size() > 0) {
			tabsPanel.add(Box.createRigidArea(new Dimension(3, 3)));
		}
		JToggleButton b = new JToggleButton(name);
		b.setName(String.valueOf(contents.size()));
		b.addActionListener(this);
		b.setSelected(false);
		{
			JComponent c = new JPanel();
			c.setLayout(new BorderLayout());
			Debug.gui(c, "tab_c BorderLayout");
			c.add(b, BorderLayout.CENTER);
			tabsPanel.add(c);
		}
		buttons.add(b);

		if (contents.size() > 0) {
			contentsPanel.add(new Separator());
		}
		Debug.gui(content, "content");
		content.setVisible(b.isSelected());
		{
			JComponent c = new JPanel();
			c.setLayout(new BorderLayout());
			c.setBorder(BorderFactory.createTitledBorder(content.getName()));
			Debug.gui(c, "content_c BorderLayout");
			c.add(content, BorderLayout.CENTER);
			contentsPanel.add(c);
		}
		contents.add(content);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JToggleButton source = (JToggleButton)e.getSource();
		select(Integer.parseInt(source.getName()), source.isSelected());
	}

	public void select(int i, boolean b) {
		buttons.elementAt(i).setSelected(b);
		contents.elementAt(i).setVisible(b);
	}
}
