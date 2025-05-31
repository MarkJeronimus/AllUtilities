package nl.airsupplies.utilities.gui;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;

import nl.airsupplies.utilities.annotation.StaticClass;

/**
 * @author Mark Jeronimus
 */
// Created 2005-04-22
@StaticClass
public final class GUIDebugging {
	public static boolean enabled = false;

	public static boolean isEnabled() {
		return enabled;
	}

	public static void setEnabled(boolean enabled) {
		GUIDebugging.enabled = enabled;
	}

	public static synchronized void gui(JComponent c) {
		if (enabled) {
			c.setOpaque(true);
			c.setBackground(new Color((float)Math.random(), (float)Math.random(), (float)Math.random()));
		}
	}

	public static void gui(JComponent c, String name) {
		if (enabled) {
			c.setBorder(BorderFactory.createTitledBorder(name));
		}
	}

	public static void gui(JFrame c) {
		if (enabled) {
			c.setBackground(new Color((float)Math.random(), (float)Math.random(), (float)Math.random()));
		}
	}
}
