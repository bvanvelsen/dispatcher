package be.dispatcher.domain.vehicle.statushandlers;

import java.util.Optional;

import be.dispatcher.domain.vehicle.Vehicle;

public class StatusHandlerFactory {
	
	public Optional<StatusHandler> getStatusHandler(Vehicle vehicle) {
		switch (vehicle.getVehicleStatus()) {
		case RESPONDING:
			return Optional.of(new RespondingStatusHandler(vehicle));
		case AT_INCIDENT:
			return Optional.of(new AtIncidentStatusHandler(vehicle));
		}
		return Optional.empty();
	}
}
