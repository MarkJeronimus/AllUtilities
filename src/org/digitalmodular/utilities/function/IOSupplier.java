package org.digitalmodular.utilities.function;

import java.io.IOException;

/**
 * @author Mark Jeronimus
 */
// Created 2021-09-26
public interface IOSupplier<R> {
	R execute() throws IOException, InterruptedException;
}
