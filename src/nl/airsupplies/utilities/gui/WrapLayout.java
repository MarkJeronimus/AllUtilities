package nl.airsupplies.utilities.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.intellij.lang.annotations.MagicConstant;

/**
 * FlowLayout subclass that fully supports wrapping of components.
 *
 * @author Rob Camick
 */
// Downloaded 2021-03-25 (also cleaned up)
public class WrapLayout extends FlowLayout {
	/**
	 * Constructs a new {@code WrapLayout} with a left
	 * alignment and a default 5-unit horizontal and vertical gap.
	 */
	public WrapLayout() {
	}

	/**
	 * Constructs a new {@code FlowLayout} with the specified
	 * alignment and a default 5-unit horizontal and vertical gap.
	 * The value of the alignment argument must be one of
	 * {@code WrapLayout}, {@code WrapLayout},
	 * or {@code WrapLayout}.
	 *
	 * @param align the alignment value
	 */
	public WrapLayout(@MagicConstant(intValues = {LEFT, CENTER, RIGHT, LEADING, TRAILING}) int align) {
		super(align);
	}

	/**
	 * Creates a new flow layout manager with the indicated alignment
	 * and the indicated horizontal and vertical gaps.
	 * <p>
	 * The value of the alignment argument must be one of
	 * {@code WrapLayout}, {@code WrapLayout},
	 * or {@code WrapLayout}.
	 *
	 * @param align         the alignment value
	 * @param horizontalGap the horizontal gap between components
	 * @param verticalGap   the vertical gap between components
	 */
	public WrapLayout(@MagicConstant(intValues = {LEFT, CENTER, RIGHT, LEADING, TRAILING}) int align,
	                  int horizontalGap,
	                  int verticalGap) {
		super(align, horizontalGap, verticalGap);
	}

	/**
	 * Returns the preferred dimensions for this layout given the
	 * <i>visible</i> components in the specified target container.
	 *
	 * @param target the component which needs to be laid out
	 * @return the preferred dimensions to lay out the
	 * subcomponents of the specified container
	 */
	@Override
	public Dimension preferredLayoutSize(Container target) {
		return layoutSize(target, true);
	}

	/**
	 * Returns the minimum dimensions needed to layout the <i>visible</i>
	 * components contained in the specified target container.
	 *
	 * @param target the component which needs to be laid out
	 * @return the minimum dimensions to lay out the
	 * subcomponents of the specified container
	 */
	@Override
	public Dimension minimumLayoutSize(Container target) {
		Dimension minimum = layoutSize(target, false);
		minimum.width -= (getHgap() + 1);
		return minimum;
	}

	/**
	 * Returns the minimum or preferred dimension needed to layout the target
	 * container.
	 *
	 * @param target    target to get layout size for
	 * @param preferred should preferred size be calculated
	 * @return the dimension to layout the target container
	 */
	private Dimension layoutSize(Container target, boolean preferred) {
		synchronized (target.getTreeLock()) {
			//  Each row must fit with the width allocated to the container.
			//  When the container width = 0, the preferred width of the container
			//  has not yet been calculated so lets ask for the maximum.

			Container container = target;

			while (container.getSize().width == 0 && container.getParent() != null) {
				container = container.getParent();
			}

			int targetWidth = container.getSize().width;

			if (targetWidth == 0) {
				targetWidth = Integer.MAX_VALUE;
			}

			int    horizontalGap          = getHgap();
			int    verticalGap            = getVgap();
			Insets insets                 = target.getInsets();
			int    horizontalInsetsAndGap = insets.left + insets.right + (horizontalGap * 2);
			int    maxWidth               = targetWidth - horizontalInsetsAndGap;

			//  Fit components into the allowed width

			Dimension dim       = new Dimension(0, 0);
			int       rowWidth  = 0;
			int       rowHeight = 0;

			int nMembers = target.getComponentCount();

			for (int i = 0; i < nMembers; i++) {
				Component m = target.getComponent(i);

				if (m.isVisible()) {
					Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();

					//  Can't add the component to current row. Start a new row.

					if (rowWidth + d.width > maxWidth) {
						addRow(dim, rowWidth, rowHeight);
						rowWidth  = 0;
						rowHeight = 0;
					}

					//  Add a horizontal gap for all components after the first

					if (rowWidth != 0) {
						rowWidth += horizontalGap;
					}

					rowWidth += d.width;
					rowHeight = Math.max(rowHeight, d.height);
				}
			}

			addRow(dim, rowWidth, rowHeight);

			dim.width += horizontalInsetsAndGap;
			dim.height += insets.top + insets.bottom + verticalGap * 2;

			//	When using a scroll pane or the DecoratedLookAndFeel we need to
			//  make sure the preferred size is less than the size of the
			//  target container so shrinking the container size works
			//  correctly. Removing the horizontal gap is an easy way to do this.

			Container scrollPane = SwingUtilities.getAncestorOfClass(JScrollPane.class, target);

			if (scrollPane != null && target.isValid()) {
				dim.width -= (horizontalGap + 1);
			}

			return dim;
		}
	}

	/*
	 *  A new row has been completed. Use the dimensions of this row
	 *  to update the preferred size for the container.
	 *
	 *  @param dim update the width and height when appropriate
	 *  @param rowWidth the width of the row to add
	 *  @param rowHeight the height of the row to add
	 */
	private void addRow(Dimension dim, int rowWidth, int rowHeight) {
		dim.width = Math.max(dim.width, rowWidth);

		if (dim.height > 0) {
			dim.height += getVgap();
		}

		dim.height += rowHeight;
	}
}
