/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2019 Mark Jeronimus. All Rights Reversed.
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
 * along with AllUtilities. If not, see <http://www.gnu.org/licenses/>.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.digitalmodular.utilities.signal;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Mark Jeronimus
 */
// Created 2005-08-07
public class Wave {
	public final double[] samples;
	public final int      numSamples;

	/**
	 * Deze constructor maakt een object van de klasse Audio. Dit object bevat een audio buffer van een bepaald aantal
	 * samples, en kan oneindig herbruikt worden zolang het aantal audio samples niet verandert hoeft te worden. - De
	 * buffer voor de audio is te bereiken via de public double[] sample. - De buffer wordt leeg gecreeerd, maar bij
	 * hergebruik is deze niet leeg.
	 *
	 * @param numSamples Het aantal samples dat de buffer moet bevatten.
	 */
	public Wave(int numSamples) {
		this.numSamples = numSamples;

		samples = new double[this.numSamples];

		Arrays.fill(samples, 0);
	}

	public void loadSamples(double[] buffer) {
		loadSamples(0, buffer, 0, buffer.length);
	}

	public void loadSamples(int fromSample, double[] buffer, int offset, int count) {
		System.arraycopy(buffer, offset, samples, fromSample, count);
	}

	public void normalize(double amplitude) {
		double max = 0;
		for (int n = 0; n < numSamples; n++) {
			if (Math.abs(samples[n]) > max) {
				max = Math.abs(samples[n]);
			}
		}
		max = amplitude / max;
		for (int n = 0; n < numSamples; n++) {
			samples[n] *= max;
		}
	}

	public void normalizeArea(double amplitude) {
		double sum = 0;
		sum = 0;
		for (int n = 0; n < numSamples; n++) {
			sum += samples[n];
		}
		sum = amplitude / sum;
		for (int n = 0; n < numSamples; n++) {
			samples[n] *= sum;
		}
	}

	public void mul(double volume) {
		for (int n = 0; n < numSamples; n++) {
			samples[n] *= volume;
		}
	}

	// This method biases the audio to remove the DC component.
	public void correctDC() {
		double sum;

		sum = 0;
		for (int n = 0; n < numSamples; n++) {
			sum += samples[n];
		}

		sum /= numSamples;

		for (int n = 0; n < numSamples; n++) {
			samples[n] -= sum;
		}
	}

	/**
	 * This method adds a 22050 Hz 8-bit mono audio PCM file to audio buffer at a specified sampling rate.
	 *
	 * @param filename de filename van het bestand met PCM
	 * @param offset   de hoeveelheid bytes aan het begin van het PCM bestand die moeten worden overgeslagen
	 * @param level    de amplitude die de 8-bit PCM file moet aannemen alvorens deze naar double wordt geconverteerd
	 * @param skip     het aantal bytes skippen iedere byte
	 */
	public void addFile8bit(String filename, int offset, double level, int skip) {
		try (DataInputStream file1 = new DataInputStream(new FileInputStream(filename))) {
			// skip bytes
			for (int a = 0; a < offset; a++) {
				file1.readByte();
			}
			// add samples to the audio buffer.
			for (int a = 0; a < numSamples; a++) {
				samples[a] += file1.readByte() / 128.0 * level;
				// skip bytes to downsample the audio
				for (int b = 0; b < skip; b++) {
					file1.readByte();
				}
			}
		} catch (IOException e) {}
	}

	/**
	 * Deze methode voegt een laag ruis toe aan de audio buffer.
	 *
	 * @param amplitude de hoeveelheid ruis in een factor van 0..1.
	 */
	public void addNoise(double amplitude) {
		double magnitude2 = 2 * amplitude;

		for (int a = 0; a < numSamples; a++) {
			samples[a] += Math.random() * magnitude2 - amplitude;
		}
	}

	public int getNumberOfValues() {
		return numSamples;
	}

	public double getValue(int index) {
		return samples[index];
	}
}
