package nl.airsupplies.utilities.gui.table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

/**
 * A {@link TableCellRenderer} to be used in {@link JTable}s.
 *
 * @author Mark Jeronimus
 */
// Created 2007-12-17 First version by jeronimus
// Created 2011-11-07 First version by foekema
// Updated 2013-12-31 Merged two versions of this (Original name: ColorRenderer)
public class ColorTableCellRenderer extends JLabel implements TableCellRenderer {
	private Border unselectedBorder = null;
	private Border selectedBorder   = null;

	public ColorTableCellRenderer() {
		// Ensure that the background is visible
		setOpaque(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
	                                               int row,
	                                               int column) {
		// Get the background color of the cell
		Color newColor = (Color)value;
		setBackground(newColor);

		// Alternate way of obtaining colors (not recommended):
		// UIManager.getColor("Table.selectionBackground")
		// UIManager.getColor("Table.background")

		if (hasFocus) {
			setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
		} else if (isSelected) {
			if (selectedBorder == null) {
				selectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5, table.getSelectionBackground());
			}
			setBorder(selectedBorder);
		} else {
			if (unselectedBorder == null) {
				unselectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5, table.getBackground());
			}
			setBorder(unselectedBorder);
		}

		// Set the tooltip.
		setToolTipText("RGB value: " + newColor.getRed() + ", " + newColor.getGreen() + ", " + newColor.getBlue());

		return this;
	}
}
