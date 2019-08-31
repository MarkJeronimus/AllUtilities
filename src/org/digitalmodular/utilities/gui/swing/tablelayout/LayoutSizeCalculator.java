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
package org.digitalmodular.utilities.gui.swing.tablelayout;

import org.digitalmodular.utilities.gui.swing.tablelayout.Size.Priority;
import static org.digitalmodular.utilities.gui.swing.tablelayout.Size.Priority.PREFERRED;
import static org.digitalmodular.utilities.gui.swing.tablelayout.Size.Priority.RELATIVE;

/**
 * @author Mark Jeronimus
 */
// Created 2017-02-10
public final class LayoutSizeCalculator {
	private LayoutSizeCalculator() { throw new AssertionError(); }

	public static boolean debug = true;

	public static void calculateLayoutSizes(Size[] layoutSizes, int containerSize) {
		int totalMinimum = Sizes.getTotalMinimum(layoutSizes);

		double totalPreferred = Sizes.getTotalPreferred(layoutSizes);

		if ((int)totalPreferred == containerSize) {
			if (debug)
				System.out.println("perfectSize");
		} else if (totalMinimum > containerSize) {
			squishSizes(layoutSizes, totalMinimum, containerSize);
		} else if (totalPreferred >= containerSize) {
			shrinkSizes(layoutSizes, totalPreferred, containerSize);
		} else {
			stretchSizes(layoutSizes, totalPreferred, containerSize);
		}

		roundSizes(layoutSizes, containerSize);
	}

	private static void roundSizes(Size[] layoutSizes, int containerPreferred) {
		double excess = containerPreferred;
		for (Size size : layoutSizes) {
			if (size.getPreferred() < 1) {
				excess += 1 - size.getPreferred();
				size.setPreferred(1);
			}
		}

		for (Size size : layoutSizes)
			if (size.getPreferred() > 1)
				size.setPreferred(size.getPreferred() * containerPreferred / excess);

		for (int i = 0; i < layoutSizes.length; i++) {
			Size size    = layoutSizes[i];
			int  rounded = (int)Math.round(size.getPreferred());
			if (i + 1 < layoutSizes.length)
				layoutSizes[i + 1].setPreferred(layoutSizes[i + 1].getPreferred() + size.getPreferred() - rounded);
			size.setPreferred(rounded);
		}
	}

	// Tier 1 (Resize entire row or column)

	private static void squishSizes(Size[] layoutSizes, double fromSize, int toSize) {
		if (debug)
			System.out.println("squishSizes");
		for (Size size : layoutSizes)
			size.setPreferred(size.getMinimum() * toSize / fromSize);
	}

	private static void shrinkSizes(Size[] layoutSizes, double fromSize, int toSize) {
		boolean shrinkRelativeSizes = Sizes.containsPriority(layoutSizes, RELATIVE);
		if (shrinkRelativeSizes)
			fromSize = shrinkRelativeSizes(layoutSizes, fromSize, toSize);

		boolean shrinkPreferredSizes = fromSize > toSize &&
		                               Sizes.containsPriority(layoutSizes, PREFERRED);
		if (shrinkPreferredSizes)
			shrinkPreferredSizes(layoutSizes, fromSize - toSize, PREFERRED);

		if (debug)
			if (shrinkRelativeSizes && shrinkPreferredSizes)
				System.out.println("shrinkRelativeSizes && shrinkPreferredSizes");
			else if (shrinkRelativeSizes)
				System.out.println("shrinkRelativeSizes ");
			else if (shrinkPreferredSizes)
				System.out.println("shrinkPreferredSizes");
			else
				System.out.println("shrinkSizes (nothing happened)");
	}

	private static void stretchSizes(Size[] layoutSizes, double fromSize, int toSize) {
		if (Sizes.containsPriority(layoutSizes, RELATIVE)) {
			if (debug)
				System.out.println("stretchRelativeSizes");
			stretchRelativeSizes(layoutSizes, toSize - fromSize);
		} else if (Sizes.containsPriority(layoutSizes, PREFERRED)) {
			if (debug)
				System.out.println("stretchPreferredSizes");
			stretchPreferredSizes(layoutSizes, toSize - fromSize, PREFERRED);
		} else if (Sizes.containsPriority(layoutSizes, Priority.MINIMUM)) {
			if (debug)
				System.out.println("stretchMinimumSizes");
			stretchPreferredSizes(layoutSizes, toSize - fromSize, Priority.MINIMUM);
		}
	}

	// Tier 2 (Resize specific priorities)

	private static double shrinkRelativeSizes(Size[] layoutSizes, double fromSize, int toSize) {
		double newStretchTotal = 0;
		for (Size size : layoutSizes)
			if (size.getPriority() == RELATIVE && size.getPreferred() > size.getMinimum())
				newStretchTotal += size.getStretchFactor();

		double newExcess = fromSize - toSize;
		while (newStretchTotal > 0 && newExcess >= 0.5) {
			double stretchTotal = newStretchTotal;
			double excess       = newExcess;
			for (Size size : layoutSizes) {
				int    minimum   = size.getMinimum();
				double preferred = size.getPreferred();
				if (size.getPriority() == RELATIVE && preferred > minimum) {
					double newPreferred = preferred - excess * size.getStretchFactor() / stretchTotal;
					newPreferred = Math.max(minimum, newPreferred);
					size.setPreferred(newPreferred);

					double sub = preferred - newPreferred;
					fromSize -= sub;
					newExcess -= sub;

					if (newPreferred == minimum)
						newStretchTotal -= size.getStretchFactor();
				}
			}

			if (debug)
				System.out.println(newStretchTotal + "\t" + newExcess);
		}

		return fromSize;
	}

	private static void shrinkPreferredSizes(Size[] layoutSizes, double excess, Priority priority) {
		double fromSize = 0;
		for (Size size : layoutSizes)
			if (size.getPriority() == priority && size.getPreferred() > size.getMinimum())
				fromSize += size.getPreferred();
		double toSize = fromSize - excess;

		for (Size size : layoutSizes) {
			double preferred = size.getPreferred();
			int    minimum   = size.getMinimum();
			if (size.getPriority() == priority && preferred > minimum) {
				preferred *= toSize / fromSize;
				double newPreferred = Math.max(minimum, preferred);
				size.setPreferred(newPreferred);
			}
		}
	}

	private static void stretchRelativeSizes(Size[] layoutSizes, double headroom) {
		double fromSize = Sizes.getPriorityPreferred(layoutSizes, RELATIVE);
		double toSize   = fromSize + headroom;

		for (Size size : layoutSizes)
			if (size.getPriority() == RELATIVE)
				size.setPreferred(size.getPreferred() * toSize / fromSize);
	}

	private static void stretchPreferredSizes(Size[] layoutSizes, double headroom, Priority priority) {
		double fromSize = Sizes.getPriorityPreferred(layoutSizes, priority);
		double toSize   = fromSize + headroom;

		for (Size size : layoutSizes)
			if (size.getPriority() == priority)
				size.setPreferred(size.getPreferred() * toSize / fromSize);
	}
}
