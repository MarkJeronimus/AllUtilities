package nl.airsupplies.utilities.gui.table;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 * @author Mark Jeronimus
 */
// Created 2007-12-17
public class IconTableCellRenderer implements TableCellRenderer {
	private static final JLabel icon = new JLabel();

	static {
		icon.setOpaque(true);
		icon.setHorizontalAlignment(SwingConstants.CENTER);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table,
	                                               Object obj,
	                                               boolean isSelected,
	                                               boolean hasFocus,
	                                               int row,
	                                               int column) {
		icon.setBackground(UIManager.getColor(isSelected ? "Table.selectionBackground" : "Table.background"));
		icon.setBorder(hasFocus ? UIManager.getBorder("Table.focusCellHighlightBorder") : null);

		icon.setIcon((Icon)obj);
		return icon;
	}
}
