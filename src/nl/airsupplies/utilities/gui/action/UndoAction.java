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

package nl.airsupplies.utilities.gui.action;

import java.awt.event.ActionEvent;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import nl.airsupplies.utilities.gui.ImageIconProxy;

/**
 * @author Mark Jeronimus
 */
// Created 2007-06-02
public class UndoAction extends UndoableAction {
	public UndoAction(ActionManager actionManager) {
		super(ActionManager.UNDO_ACTION_NAME, actionManager);
		putValue(SMALL_ICON, ImageIconProxy.getIcon("icons/16/undo.gif"));
		putValue(LARGE_ICON_KEY, ImageIconProxy.getIcon("icons/22/undo.gif"));

		checkEnable();
	}

	@Override
	public void checkEnable() {
		setEnabled(actionManager.undoManager.canUndo());
		actionManager.setActionEnabled(ActionManager.REDO_ACTION_NAME, actionManager.undoManager.canRedo());

		putValue(NAME, getValue(NAME) == null ? null : actionManager.undoManager.getUndoPresentationName());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		actionManager.undoManager.undo();
		checkEnable();
	}

	@Override
	public void redo() {
		throw new CannotRedoException();
	}

	@Override
	public void undo() {
		throw new CannotUndoException();
	}
}
