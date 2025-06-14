package nl.airsupplies.utilities.gui.tablelayout;

import java.awt.Component;
import java.awt.Dimension;

import nl.airsupplies.utilities.annotation.UtilityClass;
import static nl.airsupplies.utilities.gui.tablelayout.Size.Priority.RELATIVE;

/**
 * @author Mark Jeronimus
 */
// Created 2017-02-13
@UtilityClass
public final class PreferredSizeCalculator {
	public static Size[][] calculatePreferredSizes(Component[] components,
	                                               Number[] horizontalWeights,
	                                               Number[] verticalWeights) {
		Size[] preferredWidths  = createInitialSizes(horizontalWeights);
		Size[] preferredHeights = createInitialSizes(verticalWeights);

		processComponents(components, preferredWidths, preferredHeights);
		prepareRelativeSizes(preferredWidths);
		prepareRelativeSizes(preferredHeights);

		return new Size[][]{preferredWidths, preferredHeights};
	}

	private static Size[] createInitialSizes(Number[] weights) {
		int    num   = weights.length;
		Size[] sizes = new Size[num];
		for (int i = 0; i < num; i++) {
			if (weights[i].equals(TableLayout.PREFERRED)) {
				sizes[i] = Size.newPreferredSize();
			} else if (weights[i].equals(TableLayout.MINIMUM)) {
				sizes[i] = Size.newMinimumSize();
			} else if (weights[i] instanceof Integer) {
				sizes[i] = Size.newExactSize((Integer)weights[i]);
			} else if (weights[i] instanceof Double) {
				sizes[i] = Size.newRelativeSize((Double)weights[i]);
			}
		}

		return sizes;
	}

	private static void processComponents(Component[] components, Size[] preferredWidths, Size[] preferredHeights) {
		int numCols = preferredWidths.length;
		int numRows = preferredHeights.length;

		int col = 0;
		int row = 0;
		for (Component component : components) {
			Dimension preferredSize = component.getPreferredSize();
			Dimension minimumSize   = component.getMinimumSize();
			preferredWidths[col].recordComponent(preferredSize.width, minimumSize.width);
			preferredHeights[row].recordComponent(preferredSize.height, minimumSize.height);

			++col;
			if (col == numCols) {
				col = 0;
				row = Math.min(row + 1, numRows - 1);
			}
		}
	}

	private static void prepareRelativeSizes(Size[] layoutSizes) {
		double scale = 0;
		for (Size size : layoutSizes) {
			if (size.getPriority() == RELATIVE) {
				scale = Math.max(scale, size.getPreferred() / size.getStretchFactor());
			}
		}

		for (Size size : layoutSizes) {
			if (size.getPriority() == RELATIVE) {
				size.setPreferred(Math.max(1, size.getStretchFactor() * scale));
			}
		}
	}
}
