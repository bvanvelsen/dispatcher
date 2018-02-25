package be.dispatcher.managers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.dispatcher.domain.Route;
import be.dispatcher.domain.location.Location;

@Component
public class TimeManager {

	@Autowired
	private LocationManager locationManager;

	public Route calculateRoute(int vehicleSpeedInKmh, Location vehicleLocation, Location incidentLocation) {
		Route routeToIncident = locationManager.getRouteToIncident(vehicleLocation, incidentLocation);
		int secondsToIncident = (int) ((routeToIncident.getDistanceInKm() / vehicleSpeedInKmh) * 60 * 60);
		LocalDateTime arrivalTimeAtAccident = routeToIncident.getStartTime().plusSeconds(secondsToIncident);
		routeToIncident.setArrivalTime(arrivalTimeAtAccident);
		return routeToIncident;
	}

	public long timeInMsPerStepOnRoute(Route route) {
		int totalSteps = route.getTotalSteps();
		long travelTimeInMillis = convertLocalDateTimeToMilli(route.getArrivalTime()) - convertLocalDateTimeToMilli(route.getStartTime());
		return travelTimeInMillis / totalSteps;
	}

	private long convertLocalDateTimeToMilli(LocalDateTime localDateTime) {
		return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	public int determineCurrentStep(Route route, long timePerStep, LocalDateTime currentTime) {
		LocalDateTime startTime = route.getStartTime();
		LocalDateTime arrivalTime = route.getArrivalTime();
		LocalDateTime intermediateTime = startTime;
		for (int currentStep = 0 ; intermediateTime.isBefore(arrivalTime) || intermediateTime.isEqual(arrivalTime); currentStep++, intermediateTime = intermediateTime.plus(timePerStep, ChronoUnit.MILLIS)) {
			if (intermediateTime.isAfter(currentTime) || intermediateTime.isEqual(currentTime)) {
				return currentStep;
			}
		}
		return -1;
	}

	public boolean hasVehicleArrivedAtScene(Route route) {
		LocalDateTime arrivalTime = route.getArrivalTime();
		LocalDateTime now = LocalDateTime.now();
		return arrivalTime.isBefore(now);
	}
}
