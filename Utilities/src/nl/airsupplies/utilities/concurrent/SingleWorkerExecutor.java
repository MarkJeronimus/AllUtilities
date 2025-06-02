package nl.airsupplies.utilities.concurrent;

import java.security.PrivilegedAction;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import static nl.airsupplies.utilities.validator.StringValidatorUtilities.requireStringNotEmpty;

/**
 * Wrapper for easy access to scheduled executors.
 * <p>
 * Please note that the threads used for the runners <strong>don't</strong> have an <em>uncaught exception handler</em>,
 * because this is intrinsically incompatible with repeatable tasks, each of which could throw. Therefore, the tasks
 * handed to this <strong>must</strong> catch {@link Throwable}. Catching {@link Exception} is not enough as any
 * uncaught exception will be silently swallowed.
 *
 * @author Mark Jeronimus
 */
// Created 2017-01-05
public class SingleWorkerExecutor {
	private final ScheduledExecutorService service;

	public SingleWorkerExecutor(String threadName) {
		ThreadFactory               threadFactory = new SingleNameThreadFactory(threadName);
		ScheduledThreadPoolExecutor executor      = new ScheduledThreadPoolExecutor(0, threadFactory);
		executor.setMaximumPoolSize(1);
		executor.setKeepAliveTime(1, SECONDS);

		// Prevent memory leak (see e.g. http://stackoverflow.com/a/23370049/1052284)
		executor.setRemoveOnCancelPolicy(true);

		service = Executors.unconfigurableScheduledExecutorService(executor);
	}

	/**
	 * Submits a value-returning task for execution and returns a Future representing the pending results of the task.
	 * The Future's {@code get} method will return the task's result upon successful completion.
	 * <p>
	 * If you would like to immediately block waiting for a task, you can use constructions of the form {@code result
	 * = exec.submit(aCallable).get();}
	 * <p>
	 * Note: The {@link Executors} class includes a set of methods that can convert some other common closure-like
	 * objects, for example, {@link PrivilegedAction} to {@link Callable} form so they can be submitted.
	 *
	 * @param task the task to submit
	 * @param <T>  the type of the task's result
	 * @return a Future representing pending completion of the task
	 * @throws RejectedExecutionException if the task cannot be scheduled for execution
	 * @throws NullPointerException       if the task is null
	 */
	public <T> Future<T> submit(Callable<T> task) {
		try {
			return service.submit(task);
		} catch (RejectedExecutionException ex) {
			throw new IllegalStateException("Executor still busy or already shut down", ex);
		}
	}

	/**
	 * Submits a Runnable task for execution and returns a Future representing that task. The Future's {@code get}
	 * method will return {@code null} upon <em>successful</em> completion.
	 *
	 * @param task the task to submit
	 * @return a Future representing pending completion of the task
	 * @throws RejectedExecutionException if the task cannot be scheduled for execution
	 * @throws NullPointerException       if the task is null
	 */
	public Future<?> submit(Runnable task) {
		try {
			return service.submit(task);
		} catch (RejectedExecutionException ex) {
			throw new RejectedExecutionException("Executor still busy or already shut down", ex);
		}
	}

	/**
	 * Creates and executes a one-shot action that becomes enabled after the given delay.
	 *
	 * @param command     the task to execute
	 * @param delayMillis the time from now to delay execution
	 * @return a ScheduledFuture representing pending completion of the task and whose {@code get()} method will
	 * return {@code null} upon completion
	 * @throws RejectedExecutionException if the task cannot be scheduled for execution
	 * @throws NullPointerException       if command is null
	 */
	public ScheduledFuture<?> schedule(Runnable command, long delayMillis) {
		try {
			return service.schedule(command, delayMillis, MILLISECONDS);
		} catch (RejectedExecutionException ex) {
			throw new RejectedExecutionException("Executor still busy or already shut down", ex);
		}
	}

	/**
	 * Creates and executes a ScheduledFuture that becomes enabled after the given delay.
	 *
	 * @param callable    the function to execute
	 * @param delayMillis the time from now to delay execution
	 * @param <V>         the type of the callable's result
	 * @return a ScheduledFuture that can be used to extract result or cancel
	 * @throws RejectedExecutionException if the task cannot be scheduled for execution
	 * @throws NullPointerException       if callable is null
	 */
	public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delayMillis) {
		try {
			return service.schedule(callable, delayMillis, MILLISECONDS);
		} catch (RejectedExecutionException ex) {
			throw new RejectedExecutionException("Executor still busy or already shut down", ex);
		}
	}

	/**
	 * Creates and executes a periodic action that becomes enabled first after the given initial delay, and
	 * subsequently with the given period; that is executions will commence after {@code initialDelay} then {@code
	 * initialDelay+period}, then {@code initialDelay + 2 * period}, and so on. If any execution of the task
	 * encounters an exception, subsequent executions are suppressed. Otherwise, the task will only terminate via
	 * cancellation or termination of the executor.  If any execution of this task takes longer than its period, then
	 * subsequent executions may start late, but will not concurrently execute.
	 *
	 * @param command            the task to execute
	 * @param initialDelayMillis the time to delay first execution
	 * @param periodMillis       the period between successive executions
	 * @return a ScheduledFuture representing pending completion of
	 * the task, and whose {@code get()} method will throw an
	 * exception upon cancellation
	 * @throws RejectedExecutionException if the task cannot be scheduled for execution
	 * @throws NullPointerException       if command is null
	 * @throws IllegalArgumentException   if period less than or equal to zero
	 */
	@SuppressWarnings("UnusedReturnValue")
	public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelayMillis, long periodMillis) {
		try {
			return service.scheduleAtFixedRate(command, initialDelayMillis, periodMillis, MILLISECONDS);
		} catch (RejectedExecutionException ex) {
			throw new RejectedExecutionException("Executor still busy or already shut down", ex);
		}
	}

	/**
	 * Initiates an orderly shutdown in which previously submitted tasks are executed, but no new tasks will be
	 * accepted. Invocation has no additional effect if already shut down.
	 * <p>
	 * This method does not wait for previously submitted tasks to complete execution.  Use {@link #awaitTermination
	 * awaitTermination} to do that.
	 */
	public void shutdown() {
		service.shutdown();
	}

	/**
	 * Attempts to stop all actively executing tasks, halts the processing of waiting tasks, and returns a list of the
	 * tasks that were awaiting execution.
	 * <p>
	 * This method does not wait for actively executing tasks to terminate.  Use {@link #awaitTermination
	 * awaitTermination} to do that.
	 * <p>
	 * There are no guarantees beyond best-effort attempts to stop processing actively executing tasks.  For example,
	 * typical implementations will cancel via {@link Thread#interrupt}, so any task that fails to respond to
	 * interrupts may never terminate.
	 *
	 * @return list of tasks that never commenced execution
	 */
	@SuppressWarnings("UnusedReturnValue")
	public List<Runnable> shutdownNow() {
		return service.shutdownNow();
	}

	/**
	 * Returns {@code true} if a shutdown has been initiated or this executor has been shut down.
	 */
	public boolean isShutdown() {
		return service.isShutdown();
	}

	/**
	 * Blocks until all tasks have completed execution after a shutdown request, or the timeout occurs, or the current
	 * thread is interrupted, whichever happens first.
	 *
	 * @param timeoutMillis the maximum time to wait
	 * @return {@code true} if this executor terminated and {@code false} if the timeout elapsed before termination
	 */
	public boolean awaitTermination(long timeoutMillis) throws InterruptedException {
		return service.awaitTermination(timeoutMillis, MILLISECONDS);
	}

	/**
	 * @author Mark Jeronimus
	 */
	// Created 2017-01-05
	private static final class SingleNameThreadFactory implements ThreadFactory {
		private final String name;

		private SingleNameThreadFactory(String name) {
			this.name = requireStringNotEmpty(name, "name");
		}

		@Override
		public Thread newThread(Runnable runnable) {
			Thread thread = Executors.defaultThreadFactory().newThread(runnable);
			thread.setName(name);
			return thread;
		}
	}
}
