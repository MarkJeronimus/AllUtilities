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

package nl.airsupplies.utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mark Jeronimus
 */
// Created 2015-09-08
public class PerformanceTimer {
	private final String       format       = "%7.2f";
	private final List<Long>   durations    = new ArrayList<>();
	private final List<String> descriptions = new ArrayList<>();
	private       long         startTime;
	private       long         lastTime;
	private       int          longestDescription;

	public void reset() {
		durations.clear();
		descriptions.clear();
	}

	public void start() {
		startTime          = System.nanoTime();
		lastTime           = startTime;
		longestDescription = 0;
	}

	public void record(String description) {
		long time = System.nanoTime();
		durations.add(time - lastTime);
		descriptions.add(description);
		longestDescription = Math.max(longestDescription, description.length());
		lastTime           = time;
	}

	public void printResults() {
		String formatString = "%-" + longestDescription + "s " + format + '\n';
		for (int i = 0; i < durations.size(); i++) {
			long   duration    = durations.get(i);
			String description = descriptions.get(i);
			System.out.printf(formatString, description, duration / 1.0e6);
		}
	}

	/**
	 * Prints the durations of each record and the amount of work performed per second in each step.
	 */
	public void printResults(double workload) {
		String formatString = "%-" + longestDescription + "s " + format + " (%,9.2f)\n";
		for (int i = 0; i < durations.size(); i++) {
			long   duration    = durations.get(i);
			String description = descriptions.get(i);
			System.out.printf(formatString, description, duration / 1.0e6, workload * 1.0e3 / duration);
		}
	}

	public void printTotal() {
		String formatString = "%-" + longestDescription + "s " + format + '\n';
		long   duration     = lastTime - startTime;
		String description  = "Total";
		System.out.printf(formatString, description, duration / 1.0e6);
	}

	/**
	 * Prints the total duration and the amount of work performed per second.
	 */
	public void printTotal(long workload) {
		String formatString = "%-" + longestDescription + "s " + format + " (%,9.2f)\n";
		for (int i = 0; i < durations.size(); i++) {
			long   duration    = lastTime - startTime;
			String description = "Total";
			System.out.printf(formatString, description, duration / 1.0e6, workload * 1.0e3 / duration);
		}
	}
}
