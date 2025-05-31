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

package nl.airsupplies.utilities.gui;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;

import nl.airsupplies.utilities.annotation.StaticClass;

/**
 * @author Mark Jeronimus
 */
// Created 2005-04-22
@StaticClass
public final class GUIDebugging {
	public static boolean enabled = false;

	public static boolean isEnabled() {
		return enabled;
	}

	public static void setEnabled(boolean enabled) {
		GUIDebugging.enabled = enabled;
	}

	public static synchronized void gui(JComponent c) {
		if (enabled) {
			c.setOpaque(true);
			c.setBackground(new Color((float)Math.random(), (float)Math.random(), (float)Math.random()));
		}
	}

	public static void gui(JComponent c, String name) {
		if (enabled) {
			c.setBorder(BorderFactory.createTitledBorder(name));
		}
	}

	public static void gui(JFrame c) {
		if (enabled) {
			c.setBackground(new Color((float)Math.random(), (float)Math.random(), (float)Math.random()));
		}
	}
}
