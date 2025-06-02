package nl.airsupplies.utilities.concurrent;

import java.util.concurrent.Future;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.exception.ExceptionManager;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * A timer implemented using the executor framework, that implements a restartable timer.
 * <p>
 * When restarting the timer, the old timeout is discarded and the new timeout is applied relative to the current time.
 * It also implements an atomic way of starting the timer only if it is not already active.
 *
 * @author Mark Jeronimus
 */
// Created 2020-01-23
@ThreadSafe
public class RestartableTimer {
	private final SingleWorkerExecutor executor;
	private final Runnable             elapsedCallback;

	@GuardedBy("executor")
	private @Nullable Future<?> future = null;

	public RestartableTimer(Runnable elapsedCallback, String threadName) {
		executor             = new SingleWorkerExecutor(threadName);
		this.elapsedCallback = requireNonNull(elapsedCallback, "elapsedCallback");
	}

	/**
	 * If the Watchdog is idle, it will start as if by calling {@link #restart(long)} and returns {@code true}.
	 * If however the Watchdog is already active, it will return {@code false} and the old timeout will stay in effect.
	 * This is an atomic replacement for:<pre>
	 * if (isActive()) {
	 *     return false;
	 * } else {
	 *     restart(newTimeoutMillis);
	 *     return true;
	 * }</pre>
	 */
	public boolean startIfInactive(long newTimeoutMillis) {
		synchronized (executor) {
			if (executor.isShutdown()) {
				throw new IllegalStateException("Already shut down");
			}

			if (future != null) {
				return false;
			}

			restart(newTimeoutMillis);
			return true;
		}
	}

	/**
	 * @return {@code true} if the timer was active before restarting
	 */
	public boolean restart(long newTimeoutMillis) {
		synchronized (executor) {
			if (executor.isShutdown()) {
				throw new IllegalStateException("Already shut down");
			}

			boolean wasActive = stop();

			future = executor.schedule(this::elapsed, newTimeoutMillis);
			return wasActive;
		}
	}

	/**
	 * @return {@code true} if the timer is active
	 */
	public boolean isActive() {
		synchronized (executor) {
			if (executor.isShutdown()) {
				return false;
			}

			return future != null;
		}
	}

	private void elapsed() {
		try {
			synchronized (executor) {
				if (executor.isShutdown()) {
					return;
				}

				future = null;
				elapsedCallback.run();
			}
		} catch (Throwable th) {
			ExceptionManager.getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), th);
		}
	}

	/**
	 * @return {@code true} if the timer was active before stopping
	 */
	public boolean stop() {
		synchronized (executor) {
			if (executor.isShutdown()) {
				return false;
			}

			@Nullable Future<?> future = this.future;
			if (future == null) {
				return false;
			}

			future.cancel(true);
			return true;
		}
	}

	/**
	 * @return {@code true} if the timer was not previously shut down
	 */
	public boolean shutdown() {
		synchronized (executor) {
			if (executor.isShutdown()) {
				return false;
			}

			@Nullable Future<?> future = this.future;
			if (future != null) {
				future.cancel(true);
			}

			executor.shutdownNow();
			return true;
		}
	}
}
