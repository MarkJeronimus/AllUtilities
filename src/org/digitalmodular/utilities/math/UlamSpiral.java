/*
 * This file is part of PAO.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.digitalmodular.utilities.math;

import java.awt.Point;

import org.digitalmodular.utilities.annotation.UtilityClass;

/**
 * @author Mark Jeronimus
 */
// Created 2022-12-21
@UtilityClass
public final class UlamSpiral {
	public static Point getUlamCoordinate(int n) {
		int s = (int)Math.floor(Math.sqrt(n));
		int v = n - s * s;
		int u = s / 2;

		if ((s & 1) == 0) {
			if (v <= s) {
				return new Point(u, v - u);
			} else {
				return new Point(3 * u - v, u);
			}
		} else {
			if (v <= s) {
				return new Point(-1 - u, -v + u);
			} else {
				return new Point(v - 3 * u - 2, -1 - u);
			}
		}
	}
}
