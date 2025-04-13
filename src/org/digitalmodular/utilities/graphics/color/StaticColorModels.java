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

import java.awt.image.IndexColorModel;

/**
 * @author Mark Jeronimus
 */
// Created 2009-09-28
public abstract class StaticColorModels {
	private static IndexColorModel bwDefault   = null;
	private static IndexColorModel grayDefault = null;

	public static IndexColorModel get1bppBW() {
		if (bwDefault == null) {
			byte[] cmap = {0, (byte)255};
			bwDefault = new IndexColorModel(1, 2, cmap, cmap, cmap);
		}

		return bwDefault;
	}

	public static IndexColorModel get8bppGray() {
		if (grayDefault == null) {
			byte[] cmap = new byte[256];
			for (int i = 0; i < 256; i++) {
				cmap[i] = (byte)i;
			}

			grayDefault = new IndexColorModel(8, 256, cmap, cmap, cmap);
		}

		return grayDefault;
	}
}
