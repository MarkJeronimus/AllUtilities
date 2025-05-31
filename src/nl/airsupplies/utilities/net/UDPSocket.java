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
