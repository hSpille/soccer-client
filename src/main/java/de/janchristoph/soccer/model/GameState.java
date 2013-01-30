package de.janchristoph.soccer.model;

import java.util.List;

public class GameState {
	private Integer cycle;
	private List<Flag> flags;
	private Ball ball;

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
