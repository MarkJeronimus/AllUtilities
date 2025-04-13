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

package org.digitalmodular.utilities.graphics.swing.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.UIManager;
import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

/**
 * @author Mark Jeronimus
 */
// Created 2007-06-03
public abstract class UndoableAction extends AbstractAction implements UndoableEdit {
	protected final ActionManager actionManager;

	protected UndoableAction(String name, ActionManager actionManager) {
		super(name);

		this.actionManager = actionManager;
	}

	/**
	 * Checks if this undo or redo action should be enabled, depending on the return values of the
	 * {@code UndoManager}'s {@code canUndo()} and {@code canRedo()} return values. Basically, the
	 * buttons action when there are edits which can be undone or redone.
	 */
	public abstract void checkEnable();

	@Override
	public String getPresentationName() {
		throw new UnsupportedOperationException(
				"Not supported yet."); // To change body of generated methods, choose Tools |
		// Templates.
	}

	@Override
	public String getUndoPresentationName() {
		return UIManager.getString("AbstractUndoableEdit.undoText") + ' ' + getPresentationName();
	}

	@Override
	public String getRedoPresentationName() {
		return UIManager.getString("AbstractUndoableEdit.redoText") + ' ' + getPresentationName();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		actionManager.undoableEditHappened(new UndoableEditEvent(this, this));
		actionManager.checkUndoRedoEnable();
	}

	@Override
	public boolean isSignificant() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return false;
	}

	@Override
	public boolean canRedo() {
		return canUndo();
	}

	@Override
	public boolean addEdit(UndoableEdit anEdit) {
		return false;
	}

	@Override
	public boolean replaceEdit(UndoableEdit anEdit) {
		return false;
	}

	@Override
	public void undo() {
		throw new CannotUndoException();
	}

	@Override
	public void redo() {
		throw new CannotUndoException();
	}

	@Override
	public void die() {
	}
}
