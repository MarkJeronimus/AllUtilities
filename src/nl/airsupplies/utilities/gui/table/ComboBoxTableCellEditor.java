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
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.EventObject;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.plaf.UIResource;
import javax.swing.table.TableCellEditor;

/**
 * @author Mark Jeronimus
 */
// Created 2009-05-?7
public class ComboBoxTableCellEditor extends JPanel implements TableCellEditor, ItemListener {
	private final JComboBox<String>  comboBox;
	private       CellEditorListener cellEditorListener = null;

	public ComboBoxTableCellEditor(String... elements) {
		comboBox = new JComboBox<>(elements);
		comboBox.addItemListener(this);

		setLayout(new BorderLayout());
		add(comboBox, BorderLayout.CENTER);
	}

	@Override
	public Object getCellEditorValue() {
		// System.out.println("getCellEditorValue");
		return comboBox.getSelectedIndex();
	}

	@Override
	public boolean isCellEditable(EventObject e) {
		// System.out.println("isCellEditable");
		return true;
	}

	@Override
	public boolean shouldSelectCell(EventObject e) {
		// System.out.println("shouldSelectCell(" + e + ")");
		return true;
	}

	@Override
	public boolean stopCellEditing() {
		// System.out.println("stopCellEditing");
		cellEditorListener.editingStopped(new ChangeEvent(this));
		return true;
	}

	@Override
	public void cancelCellEditing() {
		// System.out.println("cancelCellEditing");
		cellEditorListener.editingCanceled(new ChangeEvent(this));
	}

	@Override
	public void addCellEditorListener(CellEditorListener cellEditorListener) {
		// System.out.println("addCellEditorListener(" + cellEditorListener +
		// ")");
		this.cellEditorListener = cellEditorListener;
	}

	@Override
	public void removeCellEditorListener(CellEditorListener cellEditorListener) {
		// System.out.println("removeCellEditorListener(" + cellEditorListener +
		// ")");
		if (this.cellEditorListener == cellEditorListener) {
			this.cellEditorListener = null;
		}
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object obj, boolean isSelected, int row, int column) {
		// System.out.println("getTableCellEditorComponent(..., " + row + ", " +
		// column + ")");
		Color color2 = table.getBackground();
		if (color2 == null || color2 instanceof UIResource) {
			Color color3 = UIManager.getColor("Table.alternateRowColor");
			if (color3 != null && (row & 1) == 0) {
				color2 = color3;
			}
		}
		setForeground(table.getForeground());
		setBackground(color2);
		setBorder(UIManager.getBorder("Table.cellNoFocusBorder"));

		comboBox.setSelectedIndex((Integer)obj);
		return comboBox;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {

		}
	}
}
