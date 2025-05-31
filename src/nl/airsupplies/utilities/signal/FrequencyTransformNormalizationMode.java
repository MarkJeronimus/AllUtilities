package nl.airsupplies.utilities.signal;

/**
 * @author Mark Jeronimus
 */
// Created 2016-04-03
public enum FrequencyTransformNormalizationMode {
	/** Default for forward transform */
	ONE_OVER_N,
	/** Default for reverse transform */
	NONE,
	/** Use to make forward and reverse FFT transforms identical */
	ONE_OVER_SQRT_N
}
