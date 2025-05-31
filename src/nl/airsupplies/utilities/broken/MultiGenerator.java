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

package nl.airsupplies.utilities.broken;

import java.util.ArrayList;
import java.util.List;

import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;
import nl.airsupplies.utilities.graphics.image.generator.ImageGenerator;

/**
 * @author Mark Jeronimus
 */
// Created 2012-04-05
public class MultiGenerator extends ImageGenerator {
	private List<ImageGenerator> generators = new ArrayList<>();

	public void add(ImageGenerator generator) {
		generators.add(generator);
	}

	@Override
	public ImageMatrixFloat generate() {
		image.set(0);

		for (ImageGenerator generator : generators) {
			image.add(generator.generate());
		}

		return image;
	}
}
