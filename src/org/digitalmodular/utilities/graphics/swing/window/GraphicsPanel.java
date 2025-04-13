package org.digitalmodular.utilities.graphics.swing.window;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JPanel;

import org.jetbrains.annotations.Nullable;

/**
 * @author Mark Jeronimus
 */
// Created 2019-08-14
public abstract class GraphicsPanel extends JPanel {
	private @Nullable BufferedImage image = null;
	private           Graphics2D    imageG;
	private           int[]         pixels;

	@Override
	protected void paintComponent(Graphics g) {
		if (image == null || image.getWidth() != getWidth() || image.getHeight() != getHeight()) {
			if (imageG != null) {
				imageG.dispose();
			}

			image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
			image.setAccelerationPriority(0);
			imageG = image.createGraphics();
			pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		}

		paintImage(imageG, pixels, getWidth(), getHeight());

		g.drawImage(image, 0, 0, null);
	}

	protected abstract void paintImage(Graphics2D g, int[] pixels, int width, int height);
}
