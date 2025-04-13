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

package org.digitalmodular.utilities.graphics.color;

import java.awt.Color;

/**
 * A {@link ColorPalette} returns {@link Color}s when given an index. The index
 * is a non-negative integer and can be arbitrarily large. An implementation
 * with a bounded number of colors shall wrap around when the index is larger
 * than the palette size, to ensure even very large numbers generate meaningful
 * colors.
 *
 * @author Mark Jeronimus
 */
// Created 2014-11-27
public interface ColorPalette {
	/**
	 * Get a {@link Color} from the palette.
	 *
	 * @param index A non-negative palette index
	 * @throws IndexOutOfBoundsException If index is negative (implementations may decide to return a special color
	 *                                   instead)
	 */
	Color getColor(int index);
}
