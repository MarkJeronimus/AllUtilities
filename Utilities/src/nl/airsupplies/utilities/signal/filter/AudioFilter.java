package nl.airsupplies.utilities.signal.filter;

/**
 * @author Mark Jeronimus
 */
// Created 2014-04-27
public interface AudioFilter {
	double getCutoffFrequency();

	void setCutoffFrequency(double cutoffFrequency);

	double getResonance();

	void setResonance(double resonance);

	void reset();

	void step(double sample);

	double getLowPass();

	double getBandPass();

	double getHighPass();

	double getBandReject();
}
