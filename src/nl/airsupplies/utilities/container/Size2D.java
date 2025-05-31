package nl.airsupplies.utilities.container;

import net.jcip.annotations.Immutable;

/**
 * @author Mark Jeronimus
 */
// Created 2015-08-31
@Immutable
public class Size2D {
	private final int width;
	private final int height;

	public Size2D(int width, int height) {
		this.width  = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Size2D flip() {
		return new Size2D(height, width);
	}

	@Override
	public String toString() {
		return "Size2D[" + width + ", " + height + ']';
	}
}
