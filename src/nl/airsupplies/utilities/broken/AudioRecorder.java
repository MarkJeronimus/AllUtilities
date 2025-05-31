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

package nl.airsupplies.utilities.broken;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

import org.jetbrains.annotations.Nullable;

/**
 * @author Mark Jeronimus
 */
// Created 2007-04-09
public class AudioRecorder implements AudioProvider, Runnable {
	private int   bufferSize;
	private float sampleRate;
	private int   bitsPerSample;
	private int   numChannels;

	private @Nullable TargetDataLine targetDataLine = null;
	private final     byte[][]       buffer         = new byte[2][];
	private           int            currentBuffer  = 0;

	private volatile boolean providing = false;

	private AudioProviderListener audioListener = null;

	public AudioRecorder(float sampleRate, int bitsPerSample, int numChannels, int bufferSize) {
		setSampleRate(sampleRate);
		setBitsPerSample(bitsPerSample);
		setNumChannels(numChannels);
		setBufferSize(bufferSize);

		new Thread(this).start();
	}

	@Override
	public void setBufferSize(int bufferSize) {
		if (providing) {
			throw new IllegalStateException("Cannot modify recording parameter when recording.");
		}
		this.bufferSize = bufferSize;
		buffer[0]       = new byte[bufferSize];
		buffer[1]       = new byte[bufferSize];
	}

	@Override
	public void setSampleRate(float sampleRate) {
		if (providing) {
			throw new IllegalStateException("Cannot modify recording parameter when recording.");
		}
		this.sampleRate = sampleRate;
	}

	@Override
	public void setBitsPerSample(int bits) {
		if (providing) {
			throw new IllegalStateException("Cannot modify recording parameter when recording.");
		}
		bitsPerSample = bits;
	}

	@Override
	public void setNumChannels(int numChannels) {
		if (providing) {
			throw new IllegalStateException("Cannot modify recording parameter when recording.");
		}
		this.numChannels = numChannels;
	}

	@Override
	public void addAudioListener(AudioProviderListener audioListener) {
		this.audioListener = audioListener;
	}

	@Override
	public void setProviding(boolean providing) {
		this.providing = providing;
	}

	@Override
	public boolean isProviding() {
		return providing;
	}

	public void init() {
		AudioFormat audioFormat = new AudioFormat(sampleRate, bitsPerSample, numChannels, true, true);

		try {
			Mixer.Info[] mixerInfo1 = AudioSystem.getMixerInfo();
			for (int i = 0; i < mixerInfo1.length; i++) {
				Mixer.Info  mixerInfo = mixerInfo1[i];
				Mixer       mixer     = AudioSystem.getMixer(mixerInfo);
				Line.Info[] lines     = mixer.getTargetLineInfo();
				for (int j = 0; j < lines.length; j++) {
					Line.Info line = lines[j];
					if (line instanceof DataLine.Info) {
						AudioFormat[] formats = ((DataLine.Info)line).getFormats();
						for (int k = 0; k < formats.length; k++) {
							AudioFormat format = formats[k];
							System.out.println("[" + i + "][" + j + "][" + k + "] = " + mixerInfo + " | " + format);
						}
					}
				}
			}

			Mixer.Info mixerInfo = mixerInfo1[11];

//			Mixer         mixer          = AudioSystem.getMixer(mixerInfo);
//			DataLine.Info sourceLineInfo = (DataLine.Info)mixer.getTargetLineInfo()[0];
//			System.out.println(Arrays.toString(sourceLineInfo.getFormats()));
//
//			new DataLine.Info(TargetDataLine.class, audioFormat).matches(sourceLineInfo);

			// AudioFormat[] af = dataLineInfo.getFormats();
			// Get a TargetDataLine from the selected data line.
			targetDataLine = AudioSystem.getTargetDataLine(audioFormat, mixerInfo);

			// Prepare the line for use.
			targetDataLine.open(audioFormat, bufferSize * 2);
			targetDataLine.start();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(2);
		}

		providing = true;
	}

	@Override
	public void run() {
		while (providing) {
			// Wait until there are enough samples in the buffer.
			if (targetDataLine.available() >= bufferSize) {
				// Read data from the DataLine to the buffer.
				int n = 0;
				try {
					n = targetDataLine.read(buffer[currentBuffer], 0, bufferSize);
				} catch (Exception ex) {
					System.out.println(ex);
					System.exit(1);
				}

				// Generate events.
				if (audioListener != null) {
					audioListener.audioRecorded(this, n, buffer[currentBuffer]);
				}

				currentBuffer ^= 0x01;
			}
		}
	}

	public void close() {
		providing = false;

		if (targetDataLine != null) {
			targetDataLine.stop();
			targetDataLine.close();
		}

		targetDataLine = null;
	}
}
