package de.janchristoph.soccer;

import java.util.ArrayList;
import java.util.List;

import de.janchristoph.soccer.connection.Client;
import de.janchristoph.soccer.connection.NewDataRunnable;
import de.janchristoph.soccer.model.Ball;
import de.janchristoph.soccer.model.GameState;
import de.janchristoph.soccer.protocolparser.SeeParser;

public abstract class Player implements NewDataRunnable {
	private static final String DEFAULT_TEAM_NAME = "Test-Team";
	private static final String PROTOCOL_SEE_START = "(see";

	protected Client client;
	private List<GameState> gameStates = new ArrayList<GameState>();
	private Integer lastCycle = -1;

	public Player() {
	}
	
	public void start() {
		client = new Client();
		client.setNewDataRunnable(this);
		client.startListening();
		onStart();
	}

	public void onStart() {
		client.init(DEFAULT_TEAM_NAME);
		client.move(-10, 0);
	}

	public void onNewData(String line) {
		Integer cycle = -1;
		if (line.startsWith(PROTOCOL_SEE_START)) {
			SeeParser parser = new SeeParser(line);
			cycle = parser.parseCycleNumber();
			
			GameState gameState = getOrCreateGameStateWith(cycle);
			
			Ball ball = parser.parseBall();
			gameState.setBall(ball);
		} else if (line.startsWith("(body_sense")) {
			
		}
		if (lastCycle < cycle) {
			onCycle();
			lastCycle = cycle;
		}
	}

	private GameState getOrCreateGameStateWith(Integer cycle) {
		for (GameState gameState : gameStates) {
			if (gameState.getCycle().equals(cycle)) {
				return gameState;
			}
		}
		GameState gameState = new GameState();
		gameState.setCycle(cycle);
		gameStates.add(gameState);
		return gameState;
	}

	protected GameState getLatestGameState() {
		if (gameStates.size() == 0)
			return null;
		return gameStates.get(gameStates.size() - 1);
	}

	public abstract void onCycle();
}
