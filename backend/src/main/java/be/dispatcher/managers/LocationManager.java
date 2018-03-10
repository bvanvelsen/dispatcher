package be.dispatcher.managers;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;

import org.springframework.stereotype.Component;

import be.dispatcher.domain.route.Direction;
import be.dispatcher.domain.route.Route;
import be.dispatcher.domain.location.Location;

@Component
public class LocationManager {

	private static final double SQRT_OF_2 = sqrt(2);

	/*
	 * https://stackoverflow.com/questions/30368632/calculate-distance-on-a-grid-between-2-points
	 * */
	//	public Route getRouteBetweenLocations(Location vehicleLocation, Location incidentLocation) {
	//		int incidentLocationX = incidentLocation.getX();
	//		int incidentLocationY = incidentLocation.getY();
	//		int vehicleLocationX = vehicleLocation.getX();
	//		int vehicleLocationY = vehicleLocation.getY();
	//
	//		int dx = abs(incidentLocationX - vehicleLocationX);
	//		int dy = abs(incidentLocationY - vehicleLocationY);
	//
	//		int min = min(dx, dy);
	//		int max = max(dx, dy);
	//
	//		int diagonalSteps = min;
	//		int straightSteps = max - min;
	//		double distanceInKm = getDistanceInKm(diagonalSteps, straightSteps);
	//		return new Route(LocalDateTime.now(), distanceInKm, diagonalSteps, straightSteps);
	//	}
	//	private double getDistanceInKm(int diagonalSteps, int straightSteps) {
	//		return SQRT_OF_2 * diagonalSteps + straightSteps;
	//	}

	public Route getRouteBetweenLocations(Location startLocation, Location destinationLocation) {
		int destinationX = destinationLocation.getX();
		int destinationY = destinationLocation.getY();
		int startX = startLocation.getX();
		int startY = startLocation.getY();
		Direction direction = null;

		int horizontalDistance = abs(destinationX - startX);
		int verticalDistance = abs(destinationY - startY);

		if (destinationY == startY && startX < destinationX) {
			direction = Direction.EAST;
		} else if (destinationX == startX && startY < destinationY) {
			direction = Direction.NORTH;
		} else if (destinationY == startY && startX > destinationX) {
			direction = Direction.WEST;
		} else if (destinationX == startX && startY > destinationY) {
			direction = Direction.SOUTH;
		} else if (horizontalDistance < verticalDistance && startX > destinationX) {
			direction = Direction.WEST;
		} else if (horizontalDistance < verticalDistance && startX < destinationX) {
			direction = Direction.EAST;
		} else if (verticalDistance < horizontalDistance && startY > destinationY) {
			direction = Direction.SOUTH;
		} else if (verticalDistance < horizontalDistance && startY < destinationY) {
			direction = Direction.NORTH;
		}
		int distanceInMeters = horizontalDistance + verticalDistance;
		return new Route(distanceInMeters, verticalDistance, horizontalDistance, direction);
	}

}
