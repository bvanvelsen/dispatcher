package be.dispatcher.domain;

import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.vehicle.Vehicle;

public class VehicleIncidentCoupling {

	private Vehicle vehicle;
	private Incident incident;

	public VehicleIncidentCoupling(Vehicle vehicle, Incident incident) {
		this.vehicle = vehicle;
		this.incident = incident;
	}
}
