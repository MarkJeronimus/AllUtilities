package nl.airsupplies.utilities.brokenorold;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

import org.jetbrains.annotations.Nullable;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2005-11-03
public class RPCConnection implements Runnable {
	private static final int DEFAULT_TIMEOUT = 10000;

	private Socket socket;

	private DataOutputStream dataOutputStream;
	private DataInputStream  dataInputStream;

	public  boolean             busy = false;
	private RPCRequestProcessor netRequestProcessor;

	public RPCConnection(String host, int port) throws IOException {
		this(new Socket(host, port));
	}

	public RPCConnection(Socket socket) throws SocketException {
		this.socket = requireNonNull(socket, "socket");

		this.socket.setSoTimeout(DEFAULT_TIMEOUT);

		try {
			dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
			dataInputStream  = new DataInputStream(this.socket.getInputStream());
		} catch (IOException ignored) {
		}
	}

	public String sendReceive(String message) throws IOException {
		dataOutputStream.write(message.getBytes(StandardCharsets.UTF_8));

		StringBuilder b = new StringBuilder(dataInputStream.available());
		int           i;
		while ((i = dataInputStream.read()) >= 0) {
			b.append((char)i);
		}

		return b.toString();
	}

	public @Nullable NetRequest sendReceive(NetRequest message) {
		try {
			busy = true;
			dataOutputStream.writeUTF(message.toString());
			message = new NetRequest(dataInputStream.readUTF());

			return message;
		} catch (IOException ex) {
			ex.printStackTrace();
			disconnectRequest();
			return null;
		} finally {
			busy = false;
		}
	}

	public void disconnectRequest() {
		if (busy) {
			busy = false;
		}

		try {
			dataOutputStream.writeUTF(NetRequest.DisconnectRequest.toString());
		} catch (IOException ignored) {
			close();
		}
	}

	public void close() {
		if (busy) {
			busy = false;
		}

		try {
			socket.close();
		} catch (IOException ignored) {
		}
	}

	/**
	 * Start listening for requests
	 */
	public void startResponding(RPCRequestProcessor netRequestProcessor) {
		this.netRequestProcessor = netRequestProcessor;
		new Thread(this, "Connection at " + getHostName()).start();
	}

	/**
	 * Listen for requests
	 */
	@Override
	public void run() {
		try {
			busy = true;
			while (true) {
				String requestString = dataInputStream.readUTF();

				NetRequest request = new NetRequest(requestString);

				if (!busy) {
					dataOutputStream.writeUTF(NetRequest.DisconnectRequest.toString());
					break;
				}

				NetRequest result = netRequestProcessor.execute(request);

				dataOutputStream.writeUTF(result.toString());
			}
		} catch (IOException ignored) {
			close();
		}
	}

	public String getHostName() {
		return ((InetSocketAddress)socket.getRemoteSocketAddress()).getAddress().getHostName();
	}

	public String getIP() {
		byte[] ip = ((InetSocketAddress)socket.getRemoteSocketAddress()).getAddress().getAddress();
		return String.valueOf(ip[0]) + '.' + ip[1] + '.' + ip[2] + '.' + ip[3];
	}

	public Vector<Object> toVector() {
		Vector<Object> out = new Vector<>();
		out.add(getIP());
		out.add(String.valueOf(socket.getLocalPort()));
		out.add(getHostName());
		return out;
	}
}
