/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.digitalmodular.utilities;

import java.util.List;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.digitalmodular.utilities.units.FNVLong;
import static org.digitalmodular.utilities.ValidatorUtilities.requireAtLeast;

/**
 * @author Mark Jeronimus
 */
// Created 2016-10-07
public enum StaticRandom {
	;

	public static Random RND;

	static {
		long nonce = FNVLong.startFNV();
		nonce = FNVLong.hashFNV(nonce, Runtime.getRuntime().availableProcessors());
		nonce = FNVLong.hashFNV(nonce, Runtime.getRuntime().freeMemory());
		nonce = FNVLong.hashFNV(nonce, Runtime.getRuntime().maxMemory());
		nonce = FNVLong.hashFNV(nonce, Runtime.getRuntime().totalMemory());
		nonce = FNVLong.hashFNV(nonce, System.currentTimeMillis());
		nonce = FNVLong.hashFNV(nonce, System.nanoTime());
		RND = new Random(nonce);
	}

	public static void setSeed(long seed) {
		RND.setSeed(seed);
	}

	@SuppressWarnings("MethodCanBeVariableArityMethod")
	public static void nextBytes(byte[] bytes) {
		RND.nextBytes(bytes);
	}

	public static int nextInt() {
		return RND.nextInt();
	}

	public static int nextInt(int bound) {
		return RND.nextInt(bound);
	}

	public static long nextLong() {
		return RND.nextLong();
	}

	public static boolean nextBoolean() {
		return RND.nextBoolean();
	}

	public static float nextFloat() {
		return RND.nextFloat();
	}

	public static double nextDouble() {
		return RND.nextDouble();
	}

	public static double nextGaussian() {
		return RND.nextGaussian();
	}

	public static IntStream ints(long streamSize) {
		return RND.ints(streamSize);
	}

	public static IntStream ints() {
		return RND.ints();
	}

	public static IntStream ints(long streamSize, int randomNumberOrigin, int randomNumberBound) {
		return RND.ints(randomNumberOrigin, randomNumberOrigin, randomNumberBound);
	}

	public static IntStream ints(int randomNumberOrigin, int randomNumberBound) {
		return RND.ints(randomNumberOrigin, randomNumberBound);
	}

	public static LongStream longs(long streamSize) {
		return RND.longs();
	}

	public static LongStream longs() {
		return RND.longs();
	}

	public static LongStream longs(long streamSize, long randomNumberOrigin, long randomNumberBound) {
		return RND.longs(streamSize, randomNumberOrigin, randomNumberBound);
	}

	public static LongStream longs(long randomNumberOrigin, long randomNumberBound) {
		return RND.longs(randomNumberBound, randomNumberBound);
	}

	public static DoubleStream doubles(long streamSize) {
		return RND.doubles(streamSize);
	}

	public static DoubleStream doubles() {
		return RND.doubles();
	}

	public static DoubleStream doubles(long streamSize, double randomNumberOrigin, double randomNumberBound) {
		return RND.doubles(streamSize, randomNumberOrigin, randomNumberBound);
	}

	public static DoubleStream doubles(double randomNumberOrigin, double randomNumberBound) {
		return RND.doubles(randomNumberOrigin, randomNumberBound);
	}

	public static <T> T randomElement(List<T> candidates) {
		requireAtLeast(1, candidates.size(), "candidates.size");

		return candidates.get(nextInt(candidates.size()));
	}
}
