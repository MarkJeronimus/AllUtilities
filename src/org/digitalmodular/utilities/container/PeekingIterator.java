package org.digitalmodular.utilities.container;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;

import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2025-05-04 by Claude 3.7 Sonnet
@SuppressWarnings("DataFlowIssue") // False positive. `element` *can* be null, but only when E contains @Nullable.
public class PeekingIterator<E> implements Iterator<E> {
	private final List<E> tokens;
	private       int     position = 0;

	private @Nullable E       nextElement = null;
	private           boolean hasNext;

	public PeekingIterator(List<E> tokens) {
		this.tokens = requireNonNull(tokens, "tokens");
		advance();
	}

	private void advance() {
		hasNext = position < tokens.size();

		if (hasNext) {
			nextElement = tokens.get(position++);
		}
	}

	@Override
	public boolean hasNext() {
		return hasNext;
	}

	@Override
	public E next() {
		if (!hasNext) {
			throw new NoSuchElementException();
		}

		E element = nextElement;
		advance();
		return element;
	}

	public E peek() {
		if (!hasNext) {
			throw new NoSuchElementException();
		}

		return nextElement;
	}

	@Override
	public String toString() {
		return tokens.subList(position, tokens.size()).stream().map(Object::toString).collect(Collectors.joining());
	}
}
