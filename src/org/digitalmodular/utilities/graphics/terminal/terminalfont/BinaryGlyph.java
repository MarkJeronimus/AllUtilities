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
