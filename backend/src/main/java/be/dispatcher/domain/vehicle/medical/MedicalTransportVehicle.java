package be.dispatcher.domain.vehicle.medical;

import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.people.Victim;

public abstract class MedicalTransportVehicle extends MedicalVehicle {

	public MedicalTransportVehicle(int id, String name, Base base, int healthGainPerTick, String vehicleImagePath) {
		super(id, name, base, healthGainPerTick, vehicleImagePath);
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
