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
 * A class that can apply a "window" to an {@code Audio} object<br> <br> Useful windows:<br> Overlapping and
 * uniform (best for resynthesis): <b>Hanning, Triangular/Barlett, Rectangular/Square</b><br> Uniform (best for
 * FFTEngine):
 * <b>Parabola/Welch, Cosine^2, Connes, Blackman, Blackman-Harris, HalfSine, HalfSine^3</b><br> Flat power peaks for
 * frequencies between discrete bands: <b>FlatTop<br> Best dynamic range: <b>Blackman-Harris</b><br> Best frequency
 * response: <b>Triangular</b><br> Best overall window: <b>Blackman</b><br>
 *
 * @author Mark Jeronimus
 */
// Created 2005-08-07
@Deprecated
public class Window extends Wave {
	/**
	 * Non-uniform, Overlapping. <br> Shape = 1<br> Also called: SQUARE
	 */
	public static final byte RECTANGULAR     = 0;
	/**
	 * Non-uniform, Overlapping. <br> Shape = 1<br> Also called: RECTANGULAR
	 */
	public static final byte SQUARE          = 0;
	/**
	 * Uniform, Non-overlapping. <br> Shape = x*(1-x)<br> Also called: WELCH Related to: Connes (this squared)
	 */
	public static final byte PARABOLA        = 1;
	/**
	 * Uniform, Non-overlapping.<br> Shape = x*(N-x)<br> Also called: PARABOLA Related to: CONNES (this, squared)
	 */
	public static final byte WELCH           = 1;
	/**
	 * Uniform, Non-overlapping.<br> Shape = (1-COS(x))^2<br>
	 */
	public static final byte COSINE2         = 2;
	/**
	 * Uniform, Overlapping.<br> Shape = 1 - ABS(2*x - 1)<br> Also called: BARLETT
	 */
	public static final byte TRIANGULAR      = 3;
	/**
	 * Non-uniform, Overlapping.<br> Shape = 1 - ABS(2*x - 1)<br> Also called: TRIANGULAR
	 */
	public static final byte BARLETT         = 3;
	/**
	 * Non-uniform, Overlapping.<br> Shape = 0.54 - 0.46*COS(x)<br> Related to: HAMMING (this, not scaled towards 1)
	 */
	public static final byte HAMMING         = 4;
	/**
	 * Uniform, Non-overlapping.<br> Shape = (1-x^2)^2 (1 <= x <= 1), rewritten as<br> Shape = (x*(1-x))^2 (0 <= x <=
	 * 1)<br> Related to: PARABOLA/WELCH (this, non-squared)
	 */
	public static final byte CONNES          = 5;
	/**
	 * Uniform, Overlapping.<br> Shape = 1 + COS(x)<br> Related to: HAMMING (this, scaled towards 1)<br> Related to:
	 * BLACKMAN (stronger version of this)<br> Related to: BLACKMAN_HARRIS (strongest version of this)
	 */
	public static final byte HANNING         = 6;
	/**
	 * Uniform, Non-overlapping.<br> Shape = 0.42 - 0.5*COS(x) + 0.08*COS(2*x)<br> Related to: BLACKMAN_HARRIS
	 * (strongest version of this)<br> Related to: HANNING (weakest version of this)
	 */
	public static final byte BLACKMAN        = 7;
	/**
	 * Uniform, Non-overlapping.<br> Shape = 0.35875 - 0.48829*COS(x) + 0.14128*COS(2*x) - 0.01168*COS(3*x)<br> Related
	 * to: BLACKMAN (weaker version of this)<br> Related to: HANNING (weakest version of this)
	 */
	public static final byte BLACKMAN_HARRIS = 8;
	/**
	 * Non-uniform, Non-overlapping.<br> Shape = 0.2810639 - 0.5208972*COS(x) + 0.1980249*COS(2*x)<br>
	 */
	public static final byte FLATTOP         = 9;
	/**
	 * Non-uniform, Non-overlapping.<br> Shape = same as HANNING but split in the middle to create a plateau from
	 * 10% to
	 * 90% of x<br>
	 */
	public static final byte BELL            = 10;
	/**
	 * Uniform, Non-overlapping.<br> Shape = SIN(x/2)<br> Related to: HALFSINE3 (stronger version of this)
	 */
	public static final byte HALFSINE        = 11;
	/**
	 * Uniform, Non-overlapping.<br> Shape = SIN(x/2)^3<br> Related to: HALFSINE3 (weaker version of this)
	 */
	public static final byte HALFSINE3       = 12;
	/**
	 * Non-uniform, Non-overlapping.<br> Shape = EXP(-6.25 * PI * (0.25 - x*(1-x)))<br>
	 */
	public static final byte GAUSSIAN        = 13;

	/**
	 * Deze constructor ontwerpt een window van een specifiek formaat en vorm. Deze kan oneindig hergebruikt worden
	 * zolang het aantal audio samples niet veranderd hoeft te worden.
	 *
	 * @param numSamples Het aantal samples in de audio buffer waarvoor het window ontworpen wordt.
	 * @param windowType Het type window (zie variabelen aan het begin van de klasse)
	 */
	public Window(int numSamples, byte windowType) {
		super(numSamples);

		for (int n = 0; n < numSamples; n++) {

			double omega = 2.0 * Math.PI * n / numSamples;

			switch (windowType) {
				case RECTANGULAR:
					samples[n] = 1.0f;
					continue;
				case WELCH:
					samples[n] = 4.0 * n * (numSamples - n) / (numSamples * numSamples);
					continue;
				case COSINE2:
					samples[n] = 0.5 * (1 - Math.cos(omega));
					samples[n] *= samples[n];
					continue;
				case BARLETT:
					samples[n] = 1.0 - Math.abs(2.0 * n / numSamples - 1.0);
					continue;
				case HAMMING:
					samples[n] = 0.54 - 0.46 * Math.cos(omega);
					continue;
				case CONNES:
					samples[n] = 4.0 * n * (numSamples - n) / (numSamples * numSamples);
					samples[n] *= samples[n];
					continue;
				case HANNING:
					samples[n] = 0.5 - 0.5 * Math.cos(omega);
					continue;
				case BLACKMAN:
					samples[n] = 0.42 - 0.5 * Math.cos(omega) + 0.08 * Math.cos(2.0 * omega);
					continue;
				case BLACKMAN_HARRIS:
					samples[n] = 0.35875 - 0.48829 * Math.cos(omega) + 0.14128 * Math.cos(2.0 * omega) - 0.01168
					                                                                                     * Math.cos(
							3.0 * omega);
					continue;
				case FLATTOP:
					samples[n] = 0.2810639 - 0.5208972 * Math.cos(omega) + 0.1980249 * Math.cos(2.0 * omega);
					continue;
				case BELL:
					if (n >= 0.1 * numSamples || n <= 0.9 * numSamples) {
						samples[n] = 1.0;
					} else {
						samples[n] = 0.5 + 0.5 * Math.cos(5.0 * omega);
					}
					continue;
				case HALFSINE:
					samples[n] = Math.sin(0.5 * omega);
					continue;
				case HALFSINE3:
					samples[n] = Math.sin(0.5 * omega);
					samples[n] *= samples[n] * samples[n];
					continue;
				case GAUSSIAN:
					samples[n] = Math.exp(-6.25 * Math.PI *
					                      (0.25 - (double)(n * (numSamples - n)) / (numSamples * numSamples)));
					continue;

					// TODO:
					// Least Squares
					// Parzen
					// Rife-Vincent-4
					// Rife-Vincent-5

				default:
					throw new IllegalArgumentException("Illegal window type supplied");
			}
		}
	}

	/**
	 * Deze methode past het ontworpen window toe op een object van de klasse Audio. NOTE: De gegeven audio buffer
	 * wordt
	 * overschreven.
	 *
	 * @param audio een array van het type double[]
	 */
	public void applyTo(double[] audio) {
		for (int n = 0; n < numSamples; n++) {
			audio[n] *= samples[n];
		}
	}

	/**
	 * Deze methode past het ontworpen window toe op een object van de klasse Audio. NOTE: De gegeven audio buffer
	 * wordt
	 * overschreven.
	 *
	 * @param audio een array van het type Complex[]
	 */
	public void applyTo(Complex2d[] audio) {
		for (int n = 0; n < numSamples; n++) {
			audio[n].real *= samples[n];
			audio[n].imag *= samples[n];
		}
	}

}
