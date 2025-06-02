package nl.airsupplies.utilities.signal;

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

		samples = new double[numSamples];

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
		} catch (IOException ignored) {
		}
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
