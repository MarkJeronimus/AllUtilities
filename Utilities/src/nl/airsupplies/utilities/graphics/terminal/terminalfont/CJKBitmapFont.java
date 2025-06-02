package nl.airsupplies.utilities.graphics.terminal.terminalfont;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import nl.airsupplies.utilities.encoding.CharacterEncoding;

/**
 * @author Mark Jeronimus
 */
// Created 2012-06-04
public class CJKBitmapFont extends AbstractTerminalFont {
	public CJKBitmapFont(File file, CharacterEncoding encoding) {
		try {
			BufferedImage image  = ImageIO.read(file);
			int           width  = image.getWidth();
			int           height = image.getHeight();

			gridSpacingX = findSize(image, 0, 0, 1, 0, width, height);
			gridSpacingY = findSize(image, 0, 0, 0, 1, width, height);

			int numGlyphsX = width / gridSpacingX;
			int numGlyphsY = height / gridSpacingY;

			width  = numGlyphsX * gridSpacingX;
			height = numGlyphsY * gridSpacingY;

			for (int y = 0; y < height; y += gridSpacingY) {
				for (int x = 0; x < width; x += gridSpacingX) {
					if (y / gridSpacingX == 1) {
						System.out.println();
					}
					BinaryGlyph glyph = new BinaryGlyph(gridSpacingX, gridSpacingY);
					for (int v = 0; v < gridSpacingY; v++) {
						for (int u = 0; u < gridSpacingX; u++) {
							glyph.grid[v][u] = (image.getRGB(1 + x + u, 1 + y + v) & 0xFFFFFF) == 0 && (x > 0 || y >
							                                                                                     0);
						}
					}
					setGlyph(encoding.decode(x / gridSpacingX + y / gridSpacingY * 256 + 0x2121), glyph);
				}
			}
			setSpace(getGlyph(0x3000));
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(0);
		}
	}

	private static int findSize(BufferedImage image, int x, int y, int dx, int dy, int width, int height) {
		int color = image.getRGB(x, y);

		do {
			x += dx;
			y += dy;
		} while (x < width && y < height && image.getRGB(x, y) == color);
		return Math.max(x, y);
	}
}
