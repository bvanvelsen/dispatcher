package be.dispatcher.domain.vehicle.statushandlers;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import be.dispatcher.domain.vehicle.Vehicle;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public class AtIncidentStatusHandler implements StatusHandler {

	private Vehicle vehicle;

	public AtIncidentStatusHandler(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	@Override
	public void handleStatus() {
		vehicle.performJobAtIncidentLocation();
	}
}
