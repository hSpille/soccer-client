package de.neusta.soccerclient;

import de.neusta.soccerclient.provided.Client;
import de.neusta.soccerclient.provided.IAgent;

public class Agent implements IAgent {
	public final Client client;
	
	public Agent(String server, String team){
		client = new Client(team,server, this,-10,15);
		client.start();
	}
	
	
	@Override
	public void doMove(){
			System.out.println("your intelligence goes here...");
	}
	
	@Override
	public boolean isConnected() {
		return true;
	}
}
