package nl.airsupplies.utilities.signal;

import java.util.EventListener;
import javax.sound.sampled.DataLine;

/**
 * @author Mark Jeronimus
 */
// Created 2006-01-19
public interface AudioPlayerListener extends EventListener {
	/**
	 * Invoked when the supplied amount of samples have been played and new samples are required.
	 *
	 * @param audioPlayer the {@link AudioPlayer} instance that needs refilling of its buffer
	 * @param buffer      the buffer to be filled with new audio data (always starts at index 0). Samples are stored
	 *                    MSB
	 *                    first if multiple-byte.
	 * @param amount      the amount of bytes (not samples) that are needed to re-stock the audio driver's pages
	 *                    buffer.
	 */
	void audioRequested(AudioPlayer audioPlayer, byte[] buffer, int amount);

	/**
	 * Notifies that the {@link DataLine} is closed and the player is correctly terminated.
	 *
	 * @param audioPlayer the {@link AudioPlayer} instance that stopped playing.
	 */
	void dataLineClosed(AudioPlayer audioPlayer);
}
