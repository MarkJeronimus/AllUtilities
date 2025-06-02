package nl.airsupplies.utilities.gui.table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;

/**
 * @author Mark Jeronimus
 */
// Created 2009-05-?1
public class TextFieldCellEditor extends AbstractCellEditor implements TableCellEditor {
	JTextField tf = new JTextField();

	private boolean isInteger = false;
	private Object  oldValue;

	// Start editing
	@Override
	public Component getTableCellEditorComponent(JTable table, Object obj, boolean isSelected, int row, int column) {
		Color color2 = UIManager.getColor("Table.alternateRowColor");
		tf.setBackground(color2 != null && (row & 1) == 1 ? color2 : table.getBackground());
		tf.setForeground(table.getForeground());
		tf.setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));

		tf.setText(obj.toString());

		isInteger = obj instanceof Integer;
		if (isInteger) {
			tf.setHorizontalAlignment(SwingConstants.RIGHT);
			oldValue = obj;
		}

		if (isSelected) {
			tf.selectAll();
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					tf.selectAll();
				}
			});
		}

		return tf;
	}

	// Retrieve edited value
	@Override
	public Object getCellEditorValue() {
		if (isInteger) {
			// Try to convert to integer. If input is invalid, revert.
			try {
				return Integer.valueOf(tf.getText());
			} catch (NumberFormatException ignored) {
				return oldValue;
			}
		}
		return tf.getText();
	}
}
