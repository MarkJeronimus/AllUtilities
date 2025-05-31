package nl.airsupplies.utilities.collection;

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
