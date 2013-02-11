package de.janchristoph.soccer;

import de.janchristoph.soccer.model.Side;

public class BasicPlayer extends Player {
	public BasicPlayer(String teamName) {
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

	@Override
	public void onInit() {
		System.out.println("Ich bin ein Spieler im Team " + getTeamName() + " mit der Trikotnummer " + getuNum() + " und spiele die erste Halbzeit auf der "
				+ (Side.LEFT.equals(getSide()) ? "linken" : "rechten") + " Seite.");
	}

	@Override
	public void onStart() {
		client.init(getTeamName(), false);
		client.move(-10, (int) (Math.random() * 15));
	}
}
