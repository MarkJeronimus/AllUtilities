/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.digitalmodular.utilities.graphics.swing;

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
	private final JPanel bodyPanel = new JPanel();

	public StatusBar() {
		super(new BorderLayout());

		Debug.gui(this, "this");

		setPreferredSize(new Dimension(0, 18));

		bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.X_AXIS));
		Debug.gui(bodyPanel, "bodyPanel");
		add(bodyPanel, BorderLayout.CENTER);
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

	@Override
	public void removeAll() {
		super.removeAll();
		bodyPanel.removeAll();
	}
}
