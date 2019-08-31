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
import javax.swing.Action;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import org.digitalmodular.utilities.gui.swing.ImageIconProxy;

/**
 * @author Mark Jeronimus
 */
// Created 2007-06-02
public class RedoAction extends SignificantAction {
	public RedoAction(ActionManager actionManager) {
		super(null, actionManager);
		super.putValue(Action.SMALL_ICON, ImageIconProxy.getIcon("icons/16/redo.gif"));
		super.putValue(Action.LARGE_ICON_KEY, ImageIconProxy.getIcon("icons/22/redo.gif"));

		checkEnable();
	}

	@Override
	public void checkEnable() {
		super.setEnabled(actionManager.undoManager.canRedo());
		super.actionManager.setActionEnabled(ActionManager.UNDO_ACTION, actionManager.undoManager.canUndo());

		putValue(Action.NAME,
		         super.getValue(Action.NAME) == null ? null : actionManager.undoManager.getRedoPresentationName());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionManager.undoManager.redo();
		checkEnable();
	}

	@Override
	public void redo() throws CannotRedoException {
		throw new CannotRedoException();
	}

	@Override
	public void undo() throws CannotUndoException {
		throw new CannotUndoException();
	}
}
