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

package org.digitalmodular.utilities.signal.window;

import org.digitalmodular.utilities.NumberUtilities;

/**
 * @author Mark Jeronimus
 */
// Created 2016-03-28
public enum WindowTaperMode {
	TRAPEZIUM {
		@Override
		public double getValueAt(double x, double taper) {
			if (taper <= 0) {
				return 0.5;
			}
			if (x < 0.5) {
				return Math.min(0.5, x / taper);
			} else {
				return Math.max(0.5, 1 - (1 - x) / taper);
			}
		}
	},
	SMOOTH {
		@Override
		public double getValueAt(double x, double taper) {
			if (taper <= 0) {
				return 0.5;
			}
			return (NumberUtilities.powSign(2 * x - 1, 1 / taper) + 1) / 2;
		}
	};

	public abstract double getValueAt(double x, double taper);
}
