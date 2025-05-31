package nl.airsupplies.utilities.container;

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
		this.width  = width;
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
		this.width  = width;
		this.height = height;
	}
}
