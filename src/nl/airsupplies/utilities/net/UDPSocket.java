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
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

import nl.airsupplies.utilities.StringUtilities;

/**
 * @author Mark Jeronimus
 */
// Created 2005-11-03
public class UDPSocket extends DatagramSocket {
	private byte[] receiveData = new byte[1025];

	private final InetAddress address;
	private final int         port;

	private int mtu = 1024;

	public UDPSocket(InetAddress address, int port, int localPort) throws SocketException {
		super(localPort);

		this.address = address;
		this.port    = port;

		setSoTimeout(30000);
	}

	public void setMTU(int mtu) {
		this.mtu = mtu;

		if (receiveData.length < mtu + 1) {
			receiveData = new byte[mtu + 1];
		}
	}

	public void send(String s) throws IOException {
		byte[] sendData = s.getBytes(StandardCharsets.UTF_8);

		int length = sendData.length;
		if (length > mtu) {
			length = mtu;
		}

		DatagramPacket packet = new DatagramPacket(sendData, sendData.length, address, port);
		send(packet);
	}

	public String receive() throws IOException {
		DatagramPacket packet = new DatagramPacket(receiveData, mtu);

		receive(packet);

		return new String(receiveData, 0, packet.getLength(), StandardCharsets.UTF_8);
	}

	public String receiveCString() throws IOException {
		DatagramPacket packet = new DatagramPacket(receiveData, mtu);

		receive(packet);

		receiveData[packet.getLength()] = 0;
		return StringUtilities.fromCString(receiveData);
	}
}
