package nl.airsupplies.utilities.graphics.color;

import java.awt.image.IndexColorModel;

/**
 * @author Mark Jeronimus
 */
// Created 2009-09-28
public abstract class StaticColorModels {
	private static IndexColorModel bwDefault   = null;
	private static IndexColorModel grayDefault = null;

	public static IndexColorModel get1bppBW() {
		if (bwDefault == null) {
			byte[] cmap = {0, (byte)255};
			bwDefault = new IndexColorModel(1, 2, cmap, cmap, cmap);
		}

		return bwDefault;
	}

	public static IndexColorModel get8bppGray() {
		if (grayDefault == null) {
			byte[] cmap = new byte[256];
			for (int i = 0; i < 256; i++) {
				cmap[i] = (byte)i;
			}

			grayDefault = new IndexColorModel(8, 256, cmap, cmap, cmap);
		}

		return grayDefault;
	}
}
