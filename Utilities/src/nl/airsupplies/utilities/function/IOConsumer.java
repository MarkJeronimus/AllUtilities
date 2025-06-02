package nl.airsupplies.utilities.function;

import java.io.IOException;

/**
 * @param <T> Result of the operation
 * @author Mark Jeronimus
 */
// Created 2017-06-08
// Changed 2018-02-26 Renamed from IOOperation
@FunctionalInterface
public interface IOConsumer<T> {
	void consume(T t) throws IOException;
}
