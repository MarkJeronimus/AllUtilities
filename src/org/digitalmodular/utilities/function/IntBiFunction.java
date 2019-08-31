package org.digitalmodular.utilities.function;

/**
 * @param <R> the type of the result of the function
 * @author Zom-B
 */
// Created 2019-07-29
@FunctionalInterface
public interface IntBiFunction<R> {
	R apply(int value1, int value2);
}
