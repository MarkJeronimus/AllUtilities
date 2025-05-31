package nl.airsupplies.utilities.gui;

/**
 * @author Oracle
 */
// Created 2018-03-15 Copied from javafx.geometry.Side
public enum Side {
	TOP(Orientation.HORIZONTAL),
	BOTTOM(Orientation.HORIZONTAL),
	LEFT(Orientation.VERTICAL),
	RIGHT(Orientation.VERTICAL);

	private final Orientation orientation;

	Side(Orientation orientation) {
		this.orientation = orientation;
	}

	/**
	 * @return the orientation of the long axis of this side.
	 */
	public Orientation getOrientation() {
		return orientation;
	}
}
