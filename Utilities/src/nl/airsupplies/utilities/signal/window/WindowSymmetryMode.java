package nl.airsupplies.utilities.signal.window;

/**
 * @author Mark Jeronimus
 */
// Created 2016-03-27
public enum WindowSymmetryMode {
	DFT_EVEN {
		@Override
		public double getValueAt(int x, int length) {
			return x / (double)length;
		}
	},
	SYMMETRIC {
		@Override
		public double getValueAt(int x, int length) {
			return (x + 0.5) / length;
		}
	},
	PERIODIC {
		@Override
		public double getValueAt(int x, int length) {
			return (x + 0.5) / (length + 1.0);
		}
	};

	public abstract double getValueAt(int x, int length);
}
