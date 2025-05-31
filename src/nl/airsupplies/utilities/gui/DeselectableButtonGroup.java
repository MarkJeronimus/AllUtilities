package nl.airsupplies.utilities.gui;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

/**
 * A {@link ButtonGroup} for {@link JCheckBox}es, {@link JToggleButton}s and the like, that, in addition to normal group
 * behavior, allows the selected item to be deselected.
 * <p>
 * Not: This doesn't work for {@link JRadioButton}s and the like because those can't be deselected from the GUI.
 *
 * @author Mark Jeronimus
 */
// Created 2014-02-21
public class DeselectableButtonGroup extends ButtonGroup {
	@Override
	public void setSelected(ButtonModel model, boolean selected) {
		if (selected) {
			super.setSelected(model, selected);
		} else if (getSelection() == model) {
			clearSelection();
		}
	}
}
