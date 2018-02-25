package be.dispatcher.managers;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import be.dispatcher.domain.Route;
import be.dispatcher.domain.location.Location;

@Component
public class LocationManager {

	private static final double SQRT_OF_2 = sqrt(2);

	/*
	 * https://stackoverflow.com/questions/30368632/calculate-distance-on-a-grid-between-2-points
	 * */
	//	public Route getRouteToIncident(Location vehicleLocation, Location incidentLocation) {
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

	private double getDistanceInKm(int diagonalSteps, int straightSteps) {
		return SQRT_OF_2 * diagonalSteps + straightSteps;
	}

	public Route getRouteToIncident(Location vehicleLocation, Location incidentLocation) {
		int destinationX = incidentLocation.getX();
		int destinationY = incidentLocation.getY();
		int startX = vehicleLocation.getX();
		int startY = vehicleLocation.getY();

		int horizontalSteps = abs(destinationX - startX);
		int verticalSteps = abs(destinationY - startY);

		double distanceInKm = horizontalSteps + verticalSteps;
		return new Route(LocalDateTime.now(), distanceInKm, verticalSteps, horizontalSteps);
	}

}
