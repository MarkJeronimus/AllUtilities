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
 * A {@link Processor} is a {@link Node} that processes data.
 * <p>
 * A processor is stateless and has an 'injective' behavior: for every possible input and parameter combination, there
 * is only one possible output, but there is no guarantee that each possible output is unique (<i>e.g.</i> checksum
 * functions).
 * <p>
 * There is no restriction on the input and output types. They can be arrays to allow multiple parallel inputs and/or
 * outputs.
 *
 * @param <S> Source type of the object to process
 * @param <R> Result type of the processed object
 * @author Mark Jeronimus
 */
// Created 2014-04-11
// Changed 2014-11-23
// Changed 2016-03-23
public interface Processor<S, R> {
	/**
	 * Processes the input to produce an output.
	 *
	 * @param input the input value that the processor will process
	 * @return outputs. the output value that the processor generates depending on the input value
	 */
	R process(S input);
}
