package nl.airsupplies.utilities.graphics.gradient;

import java.awt.Color;

import nl.airsupplies.utilities.NumberUtilities;
import nl.airsupplies.utilities.graphics.color.Color4f;
import nl.airsupplies.utilities.gui.color.ColorPalette;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * A {@link ColorPalette} returns {@link Color}s when given an position.
 * <p>
 * The position can be any arbitrarily floating point number, and the gradient is defined in the range [0, 1].
 * Implementations can choose how to handle out-of-range numbers, for example, clamping, looping or reflecting.
 * In case of looping, the colors at 0.0 and at 1.0 are required to be identical,
 * so the range of 'defined' colors shrinks to [0, 1). If not, behavior at non-zero integer positions is undefined.
 *
 * @author Mark Jeronimus
 */
// Created 2009-04-19
public abstract class Gradient {
	/**
	 * @author Mark Jeronimus
	 */
	// Created 2024-07-24
	public enum ClampingMode {
		/** Values outside the range [0, 1] are clamped. */
		CLAMP,
		/** Values equal to or above 1.0 wrap around. Negative values are clamped. */
		REPEAT,
		/** Values outside the range [0, 1) wrap around. */
		LOOP,
		/** Values above 1.0 bounce back to 0.0, and so on. Negative values are clamped. */
		BOUNCE_HALF,
		/** Values outside the range [0, 1] bounce until within the range. */
		BOUNCE,
	}

	private final ClampingMode clampingMode;
	private final boolean      hasTransparency;

	protected Gradient(ClampingMode clampingMode, boolean hasTransparency) {
		this.clampingMode    = requireNonNull(clampingMode, "clampingMode");
		this.hasTransparency = hasTransparency;
	}

	public ClampingMode getClampingMode() {
		return clampingMode;
	}

	/**
	 * Returns whether the gradient as a whole contains transparency.
	 * <p>
	 * In case the gradient has no transparency, the alpha channel will be 1.0.
	 */
	public boolean hasTransparency() {
		return hasTransparency;
	}

	public abstract Color4f get(double position);

	public final int getARGB(double position) {
		return get(position).toInteger();
	}

	public final Color getColor(double position) {
		return get(position).toColor();
	}

	protected double applyEdgeBehavior(double position) {
		return switch (clampingMode) {
			case CLAMP -> NumberUtilities.clamp(position, 0.0, 1.0);
			case REPEAT -> Math.max(position, 0.0) % 1.0;
			case LOOP -> NumberUtilities.modulo(position, 1.0);
			case BOUNCE_HALF -> 1.0 - Math.abs(Math.max(position, 0.0) % 2.0 - 1.0);
			case BOUNCE -> 1.0 - Math.abs(NumberUtilities.modulo(position, 2.0) - 1.0);
		};
	}
}
