package nl.airsupplies.utilities.gui.tablelayout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.LayoutManager2;

import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.gui.tablelayout.Size.Priority;
import static nl.airsupplies.utilities.validator.ArrayValidatorUtilities.requireArrayLengthAtLeast;
import static nl.airsupplies.utilities.validator.ArrayValidatorUtilities.requireNullOrArrayLengthAtLeast;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNotDegenerate;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireThat;

/**
 * @author Mark Jeronimus
 */
// Created 2017-02-10
public class TableLayout implements LayoutManager2 {
	public static final Number MINIMUM   = -Double.MAX_VALUE;
	public static final Number PREFERRED = Math.nextUp((Double)MINIMUM);

	@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
	private static int debug = GridBagConstraints.NONE; // NONE, HORIZONTAL or VERTICAL

	private static final Number[] DEFAULT_VERTICAL_WEIGHTS = {PREFERRED};

	private final int      spacing;
	private final Number[] horizontalWeights;
	private final Number[] verticalWeights;

	private Size @Nullable [] preferredWidths  = null;
	private Size @Nullable [] preferredHeights = null;
	private Size @Nullable [] layoutWidths     = null;
	private Size @Nullable [] layoutHeights    = null;
	private int               lastWidth        = -1;
	private int               lastHeight       = -1;

	/**
	 * Allowed weight values:
	 * <ul>
	 * <li>{@code Integer} - Exact size in pixels,</li>
	 * <li>{@code MINIMUM} - Highest minimum size of all components in that column,</li>
	 * <li>{@code PREFERRED} - Highest preferred size of all components in that column,</li>
	 * <li>{@code Double} - Preferred size in pixels, and all columns with Double will scale proportionally.</li>
	 * </ul>
	 * <p>
	 * If there are more rows than there are entries in {@code verticalWeights}, additional rows assume the same
	 * weight as te last element. If all rows are to behave the same, it should be one element long. For example, to
	 * make all rows fit the components in that row, use {@code {PREFERRED}}, and to make all rows the same height, use
	 * {@code {1.0}}.
	 *
	 * @param spacing           the distance between horizontally and vertically adjacent cells, in pixels
	 * @param horizontalWeights the weights list for columns.
	 * @param verticalWeights   the heights list for rows. If {@code null}, it defaults to {@code {PREFERRED}}.
	 */
	public TableLayout(int spacing, Number[] horizontalWeights, @Nullable Number[] verticalWeights) {
		this.spacing = requireAtLeast(0, spacing, "spacing");
		requireArrayLengthAtLeast(1, horizontalWeights, "horizontalWeights");
		requireNullOrArrayLengthAtLeast(1, verticalWeights, "verticalWeights");
		checkWeights(horizontalWeights, "horizontalWeights");
		if (verticalWeights != null) {
			checkWeights(verticalWeights, "verticalWeights");
		}

		this.horizontalWeights = horizontalWeights.clone();
		this.verticalWeights   = verticalWeights == null ? DEFAULT_VERTICAL_WEIGHTS : verticalWeights.clone();
	}

	@SuppressWarnings("NumberEquality")
	private static void checkWeights(Number[] weights, String varName) {
		for (int i = 0; i < weights.length; i++) {
			Number weight = weights[i];

			if (weight == PREFERRED || weight == MINIMUM) {
				continue;
			}

			int index = i;
			if (weight instanceof Integer) {
				requireThat((Integer)weight >= 1,
				            () -> varName + '[' + index + "] (Integer) is not positive: " + weight);
			} else if (weight instanceof Double) {
				requireNotDegenerate((Double)weight, varName + '[' + index + ']');
				requireThat((Double)weight > 0, () -> varName + '[' + index + "] (Double) is not positive: " + weight);
			} else {
				throw new IllegalArgumentException(
						varName + '[' + i + "] is not Integer or Double: " + weight.getClass());
			}
		}
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		addLayoutComponent(comp, null);
	}

	@Override
	public void addLayoutComponent(Component comp, @Nullable Object constraints) {
		invalidateLayout(null);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		invalidateLayout(null);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Component[] components = parent.getComponents();

		recalculatePreferredSizes(components);
		assert preferredWidths != null;
		assert preferredHeights != null;

		Insets inset      = parent.getInsets();
		int    numColumns = horizontalWeights.length;
		int    numRows    = numRows(components);

		int width  = Sizes.getTotalMinimum(preferredWidths);
		int height = Sizes.getTotalMinimum(preferredHeights, numRows);
		width += inset.left + inset.right + spacing * (numColumns - 1);
		height += inset.top + inset.bottom + spacing * (numRows - 1);
		return new Dimension(width, height);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Component[] components = parent.getComponents();

		recalculatePreferredSizes(components);
		assert preferredWidths != null;
		assert preferredHeights != null;

		Insets inset      = parent.getInsets();
		int    numColumns = horizontalWeights.length;
		int    numRows    = numRows(components);

		int width  = (int)Sizes.getTotalPreferred(preferredWidths);
		int height = (int)Sizes.getTotalPreferred(preferredHeights, numRows);
		width += inset.left + inset.right + spacing * (numColumns - 1);
		height += inset.top + inset.bottom + spacing * (numRows - 1);
		return new Dimension(width, height);
	}

	@Override
	public Dimension maximumLayoutSize(Container parent) {
		Component[] components = parent.getComponents();

		recalculatePreferredSizes(components);
		assert preferredWidths != null;
		assert preferredHeights != null;

		Insets inset      = parent.getInsets();
		int    numColumns = horizontalWeights.length;
		int    numRows    = numRows(components);

		int width;
		if (Sizes.getPriorityCount(preferredWidths, Priority.EXACT) == numColumns) {
			width = (int)Sizes.getPriorityPreferred(preferredWidths, Priority.EXACT);
			width += inset.left + inset.right + spacing * (numColumns - 1);
		} else {
			width = Integer.MAX_VALUE;
		}
		int height;
		if (Sizes.getPriorityCount(preferredHeights, numRows, Priority.EXACT) == numRows) {
			height = (int)Sizes.getPriorityPreferred(preferredHeights, numRows, Priority.EXACT);
			height += inset.top + inset.bottom + spacing * (numRows - 1);
		} else {
			height = Integer.MAX_VALUE;
		}

		return new Dimension(width, height);
	}

	@Override
	@SuppressWarnings("ConstantConditions") // No NPE when this class is thread-bound to EDT
	public void layoutContainer(Container parent) {
		Component[] components = parent.getComponents();
		if (components.length == 0) {
			return;
		}

		recalculatePreferredSizes(components);
		assert preferredWidths != null;
		assert preferredHeights != null;

		Dimension parentSize = parent.getSize();
		Insets    insets     = parent.getInsets();
		int       numColumns = horizontalWeights.length;
		int       numRows    = numRows(components);

		int availableWidth  = parentSize.width - insets.left - insets.right - spacing * (numColumns - 1);
		int availableHeight = parentSize.height - insets.top - insets.bottom - spacing * (numRows - 1);

		recalculateLayoutSizes(components, availableWidth, availableHeight);
		assert layoutWidths != null;
		assert layoutHeights != null;

		int col    = 0;
		int row    = 0;
		int x      = insets.left;
		int y      = insets.top;
		int height = (int)layoutHeights[row].getPreferred();
		for (Component component : components) {
			assert layoutWidths[col] != null;
			int width = (int)layoutWidths[col].getPreferred();
			component.setBounds(x, y, width, height);

			x += width + spacing;

			++col;
			if (col == numColumns) {
				col = 0;
				row = Math.min(row + 1, numRows - 1);

				x = insets.left;
				y += height + spacing;

				height = (int)layoutHeights[row].getPreferred();
			}
		}
	}

	@Override
	public float getLayoutAlignmentX(Container parent) {
		return 0.5f;
	}

	@Override
	public float getLayoutAlignmentY(Container parent) {
		return 0.5f;
	}

	@Override
	public void invalidateLayout(@Nullable Container parent) {
		preferredWidths  = null;
		preferredHeights = null;
		layoutWidths     = null;
		layoutHeights    = null;
	}

	private void recalculatePreferredSizes(Component[] components) {
		if (preferredWidths == null) {
			Size[][] preferredSizes = PreferredSizeCalculator.calculatePreferredSizes(components,
			                                                                          horizontalWeights,
			                                                                          verticalWeights);
			preferredWidths  = preferredSizes[0];
			preferredHeights = preferredSizes[1];
		}
	}

	@SuppressWarnings("AssignmentToStaticFieldFromInstanceMethod") // No problem when this class is thread-bound to EDT
	private void recalculateLayoutSizes(Component[] components, int availableWidth, int availableHeight) {
		if (layoutWidths == null || lastWidth != availableWidth || lastHeight != availableHeight) {
			int numRows = numRows(components);

			prepareLayoutWidths();
			prepareLayoutHeights(numRows);
			assert layoutWidths != null;
			assert layoutHeights != null;

			LayoutSizeCalculator.debug = debug == GridBagConstraints.HORIZONTAL;
			LayoutSizeCalculator.calculateLayoutSizes(layoutWidths, availableWidth);
			LayoutSizeCalculator.debug = debug == GridBagConstraints.VERTICAL;
			LayoutSizeCalculator.calculateLayoutSizes(layoutHeights, availableHeight);

			lastWidth  = availableWidth;
			lastHeight = availableHeight;
		}
	}

	private void prepareLayoutWidths() {
		assert preferredWidths != null;
		layoutWidths = Sizes.copy(preferredWidths);
	}

	private void prepareLayoutHeights(int numRows) {
		layoutHeights = new Size[numRows];

		assert preferredHeights != null;
		System.arraycopy(preferredHeights, 0, layoutHeights, 0, Math.min(preferredHeights.length, numRows));
		if (numRows > preferredHeights.length) {
			for (int i = preferredHeights.length; i < numRows; i++) {
				layoutHeights[i] = new Size(preferredHeights[preferredHeights.length - 1]);
			}
		}
	}

	private int numRows(Component[] components) {
		return (components.length + horizontalWeights.length - 1) / horizontalWeights.length;
	}

	public static String weightToString(Number weight) {
		if (weight.equals(PREFERRED)) {
			return "PREFERRED";
		} else if (weight.equals(MINIMUM)) {
			return "MINIMUM";
		} else {
			return weight.toString();
		}
	}
}
