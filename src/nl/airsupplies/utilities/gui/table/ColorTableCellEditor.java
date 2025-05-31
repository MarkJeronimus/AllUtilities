package nl.airsupplies.utilities.gui.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 * The ColorEditor is used to change the Color of a selected item.
 *
 * @author foekema, jeronimus
 */
// Created 2011-11-07 First version by foekema
// Updated 2013-12-31 Extracted from StatisticsTopComponent
public class ColorTableCellEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
	private              Color         currentColor;
	private final        JButton       button;
	private final        JColorChooser colorChooser;
	private final        JDialog       dialog;
	private static final String        EDIT = "edit";

	public ColorTableCellEditor() {
		button = new JButton();
		button.setActionCommand(EDIT);
		button.addActionListener(this);
		button.setBorderPainted(false);

		// Set up the dialog that the button brings up.
		colorChooser = new JColorChooser();
		dialog       = JColorChooser.createDialog(button, "Pick a Color", true, // Modal
		                                          colorChooser, this, // OK button handler
		                                          null); // CANCEL button handler
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (EDIT.equals(e.getActionCommand())) {
			// The user has clicked the cell, so bring up the dialog.
			colorChooser.setColor(currentColor);
			dialog.setVisible(true);

		} else {
			// User pressed OK button.
			currentColor = colorChooser.getColor();

			// Make the renderer reappear.
			fireEditingStopped();
		}
	}

	@Override
	public Object getCellEditorValue() {
		return currentColor;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		currentColor = (Color)value;
		button.setBackground(currentColor);
		return button;
	}
}
