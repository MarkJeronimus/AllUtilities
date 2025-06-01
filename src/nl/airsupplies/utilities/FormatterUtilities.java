package nl.airsupplies.utilities;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

import nl.airsupplies.utilities.annotation.UtilityClass;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireBetween;

/**
 * @author Mark Jeronimus
 */
// Created 2025-04-13
@UtilityClass
public final class FormatterUtilities {
	private static final DecimalFormatSymbols US_SYMBOLS = DecimalFormatSymbols.getInstance(Locale.US);

	public static final DecimalFormat DEC0_NUMBER  = new DecimalFormat("0");
	public static final DecimalFormat DEC1_NUMBER  = new DecimalFormat("0.0");
	public static final DecimalFormat DEC2_NUMBER  = new DecimalFormat("0.00");
	public static final DecimalFormat DEC3_NUMBER  = new DecimalFormat("0.000");
	public static final DecimalFormat DEC4_NUMBER  = new DecimalFormat("0.0000");
	public static final DecimalFormat DEC5_NUMBER  = new DecimalFormat("0.00000");
	public static final DecimalFormat DEC6_NUMBER  = new DecimalFormat("0.000000");
	public static final DecimalFormat DEC7_NUMBER  = new DecimalFormat("0.0000000");
	public static final DecimalFormat DEC8_NUMBER  = new DecimalFormat("0.00000000");
	public static final DecimalFormat DEC9_NUMBER  = new DecimalFormat("0.000000000");
	public static final DecimalFormat DEC10_NUMBER = new DecimalFormat("0.0000000000");
	public static final DecimalFormat DEC11_NUMBER = new DecimalFormat("0.00000000000");
	public static final DecimalFormat DEC12_NUMBER = new DecimalFormat("0.000000000000");
	public static final DecimalFormat DEC13_NUMBER = new DecimalFormat("0.0000000000000");
	public static final DecimalFormat DEC14_NUMBER = new DecimalFormat("0.00000000000000");
	public static final DecimalFormat DEC15_NUMBER = new DecimalFormat("0.000000000000000");

	public static final DecimalFormat COMMA_NUMBER = new DecimalFormat("#,###");

	public static final DecimalFormat SCI3_NUMBER = new DecimalFormat("0.000E0");
	public static final DecimalFormat SCI6_NUMBER = new DecimalFormat("0.000000E0");

	public static final DecimalFormat ENG3_NUMBER = new DecimalFormat("##0.000E0");
	public static final DecimalFormat ENG6_NUMBER = new DecimalFormat("##0.000000E0");

	private static final DecimalFormat[] DEC_FORMATTERS = {DEC0_NUMBER, DEC1_NUMBER, DEC2_NUMBER, DEC3_NUMBER,
	                                                       DEC4_NUMBER, DEC5_NUMBER, DEC6_NUMBER, DEC7_NUMBER,
	                                                       DEC8_NUMBER, DEC9_NUMBER, DEC10_NUMBER, DEC11_NUMBER,
	                                                       DEC12_NUMBER, DEC13_NUMBER, DEC14_NUMBER, DEC15_NUMBER};

	static {
		// Canonicalize floating point numbers.
		DEC0_NUMBER.setDecimalFormatSymbols(US_SYMBOLS);
		DEC1_NUMBER.setDecimalFormatSymbols(US_SYMBOLS);
		DEC2_NUMBER.setDecimalFormatSymbols(US_SYMBOLS);
		DEC3_NUMBER.setDecimalFormatSymbols(US_SYMBOLS);
		DEC4_NUMBER.setDecimalFormatSymbols(US_SYMBOLS);
		DEC5_NUMBER.setDecimalFormatSymbols(US_SYMBOLS);
		DEC6_NUMBER.setDecimalFormatSymbols(US_SYMBOLS);
		DEC7_NUMBER.setDecimalFormatSymbols(US_SYMBOLS);
		DEC8_NUMBER.setDecimalFormatSymbols(US_SYMBOLS);
		DEC9_NUMBER.setDecimalFormatSymbols(US_SYMBOLS);
		DEC10_NUMBER.setDecimalFormatSymbols(US_SYMBOLS);
		DEC11_NUMBER.setDecimalFormatSymbols(US_SYMBOLS);
		DEC12_NUMBER.setDecimalFormatSymbols(US_SYMBOLS);
		DEC13_NUMBER.setDecimalFormatSymbols(US_SYMBOLS);
		DEC14_NUMBER.setDecimalFormatSymbols(US_SYMBOLS);
		DEC15_NUMBER.setDecimalFormatSymbols(US_SYMBOLS);

		COMMA_NUMBER.setDecimalFormatSymbols(US_SYMBOLS);

		SCI3_NUMBER.setDecimalFormatSymbols(US_SYMBOLS);
		SCI6_NUMBER.setDecimalFormatSymbols(US_SYMBOLS);

		ENG3_NUMBER.setDecimalFormatSymbols(US_SYMBOLS);
		ENG6_NUMBER.setDecimalFormatSymbols(US_SYMBOLS);
	}

	public static NumberFormat getFixedPrecisionFormatter(int precision) {
		requireBetween(0, 15, precision, "precision");

		return new NumberFormat() {
			@Override
			public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
				int integerDigits = (int)(Math.floor(Math.log10(number))) + 1;
				int decimals      = NumberUtilities.clamp(precision - integerDigits, 0, precision);
				toAppendTo.append(DEC_FORMATTERS[decimals].format(number));
				return toAppendTo;
			}

			@Override
			public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
				toAppendTo.append(DEC_FORMATTERS[0].format(number));
				return toAppendTo;
			}

			@Override
			public Number parse(String source, ParsePosition parsePosition) {
				throw new UnsupportedOperationException("This formatter doesn't parse");
			}
		};
	}
}
