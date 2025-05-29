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

package nl.airsupplies.utilities.constant;

/**
 * @author Mark Jeronimus
 */
// Created 2006-02-28
public class Constants {
	/**
	 * Lookup table for ASCII characters that are considered as unimportant, but separate the tokens. This array is the
	 * logical inverse of the isString array, with the exceptions of the newline character (0x0A, '\n') the
	 * carriage-return character (0x0D, '\r') and the end-of-file character (0x1A).
	 */
	public static boolean[] isWhitespace = {true, true, true, true, true, true, true, true, true, true, false,
	                                        true, true, false, true, true, true, true, true, true, true, true, true,
	                                        true, true, true, false, true,
	                                        true, true,
	                                        true, true, true, false, false, false, false, false, false, false, false,
	                                        false, false, false, false, false,
	                                        false,
	                                        false, false, false, false, false, false, false, false, false, false,
	                                        false,
	                                        false, false, false, false,
	                                        false, false,
	                                        false, false, false, false, false, false, false, false, false, false,
	                                        false,
	                                        false, false, false, false,
	                                        false, false,
	                                        false, false, false, false, false, false, false, false, false, false,
	                                        false,
	                                        false, false, false, false,
	                                        false, false,
	                                        false, false, false, false, false, false, false, false, false, false,
	                                        false,
	                                        false, false, false, false,
	                                        false, false,
	                                        false, false, false, false, false, false, false, false, false, false,
	                                        false,
	                                        false, true};

	/**
	 * Lookup table for ASCII characters 0-127 which are a (part of) a literal string value.
	 */
	public static boolean[] isString = {false, false, false, false, false, false, false, false, false,
	                                    false, false, false, false, false, false, false, false, false, false, false,
	                                    false, false, false, false,
	                                    false, false,
	                                    false, false, false, false, false, false, true, true, true, true, true, true,
	                                    true, true, true, true, true,
	                                    true, true,
	                                    true, true, true, true, true, true, true, true, true, true, true, true, true,
	                                    true, true, true, true, true,
	                                    true, true,
	                                    true, true, true, true, true, true, true, true, true, true, true, true, true,
	                                    true, true, true, true, true,
	                                    true, true,
	                                    true, true, true, true, true, true, true, true, true, true, true, true, true,
	                                    true, true, true, true, true,
	                                    true, true,
	                                    true, true, true, true, true, true, true, true, true, true, true, true, true,
	                                    true, true, true, true, true,
	                                    true, true,
	                                    true, true, false};
	/**
	 * Lookup table for ASCII characters 0-127 which are a (part of) an integer number.
	 */
	public static boolean[] isNum    = {false, false,
	                                    false,
	                                    false,
	                                    false,
	                                    false,
	                                    false,
	                                    false,//
	                                    false, false, false, false, false, false, false, false, false, false, false,
	                                    false, false, false, false,
	                                    false, false,
	                                    false, false, false, false, false, false, false, false, false, false, false,
	                                    false, false, false, false,
	                                    false, false,
	                                    false, false, false, false, false, false, true, true, true, true, true, true,
	                                    true, true, true, true, false,
	                                    false,
	                                    false, false, false, false, false, false, false, false, false, false, false,
	                                    false, false, false, false,
	                                    false, false,
	                                    false, false, false, false, false, false, false, false, false, false, false,
	                                    false, false, false, false,
	                                    false, false,
	                                    false, false, false, false, false, false, false, false, false, false, false,
	                                    false, false, false, false,
	                                    false, false,
	                                    false, false, false, false, false, false, false, false, false, false, false,
	                                    false, false, false, false,
	                                    false, false};

	/**
	 * Lookup table for ASCII characters 0-127 which are alphabetical. ('_' included)
	 */
	public static boolean[] isAlpha = {false, false, false, false, false, false, false, false, false,
	                                   false, false, false, false, false, false, false, false, false, false, false,
	                                   false, false, false, false,
	                                   false, false,
	                                   false, false, false, false, false, false, false, false, false, false, false,
	                                   false, false, false, false,
	                                   false, false,
	                                   false, false, false, false, false, false, false, false, false, false, false,
	                                   false, false, false, false,
	                                   false, false,
	                                   false, false, false, false, false, true, true, true, true, true, true, true,
	                                   true, true, true, true, true,
	                                   true, true,
	                                   true, true, true, true, true, true, true, true, true, true, true, true, false,
	                                   false, false, false, true,
	                                   false, true,
	                                   true, true, true, true, true, true, true, true, true, true, true, true, true,
	                                   true, true, true, true, true,
	                                   true, true,
	                                   true, true, true, true, true, false, false, false, false, false};

	/** Lookup table for ASCII characters 0-127 which are symbols. ('_' excluded) */
	public static boolean[] isSymbol = {false, false, false, false, false, false, false, false, false,
	                                    false, false, false, false, false, false, false, false, false, false, false,
	                                    false, false, false, false,
	                                    false, false,
	                                    false, false, false, false, false, false, false, true, true, true, true, true,
	                                    true, true, true, true, true,
	                                    true,
	                                    true, true, true, true, false, false, false, false, false, false, false, false,
	                                    false, false, true, true,
	                                    true, true,
	                                    true, true, true, false, false, false, false, false, false, false, false,
	                                    false,
	                                    false, false, false, false,
	                                    false,
	                                    false, false, false, false, false, false, false, false, false, false, false,
	                                    false, true, true, true, true,
	                                    false,
	                                    true, false, false, false, false, false, false, false, false, false, false,
	                                    false, false, false, false,
	                                    false, false,
	                                    false, false, false, false, false, false, false, false, false, false, true,
	                                    true, true, true, true};

	/** 2^(1/i) */
	public static final double[] recipPowersOf2Table = {Double.longBitsToDouble(0x3FF0000000000000L), // 0
	                                                    Double.longBitsToDouble(0x3FE0000000000000L), // 1
	                                                    Double.longBitsToDouble(0x3FD0000000000000L), // 2
	                                                    Double.longBitsToDouble(0x3FC0000000000000L), // 3
	                                                    Double.longBitsToDouble(0x3FB0000000000000L), // 4
	                                                    Double.longBitsToDouble(0x3FA0000000000000L), // 5
	                                                    Double.longBitsToDouble(0x3F90000000000000L), // 6
	                                                    Double.longBitsToDouble(0x3F80000000000000L), // 7
	                                                    Double.longBitsToDouble(0x3F70000000000000L), // 8
	                                                    Double.longBitsToDouble(0x3F60000000000000L), // 9
	                                                    Double.longBitsToDouble(0x3F50000000000000L), // 10
	                                                    Double.longBitsToDouble(0x3F40000000000000L), // 11
	                                                    Double.longBitsToDouble(0x3F30000000000000L), // 12
	                                                    Double.longBitsToDouble(0x3F20000000000000L), // 13
	                                                    Double.longBitsToDouble(0x3F10000000000000L), // 14
	                                                    Double.longBitsToDouble(0x3F00000000000000L), // 15
	                                                    Double.longBitsToDouble(0x3EF0000000000000L), // 16
	                                                    Double.longBitsToDouble(0x3EE0000000000000L), // 17
	                                                    Double.longBitsToDouble(0x3ED0000000000000L), // 18
	                                                    Double.longBitsToDouble(0x3EC0000000000000L), // 19
	                                                    Double.longBitsToDouble(0x3EB0000000000000L), // 20
	                                                    Double.longBitsToDouble(0x3EA0000000000000L), // 21
	                                                    Double.longBitsToDouble(0x3E90000000000000L), // 22
	                                                    Double.longBitsToDouble(0x3E80000000000000L), // 23
	                                                    Double.longBitsToDouble(0x3E70000000000000L), // 24
	                                                    Double.longBitsToDouble(0x3E60000000000000L), // 25
	                                                    Double.longBitsToDouble(0x3E50000000000000L), // 26
	                                                    Double.longBitsToDouble(0x3E40000000000000L), // 27
	                                                    Double.longBitsToDouble(0x3E30000000000000L), // 28
	                                                    Double.longBitsToDouble(0x3E20000000000000L), // 29
	                                                    Double.longBitsToDouble(0x3E10000000000000L), // 30
	                                                    Double.longBitsToDouble(0x3E00000000000000L), // 31
	};
}
