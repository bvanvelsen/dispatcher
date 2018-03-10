package be.dispatcher.managers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.dispatcher.DispatcherSpringJunit4Test;
import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.location.LocationBuilder;
import be.dispatcher.domain.route.Direction;
import be.dispatcher.domain.route.Route;

public class LocationManagerDirectionTest extends DispatcherSpringJunit4Test {

	@Autowired
	private LocationManager locationManager;

	@Test
	public void expectNextTravelStepToEastIfYAxisDoesNotNeedTravel() {
		Location vehicleLocation = buildLocation(0, 0);
		Location incidentLocation = buildLocation(1, 0);

		Route routeToIncident = locationManager.getRouteBetweenLocations(vehicleLocation, incidentLocation);
		assertThat(routeToIncident.getTravelDirection()).isEqualTo(Direction.EAST);
	}

	@Test
	public void expectNextTravelStepToNorthIfXAxisDoesNotNeedTravel() {
		Location vehicleLocation = buildLocation(0, 0);
		Location incidentLocation = buildLocation(0, 1);

		Route routeToIncident = locationManager.getRouteBetweenLocations(vehicleLocation, incidentLocation);
		assertThat(routeToIncident.getTravelDirection()).isEqualTo(Direction.NORTH);
	}

	@Test
	public void expectNextTravelStepToSouthIfXAxisDoesNotNeedTravel() {
		Location vehicleLocation = buildLocation(0, 10);
		Location incidentLocation = buildLocation(0, 9);

		Route routeToIncident = locationManager.getRouteBetweenLocations(vehicleLocation, incidentLocation);
		assertThat(routeToIncident.getTravelDirection()).isEqualTo(Direction.SOUTH);
	}

	@Test
	public void expectNextTravelStepToWestIfYAxisDoesNotNeedTravel() {
		Location vehicleLocation = buildLocation(10, 0);
		Location incidentLocation = buildLocation(9, 0);

		Route routeToIncident = locationManager.getRouteBetweenLocations(vehicleLocation, incidentLocation);
		assertThat(routeToIncident.getTravelDirection()).isEqualTo(Direction.WEST);
	}

	@Test
	public void expectNextStepIsToSouthBecauseYAxisNeedsTheLeastTravelDistance() {
		Location vehicleLocation = buildLocation(10, 10);
		Location incidentLocation = buildLocation(4, 8);

		Route routeToIncident = locationManager.getRouteBetweenLocations(vehicleLocation, incidentLocation);
		assertThat(routeToIncident.getTravelDirection()).isEqualTo(Direction.SOUTH);
	}

	@Test
	public void expectNextStepIsToNorthBecauseYAxisNeedsTheLeastTravelDistance() {
		Location vehicleLocation = buildLocation(10, 10);
		Location incidentLocation = buildLocation(14, 12);

		Route routeToIncident = locationManager.getRouteBetweenLocations(vehicleLocation, incidentLocation);
		assertThat(routeToIncident.getTravelDirection()).isEqualTo(Direction.NORTH);
	}

	@Test
	public void expectNextStepIsToWestBecauseXAxisNeedsTheLeastTravelDistance() {
		Location vehicleLocation = buildLocation(10, 10);
		Location incidentLocation = buildLocation(8, 4);

		Route routeToIncident = locationManager.getRouteBetweenLocations(vehicleLocation, incidentLocation);
		assertThat(routeToIncident.getTravelDirection()).isEqualTo(Direction.WEST);
	}

	@Test
	public void expectNextStepIsToEastBecauseXAxisNeedsTheLeastTravelDistance() {
		Location vehicleLocation = buildLocation(10, 10);
		Location incidentLocation = buildLocation(12, 14);

		Route routeToIncident = locationManager.getRouteBetweenLocations(vehicleLocation, incidentLocation);
		assertThat(routeToIncident.getTravelDirection()).isEqualTo(Direction.EAST);
	}

	private Location buildLocation(int x, int y) {
		return LocationBuilder.aLocation()
				.withX(x)
				.withY(y)
				.build();
	}
}