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
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 * @author Mark Jeronimus
 */
// Created 2006-06-15
public class PasswordDialog extends JDialog implements ActionListener {
	private JPasswordField field1   = new JPasswordField(40);
	private JPasswordField field2   = new JPasswordField(40);
	private JCheckBox      show     = new JCheckBox("Display password", false);
	private JButton        button   = new JButton("Ok");
	private char           echoChar = field1.getEchoChar();

	public PasswordDialog(Frame owner) {
		super(owner, true);
		makeLayout();
	}

	public PasswordDialog(Dialog owner) {
		super(owner, true);
		makeLayout();
	}

	private void makeLayout() {
		super.setLayout(new BorderLayout());

		{
			JPanel p = new JPanel(new TableLayout(2, 2, 0, 300));

			p.add(new JLabel("Password"));
			{
				p.add(field1, BorderLayout.NORTH);
				field1.addActionListener(this);
			}

			p.add(new JLabel("Verify"));
			{
				p.add(field2, BorderLayout.CENTER);
				field2.addActionListener(this);
			}

			p.add(new JPanel());
			{
				p.add(show);
				show.addActionListener(this);
			}

			super.add(p, BorderLayout.CENTER);
		}
		{
			JPanel p = new JPanel(new BorderLayout());

			p.add(Box.createVerticalStrut(4), BorderLayout.NORTH);
			{
				JPanel p1 = new JPanel();
				p1.setLayout(new GridLayout(1, 2, 4, 0));
				p1.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 2));
				p1.add(button);
				p.add(p1, BorderLayout.EAST);
			}
			super.add(p, BorderLayout.SOUTH);
		}

		button.addActionListener(this);

		super.pack();
		super.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		super.setBounds(screenSize.width - super.getWidth() >> 1, screenSize.height - super.getHeight() >> 1,
		                super.getWidth(),
		                super.getHeight());
	}

	public void setEchoChar(char c) {
		echoChar = c;
		field1.setEchoChar(show.isSelected() ? 0 : c);
		field2.setEchoChar(show.isSelected() ? 0 : c);
	}

	public char[] getPassword() {
		return field1.getPassword();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == show) {
			setEchoChar(echoChar);
		} else {
			char[] p1 = field1.getPassword();
			char[] p2 = field2.getPassword();
			if (p1.length != p2.length) {
				return;
			}
			for (int i = p1.length - 1; i >= 0; i--) {
				if (p1[i] != p2[i]) {
					return;
				}
				p2[i] = 0;
			}
			super.dispose();
		}
	}
}
