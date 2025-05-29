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

package org.digitalmodular.utilities.math;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Mark Jeronimus
 */
// Created 2005-10-29
public class LabeledGraph {
	private Random random;

	public int numNodes;
	public int maxConnections;

	public int[][] connections;

	public LabeledGraph() {
	}

	public LabeledGraph(int numNodes, int numConnections, Random random) {
		this.random = random;

		setNumNodes(numNodes);
		setNumConnections(numConnections);
	}

	public void setNumNodes(int numNodes) {
		if (numNodes < 2) {
			throw new IllegalArgumentException("numNodes < 2: " + numNodes);
		}

		this.numNodes  = numNodes;
		maxConnections = this.numNodes * (this.numNodes - 1) >> 1;

		connections = new int[numNodes][numNodes];
	}

	public void setNumConnections(int numConnections) {
		int i, x, y;

		if (numConnections < 0) {
			throw new IllegalArgumentException("numConnections < 0: " + numConnections);
		}
		// MJ: minor Bugfix: Maximum number of connections was erroneously not
		// allowed.
		else if (numConnections > maxConnections) {
			throw new IllegalArgumentException(
					"Too much connections for a graph of this size: " + numConnections + " (size="
					+ numNodes + ",maxConnections=" + (maxConnections - 1) + ')');
		}

		for (y = 0; y < numNodes; y++) {
			Arrays.fill(connections[y], 0);
		}

		for (i = 0; i < numConnections; ) {
			x = random.nextInt(numNodes);
			y = random.nextInt(numNodes);

			if (!(x == y || connections[y][x] != 0)) {
				++i;
				connections[y][x] = connections[x][y] = i;
			}
		}
	}

	public void reorder(int[] order) {
		if (order.length != numNodes) {
			throw new IllegalArgumentException();
		}

		int[][] out = new int[numNodes][numNodes];
		for (int y = 0; y < numNodes; y++) {
			int newy = order[y];
			for (int x = 0; x < numNodes; x++) {
				int newx = order[x];

				out[newy][newx] = connections[y][x];
			}
		}
		connections = out;
	}

	public void sort() {
		for (int y = 0; y < (numNodes >> 1) - 1; y++) {
			int min = y;
			int max = numNodes - y - 1;
			for (int i = y + 1; i < numNodes - y; i++) {
				if (compareArrayRows(connections[min], connections[i]) > 0) {
					min = i;
				}
				if (compareArrayRows(connections[max], connections[i]) < 0) {
					max = i;
				}
			}
			if (min != y) {
				swapArrayRows(min, y);
			}
			if (max != y) {
				swapArrayRows(max, y);
			}
		}
	}

	private static int compareArrayRows(int[] array1, int[] array2) {
		for (int i = 0; i < array1.length; i++) {
			if (array1[i] != array2[i]) {
				if (array1[i] != 0) {
					return 1;
				}
				if (array2[i] != 0) {
					return -1;
				}
			}
		}
		return 0;
	}

	private void swapArrayRows(int i1, int i2) {
		{
			int[] temp = connections[i1];
			connections[i1] = connections[i2];
			connections[i2] = temp;
		}
	}

	public int countConnections() {
		int num = 0;
		for (int y = 0; y < numNodes; y++) {
			for (int x = 0; x < y; x++) {
				if (connections[y][x] != 0) {
					num++;
				}
			}
		}
		return num;
	}

	public static String array2DToString(int[][] array) {
		StringBuffer b = new StringBuffer(array.length * (array.length + 1));
		for (int y = 0; y < array.length; y++) {
			for (int x = 0; x < array.length; x++) {
				b.append(x == y ? " " : Integer.toString(array[y][x]));
				b.append('\t');
			}
			b.append('\n');
		}
		return b.toString();
	}

	@Override
	public String toString() {
		return array2DToString(connections);
	}
}
