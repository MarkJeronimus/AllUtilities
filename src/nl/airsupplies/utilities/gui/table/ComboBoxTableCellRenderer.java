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

package nl.airsupplies.utilities.gui.table;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 * @author Mark Jeronimus
 */
// Created 2007-12-17
public class ComboBoxTableCellRenderer implements TableCellRenderer {
	private final JComboBox<String> comboBox;
	private final JComponent        component = new JPanel();

	public ComboBoxTableCellRenderer(String... elements) {
		comboBox = new JComboBox<>(elements);
		component.setLayout(new BorderLayout());
		component.add(comboBox, BorderLayout.CENTER);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus,
	                                               int row,
	                                               int column) {
		comboBox.setBackground(UIManager.getColor(isSelected ? "Table.selectionBackground" : "Table.background"));

		comboBox.setSelectedIndex((Integer)obj);
		return component;
	}
}
