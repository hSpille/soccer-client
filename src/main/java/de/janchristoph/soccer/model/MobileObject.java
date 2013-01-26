package de.janchristoph.soccer.model;

public class MobileObject extends FieldObject {
	private Double directionChange;
	private Double distanceChange;

	public Double getDirectionChange() {
		return directionChange;
	}

	public void setDirectionChange(Double directionChange) {
		this.directionChange = directionChange;
	}

	public Double getDistanceChange() {
		return distanceChange;
	}

	public void setDistanceChange(Double distanceChange) {
		this.distanceChange = distanceChange;
	}
}
