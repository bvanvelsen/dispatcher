package be.dispatcher.domain.vehicle.police;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.people.Criminal;
import be.dispatcher.domain.vehicle.TrafficDutyVehicle;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.VehicleType;
import be.dispatcher.managers.incidentscene.IncidentSceneMedicalTasksManager;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public class PoliceVehicle extends Vehicle implements TrafficDutyVehicle {

	private final int arrestGainPerTick;

	@Autowired
	protected IncidentSceneMedicalTasksManager incidentSceneMedicalTasksManager;

	public PoliceVehicle(int id, String name, Base base, int arrestGainPerTick, VehicleType vehicleType, String vehicleImagePath) {
		super(id, name, base, vehicleImagePath);
		this.arrestGainPerTick = arrestGainPerTick;
		this.vehicleType = vehicleType;
	}

	@Override
	protected void handleIncident() {
			if (getIncident().isTrafficDutyStillRequired()) {
				performTrafficDutyOrGoBackToBase();
			} else {
				arrestCriminalOrGoBackToBase();
			}
	}

	private void performTrafficDutyOrGoBackToBase() {
		if (getIncident().isTrafficDutyStillRequired()) {
			getIncident().performTrafficDuty(this);
		} else {
			vehicleManager.sendVehicleToBase(this);
		}
	}

	private void arrestCriminalOrGoBackToBase() {
		Criminal criminal = incidentSceneMedicalTasksManager.getCriminalFor(this);
		if (criminal != null) {
			if (criminal.arrest(arrestGainPerTick)) {
				if (criminal.isTransportable()) {
					vehicleManager.sendVehicleToNearestPoliceStation(this);
				}
			}
		} else {
			vehicleManager.sendVehicleToBase(this);
		}
	}

	public int getArrestGainPerTick() {
		return arrestGainPerTick;
	}
}
