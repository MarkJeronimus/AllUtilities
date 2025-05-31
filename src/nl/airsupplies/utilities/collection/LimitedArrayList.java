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
