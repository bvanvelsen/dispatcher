package be.dispatcher.managers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.dispatcher.DispatcherSpringJunit4Test;
import be.dispatcher.domain.Route;
import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.location.LocationBuilder;

public class LocationManagerComplexRoutingTest extends DispatcherSpringJunit4Test {

	@Autowired
	private LocationManager locationManager;

	@Test
	public void expectDistanceCorrectlyCalculatedOverXAxis() {
		Location vehicleLocation = LocationBuilder.aLocation()
				.withX(0)
				.withY(0)
				.build();
		Location incidentLocation = LocationBuilder.aLocation()
				.withX(1)
				.withY(0)
				.build();

		Route routeToIncident = locationManager.getRouteToIncident(vehicleLocation, incidentLocation);
		assertThat(routeToIncident.getDistanceInKm()).isEqualTo(1);
	}

	@Test
	public void expectDistanceCorrectlyCalculatedOverYAxis() {
		Location vehicleLocation = LocationBuilder.aLocation()
				.withX(0)
				.withY(0)
				.build();
		Location incidentLocation = LocationBuilder.aLocation()
				.withX(0)
				.withY(1)
				.build();

		Route routeToIncident = locationManager.getRouteToIncident(vehicleLocation, incidentLocation);
		assertThat(routeToIncident.getDistanceInKm()).isEqualTo(1);
	}

	@Test
	public void expectDistanceCorrectlyCalculatedOverXAndYAxis() {
		Location vehicleLocation = LocationBuilder.aLocation()
				.withX(0)
				.withY(0)
				.build();
		Location incidentLocation = LocationBuilder.aLocation()
				.withX(1)
				.withY(1)
				.build();

		Route routeToIncident = locationManager.getRouteToIncident(vehicleLocation, incidentLocation);
		assertThat(routeToIncident.getDistanceInKm()).isEqualTo(2);
	}

	@Test
	public void expectCorrectAmountOfHorizontalSteps() {
		Location vehicleLocation = LocationBuilder.aLocation()
				.withX(0)
				.withY(0)
				.build();
		Location incidentLocation = LocationBuilder.aLocation()
				.withX(4)
				.withY(0)
				.build();

		Route routeToIncident = locationManager.getRouteToIncident(vehicleLocation, incidentLocation);
		assertThat(routeToIncident.getTotalSteps()).isEqualTo(4);
		assertThat(routeToIncident.getHorizontalSteps()).isEqualTo(4);
		assertThat(routeToIncident.getVerticalSteps()).isEqualTo(0);
		assertThat(routeToIncident.getDistanceInKm()).isEqualTo(4);
	}

	@Test
	public void expectCorrectAmountOfVerticalSteps() {
		Location vehicleLocation = LocationBuilder.aLocation()
				.withX(0)
				.withY(0)
				.build();
		Location incidentLocation = LocationBuilder.aLocation()
				.withX(0)
				.withY(4)
				.build();

		Route routeToIncident = locationManager.getRouteToIncident(vehicleLocation, incidentLocation);
		assertThat(routeToIncident.getTotalSteps()).isEqualTo(4);
		assertThat(routeToIncident.getVerticalSteps()).isEqualTo(4);
		assertThat(routeToIncident.getHorizontalSteps()).isEqualTo(0);
		assertThat(routeToIncident.getDistanceInKm()).isEqualTo(4);
	}

	@Test
	public void expectCorrectAmountOfTotalSteps() {
		Location vehicleLocation = LocationBuilder.aLocation()
				.withX(0)
				.withY(0)
				.build();
		Location incidentLocation = LocationBuilder.aLocation()
				.withX(4)
				.withY(3)
				.build();

		Route routeToIncident = locationManager.getRouteToIncident(vehicleLocation, incidentLocation);
		assertThat(routeToIncident.getTotalSteps()).isEqualTo(7);
		assertThat(routeToIncident.getHorizontalSteps()).isEqualTo(4);
		assertThat(routeToIncident.getVerticalSteps()).isEqualTo(3);
		assertThat(routeToIncident.getDistanceInKm()).isEqualTo(7);
	}

}