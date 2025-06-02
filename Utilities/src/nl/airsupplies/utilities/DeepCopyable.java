package nl.airsupplies.utilities;

/**
 * @author Mark Jeronimus
 */
// Created 2016-02-21
public interface DeepCopyable {
	/**
	 * Returns a deep copy of this object.
	 * <p>
	 * The deep copy should be functionally equivalent to the original but with it's state be completely decoupled.
	 * This allows the copies to be used with parallel processing.
	 * <p>
	 * Any non-primitive object contained in this object should be deep-copied or re-initialized. If this is not
	 * possible, as is the case with <em>e.g.</em>, resource handles, then the class should not be deep-copyable.
	 * <p>
	 * Any subclass of a non-abstract {@code DeepCopyable} object should override this method, and it should NOT call
	 * the overridden implementation {@code super.makeDeepCopy()}. Instead, all non-state fields of the super class
	 * that
	 * this class relies on should be copied here.
	 */
	DeepCopyable makeDeepCopy();
}
