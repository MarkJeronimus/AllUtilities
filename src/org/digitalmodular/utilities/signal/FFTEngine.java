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

package org.digitalmodular.utilities.signal;

import org.digitalmodular.utilities.NumberUtilities;
import org.digitalmodular.utilities.container.Complex2d;
import static org.digitalmodular.utilities.ArrayValidatorUtilities.requireArrayValuesNonNull;
import static org.digitalmodular.utilities.ValidatorUtilities.requireAtLeast;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;
import static org.digitalmodular.utilities.ValidatorUtilities.requireRange;

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
