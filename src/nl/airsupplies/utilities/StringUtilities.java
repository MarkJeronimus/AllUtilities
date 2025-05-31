package nl.airsupplies.utilities;

import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.annotation.UtilityClass;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;

/**
 * This Utility class contains static helper methods for working with {@link String}s.
 *
 * @author Mark Jeronimus
 */
// Created 2007-11-13
@SuppressWarnings("CharUsedInArithmeticContext")
@UtilityClass
public final class StringUtilities {
	private static final Pattern COMBINING_MARK_PATTERN = Pattern.compile("\\p{M}");

	private static final String[] CONTROL_NAMES          = {
			"NUL", "SOH", "STX", "ETX", "EOT", "ENQ", "ACK", "BEL",  // 0x00
			"BS", "HT", "LF", "VT", "FF", "CR", "SO", "SI",          // 0x08
			"DLE", "DC1", "DC2", "DC3", "DC4", "NAK", "SYN", "ETE",  // 0x10
			"CAN", "EM", "SUB", "ESC", "FS", "GS", "RS", "US", "SP", // 0x18
	};
	@SuppressWarnings("UnnecessaryUnicodeEscape") // Escapes needed for alignment
	private static final char[]   SUPERSCRIPT_CHARACTERS = {
			'\u2006', '\uA71D', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', // 0x20
			'\u207D', '\u207E', '\u0000', '\u207A', '\u0000', '\u207B', '\u0000', '\u0000', // 0x28
			'\u2070', '\u00B9', '\u00B2', '\u00B3', '\u2074', '\u2075', '\u2076', '\u2077', // 0x30
			'\u2078', '\u2079', '\u0000', '\u0000', '\u0000', '\u207C', '\u0000', '\u0000', // 0x38
			'\u0000', '\u1D2C', '\u1D2E', '\u0000', '\u1D30', '\u1D31', '\u0000', '\u1D33', // 0x40
			'\u1D34', '\u1D35', '\u1D36', '\u1D37', '\u1D38', '\u1D39', '\u1D3A', '\u1D3C', // 0x48
			'\u1D3E', '\u0000', '\u1D3F', '\u0000', '\u1D40', '\u1D41', '\u2C7D', '\u1D42', // 0x50
			'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', // 0x58
			'\u0000', '\u1D43', '\u1D47', '\u1D9C', '\u1D48', '\u1D49', '\u1DA0', '\u1D4D', // 0x60
			'\u02B0', '\u2071', '\u02B2', '\u1D4F', '\u02E1', '\u1D50', '\u207F', '\u1D52', // 0x68
			'\u1D56', '\u0000', '\u02B3', '\u02E2', '\u1D57', '\u1D58', '\u1D5B', '\u02B7', // 0x70
			'\u02E3', '\u02B8', '\u1DBB', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', // 0x78
	};
	@SuppressWarnings("UnnecessaryUnicodeEscape") // Escapes needed for alignment
	private static final char[]   SUBSCRIPT_CHARACTERS   = {
			'\u2006', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', // 0x20
			'\u208D', '\u208E', '\u0000', '\u208A', '\u0000', '\u208B', '\u0000', '\u0000', // 0x28
			'\u2080', '\u2081', '\u2082', '\u2083', '\u2084', '\u2085', '\u2086', '\u2087', // 0x30
			'\u2088', '\u2089', '\u0000', '\u0000', '\u0000', '\u208C', '\u0000', '\u0000', // 0x38
			'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', // 0x40
			'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', // 0x48
			'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', // 0x50
			'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', // 0x58
			'\u0000', '\u2090', '\u0000', '\u0000', '\u0000', '\u2091', '\u0000', '\u0000', // 0x60
			'\u2095', '\u0000', '\u0000', '\u2096', '\u2097', '\u2098', '\u2099', '\u2092', // 0x68
			'\u209A', '\u0000', '\u0000', '\u209B', '\u209C', '\u0000', '\u0000', '\u0000', // 0x70
			'\u2093', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', // 0x78
	};

	public static int utf8Length(CharSequence cs) {
		return cs.length() + cs.codePoints()
		                       .filter(cp -> cp >= 0x80)
		                       .map(cp -> cp < 0x800 ? 1 : (cp < 0x10000 ? 2 : 3))
		                       .sum();
	}

	/**
	 * Java 1.8 equivalent of {@code String.repeat()}, simplified for single characters.
	 * <p>
	 * Once {@code String.repeat()} becomes available, this function can be dropped.
	 */
	public static String repeatChar(char ch, int count) {
		requireAtLeast(0, count, "count");

		if (count == 0) {
			return "";
		} else {
			char[] chars = new char[count];
			Arrays.fill(chars, ch);
			return new String(chars);
		}
	}

	/** Pad left with zeroes */
	public static String fixLeft(String s, int length) {
		return repeatChar('0', Math.max(0, length - s.length())) + s;
	}

	/** Pad right with zeroes */
	public static String fixRight(String s, int length) {
		return s + repeatChar('0', Math.max(0, length - s.length()));
	}

	/** Pad left with spaces */
	public static String padLeft(String s, int length) {
		return repeatChar(' ', Math.max(0, length - s.length())) + s;
	}

	/** Pad right with spaces */
	public static String padRight(String s, int length) {
		return s + repeatChar(' ', Math.max(0, length - s.length()));
	}

	/**
	 * Converts a {@code byte[]} with a null-delimiter to a {@link String}. If no null-delimiter is found, the
	 * entire array is converted to String.
	 */
	public static String fromCString(byte[] array) {
		int i;
		for (i = 0; i < array.length; i++) {
			if (array[i] == 0) {
				break;
			}
		}

		return new String(array, 0, i, StandardCharsets.UTF_8);
	}

	/**
	 * Converts a String to a null-delimited {@code byte[]}. If the string doesn't fit into the array, the entire
	 * array will be filled and there will be no null-delimiter. This also means that a multi-byte encoded character
	 * may be stored partially, resulting in an invalid string.
	 */
	public static byte[] toCString(String string, byte[] array) {
		byte[] bytes = string.getBytes(StandardCharsets.UTF_8);

		int outLen = array.length;
		int inLen  = Math.min(bytes.length, outLen);

		System.arraycopy(bytes, 0, array, 0, inLen);
		Arrays.fill(bytes, inLen, outLen, (byte)0);

		return array;
	}

	public static @Nullable String removeQuotes(String string) {
		if (string == null ||
		    string.length() < 2 ||
		    string.charAt(0) != '"' ||
		    string.charAt(string.length() - 1) != '"') {
			return string;
		}

		return string.substring(1, string.length() - 1);
	}

	public static String snakeToCamelCase(String text) {
		return Stream.of(text.split("_"))
		             .map(s -> s.isEmpty() ? "" : s.charAt(0) + s.substring(1).toLowerCase())
		             .collect(Collectors.joining());
	}

	public static String camelToSnakeCase(String text) {
		StringBuilder sb = new StringBuilder(text.length() * 3 / 2);

		boolean lastIsLetter = false;
		boolean lastIsUpper  = true;

		for (int i = 0; i < text.length(); i++) {
			char c1 = text.charAt(i);
			char c2 = i + 1 < text.length() ? text.charAt(i + 1) : 'A';

			boolean isUpper = Character.isUpperCase(c1);

			if (lastIsLetter && isUpper && !Character.isUpperCase(c2)) {
				sb.append('_');
			} else if (!lastIsUpper && Character.isUpperCase(c1)) {
				sb.append('_');
			}

			sb.append(Character.toUpperCase(c1));

			lastIsLetter = Character.isLetter(c1);
			lastIsUpper  = isUpper;
		}

		return sb.toString();
	}

	public static String collateASCII(String text) {
		return COMBINING_MARK_PATTERN.matcher(Normalizer.normalize(text, Normalizer.Form.NFD)).replaceAll("");
	}

	public static String toEnglishCardinal(int i) {
		if (i >= 11 && i <= 13) {
			return i + "th";
		}

		int lastDigit = Math.abs(i) % 10;
		switch (lastDigit) {
			case 1:
				return i + "st";
			case 2:
				return i + "nd";
			case 3:
				return i + "rd";
			default:
				return i + "th";
		}
	}

	public static @Nullable String getControlName(char c) {
		if (c <= 0x20) {
			return CONTROL_NAMES[c];
		}

		return null;
	}

	public static String toSuperScriptUnicode(String in) {
		StringBuilder out = new StringBuilder(in.length() + 1);

		// Somehow this helps prevent squares. (found by accident)
		out.append('\u202D'); // U+202D LEFT-TO-RIGHT OVERRIDE

		for (int i = 0; i < in.length(); i++) {
			int  c  = in.charAt(i) - 0x20;
			char ch = '\0';

			// Try to translate character.
			if (c >= 0 && c < SUPERSCRIPT_CHARACTERS.length) {
				ch = SUPERSCRIPT_CHARACTERS[c];
			}

			// Untranslatable character?
			if (ch == '\0') {
				throw new IllegalArgumentException(
						"String contains characters that do not have a superscript equivalent in Unicode: " +
						in.charAt(i));
			}

			out.append(ch);
		}

		return out.toString();
	}

	public static String toSubScriptUnicode(String in) {
		StringBuilder out = new StringBuilder(in.length() + 1);

		// Somehow this helps prevent squares. (found by accident)
		out.append('\u202D'); // U+202D LEFT-TO-RIGHT OVERRIDE

		for (int i = 0; i < in.length(); i++) {
			int  c  = in.charAt(i) - 0x20;
			char ch = '\0';

			// Try to translate character.
			if (c >= 0 && c < SUBSCRIPT_CHARACTERS.length) {
				ch = SUBSCRIPT_CHARACTERS[c];
			}

			// Untranslatable character?
			if (ch == '\0') {
				throw new IllegalArgumentException(
						"String contains characters that do not have a subscript equivalent in Unicode: " +
						in.charAt(i));
			}

			out.append(ch);
		}

		return out.toString();
	}

	public static String capitalize(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}
}
