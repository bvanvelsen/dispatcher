package be.dispatcher.domain.vehicle.medical;

import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.people.Victim;

public abstract class MedicalTransportVehicle extends MedicalVehicle {

	public MedicalTransportVehicle(int id, String name, Base base, int healthGainPerTick, String vehicleImagePath) {
		super(id, name, base, healthGainPerTick, vehicleImagePath);
	}


	@Override
	protected void handleIncident() {
			Victim victim = incidentSceneMedicalTasksManager.getVictimFor(this);
			if (hasVictimToHeal(victim)) {
				healVictim(victim);
			} else {
				vehicleManager.sendVehicleToBase(this);
			}
	}

	private void healVictim(Victim victim) {
		victim.heal(healthGainPerTick);
		if (victim.isTransportable()) {
			vehicleManager.sendVehicleToNearestHospital(this);
		}
	}

	private boolean hasVictimToHeal(Victim victim) {
		return victim != null;
	}
}
