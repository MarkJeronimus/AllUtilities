package nl.airsupplies.utilities.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.jetbrains.annotations.Nullable;

import static nl.airsupplies.utilities.validator.StringValidatorUtilities.requireStringNotEmpty;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * Wrapper for a thread factory to give threads a meaningful name.
 *
 * @author Mark Jeronimus
 */
// Created 2015-09-09
public class NamedThreadFactory implements ThreadFactory {
	private final @Nullable ThreadGroup   group;
	private final @Nullable ThreadFactory factory;
	private final @Nullable String        prefix;

	private boolean daemon = false;

	public NamedThreadFactory(String prefix) {
		this(Executors.defaultThreadFactory(), prefix);
	}

	public NamedThreadFactory(ThreadFactory factory, String prefix) {
		group        = null;
		this.factory = requireNonNull(factory, "factory");
		this.prefix  = requireStringNotEmpty(prefix, "prefix");
	}

	public NamedThreadFactory(ThreadGroup group) {
		this.group = requireNonNull(group, "group");
		factory    = null;
		prefix     = null;
	}

	public boolean isDaemon() {
		return daemon;
	}

	public void setDaemon(boolean daemon) {
		this.daemon = daemon;
	}

	@Override
	public Thread newThread(Runnable runnable) {
		Thread thread;

		if (group != null) {
			thread = new Thread(group, runnable);
			thread.setName(group.getName() + '-' + thread.getName());
		} else {
			assert factory != null;
			thread = factory.newThread(runnable);
			thread.setName(prefix + '-' + thread.getName());
		}

		thread.setDaemon(daemon);

		return thread;
	}
}
