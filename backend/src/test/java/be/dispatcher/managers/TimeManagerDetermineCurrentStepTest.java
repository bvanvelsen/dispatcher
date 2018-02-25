package be.dispatcher.managers;

import static org.assertj.core.api.Assertions.assertThat;

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
public class TimeManagerDetermineCurrentStepTest {

	private static final int ONE_HOUR_IN_MS = (1000 * 60 * 60);

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
	public void expectFirstStepCorrectlyDetermined() {
		int step = timeManager.determineCurrentStep(route, ONE_HOUR_IN_MS / route.getTotalSteps(), now);
		assertThat(step).isEqualTo(0);
	}

	@Test
	public void expectLastStepCorrectlyDetermined() {
		int step = timeManager.determineCurrentStep(route, ONE_HOUR_IN_MS / route.getTotalSteps(), now.plusHours(1));
		assertThat(step).isEqualTo(route.getTotalSteps());
	}

	@Test
	public void expectMiddleStepCorrectlyDetermined() {
		int step = timeManager.determineCurrentStep(route, ONE_HOUR_IN_MS / route.getTotalSteps(), now.plusMinutes(30));
		assertThat(step).isEqualTo(route.getTotalSteps() / 2);
	}

	@Test
	public void expectVehicleLeftBaseWhenStartTimeHasPassed() {
		int step = timeManager.determineCurrentStep(route, ONE_HOUR_IN_MS / route.getTotalSteps(), now.plusNanos(1));
		assertThat(step).isEqualTo(1);
	}
}