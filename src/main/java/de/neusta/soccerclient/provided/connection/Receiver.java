package de.neusta.soccerclient.provided.connection;

import de.janchristoph.soccer.connection.ConnectionManager;
import de.neusta.soccerclient.provided.Client;

public class Receiver implements Runnable {
	private ConnectionManager cm; 
	private Client client;

	public Receiver(ConnectionManager cm, Client client) {
		this.cm = cm;
		this.client = client;
	}

	@Override
	public void run() {
		while (true) {
			String line = cm.recv();
			client.updateGameStates(line);
			if(line.isEmpty()){
				System.out.println("No Data!");
			}
		}
	}
}
