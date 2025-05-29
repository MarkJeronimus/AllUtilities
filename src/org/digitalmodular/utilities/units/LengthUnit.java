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

package org.digitalmodular.utilities.units;

/**
 * A <tt>LengthUnit</tt> represents length measures and provides utility methods to convert across units. All unit
 * definitions are exact (not rounded) unless stated otherwise. Conversions involving a non-metric unit may introduce
 * rounding errors.
 *
 * @author Mark Jeronimus
 */
// Created 2014-06-05
public enum LengthUnit {
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Metric
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * SI prefixed unit of meter (1<sup>-24</sup> m).
	 */
	YOCTOMETER(1.0e-24, "ym"),
	/**
	 * SI prefixed unit of meter (1<sup>-21</sup> m).
	 */
	ZEPTOMETER(1.0e-21, "zm"),
	/**
	 * SI prefixed unit of meter (1<sup>-18</sup> m).
	 */
	ATTOMETER(1.0e-18, "am"),
	/**
	 * SI prefixed unit of meter (1<sup>-15</sup> m).
	 */
	FEMTOMETER(1.0e-15, "fm"),
	/**
	 * SI picometer (1<sup>-12</sup> m).
	 */
	PICOMETER(1.0e-12, "pm"),
	/**
	 * A deprecated(officially discouraged") use of length equal to 0.1 nm.
	 */
	ANGSTROM(1.0e-10, "\u00C5m"),
	/**
	 * SI nanometer (1<sup>-9</sup> m).
	 */
	NANOMETER(1.0e-9, "nm"),
	/**
	 * SI micrometer (1/1000 mm).
	 */
	MICROMETER(1.0e-6, "µm"),
	/**
	 * SI millimeter (1/1000 m).
	 */
	MILLIMETER(1.0e-3, "mm"),
	/**
	 * SI decimeter (1/100 m).
	 */
	DECIMETER(1.0e-2, "cm"),
	/**
	 * SI centimeter (1/10 m).
	 */
	CENTIMETER(1.0e-1, "dm"),
	/**
	 * SI meter.
	 */
	METER(1.0, "m"),
	/**
	 * SI decameter (10 m).
	 */
	DECAMETER(1.0e1, "dam"),
	/**
	 * SI hectometer (100 m).
	 */
	HECTOMETER(1.0e2, "hm"),
	/**
	 * SI kilometer (1000 m).
	 */
	KILOMETER(1.0e3, "km"),
	/**
	 * Exactly defined as 1852 SI meters.
	 */
	NAUTICAL_MILE(1852, "nmi"),
	/**
	 * SI megameter (1000 km).
	 */
	MEGAMETER(1.0e6, "Mm"),
	/**
	 * SI prefixed unit of meter (1<sup>9</sup> m).
	 */
	GIGAMETER(1.0e9, "Gm"),
	/**
	 * SI prefixed unit of meter (1<sup>12</sup> m).
	 */
	TERAMETER(1.0e12, "Tm"),
	/**
	 * SI prefixed unit of meter (1<sup>15</sup> m).
	 */
	PETAMETER(1.0e15, "Pm"),
	/**
	 * SI prefixed unit of meter (1<sup>18</sup> m).
	 */
	ETAMETER(1.0e18, "Em"),
	/**
	 * SI prefixed unit of meter (1<sup>21</sup> m).
	 */
	ZETTAMETER(1.0e21, "Zm"),
	/**
	 * SI prefixed unit of meter (1<sup>24</sup> m).
	 */
	YOTTAMETER(1.0e24, "Ym"),
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Imperial
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Imperial unit equal to 1/1000th inch.
	 */
	MIL(0.0254e-3, "mil"),
	/**
	 * Imperial unit equal to 1/72th inch.
	 */
	POINT(127.0 / 360000, "pt"),
	/**
	 * Imperial unit equal to 2.54 cm.
	 */
	INCH(0.0254, "in"),
	/**
	 * Imperial unit equal to 12 inch.
	 */
	FOOT(0.3048, "ft"),
	/**
	 * Imperial unit equal to 3 foot inch.
	 */
	YARD(0.9144, "yd"),
	/**
	 * Imperial unit equal to 1760 yards.
	 */
	MILE(1609.344, "mi"),
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Chinese
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Chinese inch equal to 1/10 {@link #CHI} (10/3 cm).
	 */
	CUN(0.1 / 3, "c\u00F9n"),
	/**
	 * Chinese foot equal to 1/3 m.
	 */
	CHI(1.0 / 3, "ch\u01D0"),
	/**
	 * Chinese unit equal to 10 {@link #CHI} (10/3 m).
	 */
	ZHANG(10.0 / 3, "zh\u00E0ng"),
	/**
	 * Chinese unit equal to 150 {@link #ZHANG} (500 m).
	 */
	LI(1500.0 / 3, "l\u01D0"),
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Japanese
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Japanese inch equal to 1/10 {@link #SHAKU} (100/33 cm).
	 */
	SUN(1.0 / 33, "sun"),
	/**
	 * Japanese foot equal to 10/33 m
	 */
	SHAKU(10.0 / 33, "shaku"),
	/**
	 * Japanese unit equal to 6 {@link #SHAKU} (60/33 m).
	 */
	KEN(60.0 / 33, "ken"),
	/**
	 * Japanese unit equal to 2160 {@link #KEN} (129.6/33 km).
	 */
	RI(129600.0 / 33, "ri"),
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Scientific
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * The Planck length is the 'quantum of length' (in Quantum physics), the smallest measurement of length with any
	 * meaning. This is the scale at which classical ideas about gravity and space-time cease to be valid, and quantum
	 * effects dominate. it's not very precisely known, and approximated as (1.61619926 \u00B10.000097) \u00B7
	 * 10<sup>-35</sup> SI meters.
	 */
	PLANCK_LENGTH(1.61619926e-35, "\u2113P"),
	/**
	 * The wave length of the frequency emitted when a Hydrogen atom undergoes hyperfine transition. This is the ideal
	 * 'universal' length base that's not related to anything invented or defined on Earth. It's used in the Voyager
	 * spaceships and the SETI project. (approximate)
	 */
	HYDROGEN_LINE(0.2110611405413, "H"),
	/**
	 * The approximate mean distance between the Earth and Sun. (defined exactly as 149597870700 SI meters)
	 */
	ASTRONOMICAL_UNIT(149597870700.0, "AU"),
	/**
	 * The distance traveled by light in vacuum in a year. (defined exactly as 9460730472580800 SI meters)
	 */
	LIGHT_YEAR(9460730472580800.0, "ly"),
	/**
	 * The distance a star should be so it's <b>par</b>allax as seen from earth is 1 arc <b>sec</b>ond. It's about 3.26
	 * {@link #LIGHT_YEAR}s. (defined in terms of {@link #ASTRONOMICAL_UNIT} and rounded to {@code double}
	 * precision)
	 */
	PARSEC(96939420213600000.0 / Math.PI, "pc"),
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Special
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Generic unit to signify that no conversion should happen from or to any other unit. Use this when the unit is
	 * unitless, incompatible or unknown.
	 */
	GENERIC_UNIT(1, "units");

	private final double meters;
	private final String unit;

	LengthUnit(double meters, String unit) {
		this.meters = meters;
		this.unit   = unit;
	}

	public double getMeters() {
		return meters;
	}

	public String getUnit() {
		return unit;
	}

	public double convertTo(LengthUnit unit, double length) {
		// Don't convert when either is GENERIC_UNIT.
		if (this == GENERIC_UNIT || unit == GENERIC_UNIT) {
			return length;
		}

		return length * (meters / unit.meters);
	}
}
