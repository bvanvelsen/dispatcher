package be.dispatcher.domain.vehicle.fire;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import be.dispatcher.domain.incident.FireTasks;
import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.VehicleType;
import be.dispatcher.managers.incidentscene.IncidentSceneMedicalTasksManager;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public class FireTruck extends Vehicle {

	@Autowired
	protected IncidentSceneMedicalTasksManager incidentSceneMedicalTasksManager;

	private final int extinguishRatePerTick;
	private final int technicalWorkPerTick;

	public FireTruck(int id, String name, Base base, VehicleType vehicleType, int extinguishRatePerTick, int technicalWorkPerTick, String vehicleImagePath) {
		super(id, name, base, vehicleImagePath);
		this.vehicleType = vehicleType;
		this.extinguishRatePerTick = extinguishRatePerTick;
		this.technicalWorkPerTick = technicalWorkPerTick;
	}

	@Override
	protected void handleIncident() {
		Incident incident = getIncident();
		FireTasks fireTasks = incident.getFireTasks();
		if (canPerformTechnicalWork() && incidentSceneMedicalTasksManager.hasTrappedVictims(incident)) {
			extractTrappedVictims(incident);
		} else if (fireTasks != null) {
			if (fireTasks.hasFire()) {
				fireTasks.extinguishFire(extinguishRatePerTick);
			} else if (fireTasks.hasTechnicalWork()) {
				fireTasks.workOnTechnicalDetails(technicalWorkPerTick);
			}
		} else {
			vehicleManager.sendVehicleToBase(this);
		}
	}

	public int getExtinguishRatePerTick() {
		return extinguishRatePerTick;
	}

	public int getTechnicalWorkPerTick() {
		return technicalWorkPerTick;
	}

	private void extractTrappedVictims(Incident incident) {
		incidentSceneMedicalTasksManager.getTrappedVictim(incident).extract(technicalWorkPerTick);
	}

	private boolean canPerformTechnicalWork() {
		return technicalWorkPerTick > 0;
	}
}
