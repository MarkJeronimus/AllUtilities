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
