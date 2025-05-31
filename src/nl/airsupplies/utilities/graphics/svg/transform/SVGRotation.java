package nl.airsupplies.utilities.graphics.svg.transform;

import java.io.IOException;

import nl.airsupplies.utilities.NumberUtilities;
import nl.airsupplies.utilities.graphics.svg.core.SVGTransform;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNotDegenerate;

/**
 * @author Mark Jeronimus
 */
// Created 2021-06-11
public final class SVGRotation implements SVGTransform {
	private final float rotation;
	private final float rotationOriginX;
	private final float rotationOriginY;

	public SVGRotation(float rotation) {
		this.rotation   = requireNotDegenerate(rotation, "rotation");
		rotationOriginX = Float.NaN;
		rotationOriginY = Float.NaN;
	}

	public SVGRotation(float rotation, float rotationOriginX, float rotationOriginY) {
		this.rotation        = requireNotDegenerate(rotation, "rotation");
		this.rotationOriginX = requireNotDegenerate(rotationOriginX, "rotationOriginX");
		this.rotationOriginY = requireNotDegenerate(rotationOriginY, "rotationOriginY");
	}

	public float getRotation() {
		return rotation;
	}

	public float getRotationOriginX() {
		return rotationOriginX;
	}

	public float getRotationOriginY() {
		return rotationOriginY;
	}

	@Override
	public SVGTransform overwrite(SVGTransform transform) {
		if (transform instanceof SVGRotation other) {
			return new SVGRotation(other.rotation - rotation, other.rotationOriginX, other.rotationOriginY);
		}

		throw new UnsupportedOperationException("Not implemented: " + getClass().getSimpleName() +
		                                        ".overwrite() for " + transform.getClass().getSimpleName());
	}

	@Override
	public void encode(Appendable out) throws IOException {
		if (Math.abs(rotation) < 1.0e-3f) {
			return;
		}

		out.append(" transform=\"rotate(").append(Float.toString(rotation));

		if (!NumberUtilities.isDegenerate(rotationOriginX)) {
			out.append(',');
			out.append(Float.toString(rotationOriginX));
			out.append(',');
			out.append(Float.toString(rotationOriginY));
		}

		out.append(")\"");
	}
}
