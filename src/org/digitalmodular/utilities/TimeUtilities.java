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

package org.digitalmodular.utilities;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.digitalmodular.utilities.container.UnsignedInteger;

/**
 * Deprecated by Java 8
 *
 * @author Mark Jeronimus
 */
// Created 2013-11-23
@Deprecated
public enum TimeUtilities {
	;

	@Deprecated
	public static int getCurrentDay() {
		GregorianCalendar cal = new GregorianCalendar();
		long              day = cal.getTimeInMillis();
		return (int)((day + cal.getTimeZone().getOffset(day)) / 86400000);
	}

	public static long getCurrentTime() {
		GregorianCalendar cal  = new GregorianCalendar();
		long              time = cal.getTimeInMillis();
		return time + cal.getTimeZone().getOffset(time);
	}

	@Deprecated
	public static int getCurrentTimeHM() {
		GregorianCalendar cal = new GregorianCalendar();
		return cal.get(Calendar.HOUR_OF_DAY) * 3600 + cal.get(Calendar.MINUTE) * 60;
	}

	public static String dayToString(int day) {
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(day * 86400000L);
		return cal.get(Calendar.YEAR) +
		       "-" +
		       UnsignedInteger.valueOf(cal.get(Calendar.MONTH) + 1).toString(2) +
		       '-' +
		       UnsignedInteger.valueOf(cal.get(Calendar.DAY_OF_MONTH)).toString(2);
	}

	@SuppressWarnings("MagicConstant")
	public static int stringToDay(String day) {
		try {
			int i = day.indexOf('-');
			int j = day.indexOf('-', i + 1);

			int y = Integer.parseInt(day.substring(0, i));
			int m = Integer.parseInt(day.substring(i + 1, j)) - 1;
			int d = Integer.parseInt(day.substring(j + 1));

			Calendar cal = new GregorianCalendar();
			cal.set(y, m, d);

			return (int)(cal.getTimeInMillis() / 86400000);
		} catch (NumberFormatException | IndexOutOfBoundsException ex) {
			throw new IllegalArgumentException();
		}
	}

	public static int stringToTimeHM(String time) throws IllegalArgumentException {
		try {
			int i = time.indexOf(':');

			return Integer.parseInt(time.substring(0, i)) * 3600 + Integer.parseInt(time.substring(i + 1)) * 60;
		} catch (NumberFormatException | IndexOutOfBoundsException ex) {
			throw new IllegalArgumentException();
		}
	}

	public static int stringToTime(String time) throws IllegalArgumentException {
		try {
			int i = time.indexOf(':');
			int j = time.indexOf(':', i + 1);

			return Integer.parseInt(time.substring(0, i)) * 3600
			       + Integer.parseInt(time.substring(i + 1, j)) * 60
			       + Integer.parseInt(time.substring(j + 1));
		} catch (NumberFormatException | IndexOutOfBoundsException ex) {
			throw new IllegalArgumentException();
		}
	}

	public static String timeToStringHM(int time) {
		return time / 3600 +
		       ":" +
		       UnsignedInteger.valueOf(time / 60 % 60).toString(2);
	}

	public static String timeToString(int time) {
		return time / 3600 +
		       ":" +
		       UnsignedInteger.valueOf(time / 60 % 60).toString(2) +
		       ':' +
		       UnsignedInteger.valueOf(time % 60).toString(2);
	}
}
