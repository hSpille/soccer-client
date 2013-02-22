package de.neusta.soccerclient.provided;

import java.util.ArrayList;

import de.janchristoph.soccer.connection.ConnectionManager;
import de.janchristoph.soccer.model.Ball;
import de.janchristoph.soccer.model.BodyState;
import de.janchristoph.soccer.model.GameState;
import de.janchristoph.soccer.model.Goal;
import de.janchristoph.soccer.protocolparser.SeeParser;
import de.janchristoph.soccer.protocolparser.SenseBodyParser;
import de.janchristoph.soccer.protocolparser.StateParser;
import de.neusta.soccerclient.provided.connection.Receiver;

public class Client {

	public static enum GAMESTATE {
		BEFORE_KICKOFF, PLAY_ON, KICK_OFF_L, KICK_OFF_R, ELSE
	}

	private final String team;
	private final String serverIp;
	private ConnectionManager conMan;
	private ArrayList<GameState> gamestates = new ArrayList<GameState>();
	private BodyState bodyState = null;
	private GameState latestGameState = null;
	public GAMESTATE currentGameState = GAMESTATE.BEFORE_KICKOFF;
	private IAgent agent;
	private final int initialPositionX;
	private final int initialPositionY;

	public Client(String team, String server, IAgent agent, int initialPositionX, int initialPositionY) {
		this.agent = agent;
		this.team = team;
		this.serverIp = server;
		this.initialPositionX = initialPositionX;
		this.initialPositionY = initialPositionY;
		conMan = new ConnectionManager(serverIp);
	}

	public void start() {
		this.init(team);
		this.move(initialPositionX, initialPositionY);
		Thread t = new Thread(new Receiver(conMan, this));
		t.start();
	}

	public void init(String teamname) {
		conMan.send("(init " + teamname + ")");
	}

	public void reconnect(String teamname, Integer unum) {
		conMan.send("(reconnect " + teamname + " " + unum + ")");
	}

	public void bye() {
		conMan.send("(bye)");
	}

	public void catch_(Integer direction) {
		conMan.send("(catch " + direction + ")");
	}

	public void changeView(Integer width, Integer quality) {
		conMan.send("(change_view " + width + " " + quality + ")");
	}

	public void dash(Integer power) {
		conMan.send("(dash " + power + ")");
	}

	public void kick(Integer power, Integer direction) {
		conMan.send("(kick " + power + " " + direction + ")");
	}

	public void move(Integer x, Integer y) {
		conMan.send("(move " + x + " " + y + ")");
	}

	public void say(String message) {
		conMan.send("(say " + message + ")");
	}

	public void senseBody() {
		conMan.send("(sense_body)");
	}

	public void score() {
		conMan.send("(score)");
	}

	public void turn(Integer moment) {
		conMan.send("(turn " + moment + ")");
	}

	public void turnNeck(Integer angle) {
		conMan.send("(turn_neck " + angle + ")");
	}

	public synchronized void addState(GameState state) {
		this.gamestates.add(state);
	}

	public synchronized GameState getState(int state) {
		return this.gamestates.get(state);
	}

	public synchronized GameState getLatestGameState() {
		return latestGameState;
	}

	public synchronized void setLatestGameState(GameState latestGameState) {
		this.latestGameState = latestGameState;
	}

	public synchronized void updateGameStates(String line) {
		if (StateParser.isStateMessage(line)) {
			this.currentGameState = StateParser.getState(line);
			System.out.println("GameState now " + this.currentGameState.toString());
			agent.doMove();
		} 
		if (SeeParser.isSeeMessage(line)) {
			GameState state = new GameState(line);
			this.addState(state);
			this.setLatestGameState(state);
			agent.doMove();
		} 
		if (SenseBodyParser.isSenseMessage(line)) {
			this.bodyState = new BodyState(line);
			agent.doSenseBody();
		} 
		
	}

	public boolean canKick() {
		Ball ball = latestGameState.getBall();
		if (ball != null) {
			Double distance = ball.getDistance();
			return distance < 1;
		}
		return false;
	}

	public Goal getOpponentGoal() {
		return null;
	}

	public BodyState getBodyState() {
		return bodyState;
	}

	public void setBodyState(BodyState bodyState) {
		this.bodyState = bodyState;
	}

}
