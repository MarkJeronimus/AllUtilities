package nl.airsupplies.utilities.concurrent;

import java.util.concurrent.ThreadFactory;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * Wrapper for a thread factory to give threads a meaningful name.
 *
 * @author Mark Jeronimus
 */
// Created 2015-09-09
public class NamedThreadFactory implements ThreadFactory {
	private final ThreadFactory factory;
	private final String        prefix;

	public NamedThreadFactory(ThreadFactory factory, String prefix) {
		this.factory = requireNonNull(factory, "factory");
		this.prefix  = requireNonNull(prefix, "prefix");
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t = factory.newThread(r);
		t.setName(prefix + '-' + t.getName());
		return t;
	}
}
