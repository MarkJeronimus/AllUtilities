package nl.airsupplies.utilities.container;

import net.jcip.annotations.NotThreadSafe;

import nl.airsupplies.utilities.NumberUtilities;

/**
 * Collects {@code double} values and constructs a span that goes from the minimum value to the maximum value.
 *
 * @author Mark Jeronimus
 */
@NotThreadSafe
public final class SpanBuilder {
	private final Span defaultSpan;

	private double min = Double.POSITIVE_INFINITY;
	private double max = Double.NEGATIVE_INFINITY;

	/**
	 * @param defaultSpan the span to return when nothing is collected yet.
	 */
	public SpanBuilder(Span defaultSpan) {
		this.defaultSpan = defaultSpan;
	}

	public void collect(double value) {
		if (NumberUtilities.isDegenerate(value)) {
			return;
		}

		if (min > value) {
			min = value;
		}

		if (max < value) {
			max = value;
		}
	}

	public Span get() {
		try {
			return Span.getInstance(min, max);
		} catch (IllegalArgumentException ignored) {
			return defaultSpan;
		}
	}
}
