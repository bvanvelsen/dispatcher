package be.dispatcher.managers.incidentscene;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.people.Victim;
import be.dispatcher.domain.vehicle.MedicalVehicle;
import be.dispatcher.domain.vehicle.Vehicle;

@Component
public class IncidentSceneMedicalTasksManager {

	private Map<Vehicle, Victim> vehicleVictimCoupling = new HashMap<>();

	public Victim getVictimFor(Vehicle vehicle) {
		Victim victim = vehicleVictimCoupling.get(vehicle);
		if (victim == null) {
			victim = getUntreatedVictimInWorstCondition(vehicle.getIncident());
			if (victim != null) {
				vehicleVictimCoupling.put(vehicle, victim);
			}
		}
		return victim;
	}

//	public void fillEmptySlotsWithStableVictims(MedicalVehicle medicalVehicle) {
//		new AmbulanceFreeSpaceFiller(medicalVehicle).fillEmptySlotsWithStableVictims();
//	}

//	public void notifyVictimReadyForTransport(Vehicle vehicle) {
//		Incident incident = vehicle.getIncident();
//		Victim victim = vehicleVictimCoupling.remove(vehicle);
//		incident.notifyVictimStabilized(victim);
//	}

	public Victim notifyVehicleLeavingSoRemoveCoupling(Vehicle vehicle) {
		return vehicleVictimCoupling.remove(vehicle);
	}

	private Victim getUntreatedVictimInWorstCondition(Incident incident) {
		Optional<Victim> victimOptional = incident.getMedicalTasks().getVictims().stream().max(Comparator.comparing(victim -> victim.getHealCountdown()));
		if (victimOptional.isPresent()) {
			return victimOptional.get();
		}
		return null;
	}
}
