package be.dispatcher.domain.vehicle.statushandlers;

import be.dispatcher.domain.vehicle.Vehicle;

public class AtIncidentStatusHandler implements StatusHandler {

	private Vehicle vehicle;

	public AtIncidentStatusHandler(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	@Override
	public void handleStatus() {

	}
}
