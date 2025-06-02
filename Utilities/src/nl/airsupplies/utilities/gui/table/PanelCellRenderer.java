package nl.airsupplies.utilities.gui.table;

import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 * @author Mark Jeronimus
 */
// Created 2007-12-17
public class PanelCellRenderer implements TableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus,
	                                               int row,
	                                               int column) {
		JPanel panel = (JPanel)obj;
		panel.setOpaque(false);
		// panel.setBackground(UIManager.getColor(isSelected?
		// "Table.selectionBackground" : "Table.background"));
		panel.setBorder(hasFocus ? UIManager.getBorder("Table.focusCellHighlightBorder") : null);

		return panel;
	}
}
