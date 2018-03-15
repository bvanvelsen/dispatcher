package be.dispatcher.domain.vehicle.statushandlers;

import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.managers.LocationManager;

public class GoToDropOffBAseStatusHandler extends RespondingStatusHandler{

	public GoToDropOffBAseStatusHandler(Vehicle vehicle, Location destination, LocationManager locationManager) {
		super(vehicle, destination, locationManager);
	}

	@Override
	public void handleStatus() {
		super.handleStatus();
		if (isVehicleArrived(getDestination())) {
			getVehicle().arriveAtDropOff();
		}
	}
}
