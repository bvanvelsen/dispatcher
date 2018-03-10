package be.dispatcher.domain.vehicle.statushandlers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.location.LocationBuilder;
import be.dispatcher.domain.route.Direction;
import be.dispatcher.domain.route.Route;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.managers.LocationManager;

@RunWith(MockitoJUnitRunner.class)
public class RespondingStatusHandlerTest {

	private static final int X = 900;
	private static final int Y = 100;
	private static final int METER_PER_SECOND = 100;

	@InjectMocks
	private RespondingStatusHandler respondingStatusHandler;

	@Mock
	private Vehicle vehicle;

	@Mock
	private Incident incident;

	@Mock
	private LocationManager locationManager;

	@Mock
	private Route route;
	private Location destinationLocation;
	private Location startLocation;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		when(vehicle.getSpeedMeterPerSecond()).thenReturn(METER_PER_SECOND);
		startLocation = LocationBuilder.aLocation().withX(X).withY(Y).build();
		when(vehicle.getLocation()).thenReturn(startLocation);
		destinationLocation = LocationBuilder.aLocation().withX(1200).withY(400).build();
		when(incident.getLocation()).thenReturn(destinationLocation);
		when(locationManager.getRouteBetweenLocations(any(Location.class), any(Location.class))).thenReturn(route);
	}

	@Test
	public void expectPositiveVehicleTraveledOnXAsisWhenTravelDirectionIsEast() {
		when(route.getTravelDirection()).thenReturn(Direction.EAST);

		respondingStatusHandler.handleStatus();

		assertThat(vehicle.getLocation().getX()).isEqualTo(X + METER_PER_SECOND);
		assertThat(vehicle.getLocation().getY()).isEqualTo(Y);
	}

	@Test
	public void expectPositiveVehicleTraveledOnYAsisWhenTravelDirectionIsNorth() {
		when(route.getTravelDirection()).thenReturn(Direction.NORTH);

		respondingStatusHandler.handleStatus();

		assertThat(vehicle.getLocation().getY()).isEqualTo(Y + METER_PER_SECOND);
		assertThat(vehicle.getLocation().getX()).isEqualTo(X);
	}

	@Test
	public void expectNegativeVehicleTraveledOnYAsisWhenTravelDirectionIsSouth() {
		startLocation.setY(300);
		destinationLocation.setY(100);
		when(route.getTravelDirection()).thenReturn(Direction.SOUTH);

		respondingStatusHandler.handleStatus();

		assertThat(vehicle.getLocation().getY()).isEqualTo(300 - METER_PER_SECOND);
		assertThat(vehicle.getLocation().getX()).isEqualTo(X);
	}

	@Test
	public void expectNegativeVehicleTraveledOnXAsisWhenTravelDirectionIsWest() {
		destinationLocation.setX(200);
		when(route.getTravelDirection()).thenReturn(Direction.WEST);

		respondingStatusHandler.handleStatus();

		assertThat(vehicle.getLocation().getX()).isEqualTo(X - METER_PER_SECOND);
		assertThat(vehicle.getLocation().getY()).isEqualTo(Y);
	}

	@Test
	public void expectIfPositiveXAxisOvershotThenTravelBackToXMax() {
		startLocation.setX(100);
		destinationLocation.setX(105);
		when(route.getTravelDirection()).thenReturn(Direction.EAST);

		respondingStatusHandler.handleStatus();

		assertThat(vehicle.getLocation().getX()).isEqualTo(105);
	}

	@Test
	public void expectIfNegativeXAxisOvershotThenTravelBackToXMax() {
		startLocation.setX(105);
		destinationLocation.setX(100);
		when(route.getTravelDirection()).thenReturn(Direction.WEST);

		respondingStatusHandler.handleStatus();

		assertThat(vehicle.getLocation().getX()).isEqualTo(100);
	}

	@Test
	public void expectIfPositiveYAxisOvershotThenTravelBackToYMax() {
		startLocation.setY(100);
		destinationLocation.setY(105);
		when(route.getTravelDirection()).thenReturn(Direction.NORTH);

		respondingStatusHandler.handleStatus();

		assertThat(vehicle.getLocation().getY()).isEqualTo(105);
	}

	@Test
	public void expectIfNegativeYAxisOvershotThenTravelBackToYMax() {
		startLocation.setY(105);
		destinationLocation.setY(100);
		when(route.getTravelDirection()).thenReturn(Direction.SOUTH);

		respondingStatusHandler.handleStatus();

		assertThat(vehicle.getLocation().getY()).isEqualTo(100);
	}
}