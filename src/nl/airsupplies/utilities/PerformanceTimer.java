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
