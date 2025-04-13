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

package org.digitalmodular.utilities.graphics.swing.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.AbstractBorder;

/**
 * @author Mark Jeronimus
 */
public class BelowJMenuBorder extends AbstractBorder {
	public static Insets getBorderInsets() {
		return new Insets(1, 2, 2, 2);
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return getBorderInsets();
	}

	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		insets = getBorderInsets();
		return insets;
	}

	@Override
	public void paintBorder(Component component, Graphics g, int x, int y, int width, int height) {
		Graphics copy = g.create();
		if (copy != null) {
			copy.translate(x, y);

			copy.setColor(Color.gray);
			copy.fillRect(0, 0, 1, height - 1);

			copy.setColor(Color.white);
			copy.fillRect(width - 1, 0, 1, height);
			copy.fillRect(0, height - 1, width - 1, 1);

			copy.setColor(new Color(64, 64, 64));
			copy.fillRect(1, 0, 1, height - 2);
			copy.fillRect(1, 0, width - 2, 1);

			copy.setColor(new Color(204, 204, 204));
			copy.fillRect(width - 2, 1, 1, height - 2);
			copy.fillRect(1, height - 2, width - 3, 1);
			copy.dispose();
		}
	}
}
