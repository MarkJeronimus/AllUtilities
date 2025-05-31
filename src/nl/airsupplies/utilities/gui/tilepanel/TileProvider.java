package nl.airsupplies.utilities.gui.tilepanel;

import java.awt.image.BufferedImage;

import org.jetbrains.annotations.Nullable;

/**
 * @author Mark Jeronimus
 */
// Created 2013-02-15
public interface TileProvider {
	int getTileSize();

	int getTileShift();

	@Nullable BufferedImage getTile(int x, int y, int zoom);

	BufferedImage getDefaultTile(int x, int y, int zoom);
}
