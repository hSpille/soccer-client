package de.janchristoph.soccer.model;

public class Goal extends StationaryObject {
	private GoalType type;

	public GoalType getType() {
		return type;
	}

	public void setType(GoalType type) {
		this.type = type;
	}
}
