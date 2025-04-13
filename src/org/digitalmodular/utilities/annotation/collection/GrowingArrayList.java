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

package org.digitalmodular.utilities.annotation.collection;

import java.util.ArrayList;

/**
 * @author Mark Jeronimus
 */
// Created 2008-01-31
public class GrowingArrayList<E> extends ArrayList<E> {
	@Override
	public E set(int index, E element) {
		for (int i = size(); i <= index; i++) {
			add(null);
		}
		return super.set(index, element);
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public E get(int i) {
		return i >= size() ? null : super.get(i);
	}
}
