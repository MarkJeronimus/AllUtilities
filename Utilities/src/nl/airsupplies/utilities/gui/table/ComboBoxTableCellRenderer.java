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
