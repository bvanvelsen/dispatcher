package be.dispatcher.domain.vehicle.statushandlers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import be.dispatcher.domain.Route;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.managers.TimeManager;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public class RespondingStatusHandler implements StatusHandler {

	private Vehicle vehicle;

	@Autowired
	private TimeManager timeManager;

	public RespondingStatusHandler(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	@Override
	public void handleStatus() {
		calculateCurrentLocation();
	}

	private void calculateCurrentLocation() {
		Route route = vehicle.getRoute();
		long timePerStep = timeManager.timeInMsPerStepOnRoute(route);
		int currentStep = timeManager.determineCurrentStep(route, timePerStep, LocalDateTime.now());
		determineAndSetCurrentLocation(currentStep);
		if (timeManager.hasVehicleArrivedAtScene(route)) {
			vehicle.ArriveAtIncident();
		}
	}

	private void determineAndSetCurrentLocation(int currentStep) {
		boolean startAlongXAxis = startsAlongXAxisIfHorizontalStepsSmallerThenVertivalSteps();
		if (startAlongXAxis) {
			determinePostionIfStartedOnXAxis(currentStep);
		} else {
			determinePositionIfStartedOnYAxis(currentStep);
		}
	}

	private boolean startsAlongXAxisIfHorizontalStepsSmallerThenVertivalSteps() {
		return vehicle.getRoute().getHorizontalSteps() < vehicle.getRoute().getVerticalSteps();
	}

	private void determinePositionIfStartedOnYAxis(int currentStep) {
		if (currentStep <= vehicle.getRoute().getVerticalSteps()) {
			vehicle.getLocation().setY(currentStep);
		} else {
			vehicle.getLocation().setX(currentStep - vehicle.getRoute().getVerticalSteps());
		}
	}

	private void determinePostionIfStartedOnXAxis(int currentStep) {
		if (currentStep <= vehicle.getRoute().getHorizontalSteps()) {
			vehicle.getLocation().setX(currentStep);
		} else {
			vehicle.getLocation().setY(currentStep - vehicle.getRoute().getHorizontalSteps());
		}
	}
}
