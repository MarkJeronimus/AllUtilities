package nl.airsupplies.utilities;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;

import org.jetbrains.annotations.Nullable;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2021-06-27
@SuppressWarnings({"UseOfSystemOutOrSystemErr", "CallToPrintStackTrace"})
public class ColorLogger extends Handler {
	private static final Color UNKNOWN_LEVEL_COLOR = new Color(144, 0, 0); // Reverse
	private static final Color THROWABLE_COLOR     = new Color(204, 102, 111); // Reverse
	private static final Color WARNING_COLOR       = new Color(255, 0, 0); // Reverse
	private static final Color INFO_COLOR          = new Color(255, 255, 255); // Bold
	private static final Color CONFIG_COLOR        = new Color(0, 255, 144); // Reverse
	private static final Color FINE_COLOR          = new Color(255, 255, 255);
	private static final Color FINER_COLOR         = new Color(160, 160, 160);
	private static final Color FINEST_COLOR        = new Color(80, 80, 80);

	private static final DateTimeFormatter LOG_INSTANT_FORMATTER = new DateTimeFormatterBuilder()
			.append(DateTimeFormatter.ISO_LOCAL_DATE)
			.appendLiteral(' ')
			.appendValue(HOUR_OF_DAY, 2)
			.appendLiteral(':')
			.appendValue(MINUTE_OF_HOUR, 2)
			.appendLiteral(':')
			.appendValue(SECOND_OF_MINUTE, 2)
			.optionalStart()
			.appendFraction(NANO_OF_SECOND, 4, 4, true)
			.toFormatter()
			.withZone(ZoneOffset.UTC);

	private final Level logLevel;

	private final @Nullable BufferedWriter logWriter;

	public ColorLogger(Level logLevel, @Nullable BufferedWriter logWriter) {
		this.logLevel  = requireNonNull(logLevel, "logLevel");
		this.logWriter = logWriter;
	}

	public ColorLogger(Level logLevel, Path logFile) {
		this.logLevel = requireNonNull(logLevel, "logLevel");
		@Nullable BufferedWriter logWriterTemp;
		try {
			if (Files.exists(logFile)) {
				System.out.println("Last log started " +
				                   Files.readAttributes(logFile, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS)
				                        .creationTime());
				FileUtilities.makeNumberedCopy(logFile, (a, b) -> {
					try {
						Files.move(a, b, StandardCopyOption.ATOMIC_MOVE);
						return null;
					} catch (IOException ex) {
						return ex;
					}
				});
			}

			logWriterTemp = Files.newBufferedWriter(
					logFile, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException ex) {
			ex.printStackTrace();
			logWriterTemp = null;
		}
		logWriter = logWriterTemp;
	}

	@Override
	public void publish(LogRecord record) {
		boolean logToStdOut = record.getLevel().intValue() >= logLevel.intValue();
		boolean logToFile   = logWriter != null;

		if (record.getThrown() != null) {
			logToStdOut = true;
		}

		if (!(logToStdOut || logToFile)) {
			return;
		}

		ZonedDateTime zdt  = ZonedDateTime.ofInstant(Instant.ofEpochMilli(record.getMillis()), ZoneId.systemDefault());
		String        line = '[' + LOG_INSTANT_FORMATTER.format(zdt) + "] " + record.getMessage();

		if (logToStdOut) {
			logToStdOut(record, line);
		}

		if (record.getThrown() != null) {
			StringWriter sw = new StringWriter();
			PrintWriter  pw = new PrintWriter(sw);
			record.getThrown().printStackTrace(pw);
			pw.close();

			String stackTrace = sw.toString();
			logExceptionToStdOut(stackTrace);
			line += stackTrace;
		}

		if (logToFile) {
			try {
				logWriter.write(line);
				logWriter.write('\n');
				flush();
			} catch (IOException ignored) {
			}
		}
	}

	private static void logToStdOut(LogRecord record, String line) {
		switch (record.getLevel().intValue()) {
			case 300: // FINEST
				System.out.print(AnsiCodes.setColor(FINEST_COLOR));
				break;
			case 400: // FINER
				System.out.print(AnsiCodes.setColor(FINER_COLOR));
				break;
			case 500: // FINE
				System.out.print(AnsiCodes.setColor(FINE_COLOR));
				break;
			case 700: // CONFIG
				System.out.print(AnsiCodes.setColor(CONFIG_COLOR));
				System.out.print(AnsiCodes.setReverseVideo(true));
				line = record.getMessage(); // No timestamp
				break;
			case 800: // INFO
				System.out.print(AnsiCodes.setColor(INFO_COLOR));
				System.out.print(AnsiCodes.setBold(true));
				break;
			case 900: // WARNING
				System.out.print(AnsiCodes.setColor(WARNING_COLOR));
				System.out.print(AnsiCodes.setReverseVideo(true));
				break;
			default:
				System.out.print(AnsiCodes.setColor(UNKNOWN_LEVEL_COLOR));
				System.out.print(AnsiCodes.setReverseVideo(true));
				System.out.print("Unknown log level " + record.getLevel() + ':' + record.getLevel().intValue() + ' ');
				System.out.print(AnsiCodes.resetAll());
		}

		System.out.print(line);
		System.out.println(AnsiCodes.resetAll());
	}

	private static void logExceptionToStdOut(String stackTrace) {
		System.out.println(AnsiCodes.setColor(THROWABLE_COLOR));
	}

	@Override
	public void flush() {
		System.out.flush();

		if (logWriter != null) {
			try {
				logWriter.flush();
			} catch (IOException ignored) {
			}
		}
	}

	@Override
	public void close() {
		if (logWriter != null) {
			try {
				logWriter.close();
			} catch (IOException ignored) {
			}
		}
	}
}
