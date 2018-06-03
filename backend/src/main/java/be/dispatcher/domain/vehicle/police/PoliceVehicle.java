package be.dispatcher.domain.vehicle.police;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.people.Criminal;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.VehicleType;
import be.dispatcher.managers.incidentscene.IncidentSceneMedicalTasksManager;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public class PoliceVehicle extends Vehicle {

	private final int arrestGainPerTick;
	private boolean performTrafficDuty;

	@Autowired
	protected IncidentSceneMedicalTasksManager incidentSceneMedicalTasksManager;

	public PoliceVehicle(int id, String name, Base base, int arrestGainPerTick, VehicleType vehicleType, String vehicleImagePath) {
		super(id, name, base, vehicleImagePath);
		this.arrestGainPerTick = arrestGainPerTick;
		this.vehicleType = vehicleType;
	}

	@Override
	public void tick() {
		super.tick();
		switch (vehicleStatus) {
		case AT_INCIDENT:
			if (performTrafficDuty) {
				performTrafficDutyOrGoBackToBase();
			} else {
				arrestCriminalOrGoBackToBase();
			}
			break;
		}
	}

	private void performTrafficDutyOrGoBackToBase() {
		if (incident.getPoliceTasks().isTrafficDutyStillRequired()) {
			incident.getPoliceTasks().performTrafficDuty(this);
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
		return;
	}
}
