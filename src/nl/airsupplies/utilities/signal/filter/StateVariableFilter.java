package nl.airsupplies.utilities.signal.filter;

import static nl.airsupplies.utilities.NumberUtilities.clamp;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNotDegenerate;
import static nl.airsupplies.utilities.constant.NumberConstants.TAU;

/**
 * @author Mark Jeronimus
 */
// Created 2014-04-25
public class StateVariableFilter implements AudioFilter {
	private double cutoffFrequency = 0;
	private double resonance       = 0;

	private double integratorSpeed = 0;
	private double feedback        = 2;

	private double a        = 0;
	private double highPass = 0;
	private double bandPass = 0;
	private double lowPass  = 0;

	@Override
	public double getCutoffFrequency() {
		return cutoffFrequency;
	}

	@Override
	public void setCutoffFrequency(double cutoffFrequency) {
		this.cutoffFrequency = clamp(cutoffFrequency, 0, 0.125 / Math.PI);

		integratorSpeed = TAU * cutoffFrequency;
	}

	@Override
	public double getResonance() {
		return resonance;
	}

	@Override
	public void setResonance(double resonance) {
		this.resonance = clamp(resonance, 0, 1);

		feedback = 1 - (resonance * 2 - 1);
	}

	@Override
	public void reset() {
		bandPass = 0;
		lowPass  = 0;
	}

	public void set(double y, double dy) {
		lowPass  = requireNotDegenerate(y, "y");
		bandPass = requireNotDegenerate(dy, "dy");
	}

	@Override
	public void step(double sample) {
		a        = sample - bandPass * feedback;
		highPass = a - lowPass;
		bandPass += highPass * integratorSpeed;
		lowPass += bandPass * integratorSpeed;
	}

	@Override
	public double getLowPass() {
		return lowPass;
	}

	@Override
	public double getBandPass() {
		return bandPass;
	}

	@Override
	public double getHighPass() {
		return highPass;
	}

	@Override
	public double getBandReject() {
		return a;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() +
		       "{cutoffFrequency: " + cutoffFrequency +
		       "\tresonance: " + resonance +
		       "\tintegratorSpeed: " + integratorSpeed +
		       "\tfeedback: " + feedback + '}';
	}
}
