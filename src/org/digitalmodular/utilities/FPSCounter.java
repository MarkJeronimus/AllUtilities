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

package org.digitalmodular.utilities;

import java.util.Arrays;

/**
 * @author Mark Jeronimus
 */
// Created 2005-10-13
public class FPSCounter {
	private final long[] lastTime = new long[64];
	private       int    pointer;
	private       int    count;

	private double elapsedTime;
	private double fps = 0;

	public void start() {
		long currentTime = System.nanoTime();
		pointer = 0;
		count = 0;
		Arrays.fill(lastTime, currentTime);
	}

	public void update() {
		long currentTime = System.nanoTime();

		int lastPointer = pointer;
		pointer = pointer + 1 & 63;

		if (count < 64) {
			count++;
		}

		fps = 1.0e9 * count / (currentTime - lastTime[pointer]);
		elapsedTime = (currentTime - lastTime[lastPointer]) / 1.0e9;

		lastTime[pointer] = currentTime;
	}

	public double getElapsedTime() {
		return elapsedTime;
	}

	public double getFPS() {
		return fps;
	}
}
