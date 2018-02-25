package be.dispatcher.managers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import be.dispatcher.domain.Route;
import be.dispatcher.domain.location.Location;

@RunWith(MockitoJUnitRunner.class)
public class TimeManagerTimeInMsPerStepOnRouteTest {

	private static final int VEHICLE_SPEED = 70;
	private static final int ONEHOURINMS = 1000 * 60 * 60;

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
		route = new Route(now, 10.0, 10, 10);
		route.setArrivalTime(now.plusHours(1));
	}

	@Test
	public void expectTimesPerStepCorrectlyCalculated() {
		long timeInMsPerStepOnRoute = timeManager.timeInMsPerStepOnRoute(route);
		assertThat(timeInMsPerStepOnRoute).isEqualTo((ONEHOURINMS) / route.getTotalSteps());
	}
}