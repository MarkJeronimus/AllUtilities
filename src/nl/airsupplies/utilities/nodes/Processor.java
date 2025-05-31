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
