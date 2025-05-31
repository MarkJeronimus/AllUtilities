package nl.airsupplies.utilities;

import java.util.HashMap;
import java.util.Map;

import nl.airsupplies.utilities.annotation.UtilityClass;

/**
 * @author Mark Jeronimus
 */
// Created 2025-04-27
@SuppressWarnings("SpellCheckingInspection")
@UtilityClass
public final class EnglishUtilities {
	// AI-generated, so there are probably cases missing
	private static final Map<String, String> IRREGULAR_PLURALS;

	static {
		IRREGULAR_PLURALS = new HashMap<>();
		IRREGULAR_PLURALS.put("people", "person");
		IRREGULAR_PLURALS.put("children", "child");
		IRREGULAR_PLURALS.put("teeth", "tooth");
		IRREGULAR_PLURALS.put("feet", "foot");
		IRREGULAR_PLURALS.put("geese", "goose");
		IRREGULAR_PLURALS.put("phenomena", "phenomenon");
		IRREGULAR_PLURALS.put("criteria", "criterion");
		IRREGULAR_PLURALS.put("mice", "mouse");
		IRREGULAR_PLURALS.put("lives", "life");
		IRREGULAR_PLURALS.put("vertices", "vertex");
		IRREGULAR_PLURALS.put("matrices", "matrix");
		IRREGULAR_PLURALS.put("indices", "index");
		IRREGULAR_PLURALS.put("appendices", "appendix");
		IRREGULAR_PLURALS.put("vortices", "vortex");
	}

	public static String makeSingular(String phrase) {
		int i = phrase.lastIndexOf(' ');
		if (i < 0) {
			i = 0;
		}

		return phrase.substring(0, i) + makeWordSingular(phrase.substring(i));
	}

	@SuppressWarnings("NonAsciiCharacters")
	public static String makeWordSingular(String word) {
		// Handle irregular plurals first
		String irregular = IRREGULAR_PLURALS.get(word.toLowerCase());
		if (irregular != null) {
			return irregular;
		}

		// Common plural rules
		if (word.endsWith("ies") && word.length() > 3) {
			// Words ending in "ies" → "y" (countries → country)
			return word.substring(0, word.length() - 3) + 'y';
		} else if (word.endsWith("es")) {
			if (word.endsWith("ses") && (
					word.endsWith("ases") ||
					word.endsWith("oses") ||
					word.endsWith("ises") ||
					word.endsWith("uses"))) {
				return word; // Keep as is: cases, doses, premises, uses
			}
			// Special "es" endings (boxes → box, churches → church)
			if (word.endsWith("xes") ||
			    word.endsWith("ches") ||
			    word.endsWith("shes") ||
			    word.endsWith("sses")) {
				return word.substring(0, word.length() - 2);
			}
			return word.substring(0, word.length() - 1);
		} else if (word.endsWith("s")) {
			if (word.endsWith("ss") ||
			    word.endsWith("us") ||
			    word.endsWith("is")) {
				return word; // Keep as is: glass, status, basis
			}
			// Common "s" endings (cars → car)
			return word.substring(0, word.length() - 1);
		}

		return word; // Not plural or unknown form
	}

}
