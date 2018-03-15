package be.dispatcher.domain.vehicle;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import be.dispatcher.domain.Ticks;
import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.location.LocationBuilder;
import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.vehicle.statushandlers.StatusHandler;
import be.dispatcher.domain.vehicle.statushandlers.StatusHandlerFactory;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public abstract class Vehicle implements Ticks {

	@Autowired
	private StatusHandlerFactory statusHandlerFactory;

	private final int id;
	private String name;
	private final Integer speed;
	private final VehicleType vehicleType;
	private VehicleStatus vehicleStatus;
	private Location location;
	private Incident incident;
	private Base base;
	private Base dropOffBase;
	private LocalDateTime dropOffEndTime;

	Vehicle(int id, String name,  VehicleType vehicleType, Base base) {
		vehicleStatus = VehicleStatus.AT_BASE;
		this.id = id;
		this.name = name;
		this.speed = vehicleType.getSpeed();
		this.location = LocationBuilder.aLocation().withX(base.getLocation().getX()).withY(base.getLocation().getY()).build();
		this.vehicleType = vehicleType;
		this.base = base;
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

	public int getId() {
		return id;
	}

	public Integer getSpeedKmh() {
		return speed;
	}

	public Integer getSpeedMeterPerSecond() {
		return (speed * 1000) / 60 / 60;
	}

	public Location getLocation() {
		return location;
	}

	public Base getDropOffBase() {
		return dropOffBase;
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
				.append("base", base)
				.append("dropOffBase", dropOffBase)
				.toString();
	}

	@Override
	public void tick() {
		Optional<StatusHandler> handlerOptional = statusHandlerFactory.getStatusHandler(this);
		handlerOptional.ifPresent(statusHandler -> statusHandler.handleStatus());
	}

	public void goToIncident(Incident incident) {
		vehicleStatus = VehicleStatus.RESPONDING;
		this.incident = incident;
	}

	public void ArriveAtIncident() {
		vehicleStatus = VehicleStatus.AT_INCIDENT;
	}

	public boolean hasEmptySpaces() {
		return false;
	}

	public abstract void performJobAtIncidentLocation();

	public void goToDropoffLocation(Base dropOffBase) {
		vehicleStatus = VehicleStatus.GO_TO_DROPOFF;
		this.dropOffBase = dropOffBase;
		this.incident = null;
	}

	public void arriveAtDropOff() {
		vehicleStatus = VehicleStatus.AT_DROP_OFF;
		dropOffEndTime = LocalDateTime.now().plusSeconds(ThreadLocalRandom.current().nextInt(60));
	}

	public void performJobAtDropOffLocation() {
		clearVehicle();
		if (dropOffEndTime.isBefore(LocalDateTime.now())) {
			vehicleStatus = VehicleStatus.GO_TO_BASE;
			dropOffEndTime = null;
		}
	}

	protected abstract void clearVehicle();

	public LocalDateTime getDropOffEndTime() {
		return dropOffEndTime;
	}

	public void arriveAtBase() {
		vehicleStatus = VehicleStatus.AT_BASE;
	}

	public Base getBase() {
		return base;
	}

	public String getName() {
		return name;
	}
}
