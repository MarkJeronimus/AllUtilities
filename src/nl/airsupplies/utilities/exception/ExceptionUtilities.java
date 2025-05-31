package nl.airsupplies.utilities.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import nl.airsupplies.utilities.annotation.UtilityClass;

/**
 * @author Mark Jeronimus
 */
// Created 2017-02-15
@UtilityClass
public final class ExceptionUtilities {
	public static String exceptionToString(Throwable ex) {
		String errorString;
		try {
			try (StringWriter sw = new StringWriter();
			     PrintWriter pw = new PrintWriter(sw)) {
				ex.printStackTrace(pw);

				errorString = sw.toString();
			}
		} catch (IOException ex2) {
			errorString = "\nUnexpected exception during exception handling:\n" + ex2 +
			              "\n\nOriginal exception:\n" + ex;
		}
		return errorString;
	}
}
