package be.dispatcher.domain.vehicle;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import be.dispatcher.domain.Route;
import be.dispatcher.domain.Ticks;
import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.vehicle.statushandlers.StatusHandler;
import be.dispatcher.domain.vehicle.statushandlers.StatusHandlerFactory;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public abstract class Vehicle implements Ticks {

	private static final StatusHandlerFactory STATUS_HANDLER_FACTORY = new StatusHandlerFactory();
	private final String id;
	private final Integer speed;
	private final VehicleType vehicleType;
	private VehicleStatus vehicleStatus;
	private Location location;
	private Incident incident;
	private Route route;

	Vehicle(Location location, VehicleType vehicleType) {
		vehicleStatus = VehicleStatus.AT_BASE;
		this.id = UUID.randomUUID().toString();
		this.speed = vehicleType.getSpeed();
		this.location = location;
		this.vehicleType = vehicleType;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public VehicleStatus getVehicleStatus() {
		return vehicleStatus;
	}

	public void setVehicleStatus(VehicleStatus vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	public Incident getIncident() {
		return incident;
	}

	public String getId() {
		return id;
	}

	public Integer getSpeed() {
		return speed;
	}

	public Location getLocation() {
		return location;
	}

	public Route getRoute() {
		return route;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("speed", speed)
				.append("vehicleType", vehicleType)
				.append("vehicleStatus", vehicleStatus)
				.append("location", location)
				.append("incident", incident)
				.append("route", route)
				.toString();
	}

	@Override
	public void tick() {
		Optional<StatusHandler> handlerOptional = STATUS_HANDLER_FACTORY.getStatusHandler(this);
		handlerOptional.ifPresent(statusHandler -> statusHandler.handleStatus());
//		System.out.println(this);
	}

	public void goToIncident(Incident incident, Route route) {
		vehicleStatus = VehicleStatus.RESPONDING;
		this.incident = incident;
		this.route = route;
	}

	public void ArriveAtIncident() {
		vehicleStatus = VehicleStatus.AT_INCIDENT;
		this.route = null;
	}

	public boolean hasEmptySpaces() {
		return false;
	}

	public abstract void performJobAtIncidentLocation();
}
