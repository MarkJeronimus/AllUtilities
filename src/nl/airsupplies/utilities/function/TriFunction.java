package nl.airsupplies.utilities.function;

/**
 * @param <T> the type of the first argument to the function
 * @param <T> the type of the second argument to the function
 * @param <T> the type of the third argument to the function
 * @param <R> the type of the result of the function
 * @author Mark Jeronimus
 */
// Created 2024-08-03
@FunctionalInterface
public interface TriFunction<T, U, V, R> {
	R apply(T t, U u, V v);
}
