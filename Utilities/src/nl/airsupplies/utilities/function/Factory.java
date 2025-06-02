package nl.airsupplies.utilities.function;

/**
 * @param <K> the type of the keys that this factory uses to determine the type of
 *            the element to instantiate
 * @param <E> the type of elements this factory can instantiate
 * @author Mark Jeronimus
 */
@FunctionalInterface
public interface Factory<K, E> {

	/**
	 * Instantiates an element based on the specified key
	 * <p>
	 *
	 * @param key the value to use when instantiating the instance
	 * @throws IllegalArgumentException if there is no instance type associated
	 *                                  with the specified key
	 * @throws NullPointerException     if the key is {@code null}
	 */
	E instantiateFor(K key);
}
