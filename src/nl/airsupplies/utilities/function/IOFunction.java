package nl.airsupplies.utilities.function;

import java.io.IOException;

/**
 * @param <T> Type of the parameter
 * @param <R> Type of the return value
 * @author Mark Jeronimus
 */
// Created date  Copied from IOConsumer
@FunctionalInterface
public interface IOFunction<T, R> {
	R apply(T t) throws IOException;
}
