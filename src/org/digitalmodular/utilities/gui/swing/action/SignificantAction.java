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

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.UIManager;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

/**
 * @author Mark Jeronimus
 */
// Created 2007-06-03
public abstract class SignificantAction extends AbstractAction implements UndoableEdit {
	protected final ActionManager actionManager;

	protected SignificantAction(String name, ActionManager actionManager) {
		super(name);

		this.actionManager = actionManager;
	}

	public abstract void checkEnable();

	@Override
	public String getPresentationName() {
		throw new UnsupportedOperationException(
				"Not supported yet."); // To change body of generated methods, choose Tools |
		// Templates.
	}

	@Override
	public String getUndoPresentationName() {
		return UIManager.getString("AbstractUndoableEdit.undoText") + " " + getPresentationName();
	}

	@Override
	public String getRedoPresentationName() {
		return UIManager.getString("AbstractUndoableEdit.redoText") + " " + getPresentationName();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			actionManager.undoManager.addEdit((UndoableEdit)clone());
			actionManager.setActionEnabled(ActionManager.UNDO_ACTION, true);
			redo();
		} catch (CloneNotSupportedException e1) {}
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
	public void undo() throws CannotUndoException {
		throw new CannotUndoException();
	}

	@Override
	public void redo() throws CannotRedoException {
		throw new CannotUndoException();
	}

	@Override
	public void die() {}
}
