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

/**
 * @author Mark Jeronimus
 */
// Created 2006-10-18
public class BidirectionalLinkedList {
	private int               size;
	private LinkedListElement head = null;
	private LinkedListElement tail = null;

	public LinkedListElement getHead() {
		return head;
	}

	public int size() {
		return size;
	}

	public void add(Object o) {
		LinkedListElement e = new LinkedListElement(o);
		if (size == 0) {
			head = tail = e.next = e.previous = e;
		} else {
			e.next = head;
			e.previous = tail;
			tail = tail.next = head.previous = e;
		}
		size++;
	}

	public void addBefore(LinkedListElement e, Object o) {
		LinkedListElement n = new LinkedListElement(o);
		n.next = e;
		n.previous = e.previous;
		e.previous.next = e.previous = n;

		size++;
	}
}
