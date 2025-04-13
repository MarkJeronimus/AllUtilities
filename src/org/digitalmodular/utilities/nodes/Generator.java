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

package org.digitalmodular.utilities.nodes;

/**
 * An {@link Generator} is a {@link Node} that generates a sequence of outputs.
 *
 * @param <S> Source type used to initialize the generator
 * @param <R> Result type of the generated objects
 * @author Mark Jeronimus
 */
// Created 2014-11-23
// Changed 2016-03-23
public interface Generator<S, R> {
	/**
	 * Resets the pages state of this node to it's initial state.
	 */
	void initialize(S input);

	/**
	 * Returns the next output in the sequence.
	 */
	R generate();
}
