package be.dispatcher.managers.incidentscene;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.incident.MedicalTasks;
import be.dispatcher.domain.people.Criminal;
import be.dispatcher.domain.people.InjuryLevel;
import be.dispatcher.domain.people.TrappedVictim;
import be.dispatcher.domain.people.Victim;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.medical.Mug;
import be.dispatcher.domain.vehicle.police.PoliceVehicle;

@Component
public class IncidentSceneMedicalTasksManager {

	private Map<Vehicle, Victim> vehicleVictimCoupling = new HashMap<>();
	private Map<PoliceVehicle, Criminal> vehicleCriminalCoupling = new HashMap<>();
	private Map<Mug, Victim> mugVictimCoupling = new HashMap<>();

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

	public Criminal getCriminalFor(PoliceVehicle policeVehicle) {
		Criminal criminal = vehicleCriminalCoupling.get(policeVehicle);
		if (criminal == null) {
			criminal = getCriminal(policeVehicle.getIncident());
			if (criminal != null) {
				vehicleCriminalCoupling.put(policeVehicle, criminal);
			}
		}
		return criminal;
	}

	public Victim getDoctorVictimFor(Mug mug) {
		Victim victim = mugVictimCoupling.get(mug);
		if (victim == null) {
			victim = getVictimForDoctor(mug.getIncident());
			if (victim != null) {
				mugVictimCoupling.put(mug, victim);
			}
		}
		return victim;
	}

	public Victim notifyVehicleLeavingSoRemoveCoupling(Vehicle vehicle) {
		return vehicleVictimCoupling.remove(vehicle);
	}

	public Criminal notifyVehicleLeavingSoRemovePoliceVehicleCoupling(PoliceVehicle vehicle) {
		return vehicleCriminalCoupling.remove(vehicle);
	}

	public Victim notifyVictimStabilizedByDoctor(Mug mug) {
		return mugVictimCoupling.remove(mug);
	}

	private Victim getUntreatedVictimInWorstCondition(Incident incident) {
		return incident.getMedicalTasks().getVictims().stream()
				.filter(victim -> !vehicleVictimCoupling.values().contains(victim))
				.max(Comparator.comparing(Victim::getHealCountdown))
				.orElse(null);
	}

	private Criminal getCriminal(Incident incident) {
		return incident.getPoliceTasks().getCriminals().stream()
				.filter(criminal -> !vehicleCriminalCoupling.values().contains(criminal))
				.findAny()
				.orElse(null);
	}

	private Victim getVictimForDoctor(Incident incident) {
		return incident.getMedicalTasks().getVictims().stream()
				.filter(victim -> InjuryLevel.SEVERE.equals(victim.getInjuryLevel()))
				.filter(victim -> !victim.isTransportable())
				.max(Comparator.comparing(Victim::getHealCountdown))
				.orElse(null);
	}

	public boolean hasTrappedVictims(Incident incident) {
		MedicalTasks medicalTasks = incident.getMedicalTasks();
		if (medicalTasks != null) {
			return medicalTasks.getVictims().stream()
					.anyMatch(Victim::isTrapped);
		}
		return false;
	}

	public TrappedVictim getTrappedVictim(Incident incident) {
		if (hasTrappedVictims(incident)) {
			return incident.getMedicalTasks().getVictims().stream()
					.filter(Victim::isTrapped)
					.map(victim -> (TrappedVictim) victim)
					.findFirst().get();
		}
		return null;
	}
}
