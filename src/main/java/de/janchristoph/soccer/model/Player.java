package de.janchristoph.soccer.model;

public class Player extends MobileObject {
	private Integer team;
	private Integer side;
	private Integer uniform_number;
	private Double bodyDirection;
	private Double faceDirection;
	private Double neckDirection;

	public Integer getTeam() {
		return team;
	}

	public void setTeam(Integer team) {
		this.team = team;
	}

	public Integer getSide() {
		return side;
	}

	public void setSide(Integer side) {
		this.side = side;
	}

	public Integer getUniform_number() {
		return uniform_number;
	}

	public void setUniform_number(Integer uniform_number) {
		this.uniform_number = uniform_number;
	}

	public Double getBodyDirection() {
		return bodyDirection;
	}

	public void setBodyDirection(Double bodyDirection) {
		this.bodyDirection = bodyDirection;
	}

	public Double getFaceDirection() {
		return faceDirection;
	}

	public void setFaceDirection(Double faceDirection) {
		this.faceDirection = faceDirection;
	}

	public Double getNeckDirection() {
		return neckDirection;
	}

	public void setNeckDirection(Double neckDirection) {
		this.neckDirection = neckDirection;
	}
}
