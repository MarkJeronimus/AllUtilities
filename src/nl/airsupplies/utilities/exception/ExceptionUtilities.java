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

package nl.airsupplies.utilities.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import nl.airsupplies.utilities.annotation.UtilityClass;

/**
 * @author Mark Jeronimus
 */
// Created 2017-02-15
@UtilityClass
public final class ExceptionUtilities {
	public static String exceptionToString(Throwable ex) {
		String errorString;
		try {
			try (StringWriter sw = new StringWriter();
			     PrintWriter pw = new PrintWriter(sw)) {
				ex.printStackTrace(pw);

				errorString = sw.toString();
			}
		} catch (IOException ex2) {
			errorString = "\nUnexpected exception during exception handling:\n" + ex2 +
			              "\n\nOriginal exception:\n" + ex;
		}
		return errorString;
	}
}
