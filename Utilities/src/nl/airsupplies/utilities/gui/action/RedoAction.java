package nl.airsupplies.utilities.gui.action;

import java.awt.event.ActionEvent;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import nl.airsupplies.utilities.gui.ImageIconProxy;

/**
 * @author Mark Jeronimus
 */
// Created 2007-06-02
public class RedoAction extends UndoableAction {
	public RedoAction(ActionManager actionManager) {
		super(ActionManager.REDO_ACTION_NAME, actionManager);
		putValue(SMALL_ICON, ImageIconProxy.getIcon("icons/16/redo.gif"));
		putValue(LARGE_ICON_KEY, ImageIconProxy.getIcon("icons/22/redo.gif"));

		checkEnable();
	}

	@Override
	public void checkEnable() {
		setEnabled(actionManager.undoManager.canRedo());
		actionManager.setActionEnabled(ActionManager.UNDO_ACTION_NAME, actionManager.undoManager.canUndo());

		putValue(NAME, getValue(NAME) == null ? null : actionManager.undoManager.getRedoPresentationName());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		actionManager.undoManager.redo();
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
