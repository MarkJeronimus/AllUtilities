/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.digitalmodular.utilities.graphics.terminal.terminalfont;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.digitalmodular.utilities.encoding.CharacterEncoding;

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

			width = numGlyphsX * gridSpacingX;
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
