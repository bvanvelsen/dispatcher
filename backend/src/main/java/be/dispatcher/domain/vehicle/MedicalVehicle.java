package be.dispatcher.domain.vehicle;

import java.util.List;

import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.people.Victim;

public interface MedicalVehicle {
	boolean hasEmptySpaces();

	boolean hasFreeLayingPlaces();

	boolean hasFreeSittingsPlaces();

	int freeLayingPlaces();

	int freeSittingsPlaces();

	List<Victim> getLayingVictims();

	List<Victim> getSittingVictims();

	boolean canTransport(Victim victim);

	Incident getIncident();
}
