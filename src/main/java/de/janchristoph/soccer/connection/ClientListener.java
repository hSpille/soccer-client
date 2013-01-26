package de.janchristoph.soccer.connection;

public class ClientListener implements Runnable {
	private ConnectionManager cm;
	private Client client;

	public ClientListener(ConnectionManager cm, Client client) {
		this.cm = cm;
		this.client = client;
	}

	@Override
	public void run() {
		while (true) {
			String line = cm.recv();
			client.processNewData(line);
		}
	}
}
