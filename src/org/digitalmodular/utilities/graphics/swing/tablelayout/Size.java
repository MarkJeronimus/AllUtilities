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

package org.digitalmodular.utilities.graphics.swing.tablelayout;

import static org.digitalmodular.utilities.NumberUtilities.clamp;
import static org.digitalmodular.utilities.graphics.swing.tablelayout.Size.Priority.EXACT;
import static org.digitalmodular.utilities.graphics.swing.tablelayout.Size.Priority.MINIMUM;
import static org.digitalmodular.utilities.graphics.swing.tablelayout.Size.Priority.PREFERRED;
import static org.digitalmodular.utilities.graphics.swing.tablelayout.Size.Priority.RELATIVE;

/**
 * @author Mark Jeronimus
 */
// Created 2017-02-10
public class Size {
	public enum Priority {
		EXACT,
		MINIMUM,
		PREFERRED,
		RELATIVE,
	}

	private final Priority priority;
	private       int      minimum;
	private       double   preferred;
	private final double   stretchFactor;

	public Size(Size other) {
		priority = other.priority;
		minimum = other.minimum;
		preferred = other.preferred;
		stretchFactor = other.stretchFactor;
	}

	private Size(Priority priority, int minimum, double preferred) {
		this(priority, minimum, preferred, 0);
	}

	private Size(Priority priority, int minimum, double preferred, double stretchFactor) {
		this.priority = priority;
		this.minimum = minimum;
		this.preferred = preferred;
		this.stretchFactor = stretchFactor;
	}

	/**
	 * Minimum and Preferred size is specified.<br>
	 * Cannot contract.<br>
	 * Cannot stretch.
	 */
	static Size newExactSize(int size) {
		return new Size(EXACT, size, size, size);
	}

	/**
	 * Minimum size is determined by components.<br>
	 * Cannot contract, as preferred size is kept equal to minimum size.<br>
	 * Can stretch, unless the layout also contains Preferred or Relative sizes.
	 */
	static Size newMinimumSize() {
		return new Size(MINIMUM, 0, 0);
	}

	/**
	 * Minimum size and preferred size are determined by components.<br>
	 * Can contract down to minimum size of components.<br>
	 * Can stretch, unless the layout also contains Relative sizes.
	 */
	static Size newPreferredSize() {
		return new Size(PREFERRED, 0, 0);
	}

	/**
	 * Preferred size is determined by components.<br>
	 * Can contract down to 0.<br>
	 * Can stretch.
	 */
	static Size newRelativeSize(double factor) {
		return new Size(RELATIVE, 0, factor, factor);
	}

	public Priority getPriority() {
		return priority;
	}

	public int getMinimum() {
		return minimum;
	}

	public double getPreferred() {
		return preferred;
	}

	public void setPreferred(double preferred) {
		this.preferred = preferred;
	}

	public double getStretchFactor() {
		return stretchFactor;
	}

	public void recordComponent(int preferredSize, int minimumSize) {
		switch (priority) {
			case EXACT:
				break;
			case MINIMUM:
				minimum = Math.max(minimum, minimumSize);
				preferred = minimum;
				break;
			case PREFERRED:
			case RELATIVE:
				minimum = Math.max(minimum, minimumSize);
				preferred = clamp(preferredSize, 1, preferred);
				break;
		}
	}

	@Override
	public String toString() {
		return switch (priority) {
			case EXACT -> "ExactSize    {priority=" + priority + ", " + preferred + " (minimum=" + minimum + ")}";
			case MINIMUM -> "MinimumSize  {priority=" + priority + ", " + preferred + " (minimum=" + minimum + ")}";
			case PREFERRED -> "PreferredSize{priority=" + priority + ", " + preferred + " (minimum=" + minimum + ")}";
			case RELATIVE -> "RelativeSize {priority=" + priority + ", " + preferred + " (minimum=" + minimum + ")}" +
			                 ", stretchFactor=" + stretchFactor + '}';
		};
	}
}
