package nl.airsupplies.utilities.signal;

import nl.airsupplies.utilities.NumberUtilities;
import nl.airsupplies.utilities.complex.Complex2d;
import static nl.airsupplies.utilities.validator.ArrayValidatorUtilities.requireArrayValuesNonNull;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireRange;

/**
 * This class performs conversion between time-domain and frequency-domain signals. <br> Few performance optimizations
 * have been implemented.
 *
 * @author Mark Jeronimus
 */
// Created 2005-08-07
// Updated 2014-02-10 Combined two versions and converted in preparation for IFFT, windows and filters.
// Changed 2016-03-03 Made immutable
public class FFTEngine {
	private final int size;

	private FrequencyTransformNormalizationMode normalizationMode = FrequencyTransformNormalizationMode.NONE;

	/**
	 * log2 of fftSize.
	 */
	private final int fftBits;

	/**
	 * The butterfly constants.
	 */
	private final Complex2d[] omega;

	/**
	 * @param size The size of the FFT. Should always be a power of 2.
	 */
	public FFTEngine(int size) {
		requireAtLeast(2, size, "size");
		if (!NumberUtilities.isPowerOfTwo(size)) {
			throw new IllegalArgumentException("'size' should be a power of two: " + size);
		}

		fftBits = NumberUtilities.log2(size);

		this.size = size;

		// initialize the butterfly constants.
		omega = new Complex2d[size / 2];
		double dt = -2 * Math.PI / size;
		for (int i = size / 2 - 1; i >= 0; i--) {
			double angle = dt * i;
			omega[i] = new Complex2d(Math.cos(angle), Math.sin(angle));
		}
	}

	public int getSize() {
		return size;
	}

	public FrequencyTransformNormalizationMode getNormalizationMode() {
		return normalizationMode;
	}

	public void setNormalizationMode(FrequencyTransformNormalizationMode normalizationMode) {
		this.normalizationMode = requireNonNull(normalizationMode, "normalizationMode");
	}

	/**
	 * Calculates the forward FFT using the Cooley-Tukey algorithm.
	 */
	public void transform(Complex2d[] in, Complex2d[] out) {
		requireArrayValuesNonNull(in, "in");
		requireArrayValuesNonNull(out, "out");
		requireRange(size, size, in.length, "in.length");
		requireRange(size, size, out.length, "out.length");

		// Load the waveform, index bits reversed.
		for (int i = size - 1; i >= 0; i--) {
			int j = NumberUtilities.reverseBits(i, fftBits);
			out[j].set(in[i]);

			switch (normalizationMode) {
				case NONE:
					break;
				case ONE_OVER_N:
					out[j].divSelf(size);
					break;
				case ONE_OVER_SQRT_N:
					out[j].divSelf(Math.sqrt(size));
					break;
				default:
					throw new AssertionError(normalizationMode);
			}
		}

		// Initial number of group operations.
		int numGroups = size / 2;

		// Initial number of butterflies per group.
		int numPerGroup = 1;

		Complex2d temp = new Complex2d();

		do {
			// Process all groups.
			for (int groupNr = numGroups - 1; groupNr >= 0; groupNr--) {
				// Process each operation within the group.
				for (int butterflyNr = numPerGroup - 1; butterflyNr >= 0; butterflyNr--) {
					// The 'even' input sample 'x0'.
					int sampleEven = 2 * numPerGroup * groupNr + butterflyNr;
					// The 'odd' input sample 'x1'.
					int sampleOdd = sampleEven + numPerGroup;

					int k = numGroups * butterflyNr;

					// The intermediate operation of omega(k)*x1.
					temp.set(out[sampleOdd]);
					temp.mulSelf(omega[k]);

					// Apply the butterfly.
					out[sampleOdd].set(out[sampleEven]);
					out[sampleOdd].subSelf(temp);
					out[sampleEven].addSelf(temp);
				}
			}

			numGroups >>>= 1;
			numPerGroup <<= 1;
		} while (numGroups > 0);
	}
}
