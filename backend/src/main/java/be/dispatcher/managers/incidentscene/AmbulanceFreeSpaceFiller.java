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
		List<Victim> stabilizedSittingVictims = getStabilizedSittingVictims(medicalVehicle.freeSittingsPlaces(), medicalVehicle.getIncident());
		medicalVehicle.getIncident().getStabilizedVictims().removeAll(stabilizedSittingVictims);
		medicalVehicle.getSittingVictims().addAll(stabilizedSittingVictims);
	}

	private void fillMedicalVehicleWithStableLayingVictims(MedicalVehicle medicalVehicle) {
		List<Victim> stabilizedLayingVictims = getStabilizedLayingVictims(medicalVehicle.freeLayingPlaces(), medicalVehicle.getIncident());
		medicalVehicle.getIncident().getStabilizedVictims().removeAll(stabilizedLayingVictims);
		medicalVehicle.getLayingVictims().addAll(stabilizedLayingVictims);
	}

	private List<Victim> getStabilizedSittingVictims(int amountOfVictimsToRetrieve, Incident incident) {
		return incident.getStabilizedVictims().stream().filter(victim -> InjuryLevel.MINOR.equals(victim.getInjuryLevel())).limit(amountOfVictimsToRetrieve).collect(Collectors.toList());
	}

	private List<Victim> getStabilizedLayingVictims(int amountOfVictimsToRetrieve, Incident incident) {
		return incident.getStabilizedVictims().stream().filter(victim -> !InjuryLevel.MINOR.equals(victim.getInjuryLevel())).limit(amountOfVictimsToRetrieve).collect(Collectors.toList());
	}
}
