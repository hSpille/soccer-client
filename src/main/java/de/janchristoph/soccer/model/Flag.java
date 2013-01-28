package de.janchristoph.soccer.model;

public class Flag extends StationaryObject {
	private FlagType type;

	public FlagType getType() {
		return type;
	}

	public void setType(FlagType type) {
		this.type = type;
	}
}
