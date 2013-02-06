package de.janchristoph.soccer;

public class MyPlayer extends Player {

	public MyPlayer(String teamName) {
		super(teamName);
	}

	@Override
	public void onCycle() {
		if (getLatestGameState() == null) {
			System.out.println("latestGameState == null");
			return;
		}

		if (getLatestGameState().getBall() == null) {
			System.out.println("Ich kann den getCurrentGameState().getBall() nicht sehen, ich drehe mich nach rechts, um ihn zu finden.");
			client.turn(45);
			return;
		}

		if (getLatestGameState().getBall().getDirection().doubleValue() > 5) {
			System.out.println("Der getCurrentGameState().getBall() ist rechts von mir, ich drehe mich nach rechts.");
			client.turn(getLatestGameState().getBall().getDirection().intValue());
		} else if (getLatestGameState().getBall().getDirection().doubleValue() < -5) {
			System.out.println("Der getCurrentGameState().getBall() ist links von mir, ich drehe mich nach links.");
			client.turn(-getLatestGameState().getBall().getDirection().intValue());
		}

		if (getLatestGameState().getBall().getDistance().doubleValue() > 1) {
			System.out.println("Der getCurrentGameState().getBall() liegt nicht direkt vor mir, ich laufe vorwärts.");
			client.dash(100);
		} else {
			System.out.println("Der getCurrentGameState().getBall() liegt vor mir, ich schieße mit Power = 50.");
			client.kick(50, 0);
		}
	}
}
