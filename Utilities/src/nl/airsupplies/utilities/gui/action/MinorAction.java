package nl.airsupplies.utilities.gui.action;

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
