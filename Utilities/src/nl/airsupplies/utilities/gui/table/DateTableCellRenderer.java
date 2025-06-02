package nl.airsupplies.utilities.gui.table;

import java.awt.Component;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author Mark Jeronimus
 */
public class DateTableCellRenderer extends DefaultTableCellRenderer {
	private final DateFormat format;

	/**
	 * @param format <i>e.g.</i> <tt>"EEE HH:mm:ss:SSS"</tt>
	 * @see SimpleDateFormat
	 */
	public DateTableCellRenderer(String format) {
		this.format = new SimpleDateFormat(format);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
	                                               int row,
	                                               int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (value instanceof Date) {
			setText(format.format((Date)value));
		} else {
			setText(value.toString());
		}
		return this;
	}
}
