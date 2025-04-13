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

package org.digitalmodular.utilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.digitalmodular.utilities.annotation.UtilityClass;

/**
 * @author Mark Jeronimus
 */
// Created 2017-02-10
@UtilityClass
public final class IteratorUtilities {
	public static <T> List<T> toList(Iterator<T> iterator, int expectedCapacity) {
		List<T> list = new ArrayList<>(expectedCapacity);
		while (iterator.hasNext()) {
			list.add(iterator.next());
		}
		return list;
	}
}
