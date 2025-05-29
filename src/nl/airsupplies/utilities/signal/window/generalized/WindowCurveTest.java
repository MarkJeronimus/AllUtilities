package nl.airsupplies.utilities.signal.window.generalized;

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

import static nl.airsupplies.utilities.gui.GUIUtilities.setAntialiased;
import static nl.airsupplies.utilities.graphics.StaticShapes.PATH;

/**
 * @author Mark Jeronimus
 */
// Created 2016-03-28
public class WindowCurveTest extends JPanel implements MouseMotionListener {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame f = new JFrame(WindowCurveTest.class.getSimpleName());
			f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

			f.setContentPane(new WindowCurveTest());
			f.pack();
			f.setLocationRelativeTo(null);
			f.setVisible(true);
			f.createBufferStrategy(1);
		});
	}

	private double power         = 2;
	private double invPower      = 2;
	private double invPowerFirst = 0.5;

	private int mouseX;
	private int mouseY;

	public WindowCurveTest() {
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
		for (int n = 0; n < 9; n++) {
			invPowerFirst = n / 8.0;
			for (int i = 0; i < width * 8; i++) {
				double x = i / 8.0 / (width - 1.0);

				double y = fn(x);

				double j = height - 1 - y * (height - 1);
				if (i == 0) {
					PATH.moveTo(0 / 8.0, j);
				} else {
					PATH.lineTo(i / 8.0, j);
				}
			}
			g2.draw(PATH);
			PATH.reset();
		}
	}

	private double fn(double x) {
		double y1 = 1 - Math.pow(1 - Math.pow(x, power), invPower);
		double y2 = Math.pow(1 - Math.pow(1 - x, invPower), power);

		return y1 + (y2 - y1) * invPowerFirst;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		double x = Math.max(0, Math.min(1, e.getX() / (getWidth() - 1.0)));
		double y = Math.max(0, Math.min(1, 1 - e.getY() / (getHeight() - 1.0)));
		x -= 0;
		x /= 1;
		x += 0;
		System.out.println(x + "\t" + y);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();

		if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) {
			power    = Math.max(0, Math.min(1, e.getX() / (getWidth() - 1.0)));
			invPower = Math.max(0, Math.min(1, 1 - e.getY() / (getHeight() - 1.0)));

			power    = 1 / (1 - power) - 1;
			invPower = 1 / (1 - invPower) - 1;
		} else if ((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0) {
			invPowerFirst = Math.max(0, Math.min(1, e.getX() / (getWidth() - 1.0)));
		}
		System.out.println(power + "\t" + invPower + '\t' + invPowerFirst);

		repaint();
	}
}
