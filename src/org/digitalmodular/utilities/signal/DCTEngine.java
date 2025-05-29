package org.digitalmodular.utilities.signal;

import org.digitalmodular.utilities.constant.NumberConstants;
import static org.digitalmodular.utilities.ValidatorUtilities.requireAtLeast;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;
import static org.digitalmodular.utilities.ValidatorUtilities.requireRange;

/**
 * @author Mark Jeronimus
 */
// Created 2019-09-04
public class DCTEngine {
	@SuppressWarnings("UseOfSystemOutOrSystemErr")
	public static void main(String... args) {
		int       n         = 8;
		DCTEngine dctEngine = new DCTEngine(n);
		for (int f = 0; f < n; f++) {
			double[] in  = new double[n];
			double[] out = new double[n];
			in[f] = 1;
			dctEngine.setNormalizationMode(FrequencyTransformNormalizationMode.NONE);
			dctEngine.reverse(in, out);
			for (int i = 0; i < n; i++) {
				System.out.printf("%5.2f ", out[i]);
			}
			System.out.print('\t');
			for (int i = 0; i < n; i++) {
				System.out.print("▁▂▃▄▅▆▇█".charAt((int)Math.round(3.5 + out[i] * 3.5)));
			}
			System.out.print('\t');
			dctEngine.setNormalizationMode(FrequencyTransformNormalizationMode.ONE_OVER_N);
			dctEngine.forward(out, in);
			for (int i = 0; i < n; i++) {
				System.out.printf("%5.2f ", in[i]);
			}
			System.out.println();
		}
	}

	private final int size;

	private FrequencyTransformNormalizationMode normalizationMode = FrequencyTransformNormalizationMode.NONE;

	public DCTEngine(int size) {
		this.size = requireAtLeast(2, size, "size");
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

	public void forward(double[] in, double[] out) {
		requireRange(size, size, in.length, "in.length");
		requireRange(size, size, out.length, "out.length");

		for (int k = 0; k < size; k++) {
			out[k] = 0;
			for (int n = 0; n < size; n++) {
				out[k] += in[n] * Math.cos(NumberConstants.TAU05 / size * n * k);
			}
		}

		double scale;
		switch (normalizationMode) {
			case ONE_OVER_N:
				scale = 2.0 / size;
				break;
			case NONE:
				scale = 2.0;
				break;
			case ONE_OVER_SQRT_N:
				scale = 2.0 / Math.sqrt(size);
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + normalizationMode);
		}

		out[0] /= 2;
		for (int k = 0; k < size; k++) {
			out[k] *= scale;
		}
	}

	public void reverse(double[] in, double[] out) {
		requireRange(size, size, in.length, "in.length");
		requireRange(size, size, out.length, "out.length");

		for (int k = 0; k < size; k++) {
			out[k] = 0;
			for (int n = 0; n < size; n++) {
				out[k] += in[n] * Math.cos(NumberConstants.TAU05 / size * n * (k + 0.5));
			}
		}

		double scale;
		switch (normalizationMode) {
			case ONE_OVER_N:
				scale = 1.0 / size;
				break;
			case NONE:
				scale = 1.0;
				break;
			case ONE_OVER_SQRT_N:
				scale = 1.0 / Math.sqrt(size);
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + normalizationMode);
		}

		for (int k = 0; k < size; k++) {
			out[k] *= scale;
		}
	}
}
