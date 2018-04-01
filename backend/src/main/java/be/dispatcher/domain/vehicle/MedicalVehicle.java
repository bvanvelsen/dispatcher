package be.dispatcher.domain.vehicle;

import org.springframework.beans.factory.annotation.Autowired;

import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.people.Victim;
import be.dispatcher.managers.incidentscene.IncidentSceneMedicalTasksManager;

public abstract class MedicalVehicle extends Vehicle {

	private final int healthGainPerTick;

	@Autowired
	private IncidentSceneMedicalTasksManager incidentSceneMedicalTasksManager;

	public MedicalVehicle(int id, String name, Base base, int healthGainPerTick) {
		super(id, name, base);
		this.healthGainPerTick = healthGainPerTick;
	}

	@Override
	public void tick() {
		super.tick();
		switch (vehicleStatus) {
		case AT_INCIDENT:
			Victim victim = incidentSceneMedicalTasksManager.getVictimFor(this);
			if (victim != null) {
				if (victim.heal(healthGainPerTick)) {
					if (victim.isTransportable()) {
						incident.getMedicalTasks().getVictims().remove(victim);
						filled = true;
						vehicleManager.sendVehicleToNearestHospital(this);
					}
				}
			} else {
				vehicleManager.sendVehicleToBase(this);
			}
			break;
		}
	}
}
