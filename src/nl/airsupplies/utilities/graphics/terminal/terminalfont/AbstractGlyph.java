package nl.airsupplies.utilities.graphics.terminal.terminalfont;

/**
 * @author Mark Jeronimus
 */
// Created 2012-08-05
public abstract class AbstractGlyph {
	protected int width;
	protected int height;

	protected AbstractGlyph(int width, int height) {
		this.width  = width;
		this.height = height;
	}

	public abstract void drawGlyph(int[] pixels, int p, int scanline, int fgColor, int bgColor);
}
