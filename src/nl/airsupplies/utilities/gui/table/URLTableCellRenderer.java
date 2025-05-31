package nl.airsupplies.utilities.gui.table;

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
