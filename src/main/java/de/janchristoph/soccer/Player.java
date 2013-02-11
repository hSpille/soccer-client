package de.janchristoph.soccer;

import java.util.ArrayList;
import java.util.List;

import de.janchristoph.soccer.connection.Client;
import de.janchristoph.soccer.connection.DataReceiver;
import de.janchristoph.soccer.model.Ball;
import de.janchristoph.soccer.model.GameState;
import de.janchristoph.soccer.model.PlayMode;
import de.janchristoph.soccer.model.Side;
import de.janchristoph.soccer.protocolparser.InitParser;
import de.janchristoph.soccer.protocolparser.SeeParser;

public abstract class Player implements DataReceiver {
	protected Client client;
	private List<GameState> gameStates = new ArrayList<GameState>();
	private Integer lastCycle = -1;
	private final String teamName;
	private Side side;
	private Integer uNum = 0;
	private PlayMode playMode = PlayMode.NULL;

	public Player(String teamName) {
		this.teamName = teamName;
	}

	public void start() {
		client = new Client();
		client.setNewDataRunnable(this);
		client.startListening();
		onStart();
	}

	public void onNewData(String line) {
		Integer cycle = -1;
		if (line.startsWith(SeeParser.PROTOCOL_SEE_START)) {
			SeeParser parser = new SeeParser(line);
			cycle = parser.parseCycleNumber();

			GameState gameState = getOrCreateGameStateWith(cycle);

			Ball ball = parser.parseBall();
			gameState.setBall(ball);
		} else if (line.startsWith("(init")) {
			InitParser parser = new InitParser(line);
			side = parser.parseSide();
			uNum = parser.parseUNum();
			onInit();
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

	public abstract void onStart();

	public abstract void onInit();

	public abstract void onCycle();

	/**
	 * Gibt die Trikotnummer des Spielers zurück, falls die init-Message (beim
	 * Verbinden) ausgelesen werden konnte.
	 */
	public Integer getuNum() {
		return uNum;
	}

	/**
	 * Gibt eine Liste mit allen empfangenen GameStates zurück.
	 */
	public List<GameState> getGameStates() {
		return gameStates;
	}

	/**
	 * Gibt die Cycle-Nummer der letzten Nachricht/des letzten Gamestates
	 * zurück.
	 */
	public Integer getLastCycle() {
		return lastCycle;
	}

	/**
	 * Gibt den zu Beginn vergebenen Teamnamen zurück.
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * Gibt die Spielfeldseite des Teams zurück.
	 */
	public Side getSide() {
		return side;
	}

	/**
	 * Gibt den aktuellen Spielmodus zurück. (Siehe {@link PlayMode} für
	 * mögliche Werte.)
	 */
	public PlayMode getPlayMode() {
		return playMode;
	}
}
