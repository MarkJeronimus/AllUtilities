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
