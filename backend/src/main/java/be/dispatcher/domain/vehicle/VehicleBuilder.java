package be.dispatcher.domain.vehicle;

import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.location.Location;
import be.dispatcher.exceptions.NoLocationException;

public final class VehicleBuilder {
	private VehicleType vehicleType;
	private VehicleStatus vehicleStatus;
	private Incident incident;
	private Integer speed;
	private Location location;

	private VehicleBuilder() {
	}

	public static VehicleBuilder aVehicle() {
		return new VehicleBuilder();
	}

	public VehicleBuilder withVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
		return this;
	}

	public VehicleBuilder withVehicleStatus(VehicleStatus vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
		return this;
	}

	public VehicleBuilder withIncident(Incident incident) {
		this.incident = incident;
		return this;
	}

	public VehicleBuilder withLocation(Location location) {
		this.location = location;
		return this;
	}

	public Vehicle build() {
		checkPreconditions();
		Vehicle vehicle = new Vehicle(vehicleType.getSpeed(), location, vehicleType);
		vehicle.setVehicleStatus(vehicleStatus);
		vehicle.setIncident(incident);
		return vehicle;
	}

	private void checkPreconditions() {
		checkLocationPrecondition();
	}

	private void checkLocationPrecondition() {
		if (location == null) {
			throw new NoLocationException();
		}
	}
}
