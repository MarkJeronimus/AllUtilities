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

package nl.airsupplies.utilities.encoding;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Mark Jeronimus
 */
// Created 2007-07-04
public final class CodePoint {
	// A map that maps the name of the blocks to the block instances. (Filled
	// automatically by the constructor of each instance)
	static final         Map<String, UnicodeBlock> blocksByName                            = new TreeMap<>();
	// Some variables are dependent on the next 179 constants and 2 tables.
	// Don't shuffle/insert/delete any of these variables/values unless you are
	// revisiting everything, as functionality WILL be lost.
	// All unicode blocks in correct order:
	public static final  UnicodeBlock              BASIC_LATIN                             = new UnicodeBlock(
			"Basic Latin", 0x0000,
			0x0080, 128, "1.0.0");
	public static final  UnicodeBlock              LATIN_1_SUPPLEMENT                      = new UnicodeBlock(
			"Latin-1 Supplement",
			0x0080, 0x0100, 128, "1.0.0");
	public static final  UnicodeBlock              LATIN_EXTENDED_A                        = new UnicodeBlock(
			"Latin Extended-A", 0x0100,
			0x0180, 128, "1.0.0");
	public static final  UnicodeBlock              LATIN_EXTENDED_B                        = new UnicodeBlock(
			"Latin Extended-B", 0x0180,
			0x0250, 208, "1.0.0");
	public static final  UnicodeBlock              IPA_EXTENSIONS                          = new UnicodeBlock(
			"IPA Extensions", 0x0250,
			0x02B0, 96, "1.0.0");
	public static final  UnicodeBlock              SPACING_MODIFIER_LETTERS                = new UnicodeBlock(
			"Spacing Modifier Letters",
			0x02B0, 0x0300, 80, "1.0.0");
	public static final  UnicodeBlock              COMBINING_DIACRITICAL_MARKS             = new UnicodeBlock(
			"Combining Diacritical Marks", 0x0300,
			0x0370, 112, "1.0.0");
	public static final  UnicodeBlock              GREEK_AND_COPTIC                        = new UnicodeBlock(
			"Greek and Coptic", 0x0370,
			0x0400, 127, "1.0.0");
	public static final  UnicodeBlock              CYRILLIC                                = new UnicodeBlock(
			"Cyrillic", 0x0400, 0x0500,
			255, "1.0.0");
	public static final  UnicodeBlock              CYRILLIC_SUPPLEMENT                     = new UnicodeBlock(
			"Cyrillic Supplement",
			0x0500, 0x0530, 20, "3.2");
	public static final  UnicodeBlock              ARMENIAN                                = new UnicodeBlock(
			"Armenian", 0x0530, 0x0590,
			86, "1.0.0");
	public static final  UnicodeBlock              HEBREW                                  = new UnicodeBlock(
			"Hebrew", 0x0590,
			0x0600, 87, "1.0.0");
	public static final  UnicodeBlock              ARABIC                                  = new UnicodeBlock(
			"Arabic", 0x0600,
			0x0700, 235, "1.0.0");
	public static final  UnicodeBlock              SYRIAC                                  = new UnicodeBlock(
			"Syriac", 0x0700,
			0x0750, 77, "3.0");
	public static final  UnicodeBlock              ARABIC_SUPPLEMENT                       = new UnicodeBlock(
			"Arabic Supplement", 0x0750,
			0x0780, 30, "4.1");
	public static final  UnicodeBlock              THAANA                                  = new UnicodeBlock(
			"Thaana", 0x0780,
			0x07C0, 50, "3.0");
	public static final  UnicodeBlock              NKO                                     = new UnicodeBlock(
			"NKo", 0x07C0,
			0x0800, 59, "5.0");
	public static final  UnicodeBlock              RESERVED_BLOCK01                        = new UnicodeBlock(
			"<reserved>", 0x0800,
			0x0900, 0, "Future");
	public static final  UnicodeBlock              DEVANAGARI                              = new UnicodeBlock(
			"Devanagari", 0x0900,
			0x0980, 110, "1.0.0");
	public static final  UnicodeBlock              BENGALI                                 = new UnicodeBlock
			("Bengali", 0x0980,
			 0x0A00, 91, "1.0.0");
	public static final  UnicodeBlock              GURMUKHI                                = new UnicodeBlock(
			"Gurmukhi", 0x0A00, 0x0A80,
			77, "1.0.0");
	public static final  UnicodeBlock              GUJARATI                                = new UnicodeBlock(
			"Gujarati", 0x0A80, 0x0B00,
			83, "1.0.0");
	public static final  UnicodeBlock              ORIYA                                   = new UnicodeBlock(
			"Oriya", 0x0B00,
			0x0B80, 81, "1.0.0");
	public static final  UnicodeBlock              TAMIL                                   = new UnicodeBlock(
			"Tamil", 0x0B80,
			0x0C00, 71, "1.0.0");
	public static final  UnicodeBlock              TELUGU                                  = new UnicodeBlock(
			"Telugu", 0x0C00,
			0x0C80, 80, "1.0.0");
	public static final  UnicodeBlock              KANNADA                                 = new UnicodeBlock
			("Kannada", 0x0C80,
			 0x0D00, 86, "1.0.0");
	public static final  UnicodeBlock              MALAYALAM                               = new UnicodeBlock(
			"Malayalam", 0x0D00, 0x0D80,
			78, "1.0.0");
	public static final  UnicodeBlock              SINHALA                                 = new UnicodeBlock
			("Sinhala", 0x0D80,
			 0x0E00, 80, "3.0");
	public static final  UnicodeBlock              THAI                                    = new UnicodeBlock(
			"Thai", 0x0E00,
			0x0E80, 87, "1.0.0");
	public static final  UnicodeBlock              LAO                                     = new UnicodeBlock(
			"Lao", 0x0E80,
			0x0F00, 65, "1.0.0");
	public static final  UnicodeBlock              TIBETAN                                 = new UnicodeBlock
			("Tibetan", 0x0F00,
			 0x1000, 195, "2.0");
	public static final  UnicodeBlock              MYANMAR                                 = new UnicodeBlock
			("Myanmar", 0x1000,
			 0x10A0, 78, "3.0");
	public static final  UnicodeBlock              GEORGIAN                                = new UnicodeBlock(
			"Georgian", 0x10A0, 0x1100,
			83, "1.0.0");
	public static final  UnicodeBlock              HANGUL_JAMO                             = new UnicodeBlock(
			"Hangul Jamo", 0x1100,
			0x1200, 240, "1.1");
	public static final  UnicodeBlock              ETHIOPIC                                = new UnicodeBlock(
			"Ethiopic", 0x1200, 0x1380,
			356, "3.0");
	public static final  UnicodeBlock              ETHIOPIC_SUPPLEMENT                     = new UnicodeBlock(
			"Ethiopic Supplement",
			0x1380, 0x13A0, 26, "4.1");
	public static final  UnicodeBlock              CHEROKEE                                = new UnicodeBlock(
			"Cherokee", 0x13A0, 0x1400,
			85, "3.0");
	public static final  UnicodeBlock              UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS   = new UnicodeBlock(
			"Unified Canadian Aboriginal Syllabics",
			0x1400, 0x1680, 630, "3.0");
	public static final  UnicodeBlock              OGHAM                                   = new UnicodeBlock(
			"Ogham", 0x1680,
			0x16A0, 29, "3.0");
	public static final  UnicodeBlock              RUNIC                                   = new UnicodeBlock(
			"Runic", 0x16A0,
			0x1700, 81, "3.0");
	public static final  UnicodeBlock              TAGALOG                                 = new UnicodeBlock
			("Tagalog", 0x1700,
			 0x1720, 20, "3.2");
	public static final  UnicodeBlock              HANUNOO                                 = new UnicodeBlock
			("Hanunoo", 0x1720,
			 0x1740, 23, "3.2");
	public static final  UnicodeBlock              BUHID                                   = new UnicodeBlock(
			"Buhid", 0x1740,
			0x1760, 20, "3.2");
	public static final  UnicodeBlock              TAGBANWA                                = new UnicodeBlock(
			"Tagbanwa", 0x1760, 0x1780,
			18, "3.2");
	public static final  UnicodeBlock              KHMER                                   = new UnicodeBlock(
			"Khmer", 0x1780,
			0x1800, 114, "3.0");
	public static final  UnicodeBlock              MONGOLIAN                               = new UnicodeBlock(
			"Mongolian", 0x1800, 0x18B0,
			155, "3.0");
	public static final  UnicodeBlock              RESERVED_BLOCK02                        = new UnicodeBlock(
			"<reserved>", 0x18B0,
			0x1900, 0, "Future");
	public static final  UnicodeBlock              LIMBU                                   = new UnicodeBlock(
			"Limbu", 0x1900,
			0x1950, 66, "4.0");
	public static final  UnicodeBlock              TAI_LE                                  = new UnicodeBlock(
			"Tai Le", 0x1950,
			0x1980, 35, "4.0");
	public static final  UnicodeBlock              NEW_TAI_LUE                             = new UnicodeBlock(
			"New Tai Lue", 0x1980,
			0x19E0, 80, "4.1");
	public static final  UnicodeBlock              KHMER_SYMBOLS                           = new UnicodeBlock(
			"Khmer Symbols", 0x19E0,
			0x1A00, 32, "4.0");
	public static final  UnicodeBlock              BUGINESE                                = new UnicodeBlock(
			"Buginese", 0x1A00, 0x1A20,
			30, "4.1");
	public static final  UnicodeBlock              RESERVED_BLOCK03                        = new UnicodeBlock(
			"<reserved>", 0x1A20,
			0x1B00, 0, "Future");
	public static final  UnicodeBlock              BALINESE                                = new UnicodeBlock(
			"Balinese", 0x1B00, 0x1B80,
			121, "5.0");
	public static final  UnicodeBlock              RESERVED_BLOCK04                        = new UnicodeBlock(
			"<reserved>", 0x1B80,
			0x1D00, 0, "Future");
	public static final  UnicodeBlock              PHONETIC_EXTENSIONS                     = new UnicodeBlock(
			"Phonetic Extensions",
			0x1D00, 0x1D80, 128, "4.0");
	public static final  UnicodeBlock              PHONETIC_EXTENSIONS_SUPPLEMENT          = new UnicodeBlock(
			"Phonetic Extensions Supplement",
			0x1D80, 0x1DC0, 64, "4.1");
	public static final  UnicodeBlock              COMBINING_DIACRITICAL_MARKS_SUPPLEMENT  = new UnicodeBlock(
			"Combining Diacritical Marks Supplement",
			0x1DC0, 0x1E00, 13, "4.1");
	public static final  UnicodeBlock              LATIN_EXTENDED_ADDITIONAL               = new UnicodeBlock(
			"Latin Extended Additional",
			0x1E00, 0x1F00, 246, "1.1");
	public static final  UnicodeBlock              GREEK_EXTENDED                          = new UnicodeBlock(
			"Greek Extended", 0x1F00,
			0x2000, 233, "1.1");
	public static final  UnicodeBlock              GENERAL_PUNCTUATION                     = new UnicodeBlock(
			"General Punctuation",
			0x2000, 0x2070, 106, "1.0.0");
	public static final  UnicodeBlock              SUPERSCRIPTS_AND_SUBSCRIPTS             = new UnicodeBlock(
			"Superscripts and Subscripts", 0x2070,
			0x20A0, 34, "1.0.0");
	public static final  UnicodeBlock              CURRENCY_SYMBOLS                        = new UnicodeBlock(
			"Currency Symbols", 0x20A0,
			0x20D0, 22, "1.0.0");
	public static final  UnicodeBlock              COMBINING_DIACRITICAL_MARKS_FOR_SYMBOLS = new UnicodeBlock(
			"Combining Diacritical Marks for Symbols",
			0x20D0, 0x2100, 32, "1.0.0");
	public static final  UnicodeBlock              LETTERLIKE_SYMBOLS                      = new UnicodeBlock(
			"Letterlike Symbols",
			0x2100, 0x2150, 79, "1.0.0");
	public static final  UnicodeBlock              NUMBER_FORMS                            = new UnicodeBlock(
			"Number Forms", 0x2150,
			0x2190, 50, "1.0.0");
	public static final  UnicodeBlock              ARROWS                                  = new UnicodeBlock(
			"Arrows", 0x2190,
			0x2200, 112, "1.0.0");
	public static final  UnicodeBlock              MATHEMATICAL_OPERATORS                  = new UnicodeBlock(
			"Mathematical Operators",
			0x2200, 0x2300, 256, "1.0.0");
	public static final  UnicodeBlock              MISCELLANEOUS_TECHNICAL                 = new UnicodeBlock(
			"Miscellaneous Technical",
			0x2300, 0x2400, 232, "1.0.0");
	public static final  UnicodeBlock              CONTROL_PICTURES                        = new UnicodeBlock(
			"Control Pictures", 0x2400,
			0x2440, 39, "1.0.0");
	public static final  UnicodeBlock              OPTICAL_CHARACTER_RECOGNITION           = new UnicodeBlock(
			"Optical Character Recognition",
			0x2440, 0x2460, 11, "1.0.0");
	public static final  UnicodeBlock              ENCLOSED_ALPHANUMERICS                  = new UnicodeBlock(
			"Enclosed Alphanumerics",
			0x2460, 0x2500, 160, "1.0.0");
	public static final  UnicodeBlock              BOX_DRAWING                             = new UnicodeBlock(
			"Box Drawing", 0x2500,
			0x2580, 128, "1.0.0");
	public static final  UnicodeBlock              BLOCK_ELEMENTS                          = new UnicodeBlock(
			"Block Elements", 0x2580,
			0x25A0, 32, "1.0.0");
	public static final  UnicodeBlock              GEOMETRIC_SHAPES                        = new UnicodeBlock(
			"Geometric Shapes", 0x25A0,
			0x2600, 96, "1.0.0");
	public static final  UnicodeBlock              MISCELLANEOUS_SYMBOLS                   = new UnicodeBlock(
			"Miscellaneous Symbols",
			0x2600, 0x2700, 176, "1.0.0");
	public static final  UnicodeBlock              DINGBATS                                = new UnicodeBlock(
			"Dingbats", 0x2700, 0x27C0,
			174, "1.0.0");
	public static final  UnicodeBlock              MISCELLANEOUS_MATHEMATICAL_SYMBOLS_A    = new UnicodeBlock(
			"Miscellaneous Mathematical Symbols-A",
			0x27C0, 0x27F0, 39, "3.2");
	public static final  UnicodeBlock              SUPPLEMENTAL_ARROWS_A                   = new UnicodeBlock(
			"Supplemental Arrows-A",
			0x27F0, 0x2800, 16, "3.2");
	public static final  UnicodeBlock              BRAILLE_PATTERNS                        = new UnicodeBlock(
			"Braille Patterns", 0x2800,
			0x2900, 256, "3.0");
	public static final  UnicodeBlock              SUPPLEMENTAL_ARROWS_B                   = new UnicodeBlock(
			"Supplemental Arrows-B",
			0x2900, 0x2980, 128, "3.2");
	public static final  UnicodeBlock              MISCELLANEOUS_MATHEMATICAL_SYMBOLS_B    = new UnicodeBlock(
			"Miscellaneous Mathematical Symbols-B",
			0x2980, 0x2A00, 128, "3.2");
	public static final  UnicodeBlock              SUPPLEMENTAL_MATHEMATICAL_OPERATORS     = new UnicodeBlock(
			"Supplemental Mathematical Operators",
			0x2A00, 0x2B00, 256, "3.2");
	public static final  UnicodeBlock              MISCELLANEOUS_SYMBOLS_AND_ARROWS        = new UnicodeBlock(
			"Miscellaneous Symbols and Arrows",
			0x2B00, 0x2C00, 31, "4.0");
	public static final  UnicodeBlock              GLAGOLITIC                              = new UnicodeBlock(
			"Glagolitic", 0x2C00,
			0x2C60, 94, "4.1");
	public static final  UnicodeBlock              LATIN_EXTENDED_C                        = new UnicodeBlock(
			"Latin Extended-C", 0x2C60,
			0x2C80, 17, "5.0");
	public static final  UnicodeBlock              COPTIC                                  = new UnicodeBlock(
			"Coptic", 0x2C80,
			0x2D00, 114, "4.1");
	public static final  UnicodeBlock              GEORGIAN_SUPPLEMENT                     = new UnicodeBlock(
			"Georgian Supplement",
			0x2D00, 0x2D30, 38, "4.1");
	public static final  UnicodeBlock              TIFINAGH                                = new UnicodeBlock(
			"Tifinagh", 0x2D30, 0x2D80,
			55, "4.1");
	public static final  UnicodeBlock              ETHIOPIC_EXTENDED                       = new UnicodeBlock(
			"Ethiopic Extended", 0x2D80,
			0x2DE0, 79, "4.1");
	public static final  UnicodeBlock              RESERVED_BLOCK05                        = new UnicodeBlock(
			"<reserved>", 0x2DE0,
			0x2E00, 0, "Future");
	public static final  UnicodeBlock              SUPPLEMENTAL_PUNCTUATION                = new UnicodeBlock(
			"Supplemental Punctuation",
			0x2E00, 0x2E80, 26, "4.1");
	public static final  UnicodeBlock              CJK_RADICALS_SUPPLEMENT                 = new UnicodeBlock(
			"CJK Radicals Supplement",
			0x2E80, 0x2F00, 115, "3.0");
	public static final  UnicodeBlock              KANGXI_RADICALS                         = new UnicodeBlock(
			"Kangxi Radicals", 0x2F00,
			0x2FE0, 214, "3.0");
	public static final  UnicodeBlock              RESERVED_BLOCK06                        = new UnicodeBlock(
			"<reserved>", 0x2FE0,
			0x2FF0, 0, "Future");
	public static final  UnicodeBlock              IDEOGRAPHIC_DESCRIPTION_CHARACTERS      = new UnicodeBlock(
			"Ideographic Description Characters",
			0x2FF0, 0x3000, 12, "3.0");
	public static final  UnicodeBlock              CJK_SYMBOLS_AND_PUNCTUATION             = new UnicodeBlock(
			"CJK Symbols and Punctuation", 0x3000,
			0x3040, 64, "1.0.0");
	public static final  UnicodeBlock              HIRAGANA                                = new UnicodeBlock(
			"Hiragana", 0x3040, 0x30A0,
			93, "1.0.0");
	public static final  UnicodeBlock              KATAKANA                                = new UnicodeBlock(
			"Katakana", 0x30A0, 0x3100,
			96, "1.0.0");
	public static final  UnicodeBlock              BOPOMOFO                                = new UnicodeBlock(
			"Bopomofo", 0x3100, 0x3130,
			40, "1.0.0");
	public static final  UnicodeBlock              HANGUL_COMPATIBILITY_JAMO               = new UnicodeBlock(
			"Hangul Compatibility Jamo",
			0x3130, 0x3190, 94, "1.0.0");
	public static final  UnicodeBlock              KANBUN                                  = new UnicodeBlock(
			"Kanbun", 0x3190,
			0x31A0, 16, "1.0.0");
	public static final  UnicodeBlock              BOPOMOFO_EXTENDED                       = new UnicodeBlock(
			"Bopomofo Extended", 0x31A0,
			0x31C0, 24, "3.0");
	public static final  UnicodeBlock              CJK_STROKES                             = new UnicodeBlock(
			"CJK Strokes", 0x31C0,
			0x31F0, 16, "4.1");
	public static final  UnicodeBlock              KATAKANA_PHONETIC_EXTENSIONS            = new UnicodeBlock(
			"Katakana Phonetic Extensions", 0x31F0,
			0x3200, 16, "3.2");
	public static final  UnicodeBlock              ENCLOSED_CJK_LETTERS_AND_MONTHS         = new UnicodeBlock(
			"Enclosed CJK Letters and Months",
			0x3200, 0x3300, 242, "1.0.0");
	public static final  UnicodeBlock              CJK_COMPATIBILITY                       = new UnicodeBlock(
			"CJK Compatibility", 0x3300,
			0x3400, 256, "1.0.0");
	public static final  UnicodeBlock              CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A      = new UnicodeBlock(
			"CJK Unified Ideographs Extension A",
			0x3400, 0x4DC0, 6582, "3.0");
	public static final  UnicodeBlock              YIJING_HEXAGRAM_SYMBOLS                 = new UnicodeBlock(
			"Yijing Hexagram Symbols",
			0x4DC0, 0x4E00, 64, "4.0");
	public static final  UnicodeBlock              CJK_UNIFIED_IDEOGRAPHS                  = new UnicodeBlock(
			"CJK Unified Ideographs",
			0x4E00, 0xA000, 20924, "1.0.1");
	public static final  UnicodeBlock              YI_SYLLABLES                            = new UnicodeBlock(
			"Yi Syllables", 0xA000,
			0xA490, 1165, "3.0");
	public static final  UnicodeBlock              YI_RADICALS                             = new UnicodeBlock(
			"Yi Radicals", 0xA490,
			0xA4D0, 55, "3.0");
	public static final  UnicodeBlock              RESERVED_BLOCK07                        = new UnicodeBlock(
			"<reserved>", 0xA4D0,
			0xA700, 0, "Future");
	public static final  UnicodeBlock              MODIFIER_TONE_LETTERS                   = new UnicodeBlock(
			"Modifier Tone Letters",
			0xA700, 0xA720, 27, "4.1");
	public static final  UnicodeBlock              LATIN_EXTENDED_D                        = new UnicodeBlock(
			"Latin Extended-D", 0xA720,
			0xA800, 2, "5.0");
	public static final  UnicodeBlock              SYLOTI_NAGRI                            = new UnicodeBlock(
			"Syloti Nagri", 0xA800,
			0xA830, 44, "4.1");
	public static final  UnicodeBlock              RESERVED_BLOCK08                        = new UnicodeBlock(
			"<reserved>", 0xA830,
			0xA840, 0, "Future");
	public static final  UnicodeBlock              PHAGS_PA                                = new UnicodeBlock(
			"Phags-pa", 0xA840, 0xA880,
			56, "5.0");
	public static final  UnicodeBlock              RESERVED_BLOCK09                        = new UnicodeBlock(
			"<reserved>", 0xA880,
			0xAC00, 0, "Future");
	public static final  UnicodeBlock              HANGUL_SYLLABLES                        = new UnicodeBlock(
			"Hangul Syllables", 0xAC00,
			0xD7B0, 11172, "2.0");
	public static final  UnicodeBlock              RESERVED_BLOCK10                        = new UnicodeBlock(
			"<reserved>", 0xD7B0,
			0xD800, 0, "Future");
	public static final  UnicodeBlock              HIGH_SURROGATES                         = new UnicodeBlock(
			"High Surrogates", 0xD800,
			0xDB80, 0, "Future");
	public static final  UnicodeBlock              HIGH_PRIVATE_USE_SURROGATES             = new UnicodeBlock(
			"High Private Use Surrogates", 0xDB80,
			0xDC00, 0, "Future");
	public static final  UnicodeBlock              LOW_SURROGATES                          = new UnicodeBlock(
			"Low Surrogates", 0xDC00,
			0xE000, 0, "Future");
	public static final  UnicodeBlock              PRIVATE_USE_AREA                        = new UnicodeBlock(
			"Private Use Area", 0xE000,
			0xF900, 6400, "1.0.0");
	public static final  UnicodeBlock              CJK_COMPATIBILITY_IDEOGRAPHS            = new UnicodeBlock(
			"CJK Compatibility Ideographs", 0xF900,
			0xFB00, 467, "1.0.1");
	public static final  UnicodeBlock              ALPHABETIC_PRESENTATION_FORMS           = new UnicodeBlock(
			"Alphabetic Presentation Forms",
			0xFB00, 0xFB50, 58, "1.1");
	public static final  UnicodeBlock              ARABIC_PRESENTATION_FORMS_A             = new UnicodeBlock(
			"Arabic Presentation Forms-A", 0xFB50,
			0xFE00, 595, "1.1");
	public static final  UnicodeBlock              VARIATION_SELECTORS                     = new UnicodeBlock(
			"Variation Selectors",
			0xFE00, 0xFE10, 16, "3.2");
	public static final  UnicodeBlock              VERTICAL_FORMS                          = new UnicodeBlock(
			"Vertical Forms", 0xFE10,
			0xFE20, 10, "4.1");
	public static final  UnicodeBlock              COMBINING_HALF_MARKS                    = new UnicodeBlock(
			"Combining Half Marks",
			0xFE20, 0xFE30, 4, "1.1");
	public static final  UnicodeBlock              CJK_COMPATIBILITY_FORMS                 = new UnicodeBlock(
			"CJK Compatibility Forms",
			0xFE30, 0xFE50, 32, "1.0.0");
	public static final  UnicodeBlock              SMALL_FORM_VARIANTS                     = new UnicodeBlock(
			"Small Form Variants",
			0xFE50, 0xFE70, 26, "1.0.0");
	public static final  UnicodeBlock              ARABIC_PRESENTATION_FORMS_B             = new UnicodeBlock(
			"Arabic Presentation Forms-B", 0xFE70,
			0xFF00, 141, "1.0.0");
	public static final  UnicodeBlock              HALFWIDTH_AND_FULLWIDTH_FORMS           = new UnicodeBlock(
			"Halfwidth and Fullwidth Forms",
			0xFF00, 0xFFF0, 225, "1.0.0");
	public static final  UnicodeBlock              SPECIALS                                = new UnicodeBlock(
			"Specials", 0xFFF0,
			0x010000, 5, "1.0.0");
	public static final  UnicodeBlock              LINEAR_B_SYLLABARY                      = new UnicodeBlock(
			"Linear B Syllabary",
			0x010000, 0x010080, 88, "4.0");
	public static final  UnicodeBlock              LINEAR_B_IDEOGRAMS                      = new UnicodeBlock(
			"Linear B Ideograms",
			0x010080, 0x010100, 123, "4.0");
	public static final  UnicodeBlock              AEGEAN_NUMBERS                          = new UnicodeBlock(
			"Aegean Numbers", 0x010100,
			0x010140, 57, "4.0");
	public static final  UnicodeBlock              ANCIENT_GREEK_NUMBERS                   = new UnicodeBlock(
			"Ancient Greek Numbers",
			0x010140, 0x010190, 75, "4.1");
	public static final  UnicodeBlock              RESERVED_BLOCK11                        = new UnicodeBlock(
			"<reserved>", 0x010190,
			0x010300, 0, "Future");
	public static final  UnicodeBlock              OLD_ITALIC                              = new UnicodeBlock(
			"Old Italic", 0x010300,
			0x010330, 35, "3.1");
	public static final  UnicodeBlock              GOTHIC                                  = new UnicodeBlock(
			"Gothic", 0x010330,
			0x010350, 27, "3.1");
	public static final  UnicodeBlock              RESERVED_BLOCK12                        = new UnicodeBlock(
			"<reserved>", 0x010350,
			0x010380, 0, "Future");
	public static final  UnicodeBlock              UGARITIC                                = new UnicodeBlock(
			"Ugaritic", 0x010380,
			0x0103A0, 31, "4.0");
	public static final  UnicodeBlock              OLD_PERSIAN                             = new UnicodeBlock(
			"Old Persian", 0x0103A0,
			0x0103E0, 50, "4.1");
	public static final  UnicodeBlock              RESERVED_BLOCK13                        = new UnicodeBlock(
			"<reserved>", 0x0103E0,
			0x010400, 0, "Future");
	public static final  UnicodeBlock              DESERET                                 = new UnicodeBlock
			("Deseret", 0x010400,
			 0x010450, 80, "3.1");
	public static final  UnicodeBlock              SHAVIAN                                 = new UnicodeBlock
			("Shavian", 0x010450,
			 0x010480, 48, "4.0");
	public static final  UnicodeBlock              OSMANYA                                 = new UnicodeBlock
			("Osmanya", 0x010480,
			 0x0104B0, 40, "4.0");
	public static final  UnicodeBlock              RESERVED_BLOCK14                        = new UnicodeBlock(
			"<reserved>", 0x0104B0,
			0x010800, 0, "Future");
	public static final  UnicodeBlock              CYPRIOT_SYLLABARY                       = new UnicodeBlock(
			"Cypriot Syllabary",
			0x010800, 0x010840, 55, "4.0");
	public static final  UnicodeBlock              RESERVED_BLOCK15                        = new UnicodeBlock(
			"<reserved>", 0x010840,
			0x010900, 0, "Future");
	public static final  UnicodeBlock              PHOENICIAN                              = new UnicodeBlock(
			"Phoenician", 0x010900,
			0x010920, 27, "5.0");
	public static final  UnicodeBlock              RESERVED_BLOCK16                        = new UnicodeBlock(
			"<reserved>", 0x010920,
			0x010A00, 0, "Future");
	public static final  UnicodeBlock              KHAROSHTHI                              = new UnicodeBlock(
			"Kharoshthi", 0x010A00,
			0x010A60, 65, "4.1");
	public static final  UnicodeBlock              RESERVED_BLOCK17                        = new UnicodeBlock(
			"<reserved>", 0x010A60,
			0x012000, 0, "Future");
	public static final  UnicodeBlock              CUNEIFORM                               = new UnicodeBlock(
			"Cuneiform", 0x012000,
			0x012400, 879, "5.0");
	public static final  UnicodeBlock              CUNEIFORM_NUMBERS_AND_PUNCTUATION       = new UnicodeBlock(
			"Cuneiform Numbers and Punctuation",
			0x012400, 0x012480, 103, "5.0");
	public static final  UnicodeBlock              RESERVED_BLOCK18                        = new UnicodeBlock(
			"<reserved>", 0x012480,
			0x01D000, 0, "Future");
	public static final  UnicodeBlock              BYZANTINE_MUSICAL_SYMBOLS               = new UnicodeBlock(
			"Byzantine Musical Symbols",
			0x01D000, 0x01D100, 246, "3.1");
	public static final  UnicodeBlock              MUSICAL_SYMBOLS                         = new UnicodeBlock(
			"Musical Symbols", 0x01D100,
			0x01D200, 219, "3.1");
	public static final  UnicodeBlock              ANCIENT_GREEK_MUSICAL_NOTATION          = new UnicodeBlock(
			"Ancient Greek Musical Notation",
			0x01D200, 0x01D250, 70, "4.1");
	public static final  UnicodeBlock              RESERVED_BLOCK19                        = new UnicodeBlock(
			"<reserved>", 0x01D250,
			0x01D300, 0, "Future");
	public static final  UnicodeBlock              TAI_XUAN_JING_SYMBOLS                   = new UnicodeBlock(
			"Tai Xuan Jing Symbols",
			0x01D300, 0x01D360, 87, "4.0");
	public static final  UnicodeBlock              COUNTING_ROD_NUMERALS                   = new UnicodeBlock(
			"Counting Rod Numerals",
			0x01D360, 0x01D380, 18, "5.0");
	public static final  UnicodeBlock              RESERVED_BLOCK20                        = new UnicodeBlock(
			"<reserved>", 0x01D380,
			0x01D400, 0, "Future");
	public static final  UnicodeBlock              MATHEMATICAL_ALPHANUMERIC_SYMBOLS       = new UnicodeBlock(
			"Mathematical Alphanumeric Symbols",
			0x01D400, 0x01D800, 996, "3.1");
	public static final  UnicodeBlock              RESERVED_BLOCK21                        = new UnicodeBlock(
			"<reserved>", 0x01D800,
			0x020000, 0, "Future");
	public static final  UnicodeBlock              CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B      = new UnicodeBlock(
			"CJK Unified Ideographs Extension B",
			0x020000, 0x02A6E0, 42711, "3.1");
	public static final  UnicodeBlock              RESERVED_BLOCK22                        = new UnicodeBlock(
			"<reserved>", 0x02A6E0,
			0x02F800, 0, "Future");
	public static final  UnicodeBlock              CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT = new UnicodeBlock(
			"CJK Compatibility Ideographs Supplement",
			0x02F800, 0x02FA20, 542, "3.1");
	public static final  UnicodeBlock              RESERVED_BLOCK23                        = new UnicodeBlock(
			"<reserved>", 0x02FA20,
			0x0E0000, 0, "Future");
	public static final  UnicodeBlock              TAGS                                    = new UnicodeBlock(
			"Tags", 0x0E0000,
			0x0E0080, 97, "3.1");
	public static final  UnicodeBlock              RESERVED_BLOCK24                        = new UnicodeBlock(
			"<reserved>", 0x0E0080,
			0x0E0100, 0, "Future");
	public static final  UnicodeBlock              VARIATION_SELECTORS_SUPPLEMENT          = new UnicodeBlock(
			"Variation Selectors Supplement",
			0x0E0100, 0x0E01F0, 240, "4.0");
	public static final  UnicodeBlock              RESERVED_BLOCK25                        = new UnicodeBlock(
			"<reserved>", 0x0E01F0,
			0x0F0000, 0, "Future");
	public static final  UnicodeBlock              SUPPLEMENTARY_PRIVATE_USE_AREA_A        = new UnicodeBlock(
			"Supplementary Private Use Area-A",
			0x0F0000, 0x100000, 65534, "2.0");
	public static final  UnicodeBlock              SUPPLEMENTARY_PRIVATE_USE_AREA_B        = new UnicodeBlock(
			"Supplementary Private Use Area-B",
			0x100000, 0x110000, 65534, "2.0");
	// Array of all unicode blocks in correct order:
	public static final  UnicodeBlock[]            blockInstances                          = {
			BASIC_LATIN,
			LATIN_1_SUPPLEMENT,
			LATIN_EXTENDED_A,
			LATIN_EXTENDED_B,
			IPA_EXTENSIONS,
			SPACING_MODIFIER_LETTERS,
			COMBINING_DIACRITICAL_MARKS,
			GREEK_AND_COPTIC,
			CYRILLIC,
			CYRILLIC_SUPPLEMENT,
			ARMENIAN,
			HEBREW,
			ARABIC,
			SYRIAC,
			ARABIC_SUPPLEMENT,
			THAANA,
			NKO,
			RESERVED_BLOCK01,
			DEVANAGARI,
			BENGALI,
			GURMUKHI,
			GUJARATI,
			ORIYA,
			TAMIL,
			TELUGU,
			KANNADA,
			MALAYALAM,
			SINHALA,
			THAI,
			LAO,
			TIBETAN,
			MYANMAR,
			GEORGIAN,
			HANGUL_JAMO,
			ETHIOPIC,
			ETHIOPIC_SUPPLEMENT,
			CHEROKEE,
			UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS,
			OGHAM,
			RUNIC,
			TAGALOG,
			HANUNOO,
			BUHID,
			TAGBANWA,
			KHMER,
			MONGOLIAN,
			RESERVED_BLOCK02,
			LIMBU,
			TAI_LE,
			NEW_TAI_LUE,
			KHMER_SYMBOLS,
			BUGINESE,
			RESERVED_BLOCK03,
			BALINESE,
			RESERVED_BLOCK04,
			PHONETIC_EXTENSIONS,
			PHONETIC_EXTENSIONS_SUPPLEMENT,
			COMBINING_DIACRITICAL_MARKS_SUPPLEMENT,
			LATIN_EXTENDED_ADDITIONAL,
			GREEK_EXTENDED,
			GENERAL_PUNCTUATION,
			SUPERSCRIPTS_AND_SUBSCRIPTS,
			CURRENCY_SYMBOLS,
			COMBINING_DIACRITICAL_MARKS_FOR_SYMBOLS,
			LETTERLIKE_SYMBOLS,
			NUMBER_FORMS,
			ARROWS,
			MATHEMATICAL_OPERATORS,
			MISCELLANEOUS_TECHNICAL,
			CONTROL_PICTURES,
			OPTICAL_CHARACTER_RECOGNITION,
			ENCLOSED_ALPHANUMERICS,
			BOX_DRAWING,
			BLOCK_ELEMENTS,
			GEOMETRIC_SHAPES,
			MISCELLANEOUS_SYMBOLS,
			DINGBATS,
			MISCELLANEOUS_MATHEMATICAL_SYMBOLS_A,
			SUPPLEMENTAL_ARROWS_A,
			BRAILLE_PATTERNS,
			SUPPLEMENTAL_ARROWS_B,
			MISCELLANEOUS_MATHEMATICAL_SYMBOLS_B,
			SUPPLEMENTAL_MATHEMATICAL_OPERATORS,
			MISCELLANEOUS_SYMBOLS_AND_ARROWS,
			GLAGOLITIC,
			LATIN_EXTENDED_C,
			COPTIC,
			GEORGIAN_SUPPLEMENT,
			TIFINAGH,
			ETHIOPIC_EXTENDED,
			RESERVED_BLOCK05,
			SUPPLEMENTAL_PUNCTUATION,
			CJK_RADICALS_SUPPLEMENT,
			KANGXI_RADICALS,
			RESERVED_BLOCK06,
			IDEOGRAPHIC_DESCRIPTION_CHARACTERS,
			CJK_SYMBOLS_AND_PUNCTUATION,
			HIRAGANA,
			KATAKANA,
			BOPOMOFO,
			HANGUL_COMPATIBILITY_JAMO,
			KANBUN,
			BOPOMOFO_EXTENDED,
			CJK_STROKES,
			KATAKANA_PHONETIC_EXTENSIONS,
			ENCLOSED_CJK_LETTERS_AND_MONTHS,
			CJK_COMPATIBILITY,
			CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A,
			YIJING_HEXAGRAM_SYMBOLS,
			CJK_UNIFIED_IDEOGRAPHS,
			YI_SYLLABLES,
			YI_RADICALS,
			RESERVED_BLOCK07,
			MODIFIER_TONE_LETTERS,
			LATIN_EXTENDED_D,
			SYLOTI_NAGRI,
			RESERVED_BLOCK08,
			PHAGS_PA,
			RESERVED_BLOCK09,
			HANGUL_SYLLABLES,
			RESERVED_BLOCK10,
			HIGH_SURROGATES,
			HIGH_PRIVATE_USE_SURROGATES,
			LOW_SURROGATES,
			PRIVATE_USE_AREA,
			CJK_COMPATIBILITY_IDEOGRAPHS,
			ALPHABETIC_PRESENTATION_FORMS,
			ARABIC_PRESENTATION_FORMS_A,
			VARIATION_SELECTORS,
			VERTICAL_FORMS,
			COMBINING_HALF_MARKS,
			CJK_COMPATIBILITY_FORMS,
			SMALL_FORM_VARIANTS,
			ARABIC_PRESENTATION_FORMS_B,
			HALFWIDTH_AND_FULLWIDTH_FORMS,
			SPECIALS,
			LINEAR_B_SYLLABARY,
			LINEAR_B_IDEOGRAMS,
			AEGEAN_NUMBERS,
			ANCIENT_GREEK_NUMBERS,
			RESERVED_BLOCK11,
			OLD_ITALIC,
			GOTHIC,
			RESERVED_BLOCK12,
			UGARITIC,
			OLD_PERSIAN,
			RESERVED_BLOCK13,
			DESERET,
			SHAVIAN,
			OSMANYA,
			RESERVED_BLOCK14,
			CYPRIOT_SYLLABARY,
			RESERVED_BLOCK15,
			PHOENICIAN,
			RESERVED_BLOCK16,
			KHAROSHTHI,
			RESERVED_BLOCK17,
			CUNEIFORM,
			CUNEIFORM_NUMBERS_AND_PUNCTUATION,
			RESERVED_BLOCK18,
			BYZANTINE_MUSICAL_SYMBOLS,
			MUSICAL_SYMBOLS,
			ANCIENT_GREEK_MUSICAL_NOTATION,
			RESERVED_BLOCK19,
			TAI_XUAN_JING_SYMBOLS,
			COUNTING_ROD_NUMERALS,
			RESERVED_BLOCK20,
			MATHEMATICAL_ALPHANUMERIC_SYMBOLS,
			RESERVED_BLOCK21,
			CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B,
			RESERVED_BLOCK22,
			CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT,
			RESERVED_BLOCK23,
			TAGS,
			RESERVED_BLOCK24,
			VARIATION_SELECTORS_SUPPLEMENT,
			RESERVED_BLOCK25,
			SUPPLEMENTARY_PRIVATE_USE_AREA_A,
			SUPPLEMENTARY_PRIVATE_USE_AREA_B,
			};
	// Array of starting code points for all unicode blocks, in order:
	private static final int[]                     blockStartingCodePoints                 = {
			0x0000,
			0x0080,
			0x0100,
			0x0180,
			0x0250,
			0x02B0,
			0x0300,
			0x0370,
			0x0400,
			0x0500,
			0x0530,
			0x0590,
			0x0600,
			0x0700,
			0x0750,
			0x0780,
			0x07C0,
			0x0800,
			0x0900,
			0x0980,
			0x0A00,
			0x0A80,
			0x0B00,
			0x0B80,
			0x0C00,
			0x0C80,
			0x0D00,
			0x0D80,
			0x0E00,
			0x0E80,
			0x0F00,
			0x1000,
			0x10A0,
			0x1100,
			0x1200,
			0x1380,
			0x13A0,
			0x1400,
			0x1680,
			0x16A0,
			0x1700,
			0x1720,
			0x1740,
			0x1760,
			0x1780,
			0x1800,
			0x18B0,
			0x1900,
			0x1950,
			0x1980,
			0x19E0,
			0x1A00,
			0x1A20,
			0x1B00,
			0x1B80,
			0x1D00,
			0x1D80,
			0x1DC0,
			0x1E00,
			0x1F00,
			0x2000,
			0x2070,
			0x20A0,
			0x20D0,
			0x2100,
			0x2150,
			0x2190,
			0x2200,
			0x2300,
			0x2400,
			0x2440,
			0x2460,
			0x2500,
			0x2580,
			0x25A0,
			0x2600,
			0x2700,
			0x27C0,
			0x27F0,
			0x2800,
			0x2900,
			0x2980,
			0x2A00,
			0x2B00,
			0x2C00,
			0x2C60,
			0x2C80,
			0x2D00,
			0x2D30,
			0x2D80,
			0x2DE0,
			0x2E00,
			0x2E80,
			0x2F00,
			0x2FE0,
			0x2FF0,
			0x3000,
			0x3040,
			0x30A0,
			0x3100,
			0x3130,
			0x3190,
			0x31A0,
			0x31C0,
			0x31F0,
			0x3200,
			0x3300,
			0x3400,
			0x4DC0,
			0x4E00,
			0xA000,
			0xA490,
			0xA4D0,
			0xA700,
			0xA720,
			0xA800,
			0xA830,
			0xA840,
			0xA880,
			0xAC00,
			0xD7B0,
			0xD800,
			0xDB80,
			0xDC00,
			0xE000,
			0xF900,
			0xFB00,
			0xFB50,
			0xFE00,
			0xFE10,
			0xFE20,
			0xFE30,
			0xFE50,
			0xFE70,
			0xFF00,
			0xFFF0,
			0x010000,
			0x010080,
			0x010100,
			0x010140,
			0x010190,
			0x010300,
			0x010330,
			0x010350,
			0x010380,
			0x0103A0,
			0x0103E0,
			0x010400,
			0x010450,
			0x010480,
			0x0104B0,
			0x010800,
			0x010840,
			0x010900,
			0x010920,
			0x010A00,
			0x010A60,
			0x012000,
			0x012400,
			0x012480,
			0x01D000,
			0x01D100,
			0x01D200,
			0x01D250,
			0x01D300,
			0x01D360,
			0x01D380,
			0x01D400,
			0x01D800,
			0x020000,
			0x02A6E0,
			0x02F800,
			0x02FA20,
			0x0E0000,
			0x0E0080,
			0x0E0100,
			0x0E01F0,
			0x0F0000,
			0x100000,
			};
	public static final  int                       NUM_UNICODE_BLOCKS                      = UnicodeBlock.count;
	// The indices to unicode blocks that belong to one of the scripts:
	private static final int[]                     CHINESE_INDICES                         = {92, 93, 95, 96, 99, 101,
	                                                                                          102, 103, 105, 106,
	                                                                                          107, 109, 125, 131, 132,
	                                                                                          134};
	private static final int[]                     JAPANESE_INDICES                        = {92, 93, 95, 96, 97, 98,
	                                                                                          101, 103, 104, 105,
	                                                                                          106, 107, 109, 125, 131,
	                                                                                          132, 134};
	private static final int[]                     KOREAN_INDICES                          = {33, 92, 93, 95, 96, 100,
	                                                                                          101, 103, 105, 106,
	                                                                                          107, 109, 119, 125, 131,
	                                                                                          132, 134};

	public static UnicodeBlock getBlock(int codePoint) {
		if (!isValid(codePoint)) {
			throw new IllegalArgumentException();
		}

		int left    = 0;
		int right   = blockStartingCodePoints.length;
		int current = right >>> 1;
		while (right - left > 1) {
			if (codePoint >= blockStartingCodePoints[current]) {
				left = current;
			} else {
				right = current;
			}
			current = right + left >>> 1;
		}
		return blockInstances[current];
	}

	public static UnicodeBlock getBlockByName(String blockName) {
		return blocksByName.get(blockName);
	}

	/**
	 * Determines whether the specified code point is a valid Unicode code point value in the range of
	 * {@code 0x0000} to {@code 0x10FFFF} inclusive. This method is equivalent to the expression:
	 *
	 * <blockquote>
	 *
	 * <pre>
	 * codePoint &gt;= 0x0000 &amp;&amp; codePoint &lt;= 0x10FFFF
	 * </pre>
	 *
	 * </blockquote>
	 *
	 * @param codePoint the Unicode code point to be tested
	 * @return {@code true} if the specified code point value is a valid code point value; {@code false}
	 * otherwise.
	 */
	public static boolean isValid(int codePoint) {
		return codePoint >= 0 && codePoint < 0x00110000;
	}

	/**
	 * Determines whether the specified character (Unicode code point) is in the supplementary character range. The
	 * method call is equivalent to the expression: <blockquote>
	 *
	 * <pre>
	 * codePoint &gt;= 0x10000 &amp;&amp; codePoint &lt;= 0x10FFFF
	 * </pre>
	 *
	 * </blockquote>
	 *
	 * @param codePoint the character (Unicode code point) to be tested
	 * @return {@code true} if the specified character is in the Unicode supplementary character range;
	 * {@code false} otherwise.
	 */
	public static boolean isSupplementary(int codePoint) {
		return codePoint >= 0x00010000 && codePoint < 0x00110000;
	}

	/**
	 * Code is low-surrogate is the following 16-bit pattern is matched: {@code 1101.11??.????.????}
	 */
	public static boolean isLowSurrogate(int ch) {
		return (ch & 0xFC00) == 0xDC00;
	}

	/**
	 * Code is high-surrogate is the following 16-bit pattern is matched: {@code 1101.10??.????.????}
	 */
	public static boolean isHighSurrogate(int ch) {
		return (ch & 0xFC00) == 0xD800;
	}

	/**
	 * Determines whether the specified pair of {@code char} values is a valid surrogate pair. This method is
	 * equivalent to the expression: {@code  isHighSurrogate(high) &amp;&amp; isLowSurrogate(low) }
	 */
	public static boolean isSurrogatePair(int high, int low) {
		return isHighSurrogate(high) && isLowSurrogate(low);
	}

	/**
	 * Determines whether the specified pair of {@code char} values is a valid surrogate pair.
	 */
	public static boolean isSurrogatePair(char[] charArray, int index) {
		if (!isHighSurrogate(charArray[index])) {
			return false;
		}

		++index;
		return index < charArray.length
		       && isLowSurrogate(charArray[index]);
	}

	/**
	 * Converts the specified surrogate pair to its supplementary code point value. This method does not validate the
	 * specified surrogate pair. The caller must validate it using {@link #isSurrogatePair(char[], int)} if necessary.
	 *
	 * @param high the high-surrogate code unit
	 * @param low  the low-surrogate code unit
	 * @return the supplementary code point composed from the specified surrogate pair.
	 */
	public static int surrogateToCodePoint(int high, int low) {
		return ((high & 0x3FF) << 10 | low & 0x3FF) + 0x00010000;
	}

	/**
	 * Converts the specified supplementary code value point to the surrogate pair . This method does not validate the
	 * specified surrogate pair. The caller must validate it using {@link #isValid(int)} if necessary.
	 *
	 * @param charArray the UTF-16 {@code char} array
	 * @param index     the index to the {@code char} values (Unicode code units) in the {@code char} array to
	 *                  be converted
	 */
	public static void codePointToSurrogate(int codePoint, char[] charArray, int index) {
		int offset = codePoint - 0x00010000;
		charArray[index]     = (char)(offset >>> 10 & 0x03FF | 0xD800);
		charArray[index + 1] = (char)(offset & 0x03FF | 0xDC00);
	}

	/**
	 * Converts the specified character (Unicode code point) to its UTF-16 representation stored in a {@code char}
	 * array. If the specified code point is a BMP (Basic Multilingual Plane or Plane 0) value, the resulting
	 * {@code char} array has the same value as {@code codePoint}. If the specified code point is a
	 * supplementary code point, the resulting {@code char} array has the corresponding surrogate pair.
	 *
	 * @param codePoint a Unicode code point
	 * @return a {@code char} array having {@code codePoint}'s UTF-16 representation.
	 * @throws IllegalArgumentException if the specified {@code codePoint} is not a valid Unicode code point.
	 */
	public static char[] codePointToChars(int codePoint) {
		if (codePoint < 0 || codePoint >= 0x00110000) {
			throw new IllegalArgumentException();
		}
		if (codePoint < 0x00010000) {
			return new char[]{(char)codePoint};
		}
		char[] result = new char[2];
		codePointToSurrogate(codePoint, result, 0);
		return result;
	}

	/**
	 * Converts the specified character (Unicode code point) to its UTF-16 representation. If the specified code point
	 * is a BMP (Basic Multilingual Plane or Plane 0) value, the same value is stored in {@code dst[dstIndex]}, and
	 * 1 is returned. If the specified code point is a supplementary character, its surrogate values are stored in
	 * {@code dst[dstIndex]} (high-surrogate) and {@code dst[dstIndex+1]} (low-surrogate), and 2 is returned.
	 *
	 * @param codePoint the character (Unicode code point) to be converted.
	 * @param charArray an array of {@code char} in which the {@code codePoint}'s UTF-16 value is stored.
	 * @param index     the start index into the {@code dst} array where the converted value is stored.
	 * @return 1 if the code point is a BMP code point, 2 if the code point is a supplementary code point.
	 * @throws IllegalArgumentException  if the specified {@code codePoint} is not a valid Unicode code point.
	 * @throws NullPointerException      if the specified {@code dst} is null.
	 * @throws IndexOutOfBoundsException if {@code dstIndex} is negative or not less than {@code dst.length},
	 *                                   or if {@code dst} at {@code dstIndex} doesn't have enough array
	 *                                   element(s) to store the resulting {@code char} value(s). (If
	 *                                   {@code dstIndex} is equal to {@code dst.length-1} and the specified
	 *                                   {@code codePoint} is a supplementary character, the high-surrogate value
	 *                                   is not stored in {@code dst[dstIndex]}.)
	 */
	public static int codePointToChars(int codePoint, char[] charArray, int index) {
		if (codePoint < 0 || codePoint >= 0x00110000) {
			throw new IllegalArgumentException();
		}
		if (codePoint < 0x00010000) {
			charArray[index] = (char)codePoint;
			return 1;
		}
		codePointToSurrogate(codePoint, charArray, index);
		return 2;
	}

	/**
	 * Returns the code point at the given index of the {@code char} array. If the {@code char} value at the
	 * given index in the {@code char} array is in the high-surrogate range, the following index is less than the
	 * length of the {@code char} array, and the {@code char} value at the following index is in the
	 * low-surrogate range, then the supplementary code point corresponding to this surrogate pair is returned.
	 * Otherwise, the {@code char} value at the given index is returned.
	 *
	 * @param charArray the UTF-16 {@code char} array
	 * @param index     the index to the {@code char} values (Unicode code units) in the {@code char} array to
	 *                  be converted
	 * @return the Unicode code point at the given index
	 * @throws NullPointerException      if {@code a} is null.
	 * @throws IndexOutOfBoundsException if the value {@code index} is negative or not less than the length of the
	 *                                   {@code char} array.
	 */
	public static int charsToCodePoint(char[] charArray, int index) {
		int c1 = charArray[index];
		if (isHighSurrogate(c1)) {
			++index;
			if (index < charArray.length) {
				int c2 = charArray[index];
				if (isLowSurrogate(c2)) {
					return surrogateToCodePoint(c1, c2);
				}
			}
		}
		return c1;
	}

	public static boolean isChinese(int block) {
		return Arrays.binarySearch(CHINESE_INDICES, block) >= 0;
	}

	public static boolean isJapanese(int block) {
		return Arrays.binarySearch(JAPANESE_INDICES, block) >= 0;
	}

	public static boolean isKorean(int block) {
		return Arrays.binarySearch(KOREAN_INDICES, block) >= 0;
	}

	public static final class UnicodeBlock {
		private static int    count = 0;
		public         String name;
		public         int    start;
		public         int    end;
		public         int    numCodePoints;
		public         String version;
		public         int    index;

		UnicodeBlock(String name, int start, int end, int numCodePoints, String version) {
			blocksByName.put(this.name = name, this);
			this.start         = start;
			this.end           = end;
			this.numCodePoints = numCodePoints;
			this.version       = version;
			index              = count;
			count++;
		}

		@Override
		public String toString() {
			return name;
		}
	}
}
