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
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author Mark Jeronimus
 */
// Created 2007-12-17
public class EnumTableCellRenderer extends DefaultTableCellRenderer {
	private String[] elements;

	public EnumTableCellRenderer(String[] elements) {
		super.setOpaque(true);

		this.elements = elements;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus,
	                                               int row,
	                                               int column) {
		if (isSelected) {
			super.setForeground(table.getSelectionForeground());
			super.setBackground(table.getSelectionBackground());
		} else {
			Color color2 = table.getBackground();
			if (color2 == null || color2 instanceof javax.swing.plaf.UIResource) {
				Color color3 = UIManager.getColor("Table.alternateRowColor");
				if (color3 != null && (row & 1) == 0) {
					color2 = color3;
				}
			}
			super.setForeground(table.getForeground());
			super.setBackground(color2);
		}
		if (hasFocus) {
			Border border = null;
			if (isSelected) {
				border = UIManager.getBorder("Table.focusSelectedCellHighlightBorder");
			}
			if (border == null) {
				border = UIManager.getBorder("Table.focusCellHighlightBorder");
			}
			setBorder(border);
			if (!isSelected && table.isCellEditable(row, column)) {
				Color color4 = UIManager.getColor("Table.focusCellForeground");
				if (color4 != null) {
					super.setForeground(color4);
				}
				color4 = UIManager.getColor("Table.focusCellBackground");
				if (color4 != null) {
					super.setBackground(color4);
				}
			}
		} else {
			setBorder(UIManager.getBorder("Table.cellNoFocusBorder"));
		}

		setText(elements[((Integer)obj).intValue()]);
		return this;
	}
}
