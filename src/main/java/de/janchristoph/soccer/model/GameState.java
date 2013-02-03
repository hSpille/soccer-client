package de.janchristoph.soccer.model;

import java.util.List;

import de.janchristoph.soccer.protocolparser.SeeParser;

public class GameState {
	private Integer cycle;
	private List<Flag> flags;
	private Ball ball;
	private SeeParser parser;
	
	
	public GameState(){
		
	}
	
	public GameState (String serverData){
		parser = new SeeParser(serverData);
		this.ball = parser.parseBall();
		this.cycle = parser.parseCycleNumber();
	}

	public List<Flag> getFlags() {
		return flags;
	}

	public void setFlags(List<Flag> flags) {
		this.flags = flags;
	}

	public Ball getBall() {
		return ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
	}

	public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}
}
