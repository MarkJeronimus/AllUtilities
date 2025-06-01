package nl.airsupplies.utilities.gui.statusbar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.AbstractBorder;

/**
 * @author Mark Jeronimus
 */
// Created 2005-04-22
public class StatusBarBorder extends AbstractBorder {
	public static Insets getBorderInsets() {
		return new Insets(1, 3, 1, 1);
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return getBorderInsets();
	}

	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		insets = getBorderInsets();
		return insets;
	}

	@Override
	public void paintBorder(Component component, Graphics g, int x, int y, int width, int height) {
		Graphics copy = g.create();
		if (copy != null) {
			copy.translate(x, y);
			copy.setColor(Color.gray);
			copy.fillRect(0, 0, width - 1, 1);
			copy.fillRect(0, 0, 1, height - 1);
			copy.setColor(Color.white);
			copy.fillRect(width - 1, 0, 1, height - 1);
			copy.fillRect(0, height - 1, width - 1, 1);
			copy.dispose();
		}
	}
}
