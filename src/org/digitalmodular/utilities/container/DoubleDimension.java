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

package org.digitalmodular.utilities.container;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.io.Serializable;

import net.jcip.annotations.NotThreadSafe;

/**
 * Like {@link Dimension}, which only has {@code int} properties. As of Java 8 there is no public subclass of
 * {@link Dimension2D} with {@code double} properties.
 *
 * @author Mark Jeronimus
 */
// Created 2015-01-12
@NotThreadSafe
public class DoubleDimension extends Dimension2D implements Serializable {
	private double width;
	private double height;

	public DoubleDimension(double width, double height) {
		this.width = width;
		this.height = height;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	@Override
	public double getWidth() {
		return width;
	}

	@Override
	public double getHeight() {
		return height;
	}

	@Override
	public void setSize(double width, double height) {
		this.width = width;
		this.height = height;
	}
}
