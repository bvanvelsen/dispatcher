package be.dispatcher.domain.vehicle.fire;

import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.vehicle.TrafficDutyVehicle;
import be.dispatcher.domain.vehicle.VehicleType;

public class FireTruckTrafficDutyVehicle extends FireTruck implements TrafficDutyVehicle {

	public FireTruckTrafficDutyVehicle(int id, String name, Base base, VehicleType vehicleType, int extinguishRatePerTick,
			int technicalWorkPerTick,
			String vehicleImagePath) {
		super(id, name, base, vehicleType, extinguishRatePerTick, technicalWorkPerTick, vehicleImagePath);
	}


	@Override
	protected void handleIncident() {
		if (getIncident().isTrafficDutyStillRequired()) {
			performTrafficDutyOrGoBackToBase();
		}
	}

	private void performTrafficDutyOrGoBackToBase() {
		if (getIncident().isTrafficDutyStillRequired()) {
			getIncident().performTrafficDuty(this);
		} else {
			vehicleManager.sendVehicleToBase(this);
		}
	}
}
