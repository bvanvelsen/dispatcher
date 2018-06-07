package be.dispatcher.domain.vehicle.fire;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import be.dispatcher.domain.incident.FireTasks;
import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.VehicleType;
import be.dispatcher.managers.incidentscene.IncidentSceneMedicalTasksManager;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public class FireTruck extends Vehicle {

	@Autowired
	protected IncidentSceneMedicalTasksManager incidentSceneMedicalTasksManager;

	private final int fireGainPerTick;
	private final int technicalPerTick;

	public FireTruck(int id, String name, Base base, VehicleType vehicleType, int fireGainPerTick, int technicalPerTick, String vehicleImagePath) {
		super(id, name, base, vehicleImagePath);
		this.vehicleType = vehicleType;
		this.fireGainPerTick = fireGainPerTick;
		this.technicalPerTick = technicalPerTick;
	}

	@Override
	protected void handleIncident() {
			FireTasks fireTasks = getIncident().getFireTasks();
			if (incidentSceneMedicalTasksManager.hasTrappedVictims(getIncident()) && technicalPerTick > 0) {
				incidentSceneMedicalTasksManager.getTrappedVictim(getIncident()).extract(technicalPerTick);
			} else if (fireTasks != null && fireTasks.hasFire()) {
				fireTasks.extinguishFire(fireGainPerTick);
			} else if (fireTasks != null && fireTasks.hasTechnicalWork()) {
				fireTasks.workOnTechnicalDetails(technicalPerTick);
			} else {
				vehicleManager.sendVehicleToBase(this);
			}
	}
}
