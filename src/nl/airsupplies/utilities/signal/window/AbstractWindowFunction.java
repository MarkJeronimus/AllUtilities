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

package nl.airsupplies.utilities.signal.window;

import nl.airsupplies.utilities.nodes.EnumParam;
import nl.airsupplies.utilities.nodes.Generator;
import nl.airsupplies.utilities.nodes.IntParam;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * a {@code AbstractWindowFunction} is a {@link Generator} that generates 'windowing functions'.
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-17
// Changed 2016-16-26
// Changed 2019-08-30 replace hardcoded params with annotations
public abstract class AbstractWindowFunction implements WindowFunction, Generator<double[], double[]> {
	@IntParam(description = "The number of points in the window", min = 2)
	private int length = 1024;

	@EnumParam(description = "The way the window is sampled", type = WindowSymmetryMode.class)
	private WindowSymmetryMode symmetryMode = WindowSymmetryMode.DFT_EVEN;

	@EnumParam(description = "The way the amplitude of the window is scaled", type = WindowNormalizationMode.class)
	private WindowNormalizationMode normalizationMode = WindowNormalizationMode.AREA;

	private double[] window = null;

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = requireAtLeast(2, length, "length");
	}

	public WindowSymmetryMode getSymmetryMode() {
		return symmetryMode;
	}

	public void setSymmetryMode(WindowSymmetryMode symmetryMode) {
		this.symmetryMode = requireNonNull(symmetryMode, "symmetryMode");
	}

	public WindowNormalizationMode getNormalizationMode() {
		return normalizationMode;
	}

	public void setNormalizationMode(WindowNormalizationMode normalizationMode) {
		this.normalizationMode = requireNonNull(normalizationMode, "normalizationMode");
	}

	/**
	 * initialize the window generator with a pre-allocated and re-usable array.
	 * <p>
	 * Set to {@code null} to allocate a new {@code double[]} on every call to {@link #generate()}.
	 *
	 * @param input The array to re-use when generating windows.
	 */
	@Override
	@SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType") // By design
	public void initialize(double[] input) {
		window = input;
	}

	@Override
	public double[] generate() {
		int      length = getLength();
		double[] window = getWindowArray(length);

		// Preprocessing of the sample positions
		WindowFunctionUtilities.sampleWindow(window, length, getSymmetryMode());

		WindowFunctionUtilities.makeWindow(window, length, this);

		// Postprocessing of the window values
		WindowFunctionUtilities.normalize(window, length, getNormalizationMode());

		return window;
	}

	protected double[] getWindowArray(int length) {
		// If not initialized with a re-usable array, allocate a new array every time.
		double[] window = this.window != null ? this.window : new double[length];

		if (window.length < length) {
			throw new IllegalStateException(
					"Configured length exceeds array length: " + length + " > " + window.length);
		}

		return window;
	}
}
