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

import org.digitalmodular.utilities.annotation.UtilityClass;
import org.digitalmodular.utilities.gui.swing.tablelayout.Size.Priority;

/**
 * @author Mark Jeronimus
 */
// Created 2017-02-12
@UtilityClass
public final class Sizes {
	private Sizes() { throw new AssertionError(); }

	public static Size[] copy(Size[] componentSizes) {
		Size[] sizes = new Size[componentSizes.length];
		for (int i = 0; i < componentSizes.length; i++)
			sizes[i] = new Size(componentSizes[i]);
		return sizes;
	}

	public static boolean containsPriority(Size[] sizes, Priority priority) {
		for (Size size : sizes)
			if (size.getPriority() == priority)
				return true;
		return false;
	}

	public static boolean containsPriority(Size[] sizes, int length, Priority priority) {
		for (int i = 0; i < sizes.length; i++)
			if (sizes[Math.min(i, sizes.length - 1)].getPriority() == priority)
				return true;
		return false;
	}

	public static int getPriorityCount(Size[] sizes, Priority priority) {
		int count = 0;
		for (Size size : sizes)
			if (size.getPriority() == priority)
				count++;
		return count;
	}

	public static int getPriorityCount(Size[] sizes, int length, Priority priority) {
		int count = 0;
		for (int i = 0; i < sizes.length; i++)
			if (sizes[Math.min(i, sizes.length - 1)].getPriority() == priority)
				count++;
		return count;
	}

	public static int getTotalMinimum(Size[] sizes) {
		int totalMinimum = 0;
		for (Size size : sizes)
			totalMinimum += size.getMinimum();
		return totalMinimum;
	}

	public static int getTotalMinimum(Size[] sizes, int length) {
		int totalMinimum = 0;
		for (int i = 0; i < length; i++)
			totalMinimum += sizes[Math.min(i, sizes.length - 1)].getMinimum();
		return totalMinimum;
	}

	public static double getTotalPreferred(Size[] sizes) {
		double totalPreferred = 0;
		for (Size size : sizes)
			totalPreferred += size.getPreferred();
		return totalPreferred;
	}

	public static double getTotalPreferred(Size[] sizes, int length) {
		double totalPreferred = 0;
		for (int i = 0; i < length; i++)
			totalPreferred += sizes[Math.min(i, sizes.length - 1)].getPreferred();
		return totalPreferred;
	}

	public static double getTotalStretchFactor(Size[] sizes) {
		double totalStretch = 0;
		for (Size size : sizes)
			totalStretch += size.getStretchFactor();
		return totalStretch;
	}

	public static double getTotalStretchFactor(Size[] sizes, int length) {
		double totalStretch = 0;
		for (int i = 0; i < sizes.length; i++)
			totalStretch += sizes[Math.min(i, sizes.length)].getStretchFactor();
		return totalStretch;
	}

	public static double getPriorityPreferred(Size[] sizes, Priority priority) {
		double totalPreferred = 0;
		for (Size size : sizes)
			if (size.getPriority() == priority)
				totalPreferred += size.getPreferred();
		return totalPreferred;
	}

	public static double getPriorityPreferred(Size[] sizes, int length, Priority priority) {
		double totalPreferred = 0;
		for (int i = 0; i < length; i++)
			if (sizes[i].getPriority() == priority)
				totalPreferred += sizes[Math.min(i, sizes.length - 1)].getPreferred();
		return totalPreferred;
	}
}
