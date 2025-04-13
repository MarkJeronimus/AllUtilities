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

package org.digitalmodular.utilities;

import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

import org.jetbrains.annotations.Nullable;

import org.digitalmodular.utilities.annotation.UtilityClass;
import static org.digitalmodular.utilities.ValidatorUtilities.requireAtLeast;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;
import static org.digitalmodular.utilities.ValidatorUtilities.requireRange;

/**
 * This Utility class contains static helper methods for working with {@link String}s.
 *
 * @author Mark Jeronimus
 */
// Created 2007-11-13
@SuppressWarnings("CharUsedInArithmeticContext")
@UtilityClass
public final class StringUtilities {
	private static final String[] REPEATING_CHARS_CACHE   = new String[126];
	private static final char[]   ILLEGAL_FILE_NAME_CHARS = {'"', '*', '/', ':', '<', '>', '?', '\\', '|'};
	private static final Pattern  COLON_MATCHER_1         = Pattern.compile("([0-9]):([0-9])");
	private static final Pattern  COLON_MATCHER_2         = Pattern.compile(" : ");
	private static final Pattern  COLON_MATCHER_3         = Pattern.compile(": ");
	private static final Pattern  COLON_MATCHER_4         = Pattern.compile(" :");
	private static final Pattern  COMBINING_MARK_PATTERN  = Pattern.compile("\\p{M}");
	private static final String[] CONTROL_NAMES           = {
			"NUL", "SOH", "STX", "ETX", "EOT", "ENQ", "ACK", "BEL",  // 0x00
			"BS", "HT", "LF", "VT", "FF", "CR", "SO", "SI",          // 0x08
			"DLE", "DC1", "DC2", "DC3", "DC4", "NAK", "SYN", "ETE",  // 0x10
			"CAN", "EM", "SUB", "ESC", "FS", "GS", "RS", "US", "SP", // 0x18
	};
	private static final char[]   SUPERSCRIPT_CHARACTERS  = {
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
	private static final char[]   SUBSCRIPT_CHARACTERS    = {
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

	// Map for HTML code -> unicode character.
	private static final Map<String, String> HTML_CHARACTERS = new HashMap<>(251);
	// // Map for unicode character -> HTML code.
	// private static final Map<String, String> htmlCodes = new HashMap<String, String>();

	static {
		for (String[] code : new String[][]{
				{"quot", "\""}, {"amp", "&"}, {"lt", "<"}, {"gt", ">"},
				{"nbsp", "\u00A0"}, {"iexcl", "\u00A1"}, {"cent", "\u00A2"}, {"pound", "\u00A3"},
				{"curren", "\u00A4"}, {"yen", "\u00A5"}, {"brvbar", "\u00A6"}, {"sect", "\u00A7"},
				{"uml", "\u00A8"}, {"copy", "\u00A9"}, {"ordf", "\u00AA"}, {"laquo", "\u00AB"},
				{"not", "\u00AC"}, {"shy", "\u00AD"}, {"reg", "\u00AE"}, {"macr", "\u00AF"},
				{"deg", "\u00B0"}, {"plusmn", "\u00B1"}, {"sup2", "\u00B2"}, {"sup3", "\u00B3"},
				{"acute", "\u00B4"}, {"micro", "\u00B5"}, {"para", "\u00B6"}, {"middot", "\u00B7"},
				{"cedil", "\u00B8"}, {"sup1", "\u00B9"}, {"ordm", "\u00BA"}, {"raquo", "\u00BB"},
				{"frac14", "\u00BC"}, {"frac12", "\u00BD"}, {"frac34", "\u00BE"}, {"iquest", "\u00BF"},
				{"Agrave", "\u00C0"}, {"Aacute", "\u00C1"}, {"Acirc", "\u00C2"}, {"Atilde", "\u00C3"},
				{"Auml", "\u00C4"}, {"Aring", "\u00C5"}, {"AElig", "\u00C6"}, {"Ccedil", "\u00C7"},
				{"Egrave", "\u00C8"}, {"Eacute", "\u00C9"}, {"Ecirc", "\u00CA"}, {"Euml", "\u00CB"},
				{"Igrave", "\u00CC"}, {"Iacute", "\u00CD"}, {"Icirc", "\u00CE"}, {"Iuml", "\u00CF"},
				{"ETH", "\u00D0"}, {"Ntilde", "\u00D1"}, {"Ograve", "\u00D2"}, {"Oacute", "\u00D3"},
				{"Ocirc", "\u00D4"}, {"Otilde", "\u00D5"}, {"Ouml", "\u00D6"}, {"times", "\u00D7"},
				{"Oslash", "\u00D8"}, {"Ugrave", "\u00D9"}, {"Uacute", "\u00DA"}, {"Ucirc", "\u00DB"},
				{"Uuml", "\u00DC"}, {"Yacute", "\u00DD"}, {"THORN", "\u00DE"}, {"szlig", "\u00DF"},
				{"agrave", "\u00E0"}, {"aacute", "\u00E1"}, {"acirc", "\u00E2"}, {"atilde", "\u00E3"},
				{"auml", "\u00E4"}, {"aring", "\u00E5"}, {"aelig", "\u00E6"}, {"ccedil", "\u00E7"},
				{"egrave", "\u00E8"}, {"eacute", "\u00E9"}, {"ecirc", "\u00EA"}, {"euml", "\u00EB"},
				{"igrave", "\u00EC"}, {"iacute", "\u00ED"}, {"icirc", "\u00EE"}, {"iuml", "\u00EF"},
				{"eth", "\u00F0"}, {"ntilde", "\u00F1"}, {"ograve", "\u00F2"}, {"oacute", "\u00F3"},
				{"ocirc", "\u00F4"}, {"otilde", "\u00F5"}, {"ouml", "\u00F6"}, {"divide", "\u00F7"},
				{"oslash", "\u00F8"}, {"ugrave", "\u00F9"}, {"uacute", "\u00FA"}, {"ucirc", "\u00FB"},
				{"uuml", "\u00FC"}, {"yacute", "\u00FD"}, {"thorn", "\u00FE"}, {"yuml", "\u00FF"},
				{"OElig", "\u0152"}, {"oelig", "\u0153"}, {"Scaron", "\u0160"}, {"Yuml", "\u0178"},
				{"fnof", "\u0192"}, {"circ", "\u02C6"}, {"tilde", "\u02DC"}, {"Alpha", "\u0391"},
				{"Beta", "\u0392"}, {"Gamma", "\u0393"}, {"Delta", "\u0394"}, {"Epsilon", "\u0395"},
				{"Zeta", "\u0396"}, {"Eta", "\u0397"}, {"Theta", "\u0398"}, {"Iota", "\u0399"},
				{"Kappa", "\u039A"}, {"Lambda", "\u039B"}, {"Mu", "\u039C"}, {"Nu", "\u039D"},
				{"Xi", "\u039E"}, {"Omicron", "\u039F"}, {"Pi", "\u03A0"}, {"Rho", "\u03A1"},
				{"Sigma", "\u03A3"}, {"Tau", "\u03A4"}, {"Upsilon", "\u03A5"}, {"Phi", "\u03A6"},
				{"Chi", "\u03A7"}, {"Psi", "\u03A8"}, {"Omega", "\u03A9"}, {"alpha", "\u03B1"},
				{"beta", "\u03B2"}, {"gamma", "\u03B3"}, {"delta", "\u03B4"}, {"epsilon", "\u03B5"},
				{"zeta", "\u03B6"}, {"eta", "\u03B7"}, {"theta", "\u03B8"}, {"iota", "\u03B9"},
				{"kappa", "\u03BA"}, {"lambda", "\u03BB"}, {"mu", "\u03BC"}, {"nu", "\u03BD"},
				{"xi", "\u03BE"}, {"omicron", "\u03BF"}, {"pi", "\u03C0"}, {"rho", "\u03C1"},
				{"sigmaf", "\u03C2"}, {"sigma", "\u03C3"}, {"tau", "\u03C4"}, {"upsilon", "\u03C5"},
				{"phi", "\u03C6"}, {"chi", "\u03C7"}, {"psi", "\u03C8"}, {"omega", "\u03C9"},
				{"thetasym", "\u03D1"}, {"upsih", "\u03D2"}, {"piv", "\u03D6"}, {"ensp", "\u2002"},
				{"emsp", "\u2003"}, {"thinsp", "\u2009"}, {"zwnj", "\u200C"}, {"zwj", "\u200D"},
				{"lrm", "\u200E"}, {"rlm", "\u200F"}, {"ndash", "\u2013"}, {"mdash", "\u2014"},
				{"lsquo", "\u2018"}, {"rsquo", "\u2019"}, {"sbquo", "\u201A"}, {"ldquo", "\u201C"},
				{"rdquo", "\u201D"}, {"bdquo", "\u201E"}, {"dagger", "\u2020"}, {"Dagger", "\u2021"},
				{"bull", "\u2022"}, {"hellip", "\u2026"}, {"permil", "\u2030"}, {"prime", "\u2032"},
				{"Prime", "\u2033"}, {"lsaquo", "\u2039"}, {"rsaquo", "\u203A"}, {"oline", "\u203E"},
				{"frasl", "\u2044"}, {"euro", "\u20AC"}, {"image", "\u2111"}, {"weierp", "\u2118"},
				{"real", "\u211C"}, {"trade", "\u2122"}, {"alefsym", "\u2135"}, {"larr", "\u2190"},
				{"uarr", "\u2191"}, {"rarr", "\u2192"}, {"darr", "\u2193"}, {"harr", "\u2194"},
				{"crarr", "\u21B5"}, {"lArr", "\u21D0"}, {"uArr", "\u21D1"}, {"rArr", "\u21D2"},
				{"dArr", "\u21D3"}, {"hArr", "\u21D4"}, {"forall", "\u2200"}, {"part", "\u2202"},
				{"exist", "\u2203"}, {"empty", "\u2205"}, {"nabla", "\u2207"}, {"isin", "\u2208"},
				{"notin", "\u2209"}, {"ni", "\u220B"}, {"prod", "\u220F"}, {"sum", "\u2211"},
				{"minus", "\u2212"}, {"lowast", "\u2217"}, {"radic", "\u221A"}, {"prop", "\u221D"},
				{"infin", "\u221E"}, {"ang", "\u2220"}, {"and", "\u2227"}, {"or", "\u2228"},
				{"cap", "\u2229"}, {"cup", "\u222A"}, {"int", "\u222B"}, {"there4", "\u2234"},
				{"sim", "\u223C"}, {"cong", "\u2245"}, {"asymp", "\u2248"}, {"ne", "\u2260"},
				{"equiv", "\u2261"}, {"le", "\u2264"}, {"ge", "\u2265"}, {"sub", "\u2282"},
				{"sup", "\u2283"}, {"nsub", "\u2284"}, {"sube", "\u2286"}, {"supe", "\u2287"},
				{"oplus", "\u2295"}, {"otimes", "\u2297"}, {"perp", "\u22A5"}, {"sdot", "\u22C5"},
				{"lceil", "\u2308"}, {"rceil", "\u2309"}, {"lfloor", "\u230A"}, {"rfloor", "\u230B"},
				{"lang", "\u2329"}, {"rang", "\u232A"}, {"loz", "\u25CA"}, {"spades", "\u2660"},
				{"clubs", "\u2663"}, {"hearts", "\u2665"}, {"diams", "\u2666"}}) {
			HTML_CHARACTERS.put(code[0], code[1]);
			// htmlCodes.put(code[1], code[0]);
		}
	}

	public static String toWideString(byte[] data) {
		return new String(data, StandardCharsets.UTF_16LE);
	}

	public static byte[] toWideBytes(String wideString) {
		return wideString.getBytes(StandardCharsets.UTF_16LE);
	}

	/**
	 * Converts a {@code byte[]} to a {@link String}, with a null-delimiter. If no null-delimiter is found, the
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

	public static byte[] toCString(String string, byte[] array) {
		byte[] bytes = string.getBytes(StandardCharsets.UTF_8);

		int outLen = array.length;
		int inLen  = Math.min(bytes.length, outLen);

		System.arraycopy(bytes, 0, array, 0, inLen);
		Arrays.fill(bytes, inLen, outLen, (byte)0);

		return array;
	}

	/**
	 * Replaces all illegal file name characters in a String with the specified character.
	 * <p>
	 * The filename should not contain a path, as path separators are also regarded as illegal file name characters.
	 * <p>
	 * "Illegal filename characters" are based on the Microsoft specification, and are: {@code / \ ? * : | " < >}.
	 * Additionally, trailing periods are not allowed in Windows, so these can be removed.
	 *
	 * @param c the character to substitute the illegal characters with.
	 */
	public static String replaceIllegalFilenameChars(CharSequence name, boolean replaceTrailingPeriods, char c) {
		return replaceIllegalFilenameChars(name, replaceTrailingPeriods, ignored -> c);
	}

	/**
	 * Replaces all illegal file name characters in a String using the specified replacer.
	 * <p>
	 * The filename should not contain a path, as path separators are also regarded as illegal file name characters.
	 * <p>
	 * "Illegal filename characters" are based on the Microsoft specification, and are: {@code / \ ? * : | " < >}.
	 * Additionally, trailing periods are not allowed in Windows, so these can be removed.
	 *
	 * @param replacer the function that maps any of the above mentioned 9 characters to another (sequence of)
	 *                 characters.
	 *                 There's no explicit check whether the replacement character is in fact illegal or not.
	 */
	public static String replaceIllegalFilenameChars(CharSequence name,
	                                                 boolean replaceTrailingPeriods,
	                                                 Function<Character, Character> replacer) {
		requireNonNull(name, "name");

		StringBuilder sb = new StringBuilder(name.length());

		// Replace characters
		name.codePoints()
		    .forEach(c -> {
			    int i = Arrays.binarySearch(ILLEGAL_FILE_NAME_CHARS, (char)c);
			    if (i < 0) {
				    sb.append((char)c);
			    } else {
				    sb.append(replacer.apply((char)c));
			    }
		    });

		if (replaceTrailingPeriods) {
			while (sb.length() > 0 && sb.charAt(sb.length() - 1) == '.') {
				sb.setLength(sb.length() - 1);
			}
		}

		return sb.toString();
	}

	public static String collateASCII(String text) {
		return COMBINING_MARK_PATTERN.matcher(Normalizer.normalize(text, Normalizer.Form.NFD)).replaceAll("");
	}

	/**
	 * Specialized instance of {#link {@link #replaceIllegalFilenameChars(CharSequence, boolean, Function)}} tailored for
	 * filenames containing titles, which often includes colons.
	 */
	public static String replaceIllegalTitleFilenameChars(String name) {
		name = COLON_MATCHER_1.matcher(name).replaceAll("\\2.\\3");
		name = COLON_MATCHER_2.matcher(name).replaceAll(" - ");
		name = COLON_MATCHER_3.matcher(name).replaceAll(" - ");
		name = COLON_MATCHER_4.matcher(name).replaceAll(" - ");
		name = collateASCII(name);

		int[] codePoints = name.codePoints().map(c -> c <= 32 || c >= 127 ? '_' : c).toArray();
		name = new String(codePoints, 0, codePoints.length);

		return replaceIllegalFilenameChars(name, true, '_');
	}

	public static int utf8Length(CharSequence cs) {
		return cs.length() + cs.codePoints()
		                       .filter(cp -> cp >= 0x80)
		                       .map(cp -> cp < 0x800 ? 1 : (cp < 0x10000 ? 2 : 3))
		                       .sum();
	}

	/**
	 * Decodes "HTML-encoded characters" in a phrase to their respective human-readable characters. The phrase should
	 * not contain HTML tags or the result will be an invalid mix of HTML tags and non-HTML characters. For example,
	 * this converts "&amp;lt;" to "&lt;".
	 *
	 * @param string a phrase in valid HTML, without actual HTML tags.
	 * @return the equivalent human-readable phrase.
	 */
	@Deprecated
	public static String htmlCharsDecode(String string) {
		throw new UnsupportedCharsetException("Treading on dangerous waters");
//		return URLDecoder.decode(string, StandardCharsets.UTF_8);

//		int           l   = string.length();
//		StringBuilder out = new StringBuilder(l);
//
//		for (int i = 0; i < l; i++) {
//			char c = string.charAt(i);
//
//			if (c == '&') {
//				int j = string.indexOf(';', i + 1);
//				if (j > i && j <= i + 9) {
//					String replacement = string.substring(i + 1, j);
//
//					if (replacement.charAt(0) == '#') {
//						out.append((char)Integer.parseInt(replacement.substring(1)));
//						i = j;
//						continue;
//					}
//
//					replacement = HTML_CHARACTERS.get(replacement);
//					if (replacement != null) {
//						out.append(replacement);
//						i = j;
//						continue;
//					}
//				}
//			} else if (c == '%' && string.length() - i >= 3) {
//				String code = string.substring(i + 1, i + 3);
//				int    hi   = Character.digit(code.charAt(0), 16);
//				int    lo   = Character.digit(code.charAt(1), 16);
//				if (hi >= 0 && lo >= 0) {
//					int j = hi * 16 + lo;
//					if (j >= 20 && j <= 126) {
//						out.append((char)j);
//						i += 2;
//						continue;
//					}
//				}
//			}
//
//			out.append(c);
//		}
//
//		return out.toString();
	}

	public static boolean containsChar(String string, char ch) {
		for (char c : string.toCharArray()) {
			if (c == ch) {
				return true;
			}
		}
		return false;
	}

	public static String getControlName(char c) {
		if (c <= 0x20) {
			return CONTROL_NAMES[c];
		}

		return null;
	}

	@SuppressWarnings("CharUsedInArithmeticContext")
	public static String repeatChar(char ch, int count) {
		requireRange(1, 127, ch, "ch");
		requireAtLeast(0, count, "count");

		if (count == 0) {
			return "";
		} else if (count > 64) {
			char[] chars = new char[count];
			Arrays.fill(chars, ch);
			return new String(chars);
		} else {
			String cachedLine = REPEATING_CHARS_CACHE[ch - 1];
			if (cachedLine == null) {
				char[] chars = new char[64];
				Arrays.fill(chars, ch);
				cachedLine = new String(chars);
				REPEATING_CHARS_CACHE[ch - 1] = cachedLine;
			}

			return cachedLine.substring(0, count);
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

	public static @Nullable String removeQuotes(String string) {
		if (string == null ||
		    string.length() < 2 ||
		    string.charAt(0) != '"' ||
		    string.charAt(string.length() - 1) != '"') {
			return string;
		}

		return string.substring(1, string.length() - 1);
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
