/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2018 Mark Jeronimus. All Rights Reversed.
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
 * along with AllUtilities. If not, see <http://www.gnu.org/licenses/>.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.digitalmodular.utilities.time;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Mark Jeronimus
 */
// Created 2005-06-07
public class Time implements Comparable<Time> {
	private static Calendar calendar;

	public static long getCurrentTime() {
		if (Time.calendar == null) {
			Time.calendar = new GregorianCalendar();
		}
		return System.currentTimeMillis() + Time.calendar.get(Calendar.ZONE_OFFSET) +
		       Time.calendar.get(Calendar.DST_OFFSET);
	}

	/**
	 * The gregorian year
	 */
	private int  year         = -1;
	/**
	 * The month, 1 is january
	 */
	private int  month        = -1;
	/**
	 * the day, 1 is the first day of the month
	 */
	private int  date         = -1;
	private int  hours        = -1;
	private int  minutes      = -1;
	private int  seconds      = -1;
	private int  milliseconds = -1;
	private long timeMillis   = -1;

	public Time() {
		setTime(Time.getCurrentTime());
	}

	public Time(long millis) {
		setTime(millis);
	}

	/**
	 * @param years  the gregorian year
	 * @param months the month, 1 is January
	 * @param days   the day, 1 is the first of the month
	 */
	public Time(int years, int months, int days, int hours, int minutes, int seconds, int milliseconds) {
		year = years;
		month = months;
		date = days;
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
		this.milliseconds = milliseconds;
	}

	public void setTime(long millis) {
		timeMillis = millis;
		year = -1;
		month = -1;
		date = -1;
		hours = -1;
		minutes = -1;
		seconds = -1;
		milliseconds = -1;
	}

	/**
	 * Get milliseconds since last whole second.
	 */
	public int getMilliseconds() {
		if (milliseconds == -1) {
			unDate();
		}
		return milliseconds;
	}

	/**
	 * Get seconds since last whole minute.
	 */
	public int getSeconds() {
		if (seconds == -1) {
			unDate();
		}
		return seconds;
	}

	/**
	 * Get minutes since last whole hour.
	 */
	public int getMinutes() {
		if (minutes == -1) {
			unDate();
		}
		return minutes;
	}

	/**
	 * Get hours since last whole day.
	 */
	public int getHours() {
		if (hours == -1) {
			unDate();
		}
		return hours;
	}

	/**
	 * Get the day of the month, starting at 1. (Gregorian calendar)
	 */
	public int getDate() {
		if (date == -1) {
			unDate();
		}
		return date;
	}

	/**
	 * Get the month of the year, starting at 1. (Gregorian calendar)
	 */
	public int getMonth() {
		if (month == -1) {
			unDate();
		}
		return month;
	}

	/**
	 * Get the year. (Gregorian calendar)
	 */
	public int getYear() {
		if (year == -1) {
			unDate();
		}
		return year;
	}

	public long millisSinceEpoch() {
		if (timeMillis == -1) {
			timeMillis = reDate(year, month, date, hours, minutes, seconds, milliseconds);
		}
		return timeMillis;
	}

	public long secondsSinceEpoch() {
		return millisSinceEpoch() / 1000;
	}

	public long minutesSinceEpoch() {
		return millisSinceEpoch() / 60000;
	}

	public long hoursSinceEpoch() {
		return millisSinceEpoch() / 3600000;
	}

	public long daysSinceEpoch() {
		return millisSinceEpoch() / 86400000;
	}

	/**
	 * @return Example: 01:37:34
	 */
	public static String toTimeString(Time time) {
		int hours   = time.getHours();
		int minutes = time.getMinutes();
		int seconds = time.getSeconds();
		return (hours < 10 ? "0" : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes + ":" +
		       (seconds < 10 ? "0" : "") + seconds;
	}

	/**
	 * @return Example: 19 Days, 1 Hours, 39 Minutes, 34 Seconds
	 */
	public static String toTimeDateIntervalString(Time time) {
		return time.daysSinceEpoch() + " Days, " + time.getHours() + " Hours, " + time.getMinutes() + " Minutes, "
		       + time.getSeconds() + " Seconds";
	}

	/**
	 * @return Example: 37 Years, 04 Months, 18 Days, 1 Hours, 39 Minutes, 34 Seconds
	 */
	public static String toExtendedTimeDateIntervalString(Time time) {
		return time.getYear() - 1970 + " Years, " + (time.getMonth() - 1) + " Months, " + (time.getDate() - 1) +
		       " Days, "
		       + time.getHours() + " Hours, " + time.getMinutes() + " Minutes, " + time.getSeconds() + " Seconds";
	}

	/**
	 * Compares this object with the specified object for order. Returns a negative integer, zero, or a positive
	 * integer
	 * as this object is less than, equal to, or greater than the specified object.
	 * <p>
	 * The implementor must ensure <tt>sgn(x.compareTo(y)) == -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and
	 * <tt>y</tt>. (This implies that <tt>x.compareTo(y)</tt> must throw an exception iff <tt>y.compareTo(x)</tt>
	 * throws
	 * an exception.)
	 * <p>
	 * The implementor must also ensure that the relation is transitive: <tt>(x.compareTo(y)&gt;0 &amp;&amp;
	 * y.compareTo(z)&gt;0)</tt> implies <tt>x.compareTo(z)&gt;0</tt>.
	 * <p>
	 * Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt> implies that <tt>sgn(x.compareTo(z)) ==
	 * sgn(y.compareTo(z))</tt>, for all <tt>z</tt>.
	 * <p>
	 * It is strongly recommended, but <i>not</i> strictly required that <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.
	 * Generally speaking, any class that implements the <tt>Comparable</tt> interface and violates this condition
	 * should clearly indicate this fact. The recommended language is "Note: this class has a natural ordering that is
	 * inconsistent with equals."
	 * <p>
	 * In the foregoing description, the notation <tt>sgn(</tt><i>expression</i> <tt>)</tt> designates the mathematical
	 * <i>signum</i> function, which is defined to return one of <tt>-1</tt>, <tt>0</tt>, or <tt>1</tt> according to
	 * whether the value of <i>expression</i> is negative, zero or positive.
	 *
	 * @param time the object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than
	 * the specified object.
	 * @throws ClassCastException if the specified object's type prevents it from being compared to this object.
	 */
	@Override
	public int compareTo(Time time) {
		return (int)((double)millisSinceEpoch() - time.millisSinceEpoch());
	}

	/**
	 * Calculates the number of milliseconds from epoch from calendar notation.
	 * <p>
	 * source: http://www.astronomy.villanova.edu/links/jd.htm
	 *
	 * @param year  the gregorian year
	 * @param month the month, 1 is January
	 * @param date  the day, 1 is the first day of the month
	 * @return the number of milliseconds from epoch
	 */
	@Deprecated
	private static long reDate(int year, int month, int date, int hours, int minutes, int seconds, int milliseconds) {
		// 01155254400000
		// 63323769600000
		// 62168515200000
		return (367L * year //
		        - ((month + 9) / 12 + year) * 7 / 4 //
		        - (((month - 9) / 7 + year) / 100 + 1) * 3 / 4 //
		        + month * 275 / 9 //
		        + date - 719559/* number of days until 1970 */) * 86400000L + hours * 3600000L + minutes * 60000L +
		       seconds
		       * 1000L + milliseconds; //

		// [EDIT] changed constant from 719543 to 719559. 20070519 was falsely
		// reported as 20070503
	}

	@Deprecated
	private void unDate() {
		// find date by successive approximation

		int lo = 1970;
		int hi = 200000984;
		while (hi - lo > 1) {
			int  mid    = (hi + lo) / 2;
			long millis = reDate(mid, 1, 1, 0, 0, 0, 0);
			if (millis < timeMillis) {
				lo = mid;
			} else {
				hi = mid;
			}
		}
		// (reDate(lo, 1, 1, 0, 0, 0, 0) - this.timeMillis)/86400000f
		// (reDate(mid, 1, 1, 0, 0, 0, 0) - this.timeMillis)/86400000
		// (reDate(hi, 1, 1, 0, 0, 0, 0) - this.timeMillis)/86400000f
		year = reDate(hi, 1, 1, 0, 0, 0, 0) > timeMillis ? lo : hi;

		lo = 1;
		hi = 12;
		while (hi - lo > 1) {
			int  mid    = (hi + lo) / 2;
			long millis = reDate(year, mid, 1, 0, 0, 0, 0);
			if (millis < timeMillis) {
				lo = mid;
			} else {
				hi = mid;
			}
		}
		// (reDate(this.years, lo, 1, 0, 0, 0, 0) - this.timeMillis)/86400000f
		// (reDate(this.years, hi, 1, 0, 0, 0, 0) - this.timeMillis)/86400000f
		// (reDate(this.years+1, 1, 1, 0, 0, 0, 0) - this.timeMillis)/86400000f
		month = reDate(year, hi, 1, 0, 0, 0, 0) > timeMillis ? lo : hi;

		lo = 1;
		hi = 31;
		while (hi - lo > 1) {
			int  mid    = (hi + lo) / 2;
			long millis = reDate(year, month, mid, 0, 0, 0, 0);
			if (millis < timeMillis) {
				lo = mid;
			} else {
				hi = mid;
			}
		}
		// (reDate(this.years, this.months, lo, 0, 0, 0, 0) -
		// this.timeMillis)/86400000
		// (reDate(this.years, this.months, hi, 0, 0, 0, 0) -
		// this.timeMillis)/86400000
		date = reDate(year, month, hi, 0, 0, 0, 0) > timeMillis ? lo : hi;

		hours = (int)(timeMillis / 3600000 % 24);
		minutes = (int)(timeMillis / 60000 % 60);
		seconds = (int)(timeMillis / 1000 % 60);
		milliseconds = (int)(timeMillis % 1000);
	}

	/**
	 * Add a number of milliseconds to this time and return a new instance
	 */
	public Time add(Time t) {
		return new Time(millisSinceEpoch() + t.millisSinceEpoch());
	}

	/**
	 * Add a number of milliseconds to this time and return a new instance
	 */
	public Time substract(Time t) {
		return new Time(millisSinceEpoch() - t.millisSinceEpoch());
	}

	/**
	 * @return Example: 20070519013734
	 */
	public static String toTimestampString(Time time) {
		int month   = time.getMonth();
		int day     = time.getDate();
		int hours   = time.getHours();
		int minutes = time.getMinutes();
		int seconds = time.getSeconds();
		return time.getYear() + (month < 10 ? "0" : "") + month + (day < 10 ? "0" : "") + day +
		       (hours < 10 ? "0" : "") + hours
		       + (minutes < 10 ? "0" : "") + minutes + (seconds < 10 ? "0" : "") + seconds;
	}
}
