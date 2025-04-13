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

package org.digitalmodular.utilities.signal;

/**
 * @author Mark Jeronimus
 */
// Created 2016-04-03
public enum FrequencyTransformNormalizationMode {
	/** Default for forward transform */
	ONE_OVER_N,
	/** Default for reverse transform */
	NONE,
	/** Use to make forward and reverse FFT transforms identical */
	ONE_OVER_SQRT_N
}
