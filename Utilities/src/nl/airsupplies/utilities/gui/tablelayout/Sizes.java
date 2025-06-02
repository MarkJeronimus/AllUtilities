package nl.airsupplies.utilities.gui.tablelayout;

import nl.airsupplies.utilities.annotation.UtilityClass;
import nl.airsupplies.utilities.gui.tablelayout.Size.Priority;

/**
 * @author Mark Jeronimus
 */
// Created 2017-02-12
@UtilityClass
public final class Sizes {
	public static Size[] copy(Size[] componentSizes) {
		Size[] sizes = new Size[componentSizes.length];
		for (int i = 0; i < componentSizes.length; i++) {
			sizes[i] = new Size(componentSizes[i]);
		}

		return sizes;
	}

	public static boolean containsPriority(Size[] sizes, Priority priority) {
		for (Size size : sizes) {
			if (size.getPriority() == priority) {
				return true;
			}
		}

		return false;
	}

	public static boolean containsPriority(Size[] sizes, int length, Priority priority) {
		for (int i = 0; i < sizes.length; i++) {
			if (sizes[Math.min(i, sizes.length - 1)].getPriority() == priority) {
				return true;
			}
		}

		return false;
	}

	public static int getPriorityCount(Size[] sizes, Priority priority) {
		int count = 0;
		for (Size size : sizes) {
			if (size.getPriority() == priority) {
				count++;
			}
		}
		return count;
	}

	public static int getPriorityCount(Size[] sizes, int length, Priority priority) {
		int count = 0;
		for (int i = 0; i < sizes.length; i++) {
			if (sizes[Math.min(i, sizes.length - 1)].getPriority() == priority) {
				count++;
			}
		}

		return count;
	}

	public static int getTotalMinimum(Size[] sizes) {
		int totalMinimum = 0;
		for (Size size : sizes) {
			totalMinimum += size.getMinimum();
		}
		return totalMinimum;
	}

	public static int getTotalMinimum(Size[] sizes, int length) {
		int totalMinimum = 0;
		for (int i = 0; i < length; i++) {
			totalMinimum += sizes[Math.min(i, sizes.length - 1)].getMinimum();
		}

		return totalMinimum;
	}

	public static double getTotalPreferred(Size[] sizes) {
		double totalPreferred = 0;
		for (Size size : sizes) {
			totalPreferred += size.getPreferred();
		}

		return totalPreferred;
	}

	public static double getTotalPreferred(Size[] sizes, int length) {
		double totalPreferred = 0;
		for (int i = 0; i < length; i++) {
			totalPreferred += sizes[Math.min(i, sizes.length - 1)].getPreferred();
		}

		return totalPreferred;
	}

	public static double getTotalStretchFactor(Size[] sizes) {
		double totalStretch = 0;
		for (Size size : sizes) {
			totalStretch += size.getStretchFactor();
		}

		return totalStretch;
	}

	public static double getTotalStretchFactor(Size[] sizes, int length) {
		double totalStretch = 0;
		for (int i = 0; i < sizes.length; i++) {
			totalStretch += sizes[Math.min(i, sizes.length)].getStretchFactor();
		}

		return totalStretch;
	}

	public static double getPriorityPreferred(Size[] sizes, Priority priority) {
		double totalPreferred = 0;
		for (Size size : sizes) {
			if (size.getPriority() == priority) {
				totalPreferred += size.getPreferred();
			}
		}

		return totalPreferred;
	}

	public static double getPriorityPreferred(Size[] sizes, int length, Priority priority) {
		double totalPreferred = 0;
		for (int i = 0; i < length; i++) {
			if (sizes[i].getPriority() == priority) {
				totalPreferred += sizes[Math.min(i, sizes.length - 1)].getPreferred();
			}
		}
		return totalPreferred;
	}
}
