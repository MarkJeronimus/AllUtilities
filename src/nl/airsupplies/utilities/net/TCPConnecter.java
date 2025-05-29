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

package nl.airsupplies.utilities.net;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Mark Jeronimus
 */
// Created 2005-11-03
public class TCPConnecter {
	private Socket socket = null;

	public Connection connect(String host, int port) {
		try {
			socket = new Socket(host, port);

			return new Connection(socket);
		} catch (IOException ignored) {
			return null;
		}
	}
}
