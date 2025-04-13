package org.digitalmodular.utilities.signal.window.generalized;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.digitalmodular.utilities.signal.window.WindowTaperMode;
import static org.digitalmodular.utilities.graphics.GraphicsUtilities.setAntialiased;
import static org.digitalmodular.utilities.graphics.StaticShapes.PATH;

/**
 * @author Mark Jeronimus
 */
// Created 2016-03-28
public class WindowTaperModeTest extends JPanel implements MouseMotionListener {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame f = new JFrame(WindowTaperModeTest.class.getSimpleName());
			f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

			f.setContentPane(new WindowTaperModeTest());
			f.pack();
			f.setLocationRelativeTo(null);
			f.setVisible(true);
		});
	}

	private WindowTaperMode taperMode = WindowTaperMode.TRAPEZIUM;
	private double          taper     = 0.5;

	private int mouseX;
	private int mouseY;

	public WindowTaperModeTest() {
		super(null);
		setBackground(Color.BLACK);
		setOpaque(true);

		setPreferredSize(new Dimension(512, 512));

		addMouseMotionListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2     = (Graphics2D)g;
		int        width  = getWidth();
		int        height = getHeight();

		setAntialiased(g2, true);

		g2.setPaint(Color.WHITE);
		for (int i = 0; i < width; i++) {
			double x = i / (width - 1.0);
			double y = taperMode.getValueAt(x, taper);
			double j = height - 1 - y * (height - 1);
			if (i == 0) {
				PATH.moveTo(0, j);
			} else {
				PATH.lineTo(i, j);
			}
		}
		g2.draw(PATH);
		PATH.reset();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();

		if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) {
			taper = Math.max(0, Math.min(1, e.getX() / (getWidth() - 1.0)));
			taper = 1 / (1 - taper) - 1;
			System.out.println(taper);
		} else if ((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0) {
			WindowTaperMode[] values = WindowTaperMode.values();
			taperMode = values[e.getX() * values.length / getWidth()];
		}

		repaint();
	}
}
