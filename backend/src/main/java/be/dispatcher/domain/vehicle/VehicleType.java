package be.dispatcher.domain.vehicle;

public enum VehicleType {

	AMBULANCE(0.4, 0),
	MUG(0.4, 0),
	TS(0.4, 0),
	RV(0.4, 0),
	COMBI(0.4, 0),
	INTERCEPTOR(0.4, 0);

	private final double speedProfilePrioriy;
	private final double speedProfileSecundary;

	VehicleType(double speedProfilePrioriy, double speedProfileSecundary) {
		this.speedProfilePrioriy = speedProfilePrioriy;
		this.speedProfileSecundary = speedProfileSecundary;
	}

	public double getSpeedProfilePrioriy() {
		return speedProfilePrioriy;
	}

	public double getSpeedProfileSecundary() {
		return speedProfileSecundary;
	}
}
