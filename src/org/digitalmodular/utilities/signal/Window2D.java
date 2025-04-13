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

import org.digitalmodular.utilities.container.Complex2d;

/**
 * A class that can apply a "window" to an {@code Audio2D} object
 *
 * @author Mark Jeronimus
 */
// Created 2006-01-14
@Deprecated
public class Window2D extends Wave2D {

	/**
	 * Deze constructor ontwerpt een window van een specifiek formaat en vorm. Deze kan oneindig hergebruikt worden
	 * zolang het aantal audio samples niet veranderd hoeft te worden.
	 *
	 * @param numSamplesX Het aantal samples in de audio buffer waarvoor het window ontworpen wordt.
	 * @param windowType  Het type window (zie variabelen aan het begin van de klasse)
	 */
	public Window2D(int numSamplesX, int numSamplesY, byte windowType) {
		super(numSamplesX, numSamplesY);

		Window windowX = new Window(this.numSamplesX, windowType);
		Window windowY = new Window(this.numSamplesY, windowType);

		for (int y = 0; y < this.numSamplesY; y++) {
			for (int x = 0; x < this.numSamplesX; x++) {
				samples[y][x] = windowY.samples[y] * windowX.samples[x];
			}
		}
	}

	/**
	 * Deze methode past het ontworpen window toe op een object van de klasse Audio. NOTE: De gegeven audio buffer
	 * wordt
	 * overschreven.
	 *
	 * @param audio een array van het type Complex[][]
	 */
	public void applyTo(Complex2d[][] audio) {
		for (int y = 0; y < numSamplesY; y++) {
			for (int x = 0; x < numSamplesX; x++) {
				audio[y][x].real *= samples[y][x];
				audio[y][x].imag *= samples[y][x];
			}
		}
	}

	/**
	 * Deze methode past het ontworpen window toe op een object van de klasse Audio. NOTE: De gegeven audio buffer
	 * wordt
	 * overschreven.
	 *
	 * @param audio een array van het type double[][]
	 */
	public void applyTo(double[][] audio) {
		for (int y = 0; y < numSamplesY; y++) {
			for (int x = 0; x < numSamplesX; x++) {
				audio[y][x] *= samples[y][x];
			}
		}
	}
}
