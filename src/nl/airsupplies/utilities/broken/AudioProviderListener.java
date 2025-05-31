package nl.airsupplies.utilities.broken;

import java.util.EventListener;
import javax.sound.sampled.DataLine;

/**
 * @author Mark Jeronimus
 */
// Created 2006-01-19
public interface AudioProviderListener extends EventListener {
	/**
	 * Invoked when the supplied amount of buffer have been recorded.
	 */
	void audioRecorded(AudioProvider audioProvider, int numBytes, byte[] buffer);

	/**
	 * Notifies that the {@link DataLine} is closed and the provider is correctly terminated.
	 */
	void dataLineClosed(AudioProvider audioProvider);
}
