package nl.airsupplies.utilities.graphics.color;

import nl.airsupplies.utilities.annotation.UtilityClass;
import nl.airsupplies.utilities.gui.color.ColorUtilities;

/**
 * @author Mark Jeronimus
 */
// Created 2025-05-31 Split from ColorUtilities
@UtilityClass
public final class Color3fUtilities {
	public static Color3f fromSRGB(Color3f color) {
		return new Color3f(ColorUtilities.fromSRGB(color.r),
		                   ColorUtilities.fromSRGB(color.g),
		                   ColorUtilities.fromSRGB(color.b));
	}

	public static Color3f toSRGB(Color3f color) {
		return new Color3f(ColorUtilities.toSRGB(color.r),
		                   ColorUtilities.toSRGB(color.g),
		                   ColorUtilities.toSRGB(color.b));
	}
}
