package de.neusta.soccerclient;

import de.neusta.soccerclient.Client.GAMESTATE;

public class Agent {
	public final Client client;
	
	public Agent(String server, String team){
		client = new Client(team,server, this);
		client.start();
	}
	
	
	public void doMove(){
			if(GAMESTATE.BEFORE_KICKOFF.equals(client.currentGameState)){
				System.out.println("waiting for start (strg+k on monitor)");
			}
			if(GAMESTATE.PLAY_ON.equals(client.currentGameState)){
				if(client.canKick()){
					client.kick(100, 0);
				}
				if(!client.goToBall()){
					client.searchBall();
				}
			}
	}


	public boolean isConnected() {
		return true;
	}
}
