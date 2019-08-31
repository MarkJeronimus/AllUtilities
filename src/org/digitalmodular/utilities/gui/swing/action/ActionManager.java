/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2018 Mark Jeronimus. All Rights Reversed.
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
 * along with AllUtilities. If not, see <http://www.gnu.org/licenses/>.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.digitalmodular.utilities.gui.swing.action;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.undo.UndoManager;

/**
 * @author Mark Jeronimus
 */
// Created 2007-06-03
public class ActionManager {
	public static final String UNDO_ACTION = "UNDO_ACTION";
	public static final String REDO_ACTION = "REDO_ACTION";

	public    UndoManager                        undoManager = new UndoManager();
	protected HashMap<String, SignificantAction> actions     = new HashMap<>();

	/**
	 * Sets the maximum number of edits its {@code UndoManager} holds. A value less than 0 indicates the number of
	 * edits is not limited. If edits need to be discarded to shrink the limit, {@code die} will be invoked on them
	 * in the reverse order they were added. The default is 100.
	 *
	 * @param l the new limit
	 * @throws RuntimeException if this {@code UndoManager} is not in progress ({@code end} has been invoked)
	 * @see UndoManager#isInProgress()
	 * @see UndoManager#addEdit(javax.swing.undo.UndoableEdit)
	 * @see UndoManager#getLimit()
	 */
	public void setUndoLimit(int l) {
		undoManager.setLimit(l);
	}

	public void addAction(String identifier, SignificantAction action) {
		actions.put(identifier, action);
		action.checkEnable();
	}

	public SignificantAction getAction(String actionID) {
		return actions.get(actionID);
	}

	/**
	 * Sets the enabled state of the {@code Action}. When enabled, any component associated with this object is
	 * active and able to fire this object's {@code actionPerformed} method. If the value has changed, a
	 * {@code PropertyChangeEvent} is sent to listeners.
	 *
	 * @param enabled true to enable this {@code Action}, false to disable it
	 */
	public void setActionEnabled(String identifier, boolean enabled) {
		SignificantAction a = actions.get(identifier);
		if (a != null) {
			a.setEnabled(enabled);
		}
	}

	/**
	 * Returns the enabled state of the {@code Action}. When enabled, any component associated with this object is
	 * active and able to fire this object's {@code actionPerformed} method.
	 *
	 * @return true if this {@code Action} is enabled
	 */
	public boolean isActionEnabled(String identifier) {
		SignificantAction a = actions.get(identifier);
		return a == null ? false : a.isEnabled();
	}

	public String getActionName(String identifier) {
		SignificantAction a = actions.get(identifier);
		return a == null ? "<Undefined action>" : (String)a.getValue(Action.NAME);
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
		checkEnable(ActionManager.UNDO_ACTION);
		checkEnable(ActionManager.REDO_ACTION);
	}

	public void checkEnable(String identifier) {
		SignificantAction a = actions.get(identifier);
		if (a != null) {
			a.checkEnable();
		}
	}

	/**
	 * Should only be called from the event-dispatching thread or exceptions will be thrown.
	 */
	public void fireAction(String identifier, Object source) {
		SignificantAction a = actions.get(identifier);
		if (a != null) {
			a.actionPerformed(
					new ActionEvent(source, ActionEvent.ACTION_PERFORMED, (String)a.getValue("Name"), EventQueue
							.getMostRecentEventTime(), 0));
		}
	}

	public void bindAction(JComponent parent, KeyStroke keyStroke, String actionName) {
		Action action = getAction(actionName);
		if (action != null) {
			parent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, actionName);
			parent.getActionMap().put(actionName, action);
		}
	}
}
