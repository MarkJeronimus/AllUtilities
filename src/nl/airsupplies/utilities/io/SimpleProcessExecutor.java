package nl.airsupplies.utilities.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Pattern;

import nl.airsupplies.utilities.concurrent.NamedThreadFactory;
import nl.airsupplies.utilities.container.StringBuilderByte;
import static nl.airsupplies.utilities.validator.ArrayValidatorUtilities.requireArrayLengthAtLeast;

/**
 * @author Mark Jeronimus
 */
// Created 2017-11-21
@SuppressWarnings("CallToRuntimeExec")
public class SimpleProcessExecutor {
	private static final ThreadFactory   THREAD_FACTORY =
			new NamedThreadFactory(Executors.defaultThreadFactory(), "SimpleProcessExecutor");
	private static final ExecutorService EXECUTOR       = Executors.newCachedThreadPool(THREAD_FACTORY);

	private static final Pattern CRLF_PATTERN = Pattern.compile("\r+\n");

	private final String[] cmdArray;

	private int    exitCode = 0;
	private String stdOut   = "";
	private String stdErr   = "";

	public SimpleProcessExecutor(String... cmdArray) {
		requireArrayLengthAtLeast(1, cmdArray, "cmdArray");

		this.cmdArray = cmdArray.clone();
	}

	@SuppressWarnings("ProhibitedExceptionThrown")
	public SimpleProcessExecutor exec() throws IOException, InterruptedException {
		exitCode = 0;
		stdOut   = "";
		stdErr   = "";

		Process process = Runtime.getRuntime().exec(cmdArray);

		Callable<String> stdOutCallable = new OutputSink(process.getInputStream());
		Callable<String> stdErrCallable = new OutputSink(process.getErrorStream());
		Future<String>   stdOutFuture   = EXECUTOR.submit(stdOutCallable);
		Future<String>   stdErrFuture   = EXECUTOR.submit(stdErrCallable);

		try {
			exitCode = process.waitFor();
			stdOut   = stdOutFuture.get();
			stdErr   = stdErrFuture.get();
		} catch (ExecutionException ex) {
			// Launder the thrown exception
			Throwable cause = ex.getCause();
			if (cause instanceof IOException) {
				throw (IOException)cause;
			} else if (cause instanceof InterruptedException) {
				throw (InterruptedException)cause;
			} else if (cause instanceof RuntimeException) {
				throw (RuntimeException)cause;
			} else {
				throw new RuntimeException(cause);
			}
		}

		return this;
	}

	private static final class OutputSink implements Callable<String> {
		private final InputStream in;

		private OutputSink(InputStream in) {
			this.in = in;
		}

		@Override
		public String call() throws Exception {
			StringBuilderByte sb = new StringBuilderByte(1024, StandardCharsets.UTF_8);

			while (true) {
				int b = in.read();
				if (b < 0) {
					break;
				}

				sb.append((byte)b);
			}

			return CRLF_PATTERN.matcher(sb.toString()).replaceAll("\n");
		}
	}

	public int getExitCode() {
		return exitCode;
	}

	public String getStdOut() {
		return stdOut;
	}

	public String getStdErr() {
		return stdErr;
	}
}
