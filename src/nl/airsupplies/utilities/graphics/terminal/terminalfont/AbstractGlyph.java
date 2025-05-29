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
