package be.dispatcher.domain.vehicle;

public enum VehicleType {

	AMBULANCE("2", "car"),
	MUG("3", "car"),
	TS("1", "car"),
	RV("1", "car"),
	COMBI("2","car"),
	INTERCEPTOR("3", "car");

	private final String speedProfilePrioriy;
	private final String speedProfileSecundary;

	VehicleType(String speedProfilePrioriy, String speedProfileSecundary) {
		this.speedProfilePrioriy = speedProfilePrioriy;
		this.speedProfileSecundary = speedProfileSecundary;
	}

	public String getSpeedProfilePrioriy() {
		return speedProfilePrioriy;
	}

	public String getSpeedProfileSecundary() {
		return speedProfileSecundary;
	}
}
