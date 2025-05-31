package nl.airsupplies.utilities.graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.jetbrains.annotations.Nullable;

/**
 * @author Mark Jeronimus
 */
// Created 2011-06-20
public class ImagePanel extends JPanel {
	private @Nullable Image   image;
	private           boolean stretch;

	public ImagePanel() {
		this(null);
	}

	public ImagePanel(@Nullable Image image) {
		this(image, false);
	}

	public ImagePanel(@Nullable Image image, boolean stretch) {
		super(null);
		setOpaque(false);

		this.image   = image;
		this.stretch = stretch;

		pack();
	}

	public @Nullable Image getImage() {
		return image;
	}

	public void setImage(@Nullable Image image) {
		this.image = image;
		repaint();
	}

	public boolean isStretch() {
		return stretch;
	}

	public void setStretch(boolean stretch) {
		this.stretch = stretch;
		repaint();
	}

	public void pack() {
		if (stretch) {
			repaint();
			return;
		}

		Dimension size = new Dimension();

		if (image != null) {
			size.width  = image.getWidth(null);
			size.height = image.getHeight(null);
		}

		Border border = getBorder();
		if (border != null) {
			Insets insets = border.getBorderInsets(this);

			size.width += insets.left + insets.right;
			size.height += insets.top + insets.bottom;
		}

		setPreferredSize(size);
		setSize(size);
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (image == null) {
			return;
		}

		int x      = 0;
		int y      = 0;
		int width  = getWidth();
		int height = getHeight();

		Border border = getBorder();
		if (border != null) {
			Insets insets = border.getBorderInsets(this);

			x = insets.left;
			y = insets.top;
			width -= insets.right + x;
			height -= insets.right + x;
		}

		if (stretch) {
			g.drawImage(image, x, y, width, height, this);
		} else {
			g.drawImage(image, x, y, null);
		}
	}
}
