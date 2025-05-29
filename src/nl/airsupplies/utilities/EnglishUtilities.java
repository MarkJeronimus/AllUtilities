package nl.airsupplies.utilities;

import java.util.Map;

import nl.airsupplies.utilities.annotation.UtilityClass;

/**
 * @author zom-b
 */
// Created 2025-04-27
@SuppressWarnings("SpellCheckingInspection")
@UtilityClass
public final class EnglishUtilities {
	// AI-generated, so there are probably cases missing
	private static final Map<String, String> IRREGULAR_PLURALS = Map.ofEntries(
			Map.entry("people", "person"),
			Map.entry("children", "child"),
			Map.entry("teeth", "tooth"),
			Map.entry("feet", "foot"),
			Map.entry("geese", "goose"),
			Map.entry("phenomena", "phenomenon"),
			Map.entry("criteria", "criterion"),
			Map.entry("mice", "mouse"),
			Map.entry("lives", "life"),
			Map.entry("vertices", "vertex"),
			Map.entry("matrices", "matrix"),
			Map.entry("indices", "index"),
			Map.entry("appendices", "appendix"),
			Map.entry("vortices", "vortex")

	);

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
