package nl.airsupplies.utilities.brokenorold;

/**
 * @author Mark Jeronimus
 */
// Created 2007-04-09
public interface AudioProvider {
	void setBufferSize(int bufferSize);

	void setSampleRate(float sampleRate);

	void setBitsPerSample(int bits);

	void setNumChannels(int numChannels);

	void addAudioListener(AudioProviderListener audioListener);

	void setProviding(boolean providing);

	boolean isProviding();
}
