package nl.airsupplies.utilities.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * @author Mark Jeronimus
 */
// Created 2005-04-23
public class StatusBar extends JPanel {
	private final JPanel bodyPanel = new JPanel();

	public StatusBar() {
		super(new BorderLayout());

		GUIDebugging.gui(this, "this");

		setPreferredSize(new Dimension(0, 18));

		bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.X_AXIS));
		GUIDebugging.gui(bodyPanel, "bodyPanel");
		add(bodyPanel, BorderLayout.CENTER);
	}

	public Component addStatusBarItem(StatusBarItem c) {
		if (bodyPanel.getComponentCount() > 0) {
			bodyPanel.add(Box.createRigidArea(new Dimension(2, 0)));
		}
		{
			JPanel p5 = new JPanel(new BorderLayout());
			GUIDebugging.gui(p5, "p5");
			p5.add(c, BorderLayout.CENTER);
			bodyPanel.add(p5);
		}
		return c;
	}

	@Override
	public void removeAll() {
		super.removeAll();
		bodyPanel.removeAll();
	}
}
