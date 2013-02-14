package de.janchristoph.soccer;

import de.janchristoph.soccer.model.Flag;
import de.janchristoph.soccer.model.FlagType;
import de.janchristoph.soccer.model.Side;

public class BasicAgent extends AbstractAgent {
	private boolean justTurned;
	private boolean seenBallInLastCycle = false;
	private boolean seenBallInPreLastCycle = false;
	private boolean stop;

	public BasicAgent(String teamName) {
		super(teamName);
	}

	/**
	 * Wird nach dem Start des Clients aufgerufen. Hier sollte sich der Spieler
	 * beim Server über die init-Funktion anmelden.
	 */
	@Override
	public void onStart() {
		client.init(getTeamName(), false);
		client.move(-10, (int) (Math.random() * 15));
	}

	/**
	 * Wird nach einem erfolgreichen init-Aufruf aufgerufen. Ab diesem Zeitpunkt
	 * stehen Trikotnummer und Spielfeldseite zur Verfügung.
	 */
	@Override
	public void onInit() {
		if (debugging)
			System.out.println("Ich bin ein Spieler im Team " + getTeamName() + " mit der Trikotnummer " + getuNum()
					+ " und spiele die erste Halbzeit auf der " + (Side.LEFT.equals(getSide()) ? "linken" : "rechten") + " Seite.");
	}

	/**
	 * Wird bei jedem neuen Cycle (nach dem erhalt der ERSTEN! Nachricht in
	 * einem Cycle) aufgerufen.
	 */
	@Override
	public void onCycle() {
		if (getLatestGameState() == null) {
			if (debugging)
				System.out.println("latestGameState == null");
			return;
		}

		// don't act like an idiot, if game is not started yet.
		if (getLatestGameState().getCycle() < 1)
			return;

		if (getLatestGameState().getBall() == null) {
			if (!justTurned) {
				if (debugging)
					System.out.println("Ich kann den Ball nicht sehen, ich drehe mich nach rechts, um ihn zu finden.");

				if (seenBallInLastCycle == false) {
					if (seenBallInPreLastCycle == false)
						client.turn(60);
					seenBallInPreLastCycle = false;
				}
				seenBallInLastCycle = false;

				// justTurned = true;
			} else {
				justTurned = false;
				// wait
			}
			return;
		}

		seenBallInLastCycle = true;

		if (stop)
			return;

		if (getLatestGameState().getBall().getDirection().doubleValue() > 5) {
			if (debugging)
				System.out.println("Der Ball ist rechts von mir, ich drehe mich nach rechts.");
			client.turn(getLatestGameState().getBall().getDirection().intValue());
		} else if (getLatestGameState().getBall().getDirection().doubleValue() < -5) {
			if (debugging)
				System.out.println("Der Ball ist links von mir, ich drehe mich nach links.");
			client.turn(-getLatestGameState().getBall().getDirection().intValue());
		}

		double distanceToKickable = getLatestGameState().getBall().getDistance().doubleValue() - 0.3 - 0.085 + 0.7 - 0.5;

		if (distanceToKickable > 1) {
			if (debugging)
				System.out.println("Der Ball liegt nicht direkt vor mir, ich laufe vorwärts.");

			Integer power = 100;
			if (distanceToKickable < 1) {
				power = 60;
			}
			client.dash(power);
			return;
		} else {
			if (debugging)
				System.out.println("Der Ball liegt vor mir, ich schieße mit Power = 50.");

			Integer direction = guessDirectionToEnemyGoal();
			if (direction != null) {
				client.kick(100, direction);
				client.kick(100, direction);
				client.turn(direction);
			} else {
				client.kick(10, 80);
				client.kick(10, 80);
				client.turn(80);
			}
			return;
		}
	}

	private Integer guessDirectionToEnemyGoal() {
		// check if goal is visible

		/*
		 * Goal enemyGoal = null; if (getSide().equals(Side.LEFT) &&
		 * getLatestGameState().getRightGoal() != null) enemyGoal =
		 * getLatestGameState().getRightGoal(); else if
		 * (getSide().equals(Side.RIGHT) && getLatestGameState().getLeftGoal()
		 * != null) enemyGoal = getLatestGameState().getLeftGoal();
		 * 
		 * if (enemyGoal != null) { System.out.println("############1"); return
		 * enemyGoal.getDirection().intValue(); }
		 */

		FlagType enemyBehindTheGoalFlagType;
		FlagType enemyGoalTopFlagType;
		FlagType enemyGoalBottomFlagType;

		enemyBehindTheGoalFlagType = FlagType.LEFT_0;
		enemyGoalTopFlagType = FlagType.GOAL_LEFT_TOP;
		enemyGoalBottomFlagType = FlagType.GOAL_LEFT_BOTTOM;

		if (getSide().equals(Side.LEFT)) {
			enemyBehindTheGoalFlagType = FlagType.RIGHT_0;
			enemyGoalTopFlagType = FlagType.GOAL_RIGHT_TOP;
			enemyGoalBottomFlagType = FlagType.GOAL_RIGHT_BOTTOM;
		}

		Flag enemyGoalTop = getLatestGameState().getFlagByType(enemyGoalTopFlagType);
		if (enemyGoalTop != null) {
			System.out.println("############4");
			return enemyGoalTop.getDirection().intValue() + (getSide().equals(Side.LEFT) ? 6 : -6);
		}

		Flag enemyGoalBottom = getLatestGameState().getFlagByType(enemyGoalBottomFlagType);
		if (enemyGoalBottom != null) {
			System.out.println("############4");
			return enemyGoalBottom.getDirection().intValue() + (getSide().equals(Side.LEFT) ? -6 : 6);
		}

		Flag enemyBehinTheGoalFlag = getLatestGameState().getFlagByType(enemyBehindTheGoalFlagType);
		if (enemyBehinTheGoalFlag != null) {
			System.out.println("############3");
			return enemyBehinTheGoalFlag.getDirection().intValue();
		}

		System.out.println("############9");

		// no idea -.- just kick *trollface*
		return null;
	}
}
