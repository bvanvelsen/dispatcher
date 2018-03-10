package be.dispatcher.managers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.dispatcher.DispatcherSpringJunit4Test;
import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.location.LocationBuilder;
import be.dispatcher.domain.route.Route;

public class LocationManagerTest extends DispatcherSpringJunit4Test {

	@Autowired
	private LocationManager locationManager;

	@Test
	public void expectDistanceCorrectlyCalculatedOverXAxis() {
		Location vehicleLocation = buildLocation(0, 0);
		Location incidentLocation = buildLocation(1, 0);

		Route routeToIncident = locationManager.getRouteBetweenLocations(vehicleLocation, incidentLocation);
		assertThat(routeToIncident.getDistanceInMeters()).isEqualTo(1);
	}

	@Test
	public void expectDistanceCorrectlyCalculatedOverYAxis() {
		Location vehicleLocation = buildLocation(0, 0);
		Location incidentLocation = buildLocation(0, 1);

		Route routeToIncident = locationManager.getRouteBetweenLocations(vehicleLocation, incidentLocation);
		assertThat(routeToIncident.getDistanceInMeters()).isEqualTo(1);
	}

	@Test
	public void expectDistanceCorrectlyCalculatedOverXAndYAxis() {
		Location vehicleLocation = buildLocation(0, 0);
		Location incidentLocation = buildLocation(1, 1);

		Route routeToIncident = locationManager.getRouteBetweenLocations(vehicleLocation, incidentLocation);
		assertThat(routeToIncident.getDistanceInMeters()).isEqualTo(2);
	}

	@Test
	public void expectCorrectAmountOfHorizontalSteps() {
		Location vehicleLocation = buildLocation(0, 0);
		Location incidentLocation = buildLocation(4, 0);

		Route routeToIncident = locationManager.getRouteBetweenLocations(vehicleLocation, incidentLocation);
		assertThat(routeToIncident.getTotalDistance()).isEqualTo(4);
		assertThat(routeToIncident.getHorizontalDistance()).isEqualTo(4);
		assertThat(routeToIncident.getVerticalDistance()).isEqualTo(0);
		assertThat(routeToIncident.getDistanceInMeters()).isEqualTo(4);
	}

	@Test
	public void expectCorrectAmountOfVerticalSteps() {
		Location vehicleLocation = buildLocation(0, 0);
		Location incidentLocation = buildLocation(0, 4);

		Route routeToIncident = locationManager.getRouteBetweenLocations(vehicleLocation, incidentLocation);
		assertThat(routeToIncident.getTotalDistance()).isEqualTo(4);
		assertThat(routeToIncident.getVerticalDistance()).isEqualTo(4);
		assertThat(routeToIncident.getHorizontalDistance()).isEqualTo(0);
		assertThat(routeToIncident.getDistanceInMeters()).isEqualTo(4);
	}

	@Test
	public void expectCorrectAmountOfTotalSteps() {
		Location vehicleLocation = buildLocation(0, 0);
		Location incidentLocation = buildLocation(4, 3);

		Route routeToIncident = locationManager.getRouteBetweenLocations(vehicleLocation, incidentLocation);
		assertThat(routeToIncident.getTotalDistance()).isEqualTo(7);
		assertThat(routeToIncident.getHorizontalDistance()).isEqualTo(4);
		assertThat(routeToIncident.getVerticalDistance()).isEqualTo(3);
		assertThat(routeToIncident.getDistanceInMeters()).isEqualTo(7);
	}

	private Location buildLocation(int x, int y) {
		return LocationBuilder.aLocation()
				.withX(x)
				.withY(y)
				.build();
	}
}