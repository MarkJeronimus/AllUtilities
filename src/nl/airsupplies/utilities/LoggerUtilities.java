package nl.airsupplies.utilities;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import nl.airsupplies.utilities.annotation.Hardcoded;
import nl.airsupplies.utilities.annotation.UtilityClass;

/**
 * @author Mark Jeronimus
 */
// Created 2016-04-23
@UtilityClass
public final class LoggerUtilities {
	/**
	 * Template of the form {@code [yyMMddTHHmmss.SSS LEVEL] message}
	 */
	@Hardcoded
	public static final String LOGGER_TEMPLATE             =
			"[%1$tY%1$tm%1$tdT%1$tH%1$tM%1$tS.%1$tL %4$s] %5$s%6$s%n";
	/**
	 * Template of the form {@code [yyMMddTHHmmss.SSS LEVEL] method: message}
	 */
	@Hardcoded
	public static final String LOGGER_TEMPLATE_WITH_METHOD =
			"[%1$tY%1$tm%1$tdT%1$tH%1$tM%1$tS.%1$tL %4$s] %2$s: %5$s%6$s%n";

	public static void configure(Level level) {
		configure(level, LOGGER_TEMPLATE_WITH_METHOD);
	}

	public static void configure(Level level, String format) {
		System.setProperty("java.util.logging.SimpleFormatter.format", format);
		Logger.getGlobal().setLevel(level);
		Logger.getGlobal().getParent().removeHandler(Logger.getGlobal().getParent().getHandlers()[0]);
		Logger.getGlobal().getParent().addHandler(new ConsoleHandler());
		Logger.getGlobal().getParent().getHandlers()[0].setLevel(Level.ALL);
	}

	public static void configure(Level level, Handler customHandler) {
		Logger.getGlobal().setLevel(level);
		Logger.getGlobal().getParent().removeHandler(Logger.getGlobal().getParent().getHandlers()[0]);
		Logger.getGlobal().getParent().addHandler(customHandler);
		Logger.getGlobal().getParent().getHandlers()[0].setLevel(Level.ALL);
	}
}
