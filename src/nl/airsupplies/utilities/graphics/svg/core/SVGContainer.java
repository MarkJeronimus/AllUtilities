/*
 * This file is part of PAO.
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package nl.airsupplies.utilities.graphics.svg.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2018-01-26
public abstract class SVGContainer extends SVGElement implements Iterable<SVGElement> {
	private final List<SVGElement> elements;

	protected SVGContainer(String tagName, int initialCapacity) {
		super(tagName);

		elements = new ArrayList<>(requireAtLeast(1, initialCapacity, "initialCapacity"));
	}

	public int size() {
		return elements.size();
	}

	public boolean add(SVGElement element) {
		return elements.add(requireNonNull(element, "element"));
	}

	public boolean addAll(Collection<? extends SVGElement> elements) {
		return this.elements.addAll(requireNonNull(elements, "elements"));
	}

	public void clear() {
		elements.clear();
	}

	public SVGElement get(int index) {
		return elements.get(index);
	}

	public SVGElement set(int index, SVGElement element) {
		return elements.set(index, requireNonNull(element, "element"));
	}

	public void add(int index, SVGElement element) {
		elements.add(index, requireNonNull(element, "element"));
	}

	public SVGElement remove(int index) {
		return elements.remove(index);
	}

	public void removeEmptyElements() {
		for (Iterator<SVGElement> iterator = elements.iterator(); iterator.hasNext(); ) {
			SVGElement element = iterator.next();

			if (element instanceof SVGContainer container) {
				container.removeEmptyElements();

				if (container.isEmpty()) {
					iterator.remove();
				}
			}
		}
	}

	public boolean isEmpty() {
		return elements.isEmpty();
	}

	@Override
	protected boolean hasBody() {
		return !isEmpty();
	}

	@Override
	protected void encodeBody(Appendable out, int indentation) throws IOException {
		for (SVGElement element : this) {
			element.encode(out, indentation);
		}
	}

	@Override
	public Iterator<SVGElement> iterator() {
		return elements.iterator();
	}
}
