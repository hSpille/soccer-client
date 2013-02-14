package de.janchristoph.soccer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.janchristoph.soccer.connection.Client;
import de.janchristoph.soccer.connection.DataReceiver;
import de.janchristoph.soccer.model.Ball;
import de.janchristoph.soccer.model.GameState;
import de.janchristoph.soccer.model.Goal;
import de.janchristoph.soccer.model.GoalType;
import de.janchristoph.soccer.model.PlayMode;
import de.janchristoph.soccer.model.Side;
import de.janchristoph.soccer.protocolparser.InitParser;
import de.janchristoph.soccer.protocolparser.SeeParser;

public abstract class AbstractAgent implements DataReceiver {
	protected Client client;
	private List<GameState> gameStates = new ArrayList<GameState>();
	private Integer lastCycle = -1;
	private final String teamName;
	private Side side;
	private Integer uNum = 0;
	private PlayMode playMode = PlayMode.NULL;
	private Timer timer = new Timer(true);
	protected boolean debugging;

	public AbstractAgent(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * Initialisiert den Client und ruft danach die onStart-Methode auf.
	 */
	public void start() {
		client = new Client();
		client.setDataReceiver(this);
		client.startListening();
		onStart();
	}

	public void onNewData(String line) {
		// if (debugging)
		// System.out.println(line);

		GameState gameState = null;
		boolean newGameState = false;
		if (line.startsWith(SeeParser.PROTOCOL_SEE_START)) {
			SeeParser parser = new SeeParser(line);
			Integer cycle = parser.parseCycleNumber();

			gameState = findGameStateForCycle(cycle);
			if (gameState == null) {
				newGameState = true;
				gameState = createNewGameState(cycle);
			}

			Ball ball = parser.parseBall();
			gameState.setBall(ball);

			gameState.setFlags(parser.parseAllFlags());
			for (Goal g : parser.parseGoals()) {
				if (g.getType().equals(GoalType.LEFT))
					gameState.setLeftGoal(g);
				else if (g.getType().equals(GoalType.RIGHT))
					gameState.setRightGoal(g);
			}
			
			gameState.setLines(parser.parseLines());

			gameState.setGotSeeParserData(true);
		} else if (line.startsWith("(init")) {
			InitParser parser = new InitParser(line);
			side = parser.parseSide();
			uNum = parser.parseUNum();
			onInit();
		} else if (line.startsWith("(body_sense")) {
		}

		if (newGameState) {
			// This is a new Cycle, so set a Timer to 50ms
			// (let's wait for other messages in this cycle)
			timer.schedule(new TimerTask() {
				public void run() {
					// run agent's onCycle
					onPreCycle();
				}
			}, 10);
			if (debugging)
				System.out.println("timer gesetzt");
		}
	}

	private void onPreCycle() {
		GameState latestGameState = getLatestGameState();
		GameState preLatestGameState = gameStates.get(gameStates.size() - 1);
		
		if (!latestGameState.isGotSeeParserData()) {
			latestGameState.setBall(preLatestGameState.getBall());
			latestGameState.setFlags(preLatestGameState.getFlags());
			latestGameState.setLeftGoal(preLatestGameState.getLeftGoal());
			latestGameState.setRightGoal(preLatestGameState.getRightGoal());
			latestGameState.setLines(preLatestGameState.getLines());
		}
		
		onCycle();
	}

	private GameState createNewGameState(Integer cycle) {
		GameState gameState = new GameState();
		gameState.setCycle(cycle);
		gameStates.add(gameState);
		return gameState;
	}

	private GameState findGameStateForCycle(Integer cycle) {
		GameState gameState = null;
		for (GameState gs : gameStates) {
			if (gs.getCycle().equals(cycle)) {
				gameState = gs;
			}
		}
		return gameState;
	}

	/**
	 * Gibt das letzte GameState-Objekt zurück.
	 */
	protected GameState getLatestGameState() {
		if (gameStates.size() == 0) {
			return null;
		}
		return gameStates.get(gameStates.size() - 1);
	}

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

	/**
	 * Wird nach dem Start des Clients aufgerufen. Hier sollte sich der Spieler
	 * beim Server über die init-Funktion anmelden.
	 */
	public abstract void onStart();

	/**
	 * Wird nach einem erfolgreichen init-Aufruf aufgerufen. Ab diesem Zeitpunkt
	 * stehen Trikotnummer und Spielfeldseite zur Verfügung.
	 */
	public abstract void onInit();

	/**
	 * Wird bei jedem neuen Cycle (nach dem erhalt der ERSTEN! Nachricht in
	 * einem Cycle) aufgerufen.
	 */
	public abstract void onCycle();

	public void enableDebugging() {
		debugging = true;
	}
}
