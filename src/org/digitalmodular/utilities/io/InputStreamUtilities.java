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

package org.digitalmodular.utilities.io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * @author Mark Jeronimus
 */
// Created 2017-03-05
public final class InputStreamUtilities {
	public static boolean isGzipped(BufferedInputStream in) throws IOException {
		in.mark(2);
		int lo = in.read();
		int hi = in.read();
		in.reset();

		// Check for gzip magic number
		int magic = hi << 8 | lo;
		return magic == GZIPInputStream.GZIP_MAGIC;
	}

	public static InputStream decompressIfPossible(BufferedInputStream in) throws IOException {
		if (isGzipped(in)) {
			return new GZIPInputStream(in);
		}

		return in;
	}
}
