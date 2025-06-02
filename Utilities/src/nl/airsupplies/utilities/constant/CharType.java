package nl.airsupplies.utilities.constant;

/**
 * @author Mark Jeronimus
 */
// Created 2006-03-01
public enum CharType {
	LETTER,
	DIGIT,
	SYMBOL,

	WHITESPACE,

	CONTROL,
	UNICODE,

	END_OF_LINE,
	END_OF_FILE,

	NOT_A_CHAR,
	;

	public static CharType getCharType(int c) {
		// All tests are in order of frequency except when a quicker check has
		// been used.

		if (c < 128) {
			if (Constants.isAlpha[c]) {
				return LETTER;
			}
			if (Constants.isNum[c]) {
				return DIGIT;
			}
			if (Constants.isSymbol[c]) {
				return SYMBOL;
			}
			if (Constants.isWhitespace[c]) {
				return WHITESPACE;
			}
			if (c == '\n' || c == '\r') {
				return END_OF_LINE;
			}
			if (c == 0x1A) {
				return END_OF_FILE;
			}
			return CONTROL;
		}

		if (Character.isDefined(c)
		    &&
		    !(Character.isISOControl(c) || Character.isHighSurrogate((char)c) || Character.isLowSurrogate((char)c))) {
			// Arrows [2190..21FF]
			// Mathematical Operators [2200..22FF]
			if (c >= 0x2190 && c < 0x22FF) {
				return SYMBOL;
			}
			// Miscellaneous Mathematical Symbols-A [27C0..27EF]
			// Supplemental Arrows-A [27F0..27FF]
			if (c >= 0x27C0 && c < 0x27FF) {
				return SYMBOL;
			}
			// Supplemental Arrows-B [2900..297F]
			// Miscellaneous Mathematical Symbols-B [2980..29FF]
			// Supplemental Mathematical Operators [2A00..2AFF]
			if (c >= 0x2900 && c < 0x2AFF) {
				return SYMBOL;
			}
			if (c < 0xA0) {
				return CONTROL;
			}

			return UNICODE;
		}

		return NOT_A_CHAR;
	}
}
