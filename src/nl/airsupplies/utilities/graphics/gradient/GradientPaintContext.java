package nl.airsupplies.utilities.graphics.gradient;

import java.awt.PaintContext;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;

import org.jetbrains.annotations.Nullable;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireState;

/**
 * @author Mark Jeronimus
 */
// Created 2009-04-04
abstract class GradientPaintContext implements PaintContext {
	private static final ColorModel COLOR_MODEL_24 = new DirectColorModel(24, 16711680, 65280, 255);
	private static final ColorModel COLOR_MODEL_32 = ColorModel.getRGBdefault();

	private @Nullable ColorModel     persistentColorModel = null;
	private @Nullable WritableRaster persistentRaster     = null;

	@SuppressWarnings("FieldHasSetterButNoGetter")
	protected @Nullable Gradient gradient = null;

	private @Nullable ColorModel colorModel = null;

	protected GradientPaintContext() {
	}

	public void setGradient(Gradient gradient) {
		this.gradient = gradient;

		colorModel = gradient.hasTransparency() ? COLOR_MODEL_32 : COLOR_MODEL_24;
	}

	@Override
	public final Raster getRaster(int x, int y, int w, int h) {
		ensureRaster(w, h);

		assert persistentRaster != null;
		DataBufferInt dataBuffer = (DataBufferInt)persistentRaster.getDataBuffer();

		int[] pixels = dataBuffer.getData();
		int   offset = dataBuffer.getOffset();
		int skip = ((SinglePixelPackedSampleModel)persistentRaster.getSampleModel())
				           .getScanlineStride() - w;

		fillRaster(x, y, w, h, pixels, offset, skip);

		return persistentRaster;
	}

	protected abstract void fillRaster(int x, int y, int w, int h, int[] pixels, int offset, int skip);

	private synchronized void ensureRaster(int w, int h) {
		requireState(colorModel != null, () -> "setGradient() has not been called");

		//noinspection ObjectEquality
		if (colorModel != persistentColorModel || persistentRaster == null ||
		    persistentRaster.getWidth() < w || persistentRaster.getHeight() < h) {

			persistentColorModel = colorModel;
			persistentRaster     = colorModel.createCompatibleWritableRaster(w, h);
		}
	}

	@Override
	public final synchronized void dispose() {
		persistentColorModel = null;
		persistentRaster     = null;
	}

	@Override
	public final @Nullable ColorModel getColorModel() {
		return colorModel;
	}
}
