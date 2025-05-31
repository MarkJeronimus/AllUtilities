package nl.airsupplies.utilities.concurrent;

import nl.airsupplies.utilities.exception.ExceptionManager;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

// @Formatter:off

/**
 * Wraps a given {@link Runnable} task and guarantees that the interval between calls to that task is at least equal
 * to the specified interval. This prevents congestion in standard scheduled executors, which collect 'overdue' calls
 * and then rapidly 'catch up'.
 * <p>
 * Typical use case:
 * <pre>
 *     long interval = 1000;
 *     ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(0, threadFactory);
 *     executor.setRemoveOnCancelPolicy(true);
 *     Runnable task = this::run;
 *     task = new NoCongestionExecutorTask(task, interval - delta);
 *     ScheduledFuture<?> future = executor.scheduleAtFixedRate(task, interval, interval, MILLISECONDS);
 * </pre>
 * Here, {@code delta} should be a positive number to prevent a race conditions where the next invocation happens at
 * nearly the same time as the previous interval would expire, and every other invocation is skipped. It should be
 * small in comparison to interval to prevent 'catch up' calls from being executed closer together than {@code
 * interval - delta}. (Setting {@code delta >= interval} will effectively be the same as not using this wrapper.)
 * Ideally {@code delta} should be equal to the largest clock jitter that can be observed between this class and the
 * scheduled executor. A value of at least 10ms is recommended in practice.
 *
 * @author Mark Jeronimus
 */
// Created 2018-01-29 Extracted from project
// Changed 2019-09-02 Remove hardcoded catch blocks (let implementer add catch blocks in wrapped task)
// TODO rename to NoCongestionRunnable
public final class NoCongestionExecutorTask implements Runnable {
	private final Object lock = new Object();

	private final Runnable task;
	private final double   minIntervalMillis;

	private long lastExecutionTime = 0;

	public NoCongestionExecutorTask(Runnable task, long minIntervalMillis) {
		this.task = requireNonNull(task, "task");

		this.minIntervalMillis = requireAtLeast(1, minIntervalMillis, "minIntervalMillis");

		// Guarantee first execution is not skipped
		lastExecutionTime = Math.floorDiv(System.nanoTime(), 1_000_000L) - minIntervalMillis;
	}

	@Override
	public void run() {
		try {
			long now = Math.floorDiv(System.nanoTime(), 1_000_000L);

			// Skip this execution if the time window of the previous execution has not yet elapsed.
			synchronized (lock) {
				boolean withinPreviousExecutionWindow = now < lastExecutionTime + minIntervalMillis;
				if (withinPreviousExecutionWindow) {
					return;
				}

				lastExecutionTime = now;
			}

			task.run();
		} catch (Throwable th) {
			ExceptionManager.getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), th);
		}
	}
}
