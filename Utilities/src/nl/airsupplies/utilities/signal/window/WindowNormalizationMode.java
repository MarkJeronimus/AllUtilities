package nl.airsupplies.utilities.signal.window;

/**
 * @author Mark Jeronimus
 */
// Created 2016-46-27
public enum WindowNormalizationMode {
	/**
	 * Use the raw formula and don't do any normalization.
	 */
	NONE,
	/**
	 * Normalize the peak of the windowCurve to 1.
	 */
	PEAK,
	/**
	 * Normalize the center of the windowCurve to 1 (regardless of if there are higher peaks off-center).
	 */
	CENTER,
	/**
	 * Normalize the area under the windowCurve to N (negative values subtract from area).
	 */
	AREA
}
