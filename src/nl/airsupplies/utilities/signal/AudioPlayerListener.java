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
