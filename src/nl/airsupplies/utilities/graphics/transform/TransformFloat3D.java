package nl.airsupplies.utilities.graphics.transform;

/**
 * @author Mark Jeronimus
 */
// Created 2009-04-21
public interface TransformFloat3D {
	void setIdentity();

	float transformX(float x, float y, float z);

	float transformY(float x, float y, float z);

	float transformZ(float x, float y, float z);

	float transformRelativeX(float x, float y, float z);

	float transformRelativeY(float x, float y, float z);

	float transformRelativeZ(float x, float y, float z);

	float transformR(float r);

	float reverseX(float x, float y, float z);

	float reverseY(float x, float y, float z);

	float reverseZ(float x, float y, float z);

	float reverseRelativeX(float x, float y, float z);

	float reverseRelativeY(float x, float y, float z);

	float reverseRelativeZ(float x, float y, float z);

	float reverseR(float r);
}
