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

package org.digitalmodular.utilities.math;

import java.text.NumberFormat;

import static org.digitalmodular.utilities.ValidatorUtilities.requireAtLeast;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2025-04-13
public final class FileSizeFormatter {
	/** The minimum Multiplier such that MAX_LONG/pow(Multiplier, 6) < Multiplier. */
	public static final  int    MIN_MULTIPLIER = 512;
	/** The maximum value rendered after scaling. */
	private static final double THRESHOLD      = 1200.0;

	/**
	 * @author Mark Jeronimus
	 */
	// Created 2025-04-13
	private interface FilesizeUnit {
		String getName();
	}

	/**
	 * @author Mark Jeronimus
	 */
	// Created 2025-04-13
	public enum FilesizeUnitSI implements FilesizeUnit {
		B("B"),
		KB("KB"),
		MB("MB"),
		GB("GB"),
		TB("TB"),
		PB("PB"),
		EB("EB");

		private final String name;

		FilesizeUnitSI(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	}

	/**
	 * @author Mark Jeronimus
	 */
	// Created 2025-04-13
	public enum FilesizeUnitIEC implements FilesizeUnit {
		B("B"),
		KIB("KiB"),
		MIB("MiB"),
		GIB("GiB"),
		TIB("TiB"),
		PIB("PiB"),
		EIB("EiB");

		private final String name;

		FilesizeUnitIEC(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	}

	public static String formatFilesize(double value, NumberFormat formatter, boolean iecMultiplier) {
		return formatFilesize(value, formatter, iecMultiplier ? 1024 : 1000);
	}

	public static String formatFilesize(double value, NumberFormat formatter, long multiplier) {
		requireAtLeast(0, value, "value");
		requireNonNull(formatter, "formatter");
		requireAtLeast(MIN_MULTIPLIER, multiplier, "multiplier");

		FilesizeUnit[] values;
		if (multiplier == 1024) {
			values = FilesizeUnitIEC.values();
		} else {
			values = FilesizeUnitSI.values();
		}

		int ordinal = 0;
		while (value > THRESHOLD && ordinal < values.length - 1) {
			value /= multiplier;
			ordinal++;
		}

		return formatter.format(value) + ' ' + values[ordinal].getName();
	}
}
