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
