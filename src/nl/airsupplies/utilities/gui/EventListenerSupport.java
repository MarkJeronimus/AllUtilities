package nl.airsupplies.utilities.gui;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @param <P> The type of the parameter to pass to the listener method
 * @author Mark Jeronimus
 */
// Created 2022-06-29
public class EventListenerSupport<P> {
	private final Set<Consumer<P>> measurementSetReplacedListeners = new CopyOnWriteArraySet<>();

	public void add(Consumer<P> listener) {
		requireNonNull(listener, "listener");

		measurementSetReplacedListeners.add(listener);
	}

	public void remove(Consumer<P> listener) {
		requireNonNull(listener, "listener");

		measurementSetReplacedListeners.remove(listener);
	}

	public void fire(@Nullable P parameter) {
		@Nullable RuntimeException thrown = null;

		for (Consumer<P> listener : measurementSetReplacedListeners) {
			try {
				listener.accept(parameter);
			} catch (RuntimeException ex) {
				if (thrown == null) {
					thrown = ex;
				} else {
					thrown.addSuppressed(ex);
				}
			}
		}

		if (thrown != null) {
			//noinspection ProhibitedExceptionThrown
			throw thrown;
		}
	}
}
