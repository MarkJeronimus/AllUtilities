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

package nl.airsupplies.utilities.time;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Mark Jeronimus
 */
// Created 2009-04-20
public class ModernEarthTimeProvider extends TimeProvider {
	private static final Calendar CALENDAR = new GregorianCalendar();

	@Override
	public long getTimeImpl() {
		return System.currentTimeMillis() + CALENDAR.get(Calendar.ZONE_OFFSET) + CALENDAR.get(Calendar.DST_OFFSET);
	}

	@Override
	public double getMonthsPerYear() {
		return 12;
	}

	@Override
	public double getWeeksPerMonth() {
		return 365.25 / (getMonthsPerYear() * getDaysPerWeek());
	}

	@Override
	public double getDaysPerWeek() {
		return 7;
	}

	@Override
	public double getHoursPerDay() {
		return 24;
	}

	@Override
	public double getMinutesPerHour() {
		return 60;
	}

	@Override
	public double getSecondsPerMinute() {
		return 60;
	}

	@Override
	public double getMillisPerSecond() {
		return 1000;
	}
}
