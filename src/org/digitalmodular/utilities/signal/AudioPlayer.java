/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2018 Mark Jeronimus. All Rights Reversed.
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

import java.util.Arrays;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

/**
 * @author Mark Jeronimus
 */
// Created 2009-10-03
public class AudioPlayer implements Runnable {
	private final Thread thread;

	private int   bufferSize;
	private float sampleRate;
	private int   bitsPerSample;
	private int   numChannels;

	private SourceDataLine sourceDataLine;
	private byte[]         buffer;

	private boolean play      = false;
	private boolean isPlaying = false;

	private AudioPlayerListener audioListener = null;

	public AudioPlayer(float sampleRate, int bitsPerSample, int numChannels, int bufferSize) {
		setSampleRate(sampleRate);
		setBitsPerSample(bitsPerSample);
		setNumChannels(numChannels);
		setBufferSize(bufferSize);

		thread = new Thread(this);
		thread.start();
		thread.setPriority(5);
	}

	public void setBufferSize(int bufferSize) {
		if (isPlaying) {
			throw new IllegalStateException("Cannot modify playing parameter when playing.");
		}
		this.bufferSize = bufferSize;
		buffer = new byte[this.bufferSize * 4];
	}

	public void setSampleRate(float sampleRate) {
		if (isPlaying) {
			throw new IllegalStateException("Cannot modify playing parameter when playing.");
		}
		this.sampleRate = sampleRate;
	}

	public void setBitsPerSample(int bits) {
		if (isPlaying) {
			throw new IllegalStateException("Cannot modify playing parameter when playing.");
		}
		bitsPerSample = bits;
	}

	public void setNumChannels(int numChannels) {
		if (isPlaying) {
			throw new IllegalStateException("Cannot modify playing parameter when playing.");
		}
		this.numChannels = numChannels;
	}

	public void addAudioListener(AudioPlayerListener audioListener) {
		this.audioListener = audioListener;
	}

	public void setPlaying(boolean playing) {
		if (play == playing) {
			return;
		}

		if (playing) {
			start();
		} else {
			stop();
		}
		play = playing;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public long getPlayedSamples() {
		return sourceDataLine.getLongFramePosition();
	}

	public long getPlayedTime() {
		return sourceDataLine.getMicrosecondPosition();
	}

	private void start() {
		AudioFormat   audioFormat  = new AudioFormat(sampleRate, bitsPerSample, numChannels, true, true);
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat, bufferSize);

		try {
			// AudioFormat[] af = dataLineInfo.getFormats();
			// Get a TargetDataLine from the selected data line.
			sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLineInfo);

			// Prepare the line for use.
			sourceDataLine.open(audioFormat, bufferSize * 4);
			sourceDataLine.start();

			Arrays.fill(buffer, (byte)0);
			// this.sourceDataLine.write(this.buffer, this.bytesWritten,
			// this.buffer.length - this.bytesWritten);
			// this.sourceDataLine.write(this.buffer, this.bytesWritten,
			// this.buffer.length - this.bytesWritten);
			// this.sourceDataLine.write(this.buffer, this.bytesWritten,
			// this.buffer.length - this.bytesWritten);
			// this.sourceDataLine.write(this.buffer, this.bytesWritten,
			// this.buffer.length - this.bytesWritten);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(2);
		}

		isPlaying = true;
	}

	private void stop() {
		if (sourceDataLine != null) {
			sourceDataLine.stop();
			sourceDataLine.close();
		}
		sourceDataLine = null;

		if (audioListener != null) {
			audioListener.dataLineClosed(this);
		}

		isPlaying = false;
	}

	@Override
	public void run() {
		try {
			while (true) {
				if (play) {
					int available = sourceDataLine.available();
					if (available > 0) {
						// Generate events.
						if (audioListener != null) {
							audioListener.audioRequested(this, buffer, available);
						}

						// Still playing?
						if (play) {
							// Write data from the buffer to the DataLine.
							sourceDataLine.write(buffer, 0, available);
						}
					}
				}

				Thread.sleep(1);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void interrupt() {
		sourceDataLine.flush();
		Arrays.fill(buffer, (byte)0);
		// this.sourceDataLine.write(this.buffer, 0, 4);
	}
}
