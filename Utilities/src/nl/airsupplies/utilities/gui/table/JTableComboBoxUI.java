package nl.airsupplies.utilities.gui.table;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalComboBoxUI;

import sun.swing.DefaultLookup;

/**
 * @author Mark Jeronimus
 */
public class JTableComboBoxUI extends MetalComboBoxUI {
	@Override
	public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
	}

	/**
	 * 1:1 copy of BasicComboBoxUI{@link #paintCurrentValue(Graphics, Rectangle, boolean)}
	 */
	@Override
	public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
		ListCellRenderer<? super Object> renderer = comboBox.getRenderer();
		Component                        c;

		if (hasFocus && !isPopupVisible(comboBox)) {
			c = renderer.getListCellRendererComponent(listBox,
			                                          comboBox.getSelectedItem(),
			                                          -1,
			                                          true,
			                                          false);
		} else {
			c = renderer.getListCellRendererComponent(listBox,
			                                          comboBox.getSelectedItem(),
			                                          -1,
			                                          false,
			                                          false);
			c.setBackground(UIManager.getColor("ComboBox.background"));
		}
		c.setFont(comboBox.getFont());
		if (hasFocus && !isPopupVisible(comboBox)) {
			c.setForeground(listBox.getSelectionForeground());
			c.setBackground(listBox.getSelectionBackground());
		} else {
			if (comboBox.isEnabled()) {
				c.setForeground(comboBox.getForeground());
				c.setBackground(comboBox.getBackground());
			} else {
				c.setForeground(DefaultLookup.getColor(
						comboBox, this, "ComboBox.disabledForeground", null));
				c.setBackground(DefaultLookup.getColor(
						comboBox, this, "ComboBox.disabledBackground", null));
			}
		}

		// Fix for 4238829: should lay out the JPanel.
		boolean shouldValidate = false;
		if (c instanceof JPanel) {
			shouldValidate = true;
		}

		int x = bounds.x;
		int y = bounds.y;
		int w = bounds.width;
		int h = bounds.height;
		if (padding != null) {
			x = bounds.x + padding.left;
			y = bounds.y + padding.top;
			w = bounds.width - (padding.left + padding.right);
			h = bounds.height - (padding.top + padding.bottom);
		}

		currentValuePane.paintComponent(g, c, comboBox, x, y, w, h, shouldValidate);
	}
}
