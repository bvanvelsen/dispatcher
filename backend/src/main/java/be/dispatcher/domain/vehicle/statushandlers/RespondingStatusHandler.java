package be.dispatcher.domain.vehicle.statushandlers;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.route.Direction;
import be.dispatcher.domain.route.Route;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.managers.LocationManager;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public abstract class RespondingStatusHandler implements StatusHandler {

	private Vehicle vehicle;
	private Location destination;

	private LocationManager locationManager;

	public RespondingStatusHandler(Vehicle vehicle, Location destination, LocationManager locationManager) {
		this.vehicle = vehicle;
		this.destination = destination;
		this.locationManager = locationManager;
	}

	protected Location getDestination() {
		return destination;
	}

	protected Vehicle getVehicle() {
		return vehicle;
	}

	@Override
	public void handleStatus() {
		driveToIncident();
	}

	private void driveToIncident() {
		Route routeToIncident = locationManager.getRouteBetweenLocations(vehicle.getLocation(), destination);
		travelTowardsDirection(vehicle, routeToIncident.getTravelDirection(), destination);
	}

	protected boolean isVehicleArrived(Location destination) {
		return vehicle.getLocation().equals(destination);
	}

	private void travelTowardsDirection(Vehicle vehicle, Direction travelDirection, Location destination) {
		int distanceToTravel = vehicle.getSpeedMeterPerSecond();
		Location vehicleLocation = vehicle.getLocation();
		switch (travelDirection) {
		case EAST:
			vehicleLocation.setX(vehicleLocation.getX() + distanceToTravel);
			break;
		case WEST:
			vehicleLocation.setX(vehicleLocation.getX() - distanceToTravel);
			break;
		case NORTH:
			vehicleLocation.setY(vehicleLocation.getY() + distanceToTravel);
			break;
		case SOUTH:
			vehicleLocation.setY(vehicleLocation.getY() - distanceToTravel);
			break;
		}
		if (overshotDestination(destination, vehicleLocation, travelDirection)) {
			setVehicleToLimit(destination, vehicleLocation, travelDirection);
		}
	}

	private void setVehicleToLimit(Location destination, Location vehicleLocation, Direction travelDirection) {
		switch (travelDirection) {
		case EAST:
		case WEST:
			vehicleLocation.setX(destination.getX());
			vehicleLocation.setX(destination.getX());
			break;
		case NORTH:
		case SOUTH:
			vehicleLocation.setY(destination.getY());
			break;
		}
	}

	private boolean overshotDestination(Location destination, Location vehicleLocation, Direction travelDirection) {
		switch (travelDirection) {
		case EAST:
			return vehicleLocation.getX() > destination.getX();
		case WEST:
			return vehicleLocation.getX() < destination.getX();
		case NORTH:
			return vehicleLocation.getY() > destination.getY();
		case SOUTH:
			return vehicleLocation.getY() < destination.getY();
		}
		throw new RuntimeException(String.format("travelDirection %s could not be checked for overshot location", travelDirection));
	}
}
