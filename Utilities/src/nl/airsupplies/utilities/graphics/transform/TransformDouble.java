package nl.airsupplies.utilities.graphics.transform;

import java.awt.geom.AffineTransform;

/**
 * @author Mark Jeronimus
 */
// Created 2009-04-21
public interface TransformDouble {
	void setIdentity();

	double transformX(double x, double y);

	double transformY(double x, double y);

	double transformRelativeX(double x, double y);

	double transformRelativeY(double x, double y);

	double transformR(double r);

	double reverseX(double x, double y);

	double reverseY(double x, double y);

	double reverseRelativeX(double x, double y);

	double reverseRelativeY(double x, double y);

	double reverseR(double r);

	AffineTransform getSwingTransform();
}
