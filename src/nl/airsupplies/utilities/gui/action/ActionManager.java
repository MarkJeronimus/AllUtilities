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

import java.util.HashMap;
import java.util.Map;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.annotation.ThreadBounded;

/**
 * @author Mark Jeronimus
 */
// Created 2007-06-03
@ThreadBounded("AWT-EventQueue-0")
public class ActionManager implements UndoableEditListener {
	public static final String UNDO_ACTION_NAME = "UNDO_ACTION";
	public static final String REDO_ACTION_NAME = "REDO_ACTION";

	public final  UndoManager                 undoManager = new UndoManager();
	private final Map<String, UndoableAction> actions     = new HashMap<>(64);

	@SuppressWarnings({"OverridableMethodCallDuringObjectConstruction", "ThisEscapedInObjectConstruction"})
	public ActionManager() {
		addAction(new UndoAction(this));
		addAction(new RedoAction(this));
	}

	/**
	 * Sets the maximum number of edits its {@code UndoManager} holds. A value less than 0 indicates the number of
	 * edits is not limited. If edits need to be discarded to shrink the limit, {@code die} will be invoked on them
	 * in the reverse order they were added. The default is 100.
	 *
	 * @param undoLimit the new limit
	 * @throws RuntimeException if this {@code UndoManager} is not in progress ({@code end} has been invoked)
	 * @see UndoManager#isInProgress()
	 * @see UndoManager#addEdit(UndoableEdit)
	 * @see UndoManager#getLimit()
	 */
	public void setUndoLimit(int undoLimit) {
		undoManager.setLimit(undoLimit);
	}

	public void addAction(UndoableAction action) {
		String name = getActionName(action);
		actions.put(name, action);
		action.checkEnable();
	}

	public UndoableAction getAction(String name) {
		@Nullable UndoableAction action = actions.get(name);
		if (action == null) {
			throw new IllegalArgumentException("Action not defined: " + name);
		}

		return action;
	}

	/**
	 * Sets the enabled state of the {@code Action}. When enabled, any component associated with this object is
	 * active and able to fire this object's {@code actionPerformed} method. If the value has changed, a
	 * {@code PropertyChangeEvent} is sent to listeners.
	 */
	public void setActionEnabled(String name, boolean enabled) {
		getAction(name).setEnabled(enabled);
	}

	/**
	 * Returns the enabled state of the {@code Action}. When enabled, any component associated with this object is
	 * active and able to fire this object's {@code actionPerformed} method.
	 */
	public boolean isActionEnabled(String name) {
		return getAction(name).isEnabled();
	}

	public String getActionName(String name) {
		return getActionName(getAction(name));
	}

	/**
	 * Sets the enabled state of the Undo and Redo Actions depending on the return values of the
	 * {@code UndoManager}'s {@code canUndo()} and {@code canRedo()} return values. Basically, the
	 * buttons are enabled when there are edits which can be undone or redone.
	 *
	 * @see UndoManager
	 * @see UndoManager#canUndo
	 * @see UndoManager#canRedo
	 */
	public void checkUndoRedoEnable() {
		getAction(UNDO_ACTION_NAME).checkEnable();
		getAction(REDO_ACTION_NAME).checkEnable();
	}

	public void checkEnable() {
		for (UndoableAction action : actions.values()) {
			action.checkEnable();
		}
	}

	@Override
	public void undoableEditHappened(UndoableEditEvent undoableEditEvent) {
		undoManager.undoableEditHappened(undoableEditEvent);
	}

//	public void fireAction(String name, Object source) {
//		getAction(name).actionPerformed(new ActionEvent(source,
//		                                                ActionEvent.ACTION_PERFORMED,
//		                                                name,
//		                                                EventQueue.getMostRecentEventTime(),
//		                                                0));
//	}

	public void bindAction(JComponent parent, KeyStroke keyStroke, String actionName) {
		Action action = getAction(actionName);
		if (action != null) {
			parent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, actionName);
			parent.getActionMap().put(actionName, action);
		}
	}

	private static String getActionName(Action action) {
		@Nullable Object name = action.getValue(Action.NAME);
		if (name == null) {
			throw new IllegalArgumentException('\'' + Action.NAME + "' property is not set");
		}
		if (!(name instanceof String)) {
			throw new IllegalArgumentException('\'' + Action.NAME + "' property is not a String value: " + name);
		}
		if (((String)name).isEmpty()) {
			throw new IllegalArgumentException('\'' + Action.NAME + "' property is empty");
		}
		return (String)name;
	}
}
