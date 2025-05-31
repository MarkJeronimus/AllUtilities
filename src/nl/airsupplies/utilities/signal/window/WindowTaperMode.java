package nl.airsupplies.utilities.signal.window;

import nl.airsupplies.utilities.NumberUtilities;

/**
 * @author Mark Jeronimus
 */
// Created 2016-03-28
public enum WindowTaperMode {
	TRAPEZIUM {
		@Override
		public double getValueAt(double x, double taper) {
			if (taper <= 0) {
				return 0.5;
			}
			if (x < 0.5) {
				return Math.min(0.5, x / taper);
			} else {
				return Math.max(0.5, 1 - (1 - x) / taper);
			}
		}
	},
	SMOOTH {
		@Override
		public double getValueAt(double x, double taper) {
			if (taper <= 0) {
				return 0.5;
			}
			return (NumberUtilities.powSign(2 * x - 1, 1 / taper) + 1) / 2;
		}
	};

	public abstract double getValueAt(double x, double taper);
}
