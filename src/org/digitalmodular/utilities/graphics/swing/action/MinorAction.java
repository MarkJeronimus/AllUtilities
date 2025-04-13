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
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

/**
 * @author Mark Jeronimus
 */
// Created 2007-06-03
public abstract class MinorAction extends UndoableAction {
	protected MinorAction(String name, ActionManager actionManager) {
		super(name, actionManager);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		redo();
	}

	@Override
	public final boolean isSignificant() {
		return false;
	}

	@Override
	public final boolean canUndo() {
		return false;
	}

	@Override
	public final boolean canRedo() {
		return false;
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
	public void undo() throws CannotUndoException {
		throw new CannotUndoException();
	}

	@Override
	public void redo() throws CannotRedoException {
		throw new CannotRedoException();
	}

	@Override
	public void die() {
	}
}
