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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * @author Mark Jeronimus
 */
// Created 2005-10-29
public class ConnectedGraph {
	private final Random rnd;

	private static final byte[] BITS = {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, (byte)0x80};

	public int numNodes;
	public int maxConnections;

	public boolean[][] connections;

	public ConnectedGraph(Random random) {
		rnd = random;
	}

	public ConnectedGraph(int numNodes, int numConnections, Random random) {
		rnd = random;

		setNumNodes(numNodes);
		setNumConnections(numConnections);
	}

	public ConnectedGraph(int numNodes, String filename, Random random) {
		rnd = random;

		setNumNodes(numNodes);

		try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(filename))) {
			for (int y = 0; y < this.numNodes; y++) {
				for (int x = 0; x < this.numNodes; x++) {
					connections[y][x] = inputStream.read() > 0;
				}
			}

			inputStream.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public ConnectedGraph(String filename, Random random) {
		rnd = random;

		try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
			// Number of nodes.
			setNumNodes(in.readChar());
			// Skip some data.
			in.skip(in.readChar());

			// Get the table from a bitmap of octets of bits. (upper-right
			// triangle)
			byte b = 0;
			int  n = 7;
			for (int y = 0; y < numNodes; y++) {
				for (int x = y + 1; x < numNodes; x++) {
					++n;
					if (n == 8) {
						b = in.readByte();
						n = 0;
					}
					boolean c = (b & BITS[n]) > 0;
					connections[y][x] = c;
					connections[x][y] = c;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void setNumNodes(int numNodes) {
		if (numNodes < 2) {
			throw new IllegalArgumentException("numNodes < 2: " + numNodes);
		}

		this.numNodes = numNodes;
		maxConnections = this.numNodes * (this.numNodes - 1) >> 1;

		connections = new boolean[numNodes][numNodes];
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
			Arrays.fill(connections[y], false);
		}

		i = numConnections;
		while (i > 0) {
			x = rnd.nextInt(numNodes);
			y = rnd.nextInt(numNodes);

			if (!(x == y || connections[y][x])) {
				connections[y][x] = connections[x][y] = true;
				i--;
			}
		}
	}

	public void reorder(int[] order) {
		if (order.length != numNodes) {
			throw new IllegalArgumentException();
		}

		boolean[][] out = new boolean[numNodes][numNodes];
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

	private static int compareArrayRows(boolean[] array1, boolean[] array2) {
		for (int i = 0; i < array1.length; i++) {
			if (array1[i] != array2[i]) {
				if (array1[i]) {
					return 1;
				}
				if (array2[i]) {
					return -1;
				}
			}
		}
		return 0;
	}

	private void swapArrayRows(int i1, int i2) {
		{
			boolean[] temp = connections[i1];
			connections[i1] = connections[i2];
			connections[i2] = temp;
		}
	}

	public int countConnections() {
		int num = 0;
		for (int y = 0; y < numNodes; y++) {
			for (int x = 0; x < y; x++) {
				if (connections[y][x]) {
					num++;
				}
			}
		}
		return num;
	}

	public void saveToFile(String filename) {
		try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filename))) {
			for (int y = 0; y < numNodes; y++) {
				for (int x = 0; x < numNodes; x++) {
					outputStream.write(connections[y][x] ? 1 : 0);
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static String array2DToString(boolean[][] array) {
		StringBuffer b = new StringBuffer(array.length * (array.length + 1));
		for (int y = 0; y < array.length; y++) {
			for (int x = 0; x < array.length; x++) {
				b.append(x == y ? ' ' : array[y][x] ? 'X' : '.');
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
