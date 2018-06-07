package be.dispatcher.domain.vehicle.medical;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.people.Victim;
import be.dispatcher.domain.vehicle.VehicleType;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public class Mug extends MedicalVehicle {

	public Mug(int id, String name, Base base, int healthGainPerTick, String vehicleImagePath) {
		super(id, name, base, healthGainPerTick, vehicleImagePath);
		vehicleType = VehicleType.MUG;
	}

	@Override
	protected void handleIncident() {
			Victim victim = incidentSceneMedicalTasksManager.getDoctorVictimFor(this);
			if (victim != null) {
				if (victim.heal(healthGainPerTick)) {
					if (victim.isTransportable()) {
						incidentSceneMedicalTasksManager.notifyVictimStabilizedByDoctor(this);
					}
				}
			} else {
				vehicleManager.sendVehicleToBase(this);
			}
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("name", name)
				.append("vehicleType", vehicleType)
				.append("base", base)
				.append("location", location)
				.toString();
	}
}
