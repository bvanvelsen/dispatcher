package be.dispatcher.managers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.people.Victim;
import be.dispatcher.domain.vehicle.MedicalVehicle;
import be.dispatcher.domain.vehicle.Vehicle;

@Component
public class IncidentSceneManager {

	private Map<Vehicle, Victim> vehicleVictimCoupling = new HashMap<>();
	private List<Victim> victimsReadyForTransport = new ArrayList<>();

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

	public void notifyVictimReadyForTransport(MedicalVehicle medicalVehicle) {
		victimsReadyForTransport.add(vehicleVictimCoupling.get(medicalVehicle));
		vehicleVictimCoupling.remove(medicalVehicle);
	}

	private Victim getUntreatedVictimInWorstCondition(Incident incident) {
		Optional<Victim> victimOptional = incident.getVictims().stream().min(Comparator.comparing(victim -> victim.getHealth()));
		if (victimOptional.isPresent()) {
			return victimOptional.get();
		}
		return null;
	}

}
