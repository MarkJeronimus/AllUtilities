package nl.airsupplies.utilities.brokenorold;

import java.awt.Font;
import static java.awt.Font.PLAIN;

import nl.airsupplies.utilities.annotation.ConstClass;

/**
 * @author Mark Jeronimus
 */
// Created 2007-12-01
@Deprecated
@ConstClass
public final class StaticFonts {
	public static final Font DEFAULT_SANSSERIF_FONT  = new Font("SansSerif", PLAIN, 12);
	public static final Font DEFAULT_MONOSPACED_FONT = new Font("Monospaced", PLAIN, 12);
	public static final Font DEFAULT_SERIF_FONT      = new Font("Serif", PLAIN, 12);
}
