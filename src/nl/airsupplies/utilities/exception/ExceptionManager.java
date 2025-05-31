package nl.airsupplies.utilities.exception;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import nl.airsupplies.utilities.annotation.StaticClass;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2017-09-05
@StaticClass
public final class ExceptionManager {
	private static final UncaughtExceptionHandler uncaughtExceptionHandler =
			(thread, th) -> reportUncaughtException(th);

	private static final Collection<Consumer<Throwable>>            uncaughtExceptionHandlers  =
			new CopyOnWriteArraySet<>();
	private static final Collection<BiConsumer<String, Exception>>  warningHandlers            =
			new CopyOnWriteArraySet<>();
	private static final Collection<BiPredicate<String, Exception>> retryableExceptionHandlers =
			new CopyOnWriteArraySet<>();
	private static final Collection<BiConsumer<String, Throwable>>  unrecoverableErrorHandlers =
			new CopyOnWriteArraySet<>();

	public static void addUncaughtExceptionHandler(Consumer<Throwable> handler) {
		uncaughtExceptionHandlers.add(requireNonNull(handler, "handler"));
	}

	public static void removeUncaughtExceptionHandler(Consumer<Throwable> handler) {
		uncaughtExceptionHandlers.remove(requireNonNull(handler, "handler"));
	}

	public static void addWarningHandler(BiConsumer<String, Exception> handler) {
		warningHandlers.add(requireNonNull(handler, "handler"));
	}

	public static void removeWarningHandler(BiConsumer<String, Exception> handler) {
		warningHandlers.remove(requireNonNull(handler, "handler"));
	}

	public static void addRetryableExceptionHandler(BiPredicate<String, Exception> handler) {
		retryableExceptionHandlers.add(requireNonNull(handler, "handler"));
	}

	public static void removeRetryableExceptionHandler(BiPredicate<String, Exception> handler) {
		retryableExceptionHandlers.remove(requireNonNull(handler, "handler"));
	}

	public static void addUnrecoverableErrorHandler(BiConsumer<String, Throwable> handler) {
		unrecoverableErrorHandlers.add(requireNonNull(handler, "handler"));
	}

	public static void removeUnrecoverableErrorHandler(BiConsumer<String, Throwable> handler) {
		unrecoverableErrorHandlers.remove(requireNonNull(handler, "handler"));
	}

	public static UncaughtExceptionHandler getUncaughtExceptionHandler() {
		return uncaughtExceptionHandler;
	}

	private static void reportUncaughtException(Throwable th) {
		for (Consumer<Throwable> handler : uncaughtExceptionHandlers) {
			try {
				handler.accept(th);
			} catch (Throwable ignored) {
			}
		}

		if (uncaughtExceptionHandlers.isEmpty()) {
			//noinspection CallToPrintStackTrace
			th.printStackTrace();
		}
	}

	public static void reportWarning(String message, Exception ex) {
		if (message == null || message.isEmpty()) {
			message = ex.getMessage();
		}

		// 2020-04-22 Removed "Warning"+ from the message because it looks weird on e.g. "Connection reset" error.
		message = message == null ? "Warning" : message;

		for (BiConsumer<String, Exception> handler : warningHandlers) {
			try {
				handler.accept(message, ex);
			} catch (Throwable ignored) {
			}
		}
	}

	/**
	 * @return {@code true} if user wants to retry
	 */
	public static boolean reportRetryableException(String message, Exception ex) {
		if (message == null || message.isEmpty()) {
			message = ex.getMessage();
		}

		message = message == null ? "Retryable Exception" : "Retryable Exception: " + message;

		boolean shouldRetry = false;
		for (BiPredicate<String, Exception> handler : retryableExceptionHandlers) {
			try {
				shouldRetry |= handler.test(message, ex);
			} catch (Throwable ignored) {
			}
		}

		return shouldRetry;
	}

	public static void reportUnrecoverableError(String message, Throwable th) {
		if (message == null || message.isEmpty()) {
			message = th.getMessage();
		}

		message = message == null ? "Unrecoverable Error" : "Unrecoverable Error: " + message;

		for (BiConsumer<String, Throwable> handler : unrecoverableErrorHandlers) {
			try {
				handler.accept(message, th);
			} catch (Throwable ignored) {
			}
		}

		System.exit(-1);
	}

	/**
	 * Thrown {@code Throwable}s are caught by the uncaughtExceptionHandler, so if that handler causes {@code
	 * Throwable}s, they can't be rethrown to prevent an infinite loop, so this method should be called instead.
	 */
	public static void die(Throwable th) {
		//noinspection CallToPrintStackTrace
		th.printStackTrace();
		System.exit(-1);
	}
}
