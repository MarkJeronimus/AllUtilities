package nl.airsupplies.utilities.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * Almost identical copy of {@link Executors.DefaultThreadFactory} that allows
 * changing the prefix string.
 *
 * @author Mark Jeronimus
 */
// Created 2015-08-28
public class NamedThreadPool implements ThreadFactory {
	private final ThreadGroup   group;
	private final String        namePrefix;
	private final AtomicInteger threadNumber = new AtomicInteger(1);

	public NamedThreadPool(String prefix) {
		namePrefix = requireNonNull(prefix, "prefix");

		SecurityManager s = System.getSecurityManager();
		group = s != null ?
		        s.getThreadGroup() :
		        Thread.currentThread().getThreadGroup();
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
		if (t.isDaemon()) {
			t.setDaemon(false);
		}

		if (t.getPriority() != Thread.NORM_PRIORITY) {
			t.setPriority(Thread.NORM_PRIORITY);
		}

		return t;
	}
}
