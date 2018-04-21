package be.dispatcher.domain.vehicle.medical;

import org.springframework.beans.factory.annotation.Autowired;

import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.managers.incidentscene.IncidentSceneMedicalTasksManager;

public abstract class MedicalVehicle extends Vehicle {

	protected final int healthGainPerTick;

	@Autowired
	protected IncidentSceneMedicalTasksManager incidentSceneMedicalTasksManager;

	public MedicalVehicle(int id, String name, Base base, int healthGainPerTick, String vehicleImagePath) {
		super(id, name, base, vehicleImagePath);
		this.healthGainPerTick = healthGainPerTick;
	}

	@Override
	public void tick() {
		super.tick();
	}
}
