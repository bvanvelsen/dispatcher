package be.dispatcher.domain.vehicle.statushandlers;

import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.managers.LocationManager;

public class GoToBaseStatusHandler extends RespondingStatusHandler{

	public GoToBaseStatusHandler(Vehicle vehicle, Location destination, LocationManager locationManager) {
		super(vehicle, destination, locationManager);
	}

	@Override
	public void handleStatus() {
		if (!isVehicleArrived(getDestination())) {
			super.handleStatus();
		}
		if (isVehicleArrived(getDestination())) {
			getVehicle().arriveAtBase();
		}
	}
}
