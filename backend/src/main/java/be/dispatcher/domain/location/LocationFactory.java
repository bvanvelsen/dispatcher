package be.dispatcher.domain.location;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.dispatcher.DispatcherProperties;

@Component
public class LocationFactory {

	@Autowired
	private DispatcherProperties dispatcherProperties;

	public Location createDefaultVehicleLocation() {
		return LocationBuilder.aLocation()
				.withX(0)
				.withY(0)
				.build();
	}

	public Location createIncidentLocation() {
		Integer x = getRandomLocationCoordinate();
		Integer y = getRandomLocationCoordinate();
		return LocationBuilder.aLocation()
				.withX(x)
				.withY(y)
				.build();
	}

	private int getRandomLocationCoordinate() {
		return new Random().nextInt(Integer.valueOf(dispatcherProperties.getWorldSize()));
	}
}
