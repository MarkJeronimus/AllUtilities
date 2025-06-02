package nl.airsupplies.utilities.collection;

import java.util.ArrayList;

import org.jetbrains.annotations.Nullable;

/**
 * @param <E> Type of element in the list
 * @author Mark Jeronimus
 */
// Created 2008-01-31
public class AutoGrowingArrayList<E> extends ArrayList<E> {
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
	public @Nullable E get(int i) {
		return i >= size() ? null : super.get(i);
	}
}
