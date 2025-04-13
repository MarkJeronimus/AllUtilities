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

package org.digitalmodular.utilities.graphics.swing.table;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 * @author Mark Jeronimus
 */
// Created 2007-12-17
public class URLTableCellRenderer implements TableCellRenderer {
	private static final JLabel url = new JLabel();

	static {
		url.setOpaque(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus,
	                                               int row,
	                                               int column) {
		url
				.setBackground(UIManager.getColor(isSelected ? "Table.selectionBackground" : "Table.background"));
		url.setBorder(hasFocus ? UIManager.getBorder("Table.focusCellHighlightBorder") : null);

		String s = obj.toString();
		url.setText("<html><body><a href=\"" + s + "\">" + s + "</a></body></html>");
		return url;
	}
}
