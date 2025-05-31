package nl.airsupplies.utilities.graphics.terminal.terminalfont;

/**
 * @author Mark Jeronimus
 */
// Created 2012-08-05
public class BinaryGlyph extends AbstractGlyph {
	protected boolean[][] grid;

	public BinaryGlyph(int width, int height) {
		super(width, height);
		grid = new boolean[height][width];
	}

	@Override
	public void drawGlyph(int[] pixels, int p, int scanline, int fgColor, int bgColor) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[p] = grid[y][x] ? fgColor : bgColor;
				p++;
			}
			p += scanline - width;
		}
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder(height * (width + 1));

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				out.append(grid[y][x] ? '#' : ' ');
			}
			out.append('\n');
		}

		return out.toString();
	}
}
