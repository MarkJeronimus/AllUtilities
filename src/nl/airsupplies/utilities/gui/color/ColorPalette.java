package nl.airsupplies.utilities.gui.color;

import java.awt.Color;

/**
 * A {@link ColorPalette} returns {@link Color}s when given an index. The index
 * is a non-negative integer and can be arbitrarily large. An implementation
 * with a bounded number of colors shall wrap around when the index is larger
 * than the palette size, to ensure even very large numbers generate meaningful
 * colors.
 *
 * @author Mark Jeronimus
 */
// Created 2014-11-27
public interface ColorPalette {
	/**
	 * Get a {@link Color} from the palette.
	 *
	 * @param index A non-negative palette index
	 * @throws IndexOutOfBoundsException If index is negative (implementations may decide to return a special color
	 *                                   instead)
	 */
	Color getColor(int index);
}
