package nl.airsupplies.utilities.function;

import java.io.IOException;

/**
 * @param <T> Result of the operation
 * @author Mark Jeronimus
 */
// Created 2017-06-08
@FunctionalInterface
public interface IOOperation<T> {
	void operateOn(T t) throws IOException;
}
