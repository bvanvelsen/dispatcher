package be.dispatcher.managers;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;

import org.springframework.stereotype.Component;

import be.dispatcher.domain.route.Direction;
import be.dispatcher.domain.route.Route;
import be.dispatcher.domain.location.Location;
/*
 * https://stackoverflow.com/questions/30368632/calculate-distance-on-a-grid-between-2-points
 * */
@Component
public class LocationManager {

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
		} else if (verticalDistance <= horizontalDistance && startY > destinationY) {
			direction = Direction.SOUTH;
		} else if (verticalDistance <= horizontalDistance && startY < destinationY) {
			direction = Direction.NORTH;
		}
		int distanceInMeters = horizontalDistance + verticalDistance;
		return new Route(distanceInMeters, verticalDistance, horizontalDistance, direction);
	}

}
