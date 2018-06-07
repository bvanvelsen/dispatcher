package be.dispatcher.domain.vehicle;

import java.time.LocalDateTime;
import java.util.Random;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import be.dispatcher.domain.Ticks;
import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.graphhopper.LatLon;
import be.dispatcher.graphhopper.external_router.RouteInfoEnriched;
import be.dispatcher.managers.VehicleManager;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public abstract class Vehicle implements Ticks, Comparable {

	@Autowired
	protected VehicleManager vehicleManager;

	protected int id;
	protected String name;
	protected VehicleType vehicleType;

	protected LatLon location;
	protected String vehicleImagePath;
	protected Incident incident;

	protected VehicleStatus vehicleStatus;
	protected boolean filled;
	protected LocalDateTime timeUntilReadyAtDropoff;
	protected LocalDateTime timeUntilAlarmedStateDone;

	@JsonIgnore
	protected Base base;

	@JsonIgnore
	protected RouteInfoEnriched routeInfo;

	public Vehicle(int id, String name, Base base, String vehicleImagePath) {
		this.id = id;
		this.name = name;
		this.base = base;
		location = base.getLocation();
		this.vehicleImagePath = vehicleImagePath;
		this.vehicleStatus = VehicleStatus.AT_BASE;
		filled = false;
	}

	@Override
	public void tick() {
		switch (vehicleStatus) {
		case AT_BASE:
			break;
		case ALARMED:
			checkAlarmedStateAndReadyToGoToResponding();
			break;
		case RESPONDING:
			setLocation(routeInfo.getLocationForCurrentTime(LocalDateTime.now()));
			checkAndSetArrivalForArrivalAtIncident();
			break;
		case AT_INCIDENT:
			handleIncident();
			break;
		case GO_TO_DROPOFF:
			setLocation(routeInfo.getLocationForCurrentTime(LocalDateTime.now()));
			checkAndSetArrivalForArrivalAtDropOff();
			break;
		case AT_DROP_OFF:
			checkReadyAtDropOffAndGoBackToBase();
			break;
		case GO_TO_BASE:
			setLocation(routeInfo.getLocationForCurrentTime(LocalDateTime.now()));
			checkAndSetAtBase();
			break;
		}
	}

	protected abstract void handleIncident();

	private void checkAlarmedStateAndReadyToGoToResponding() {
		if (LocalDateTime.now().isAfter(timeUntilAlarmedStateDone)) {
			vehicleManager.sendVehicleToIncident(id, getIncident().getId());
			timeUntilAlarmedStateDone = null;
		}
	}

	private void checkAndSetArrivalForArrivalAtIncident() {
		if (LocalDateTime.now().isAfter(routeInfo.getArrivalTime())) {
			vehicleStatus = VehicleStatus.AT_INCIDENT;
			location = incident.getLocation();
			routeInfo = null;
		}
	}

	private void checkAndSetArrivalForArrivalAtDropOff() {
		if (LocalDateTime.now().isAfter(routeInfo.getArrivalTime())) {
			vehicleStatus = VehicleStatus.AT_DROP_OFF;
			location = routeInfo.getDestination();
			routeInfo = null;
			filled = false;
			timeUntilReadyAtDropoff = LocalDateTime.now().plusSeconds(new Random().ints(3 * 60, 10 * 60).findFirst().getAsInt());
		}
	}

	private void checkReadyAtDropOffAndGoBackToBase() {
		if (LocalDateTime.now().isAfter(timeUntilReadyAtDropoff)) {
			if (location.equals(base.getLocation())) {
				setIncident(null);
				vehicleStatus = VehicleStatus.AT_BASE;
			} else {
				vehicleStatus = VehicleStatus.GO_TO_BASE;
				vehicleManager.sendVehicleToBase(this);
			}
			timeUntilReadyAtDropoff = null;
		}
	}

	private void checkAndSetAtBase() {
		if (LocalDateTime.now().isAfter(routeInfo.getArrivalTime())) {
			vehicleStatus = VehicleStatus.AT_BASE;
			location = getBase().getLocation();
			setIncident(null);
			routeInfo = null;
		}
	}

	private void setLocation(LatLon location) {
		this.location = location;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setRouteInfo(RouteInfoEnriched routeInfo) {
		this.routeInfo = routeInfo;
	}

	public void setVehicleStatus(VehicleStatus vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	public LatLon getLocation() {
		return location;
	}

	public VehicleStatus getVehicleStatus() {
		return vehicleStatus;
	}

	public Incident getIncident() {
		return incident;
	}

	public RouteInfoEnriched getRouteInfo() {
		return routeInfo;
	}

	public Base getBase() {
		return base;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	public boolean isFilled() {
		return filled;
	}

	public String getVehicleImagePath() {
		return vehicleImagePath;
	}

	public void setVehicleImagePath(String vehicleImagePath) {
		this.vehicleImagePath = vehicleImagePath;
	}

	public void setTimeUntilAlarmedStateDone(LocalDateTime timeUntilAlarmedStateDone) {
		this.timeUntilAlarmedStateDone = timeUntilAlarmedStateDone;
	}

	@Override
	public int compareTo(Object o) {
		Vehicle v = (Vehicle)o;
		return new CompareToBuilder()
				.append(this.vehicleType, v.vehicleType)
				.append(this.name,v.name)
				.toComparison();
	}
}
