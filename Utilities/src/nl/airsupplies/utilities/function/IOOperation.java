package nl.airsupplies.utilities.function;

import java.io.IOException;

/**
 * @author Mark Jeronimus
 */
// Created 2018-02-26
@FunctionalInterface
public interface IOOperation {
	void call() throws IOException;
}