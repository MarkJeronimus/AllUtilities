package nl.airsupplies.utilities.function;

import java.util.function.Consumer;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * Represents an operation that accepts two {@code long}-valued arguments and returns no result.  This is the
 * two-arity specialization of {@link Consumer} for {@code long}.  Unlike most other functional interfaces, {@code
 * LongBiConsumer} is expected to operate via side-effects.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(long, long)}.
 *
 * @author Mark Jeronimus
 * @see Consumer
 * @since 1.8
 */
// Created 2018-10-03 Combined from both BiConsumer and LongConsumer
public interface LongBiConsumer {
	/**
	 * Performs this operation on the given arguments.
	 *
	 * @param l the first input argument
	 * @param r the second input argument
	 */
	void accept(long l, long r);

	/**
	 * Returns a composed {@code LongBiConsumer} that performs, in sequence, this operation followed by the {@code
	 * after} operation. If performing either operation throws an exception, it is relayed to the caller of the
	 * composed operation.  If performing this operation throws an exception, the {@code after} operation will not be
	 * performed.
	 *
	 * @param after the operation to perform after this operation
	 * @return a composed {@code LongBiConsumer} that performs in sequence this operation followed by the {@code
	 * after} operation
	 * @throws NullPointerException if {@code after} is null
	 */
	default LongBiConsumer andThen(LongBiConsumer after) {
		requireNonNull(after, "after");
		return (l, r) -> {
			accept(l, r);
			after.accept(l, r);
		};
	}
}
