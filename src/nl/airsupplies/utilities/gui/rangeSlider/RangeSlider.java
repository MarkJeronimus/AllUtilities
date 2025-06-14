package nl.airsupplies.utilities.gui.rangeSlider;

import java.awt.Color;
import javax.swing.JSlider;

/**
 * @author Mark Jeronimus
 */
// Created 2011-08-19
public class RangeSlider extends JSlider {
	private RangeSliderUI rangeUI    = null;
	private Color         rangePaint = Color.GRAY;

	/**
	 * Constructs a RangeSlider with default minimum and maximum values of 0 and 100.
	 */
	public RangeSlider() {
		initSlider();
	}

	/**
	 * Creates a horizontal RangeSlider using the specified min and max with an initial value equal to the average of
	 * the min plus max.
	 */
	public RangeSlider(int min, int max) {
		super(min, max);
		initSlider();
	}

	/**
	 * Creates a RangeSlider with the specified orientation and the specified minimum, maximum, and initial values. The
	 * orientation can be either SwingConstants.VERTICAL or SwingConstants.HORIZONTAL.
	 */
	public RangeSlider(int orientation, int min, int max, int lowerValue, int upperValue) {
		super(orientation, min, max, lowerValue);
		setUpperValue(upperValue);
	}

	/**
	 * Initializes the slider by setting default properties.
	 */
	private void initSlider() {
		setOrientation(HORIZONTAL);
	}

	/**
	 * Overrides the superclass method to install the UI delegate to draw two thumbs.
	 */
	@Override
	public void updateUI() {
		rangeUI = new RangeSliderUI(this);
		rangeUI.setRangePaint(rangePaint);

		setUI(rangeUI);
		// Update UI for slider labels. This must be called after updating the
		// UI of the slider. Refer to JSlider.updateUI().
		updateLabelUIs();
	}

	/**
	 * Returns the lower value in the range.
	 */
	@Override
	public int getValue() {
		return super.getValue();
	}

	/**
	 * Sets the lower value in the range.
	 */
	@Override
	public void setValue(int value) {
		int oldValue = getValue();
		if (oldValue == value) {
			return;
		}

		// Compute new value and extent to maintain upper value.
		int oldExtent = getExtent();
		int newValue  = Math.min(Math.max(getMinimum(), value), oldValue + oldExtent);
		int newExtent = oldExtent + oldValue - newValue;

		// Set new value and extent, and fire a single change event.
		getModel().setRangeProperties(newValue, newExtent, getMinimum(), getMaximum(), getValueIsAdjusting());
	}

	/**
	 * Returns the upper value in the range.
	 */
	public int getUpperValue() {
		return getValue() + getExtent();
	}

	/**
	 * Sets the upper value in the range.
	 */
	public void setUpperValue(int value) {
		// Compute new extent.
		int lowerValue = getValue();
		int newExtent  = Math.min(Math.max(0, value - lowerValue), getMaximum() - lowerValue);

		// Set extent to set upper value.
		setExtent(newExtent);
	}

	public void setRangeColor(Color paint) {
		rangePaint = paint;

		updateUI();
	}

	public Color getRangeColor() {
		return rangePaint;
	}
}
