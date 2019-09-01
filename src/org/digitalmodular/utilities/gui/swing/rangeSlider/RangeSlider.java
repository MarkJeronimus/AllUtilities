/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2019 Mark Jeronimus. All Rights Reversed.
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
 * along with AllUtilities. If not, see <http://www.gnu.org/licenses/>.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.digitalmodular.utilities.gui.swing.rangeSlider;

import java.awt.Color;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

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
		setOrientation(SwingConstants.HORIZONTAL);
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
