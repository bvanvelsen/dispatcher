package be.dispatcher.domain.vehicle;

public enum VehicleType {

	AMBULANCE(200);

	private final Integer speed;

	VehicleType(Integer speed) {
		this.speed = speed;
	}

	public Integer getSpeed() {
		return speed;
	}
}
