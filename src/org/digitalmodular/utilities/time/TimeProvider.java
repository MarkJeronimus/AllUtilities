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

/**
 * @author Mark Jeronimus
 */
// Created 2009-04-20
public abstract class TimeProvider {
	protected long    time       = 0;
	protected boolean live       = true;
	protected boolean normalized = false;

	private double yearsFactor;
	private double monthsFactor;
	private double weeksFactor;
	private double daysFactor;
	private double hoursFactor;
	private double minutesFactor;
	private double secondsFactor;
	private double millisFactor;
	private long   offset;

	protected TimeProvider() {
		setNormalized(false);
	}

	public void setTime(long time) {
		this.time = time;
		live = false;
	}

	public void setLive() {
		live = true;
	}

	public final long getTime() {
		return live ? getTimeImpl() + offset : time;
	}

	public void setNormalized(boolean normalized) {
		this.normalized = normalized;
		yearsFactor = 0;
		monthsFactor = 0;
		weeksFactor = 0;
		daysFactor = 0;
		hoursFactor = 0;
		minutesFactor = 0;
		secondsFactor = 0;
		millisFactor = 0;
	}

	public final double getYearsFactor() {
		if (yearsFactor == 0) {
			if (normalized) {
				throw new IllegalStateException(
						"The years value cannot be normalized as there is no bigger unit to fit it into.");
			}
			yearsFactor = getMonthsPerYear() * getMonthsFactor();
		}
		return yearsFactor;
	}

	public final double getMonthsFactor() {
		if (monthsFactor == 0) {
			monthsFactor = (normalized ? getMonthsPerYear() : getWeeksPerMonth()) * getWeeksFactor();
		}
		return monthsFactor;
	}

	public final double getWeeksFactor() {
		if (weeksFactor == 0) {
			weeksFactor = (normalized ? getWeeksPerMonth() : getDaysPerWeek()) * getDaysFactor();
		}
		return weeksFactor;
	}

	public final double getDaysFactor() {
		if (daysFactor == 0) {
			daysFactor = (normalized ? getDaysPerWeek() : getHoursPerDay()) * getHoursFactor();
		}
		return daysFactor;
	}

	public final double getHoursFactor() {
		if (hoursFactor == 0) {
			hoursFactor = (normalized ? getHoursPerDay() : getMinutesPerHour()) * getMinutesFactor();
		}
		return hoursFactor;
	}

	public final double getMinutesFactor() {
		if (minutesFactor == 0) {
			minutesFactor = (normalized ? getMinutesPerHour() : getSecondsPerMinute()) * getSecondsFactor();
		}
		return minutesFactor;
	}

	public final double getSecondsFactor() {
		if (secondsFactor == 0) {
			secondsFactor = (normalized ? getSecondsPerMinute() : getMillisPerSecond()) * getMillisFactor();
		}
		return secondsFactor;
	}

	public final double getMillisFactor() {
		if (millisFactor == 0) {
			millisFactor = normalized ? getMillisPerSecond() : 1;
		}
		return millisFactor;
	}

	public abstract long getTimeImpl();

	public abstract double getMonthsPerYear();

	public abstract double getWeeksPerMonth();

	public abstract double getDaysPerWeek();

	public abstract double getHoursPerDay();

	public abstract double getMinutesPerHour();

	public abstract double getSecondsPerMinute();

	public abstract double getMillisPerSecond();

	public void setZero() {
		offset = 0;
		offset = -getTime();
	}
}
