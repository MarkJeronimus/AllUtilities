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

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author Mark Jeronimus
 */
// Created 2007-12-17
public class EnumTableCellRenderer extends DefaultTableCellRenderer {
	private final String[] elements;

	public EnumTableCellRenderer(String[] elements) {
		setOpaque(true);

		this.elements = elements;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus,
	                                               int row,
	                                               int column) {
		if (isSelected) {
			setForeground(table.getSelectionForeground());
			setBackground(table.getSelectionBackground());
		} else {
			Color color2 = table.getBackground();
			if (color2 == null || color2 instanceof javax.swing.plaf.UIResource) {
				Color color3 = UIManager.getColor("Table.alternateRowColor");
				if (color3 != null && (row & 1) == 0) {
					color2 = color3;
				}
			}
			setForeground(table.getForeground());
			setBackground(color2);
		}
		if (hasFocus) {
			Border border = null;
			if (isSelected) {
				border = UIManager.getBorder("Table.focusSelectedCellHighlightBorder");
			}
			if (border == null) {
				border = UIManager.getBorder("Table.focusCellHighlightBorder");
			}
			setBorder(border);
			if (!isSelected && table.isCellEditable(row, column)) {
				Color color4 = UIManager.getColor("Table.focusCellForeground");
				if (color4 != null) {
					setForeground(color4);
				}
				color4 = UIManager.getColor("Table.focusCellBackground");
				if (color4 != null) {
					setBackground(color4);
				}
			}
		} else {
			setBorder(UIManager.getBorder("Table.cellNoFocusBorder"));
		}

		setText(elements[((Integer)obj).intValue()]);
		return this;
	}
}
