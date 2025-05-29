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

package nl.airsupplies.utilities.collection;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Mark Jeronimus
 */
// Created 2017-06-25
public class LimitedArrayList<E> extends ArrayList<E> {
	private final int capacity;

	public LimitedArrayList(int capacity) {
		super(capacity);

		this.capacity = capacity;
	}

	@Override
	public boolean add(E e) {
		if (size() == capacity) {
			throw new IllegalStateException("List full");
		}

		return super.add(e);
	}

	@Override
	public void add(int index, E element) {
		if (size() == capacity) {
			throw new IllegalStateException("List full");
		}

		super.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean changed = false;
		for (E e : c) {
			changed |= add(e);
		}
		return changed;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		boolean changed = false;
		for (E e : c) {
			add(index, e);
			index++;
			changed = true;
		}
		return changed;
	}
}
