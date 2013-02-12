package de.janchristoph.soccer.model;

import java.util.List;

import de.janchristoph.soccer.protocolparser.SeeParser;

public class GameState {
	private Integer cycle;
	private List<Flag> flags;
	private Ball ball;
	private Goal rightGoal;
	private Goal leftGoal;
	private SeeParser parser;

	public GameState() {

	}

	public GameState(String serverData) {
		parser = new SeeParser(serverData);
		this.ball = parser.parseBall();
		this.cycle = parser.parseCycleNumber();
		List<Goal> goals = parser.parseGoals();
		for (Goal goal : goals) {
			if (GoalType.LEFT.equals(goal.getType())) {
				leftGoal = goal;
			}
			if (GoalType.RIGHT.equals(goal.getType())) {
				rightGoal = goal;
			}
		}
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

	public Goal getRightGoal() {
		return rightGoal;
	}

	public void setRightGoal(Goal rightGoal) {
		this.rightGoal = rightGoal;
	}

	public Goal getLeftGoal() {
		return leftGoal;
	}

	public void setLeftGoal(Goal leftGoal) {
		this.leftGoal = leftGoal;
	}
}
