package nl.airsupplies.utilities;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import nl.airsupplies.utilities.annotation.UtilityClass;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireBetween;

/**
 * @author Mark Jeronimus
 */
// Created 2025-04-13
@UtilityClass
public final class FormatterUtilities {
	private static final DecimalFormatSymbols US_FORMATTER_SYMBOLS = DecimalFormatSymbols.getInstance(Locale.US);

	private static final DecimalFormat[] DECIMAL_FORMATTERS = new DecimalFormat[17];

	public static final DecimalFormat COMMA_NUMBER = new DecimalFormat("#,###");

	public static final DecimalFormat SCI3_NUMBER = new DecimalFormat("0.000E0");
	public static final DecimalFormat SCI6_NUMBER = new DecimalFormat("0.000000E0");

	public static final DecimalFormat ENG3_NUMBER = new DecimalFormat("##0.000E0");
	public static final DecimalFormat ENG6_NUMBER = new DecimalFormat("##0.000000E0");

	// Canonicalize floating point numbers.
	static {
		COMMA_NUMBER.setDecimalFormatSymbols(US_FORMATTER_SYMBOLS);

		SCI3_NUMBER.setDecimalFormatSymbols(US_FORMATTER_SYMBOLS);
		SCI6_NUMBER.setDecimalFormatSymbols(US_FORMATTER_SYMBOLS);

		ENG3_NUMBER.setDecimalFormatSymbols(US_FORMATTER_SYMBOLS);
		ENG6_NUMBER.setDecimalFormatSymbols(US_FORMATTER_SYMBOLS);
	}

	public static DecimalFormat getDecimalFormatter(int precision) {
		requireBetween(0, DECIMAL_FORMATTERS.length - 1, precision, "precision");

		// No synchronization necessary because the initialization function is idempotent and has low overhead.
		if (DECIMAL_FORMATTERS[precision] == null) {
			DECIMAL_FORMATTERS[precision] = new DecimalFormat();
			DECIMAL_FORMATTERS[precision].setMinimumFractionDigits(precision);
			DECIMAL_FORMATTERS[precision].setMaximumFractionDigits(precision);
			DECIMAL_FORMATTERS[precision].setGroupingUsed(false);
			DECIMAL_FORMATTERS[precision].setDecimalFormatSymbols(US_FORMATTER_SYMBOLS);
		}

		return DECIMAL_FORMATTERS[precision];
	}
}
