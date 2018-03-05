package be.dispatcher.domain.vehicle;

import be.dispatcher.domain.people.Victim;

public interface MedicalVehicle {
	boolean hasEmptySpaces();

	boolean canTransport(Victim victim);
}
