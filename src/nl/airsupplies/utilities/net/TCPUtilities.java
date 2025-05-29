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

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

import nl.airsupplies.utilities.annotation.UtilityClass;
import static nl.airsupplies.utilities.validator.StringValidatorUtilities.requireStringNotEmpty;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireRange;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireThat;

/**
 * @author Mark Jeronimus
 */
// Created 2017-01-17
@UtilityClass
public final class TCPUtilities {
	public static String getHostName(Socket socket) {
		return socket.getInetAddress().getHostName();
	}

	public static String getIP(Socket socket) {
		byte[] ip = socket.getInetAddress().getAddress();
		return ip[0] + "." + ip[1] + '.' + ip[2] + '.' + ip[3];
	}

	/**
	 * Parses a string in the form of {@code "host"} or {@code "host:port"}, where 'host' can be a hostname or IP
	 * address.
	 */
	public static InetSocketAddress parseInetSocketAddress(String hostName, int defaultPort) {
		requireStringNotEmpty(hostName, "hostName");
		requireRange(0, 65535, defaultPort, "defaultPort");

		try {
			// WORKAROUND: add any scheme to make the resulting URI valid.
			URI    uri  = new URI("my://" + hostName);
			String host = uri.getHost();
			int    port = uri.getPort();

			requireThat(host != null && !host.isEmpty(), () -> "No hostname given");

			if (port == -1) {
				port = defaultPort;
			}

			return new InetSocketAddress(host, port);
		} catch (URISyntaxException ex) {
			IllegalArgumentException ex2 = new IllegalArgumentException(ex.getMessage());
			ex2.setStackTrace(ex.getStackTrace());
			throw ex2;
		}
	}
}
