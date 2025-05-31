package nl.airsupplies.utilities.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JPanel;

import nl.airsupplies.utilities.gui.border.StatusBarBorder;

/**
 * @author Mark Jeronimus
 */
// Created 2005-04-22
public class StatusBarItem extends JPanel {
	public StatusBarItem() {
		super(new BorderLayout());
		setBorder(new StatusBarBorder());
	}

	public StatusBarItem(Component c) {
		super(new BorderLayout());
		setBorder(new StatusBarBorder());

		set(c);
	}

	public void set(Component c) {
		removeAll();
		add(c, BorderLayout.CENTER);
	}
}
