package nl.airsupplies.utilities.net;

import java.util.Vector;

/**
 * @author Mark Jeronimus
 */
// Created 2005-10-30
public class Connections {
	private final Vector<Connection> connections = new Vector<>();

	public int size() {
		return connections.size();
	}

	public void addConnection(Connection connection) {
		connections.add(connection);
	}

	public Connection get(int i) {
		return connections.get(i);
	}

	public void removeConnection(Connection connection) {
		connections.remove(connection);
	}
}
