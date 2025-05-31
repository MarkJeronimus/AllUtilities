package nl.airsupplies.utilities.nodes;

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
