package be.dispatcher.managers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import be.dispatcher.domain.Route;
import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.location.LocationBuilder;

@RunWith(MockitoJUnitRunner.class)
public class TimeManagerCalculateRouteTest {

	private static final int VEHICLE_SPEED = 70;

	@Mock
	private LocationManager locationManager;

	@Mock
	private Location location;

	@InjectMocks
	private TimeManager timeManager;

	private LocalDateTime now;
	private Route route;

	@Before
	public void setup() {
		now = LocalDateTime.now();
		route = new Route(now, Double.valueOf(String.valueOf(VEHICLE_SPEED)), 0, VEHICLE_SPEED);
		when(locationManager.getRouteToIncident(any(Location.class), any(Location.class))).thenReturn(route);
	}

	@Test
	public void expectArrivalTimeCorrectlyCalculatedBasedOnDistanceAndVehicleSpeed() {
		Route calculateRoute = timeManager.calculateRoute(VEHICLE_SPEED, location, location);
		assertThat(calculateRoute.getArrivalTimeInMs()).isBetween(localDateTimeToLong(now.plusMinutes(59)), localDateTimeToLong(now.plusMinutes(61)));
	}

	@Test
	public void expectArrivalTimeCorrectlyCalculatedBasedOnDistanceAndVehicleSpeedForShortDistance() {
		now = LocalDateTime.now();
		route = new Route(now, 1.0, 0, 1);
		when(locationManager.getRouteToIncident(any(Location.class), any(Location.class))).thenReturn(route);
		Route calculatedRoute = timeManager.calculateRoute(VEHICLE_SPEED, location, location);
		assertThat(calculatedRoute.getArrivalTimeInMs()).isBetween(localDateTimeToLong(now.plusSeconds(51)), localDateTimeToLong(now.plusSeconds(52)));
	}

	private Long localDateTimeToLong(LocalDateTime localDateTime) {
		return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}
}