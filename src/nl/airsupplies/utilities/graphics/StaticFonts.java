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

package nl.airsupplies.utilities.graphics;

import java.awt.Font;
import static java.awt.Font.PLAIN;

import nl.airsupplies.utilities.annotation.UtilityClass;

/**
 * @author Mark Jeronimus
 */
// Created 2007-12-01
@UtilityClass
public final class StaticFonts {
	public static final Font DEFAULT_SANSSERIF_FONT  = new Font("SansSerif", PLAIN, 12);
	public static final Font DEFAULT_MONOSPACED_FONT = new Font("Monospaced", PLAIN, 12);
	public static final Font DEFAULT_SERIF_FONT      = new Font("Serif", PLAIN, 12);
}
