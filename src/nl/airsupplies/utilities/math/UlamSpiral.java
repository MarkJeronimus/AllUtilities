package nl.airsupplies.utilities.math;

import java.awt.Point;

import nl.airsupplies.utilities.annotation.UtilityClass;

/**
 * @author Mark Jeronimus
 */
// Created 2022-12-21
@UtilityClass
public final class UlamSpiral {
	public static Point getUlamCoordinate(int n) {
		int s = (int)Math.floor(Math.sqrt(n));
		int v = n - s * s;
		int u = s / 2;

		if ((s & 1) == 0) {
			if (v <= s) {
				return new Point(u, v - u);
			} else {
				return new Point(3 * u - v, u);
			}
		} else {
			if (v <= s) {
				return new Point(-1 - u, -v + u);
			} else {
				return new Point(v - 3 * u - 2, -1 - u);
			}
		}
	}
}
