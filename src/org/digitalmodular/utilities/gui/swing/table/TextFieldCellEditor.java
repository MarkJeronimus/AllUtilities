/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2019 Mark Jeronimus. All Rights Reversed.
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
package org.digitalmodular.utilities.gui.swing.table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;

/**
 * @author Mark Jeronimus
 */
// Created 2009-05-?1
public class TextFieldCellEditor extends AbstractCellEditor implements TableCellEditor {
	JTextField tf = new JTextField();

	private boolean isInteger = false;
	private Object  oldValue;

	// Start editing
	@Override
	public Component getTableCellEditorComponent(JTable table, Object obj, boolean isSelected, int row, int column) {
		Color color2 = UIManager.getColor("Table.alternateRowColor");
		tf.setBackground(color2 != null && (row & 1) == 1 ? color2 : table.getBackground());
		tf.setForeground(table.getForeground());
		tf.setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));

		tf.setText(obj.toString());

		isInteger = obj instanceof Integer;
		if (isInteger) {
			tf.setHorizontalAlignment(SwingConstants.RIGHT);
			oldValue = obj;
		}

		if (isSelected) {
			tf.selectAll();
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					tf.selectAll();
				}
			});
		}

		return tf;
	}

	// Retrieve edited value
	@Override
	public Object getCellEditorValue() {
		if (isInteger) {
			// Try to convert to integer. If input is invalid, revert.
			try {
				return new Integer(tf.getText());
			} catch (NumberFormatException e) {
				return oldValue;
			}
		}
		return tf.getText();
	}
}
