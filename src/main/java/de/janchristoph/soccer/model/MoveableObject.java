package de.janchristoph.soccer.model;

public class MoveableObject extends GameObject {
	private Double distanceChange;
	private Double directionChange;

	public Double getDistanceChange() {
		return distanceChange;
	}

	public void setDistanceChange(Double distanceChange) {
		this.distanceChange = distanceChange;
	}

	public Double getDirectionChange() {
		return directionChange;
	}

	public void setDirectionChange(Double directionChange) {
		this.directionChange = directionChange;
	}
}
