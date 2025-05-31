package nl.airsupplies.utilities.broken;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import nl.airsupplies.utilities.gui.tablelayout.TableLayout;

/**
 * @author Mark Jeronimus
 */
// Created 2006-06-15
public class PasswordDialog extends JDialog implements ActionListener {
	private final JPasswordField field1   = new JPasswordField(40);
	private final JPasswordField field2   = new JPasswordField(40);
	private final JCheckBox      show     = new JCheckBox("Display password", false);
	private final JButton        button   = new JButton("Ok");
	private       char           echoChar = field1.getEchoChar();

	public PasswordDialog(Frame owner) {
		super(owner, true);
		makeLayout();
	}

	public PasswordDialog(Dialog owner) {
		super(owner, true);
		makeLayout();
	}

	private void makeLayout() {
		setLayout(new BorderLayout());

		{
			JPanel p = new JPanel(new TableLayout(2, new Double[]{0.0, 300.0}, new Double[0]));

			p.add(new JLabel("Password"));
			{
				p.add(field1, BorderLayout.NORTH);
				field1.addActionListener(this);
			}

			p.add(new JLabel("Verify"));
			{
				p.add(field2, BorderLayout.CENTER);
				field2.addActionListener(this);
			}

			p.add(new JPanel());
			{
				p.add(show);
				show.addActionListener(this);
			}

			add(p, BorderLayout.CENTER);
		}
		{
			JPanel p = new JPanel(new BorderLayout());

			p.add(Box.createVerticalStrut(4), BorderLayout.NORTH);
			{
				JPanel p1 = new JPanel();
				p1.setLayout(new GridLayout(1, 2, 4, 0));
				p1.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 2));
				p1.add(button);
				p.add(p1, BorderLayout.EAST);
			}
			add(p, BorderLayout.SOUTH);
		}

		button.addActionListener(this);

		pack();
		setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(screenSize.width - getWidth() >> 1, screenSize.height - getHeight() >> 1,
		          getWidth(),
		          getHeight());
	}

	public void setEchoChar(char c) {
		echoChar = c;
		field1.setEchoChar(show.isSelected() ? 0 : c);
		field2.setEchoChar(show.isSelected() ? 0 : c);
	}

	public char[] getPassword() {
		return field1.getPassword();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == show) {
			setEchoChar(echoChar);
		} else {
			char[] p1 = field1.getPassword();
			char[] p2 = field2.getPassword();
			if (p1.length != p2.length) {
				return;
			}
			for (int i = p1.length - 1; i >= 0; i--) {
				if (p1[i] != p2[i]) {
					return;
				}
				p2[i] = 0;
			}
			dispose();
		}
	}
}
