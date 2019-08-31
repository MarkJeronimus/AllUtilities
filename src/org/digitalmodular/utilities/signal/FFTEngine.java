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

import java.util.Objects;

import org.digitalmodular.utilities.NumberUtilities;
import org.digitalmodular.utilities.container.Complex2d;
import static org.digitalmodular.utilities.signal.FFTNormalizationMode.NONE;

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
	/**
	 * log2 of fftSize.
	 */
	private final int                  fftBits;
	/**
	 * The size of the FFTEngine. Should always be a power of 2.
	 */
	private final int                  fftSize;
	private final FFTNormalizationMode normalization;

	/**
	 * The butterfly constants.
	 */
	public final Complex2d[] omega;

	/**
	 * Constructs an FFTEngine converter and sets an FFTEngine size. See {@link #setFFTSize(int)} for more information
	 * on the FFTEngine
	 * size conditions.
	 */
	public FFTEngine(int fftSize) {
		this(fftSize, NONE);
	}

	/**
	 * Constructs an FFTEngine converter and sets an FFTEngine size. See {@link #setFFTSize(int)} for more information
	 * on the FFTEngine
	 * size conditions.
	 */
	public FFTEngine(int fftSize, FFTNormalizationMode normalization) {
		Objects.requireNonNull(normalization, "normalization can't be null");
		if (fftSize < 2)
			throw new IllegalArgumentException("fftSize should be at least 2: " + fftSize);
		if (!NumberUtilities.isPowerOfTwo(fftSize))
			throw new IllegalArgumentException("fftSize should be a power of two: " + fftSize);

		fftBits = NumberUtilities.log2(fftSize);

		this.fftSize = fftSize;
		this.normalization = normalization;

		// initialize the butterfly constants.
		omega = new Complex2d[fftSize / 2];
		double dt = -2 * Math.PI / fftSize;
		for (int i = fftSize / 2 - 1; i >= 0; i--) {
			double angle = dt * i;
			omega[i] = new Complex2d(Math.cos(angle), Math.sin(angle));
		}
	}

	public int getFFTSize() { return fftSize; }

	/**
	 * Calculates the forward FFT using the Cooley-Tukey algorithm.
	 */
	public void fftForward(Complex2d[] in, Complex2d[] out) {
		// Load the waveform, index bits reversed.
		for (int i = fftSize - 1; i >= 0; i--) {
			int j = NumberUtilities.reverseBits(i, fftBits);
			out[j].set(in[i]);

			switch (normalization) {
				case NONE:
					break;
				case ONE_OVER_N:
					out[j].div(fftSize);
					break;
				case ONE_OVER_SQRT_N:
					out[j].div(Math.sqrt(fftSize));
					break;
				default:
					throw new AssertionError(normalization);
			}
		}

		// Initial number of group operations.
		int numGroups = fftSize / 2;

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

					// The intermediate operation of okega(k)*x1.
					temp.set(omega[k]);
					temp.mul(out[sampleOdd]);

					// Apply the butterfly.
					out[sampleOdd].set(out[sampleEven]);
					out[sampleOdd].sub(temp);
					out[sampleEven].add(temp);
				}
			}

			numGroups >>>= 1;
			numPerGroup <<= 1;
		} while (numGroups > 0);
	}
}
