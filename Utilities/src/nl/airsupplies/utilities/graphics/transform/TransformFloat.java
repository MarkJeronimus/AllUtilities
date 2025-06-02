package nl.airsupplies.utilities.graphics.transform;

import java.awt.geom.AffineTransform;

/**
 * @author Mark Jeronimus
 */
// Created 2009-04-21
public interface TransformFloat {
	void setIdentity();

	float transformX(float x, float y);

	float transformY(float x, float y);

	float transformRelativeX(float x, float y);

	float transformRelativeY(float x, float y);

	float transformR(float r);

	float reverseX(float x, float y);

	float reverseY(float x, float y);

	float reverseRelativeX(float x, float y);

	float reverseRelativeY(float x, float y);

	float reverseR(float r);

	AffineTransform getSwingTransform();
}
