package de.janchristoph.soccer.model;

import de.janchristoph.soccer.protocolparser.SenseBodyParser;

public class BodyState {
	
	public BodyState(String line){
		SenseBodyParser parser = new SenseBodyParser();
		parser.parse(line,this);
	}

	private Integer stamina;

	public Integer getStamina() {
		return stamina;
	}

	public void setStamina(Integer stamina) {
		this.stamina = stamina;
	}
	
}
