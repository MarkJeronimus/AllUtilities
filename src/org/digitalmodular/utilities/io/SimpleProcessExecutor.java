/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.digitalmodular.utilities.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import org.digitalmodular.utilities.concurrent.NamedThreadFactory;
import static org.digitalmodular.utilities.ArrayValidatorUtilities.requireArrayLengthAtLeast;

/**
 * @author Mark Jeronimus
 */
// Created 2017-11-21
@SuppressWarnings("CallToRuntimeExec")
public class SimpleProcessExecutor {
	private static final ThreadFactory   THREAD_FACTORY =
			new NamedThreadFactory(Executors.defaultThreadFactory(), "SimpleProcessExecutor");
	private static final ExecutorService EXECUTOR       = Executors.newCachedThreadPool(THREAD_FACTORY);

	private final String[] cmdArray;

	private int    exitCode = 0;
	private String stdOut   = "";
	private String stdErr   = "";

	public SimpleProcessExecutor(String... cmdArray) {
		requireArrayLengthAtLeast(1, cmdArray, "cmdArray");

		this.cmdArray = cmdArray.clone();
	}

	public SimpleProcessExecutor exec() throws IOException, InterruptedException {
		exitCode = 0;
		stdOut = "";
		stdErr = "";

		Process process = Runtime.getRuntime().exec(cmdArray);

		Callable<String> stdOutCallable = new OutputSink(process.getInputStream());
		Callable<String> stdErrCallable = new OutputSink(process.getErrorStream());
		Future<String>   stdOutFuture   = EXECUTOR.submit(stdOutCallable);
		Future<String>   stdErrFuture   = EXECUTOR.submit(stdErrCallable);

		try {
			exitCode = process.waitFor();
			stdOut = stdOutFuture.get();
			stdErr = stdErrFuture.get();
		} catch (InterruptedException ignored) {
		} catch (ExecutionException ex) {
			Throwable th = ex.getCause();
			if (th instanceof IOException) {
				throw (IOException)th;
			} else if (th instanceof InterruptedException) {
				throw (InterruptedException)th;
			} else if (th instanceof RuntimeException) {
				throw (RuntimeException)th;
			} else {
				throw new RuntimeException(th);
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
			StringBuilder sb = new StringBuilder(1024);

			while (true) {
				int b = in.read();
				if (b < 0) {
					break;
				}

				sb.append((char)b);
			}

			return sb.toString();
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
