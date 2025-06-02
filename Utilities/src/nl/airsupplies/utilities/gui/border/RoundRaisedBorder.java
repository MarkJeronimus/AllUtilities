package nl.airsupplies.utilities.gui.border;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 * @author Mark Jeronimus
 */
// Created 2007-05-12
public class RoundRaisedBorder implements Border {
	private static final Insets insets = new Insets(1, 1, 1, 1);

	@Override
	public Insets getBorderInsets(Component c) {
		return insets;
	}

	@Override
	public boolean isBorderOpaque() {
		return false;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		g.setColor(UIManager.getColor("controlLtHighlight"));
		g.fillRect(x + 2, y + 1, width - 4, 1);
		g.fillRect(x + 1, y + 2, 1, height - 4);
		g.fillRect(x + 2, y + 2, 1, 1);
		// g.fillRect(x +2, y+height - 3, 1, 1);

		g.setColor(UIManager.getColor("controlShadow"));
		g.fillRect(x + width - 2, y + 2, 1, height - 4);
		g.fillRect(x + 2, y + height - 2, width - 4, 1);
		g.fillRect(x + width - 3, y + height - 3, 1, 1);
		// g.fillRect(x +width - 3, y+2, 1, 1);
	}
}
