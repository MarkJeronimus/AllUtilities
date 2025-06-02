package nl.airsupplies.utilities.function;

/**
 * @param <T> the type of the first argument to the function (the second one is an {@code int})
 * @param <R> the type of the result of the function
 * @author Mark Jeronimus
 */
// Created 2019-07-29
@FunctionalInterface
public interface ObjIntFunction<T, R> {
	R apply(T t, int value);
}
