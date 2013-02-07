package de.neusta.soccerclient;

import de.janchristoph.soccer.model.Ball;
import de.neusta.soccerclient.provided.Client;
import de.neusta.soccerclient.provided.Client.GAMESTATE;

public class Agent {
	public final Client client;
	
	public Agent(String server, String team){
		client = new Client(team,server, this,-10,15);
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
				if(!this.goToBall()){
					this.searchBall();
				}
			}
	}
	
	private boolean goToBall() {
		boolean toReturn = false;
		Ball ball = client.getLatestGameState().getBall();
		if (ball != null) {
			if (ball.getDirection().doubleValue() > 5) {
				System.out.println("Turning");
				client.turn(client.getLatestGameState().getBall().getDirection()
						.intValue());
				toReturn = true;
			} else if (ball.getDirection().doubleValue() < -5) {
				System.out.println("Turning");
				client.turn(-client.getLatestGameState().getBall().getDirection()
						.intValue());
				toReturn = true;
			} else if (ball.getDistance() > Double.valueOf(1)) {
				System.out.println("walking");
				client.dash(100);
				toReturn = true;
			}
		}
		return toReturn;
	}
	
	
	private void searchBall() {
		System.out.println("Searching Ball");
		client.turn(25);
	}


	public boolean isConnected() {
		return true;
	}
}
