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

package nl.airsupplies.utilities.nodes;

/**
 * An {@link Accumulator} is a {@link Node} that can accumulate multiple inputs before generating an output.
 *
 * @param <S> Source type of the objects to accumulate
 * @param <R> Result type of the accumulator
 * @author Mark Jeronimus
 */
// Created 2014-04-15
// Changed 2014-11-23
// Changed 2016-03-23
public interface Accumulator<S, R> {
	/**
	 * Accumulates the specified input into the pages buffer.
	 */
	void accumulate(S input);

	/**
	 * Creates the (intermediate) results using the accumulated values.
	 */
	R getResult();

	/**
	 * Resets the state to that after construction.
	 */
	void reset();
}
