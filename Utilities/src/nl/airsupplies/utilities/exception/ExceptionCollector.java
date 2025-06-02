package nl.airsupplies.utilities.exception;

import org.jetbrains.annotations.Nullable;

/**
 * @author Mark Jeronimus
 */
// Created 2016-12-22
public class ExceptionCollector<E extends Exception> {
	private @Nullable E rootException = null;

	public void collect(E exception) {
		if (rootException == null) {
			rootException = exception;
		} else {
			rootException.addSuppressed(exception);
		}
	}

	public boolean hasExceptions() {
		return rootException != null;
	}

	public void throwCollectedExceptions() throws E {
		if (rootException != null) {
			throw rootException;
		}
	}
}
