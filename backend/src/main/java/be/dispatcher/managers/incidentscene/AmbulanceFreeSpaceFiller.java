package be.dispatcher.managers.incidentscene;

import java.util.List;
import java.util.stream.Collectors;

import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.people.InjuryLevel;
import be.dispatcher.domain.people.Victim;
import be.dispatcher.domain.vehicle.MedicalVehicle;

public class AmbulanceFreeSpaceFiller {

	private MedicalVehicle medicalVehicle;

	public AmbulanceFreeSpaceFiller(MedicalVehicle medicalVehicle) {
		this.medicalVehicle = medicalVehicle;
	}

	public void fillEmptySlotsWithStableVictims() {
		if (medicalVehicle.hasFreeLayingPlaces()) {
			fillMedicalVehicleWithStableLayingVictims(medicalVehicle);
		}
		if (medicalVehicle.hasFreeSittingsPlaces()) {
			fillMedicalVehicleWithStableSittingVictims(medicalVehicle);
		}
	}

	private void fillMedicalVehicleWithStableSittingVictims(MedicalVehicle medicalVehicle) {
		medicalVehicle.getSittingVictims().addAll(getStabilizedSittingVictims(medicalVehicle.freeSittingsPlaces(), medicalVehicle.getIncident()));
	}

	private void fillMedicalVehicleWithStableLayingVictims(MedicalVehicle medicalVehicle) {
		medicalVehicle.getLayingVictims().addAll(getStabilizedLayingVictims(medicalVehicle.freeLayingPlaces(), medicalVehicle.getIncident()));
	}

	private List<Victim> getStabilizedSittingVictims(int amountOfVictimsToRetrieve, Incident incident) {
		return incident.getStabilizedVictims().stream().filter(victim -> InjuryLevel.MINOR.equals(victim.getInjuryLevel())).limit(amountOfVictimsToRetrieve).collect(Collectors.toList());
	}

	private List<Victim> getStabilizedLayingVictims(int amountOfVictimsToRetrieve, Incident incident) {
		return incident.getStabilizedVictims().stream().filter(victim -> !InjuryLevel.MINOR.equals(victim.getInjuryLevel())).limit(amountOfVictimsToRetrieve).collect(Collectors.toList());
	}
}
