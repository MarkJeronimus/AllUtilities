package nl.airsupplies.utilities.signal.window;

import nl.airsupplies.utilities.nodes.DoubleParam;
import nl.airsupplies.utilities.nodes.EnumParam;
import nl.airsupplies.utilities.nodes.Generator;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAbove;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireRange;

/**
 * a {@code AbstractWindowFunction} is a {@link Generator} that generates 'windowing functions'.
 *
 * @author Mark Jeronimus
 */
// Created 2016-54-28
// Changed 2019-08-30
// Changed 2019-08-30 replace hardcoded params with annotations
public abstract class AbstractGeneralizedWindowFunction extends AbstractWindowFunction {
	@EnumParam(description = "The way the window is tapered", type = WindowTaperMode.class)
	private WindowTaperMode taperMode = WindowTaperMode.TRAPEZIUM;

	@DoubleParam(description = "The ratio of the time scale that's being tapered", min = 0, max = 1)
	private double taper = 1.0;

	@DoubleParam(description = "The power used to exponentiate the window function to",
	             min = 0, minIsInclusive = false, maxIsInclusive = false)
	private double power = 1.0;

	@DoubleParam(description = "The power used to exponentiate the inverse of the window function to",
	             min = 0, minIsInclusive = false, maxIsInclusive = false)
	private double invPower = 1.0;

	@DoubleParam(description = "Linearly interpolates between the power applied before the invPower" +
	                           " and the invPower applied before the power", min = 0, max = 1)
	private double powerLerp = 0;

	@DoubleParam(description = "Scales the window samples relative to 1.0." +
	                           " For some values, this creates flat-top windows",
	             min = 0, minIsInclusive = false, maxIsInclusive = false)
	private double topScale = 1.0;

	public WindowTaperMode getTaperMode() {
		return taperMode;
	}

	public void setTaperMode(WindowTaperMode taperMode) {
		this.taperMode = requireNonNull(taperMode, "taperMode");
	}

	public double getTaper() {
		return taper;
	}

	public void setTaper(double taper) {
		this.taper = requireRange(0, 1, taper, "taper");
	}

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = requireRange(1, 10, power, "power");
	}

	public double getInvPower() {
		return invPower;
	}

	public void setInvPower(double invPower) {
		this.invPower = requireRange(0.01, 100, invPower, "invPower");
	}

	public double getPowerLerp() {
		return powerLerp;
	}

	public void setPowerLerp(double powerLerp) {
		this.powerLerp = requireRange(0, 1, powerLerp, "powerLerp");
	}

	public double getTopScale() {
		return topScale;
	}

	public void setTopScale(double topScale) {
		this.topScale = requireAbove(0, topScale, "topScale");
	}

	@Override
	public double[] generate() {
		int      length = getLength();
		double[] window = getWindowArray(length);

		// Preprocessing of the sample positions
		WindowFunctionUtilities.sampleWindow(window, length, getSymmetryMode());
		WindowFunctionUtilities.taperWindow(window, length, getTaperMode(), getTaper());

		WindowFunctionUtilities.makeWindow(window, length, this);

		// Postprocessing of the window values
		WindowFunctionUtilities.lerpedBiPower(window, length, getPower(), getInvPower(), getPowerLerp());
		WindowFunctionUtilities.topScale(window, length, getTopScale());
		WindowFunctionUtilities.normalize(window, length, getNormalizationMode());

		return window;
	}
}
