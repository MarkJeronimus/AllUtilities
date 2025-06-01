package nl.airsupplies.utilities.brokenorold;

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
