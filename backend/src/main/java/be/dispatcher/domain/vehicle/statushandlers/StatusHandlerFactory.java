package be.dispatcher.domain.vehicle.statushandlers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.managers.LocationManager;

@Component
public class StatusHandlerFactory {

	@Autowired
	private LocationManager locationManager;

	public Optional<StatusHandler> getStatusHandler(Vehicle vehicle) {
		switch (vehicle.getVehicleStatus()) {
		case RESPONDING:
			return Optional.of(new RespondingToIncidentStatusHandler(vehicle, vehicle.getIncident().getLocation(), locationManager));
		case AT_INCIDENT:
			return Optional.of(new AtIncidentStatusHandler(vehicle));
		case GO_TO_DROPOFF:
			return Optional.of(new GoToDropOffBAseStatusHandler(vehicle, vehicle.getDropOffBase().getLocation(), locationManager));
		case AT_DROP_OFF:
			return Optional.of(new AtDropOffStatusHandler(vehicle));
		case GO_TO_BASE:
			return Optional.of(new GoToBaseStatusHandler(vehicle, vehicle.getBase().getLocation(), locationManager));
		}
		return Optional.empty();
	}
}
