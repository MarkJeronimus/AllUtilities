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

package nl.airsupplies.utilities.signal.window;

/**
 * @author Mark Jeronimus
 */
// Created 2016-03-27
public enum WindowSymmetryMode {
	DFT_EVEN {
		@Override
		public double getValueAt(int x, int length) {
			return x / (double)length;
		}
	},
	SYMMETRIC {
		@Override
		public double getValueAt(int x, int length) {
			return (x + 0.5) / length;
		}
	},
	PERIODIC {
		@Override
		public double getValueAt(int x, int length) {
			return (x + 0.5) / (length + 1.0);
		}
	};

	public abstract double getValueAt(int x, int length);
}
