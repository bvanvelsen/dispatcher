package be.dispatcher.managers;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import be.dispatcher.domain.Route;

@RunWith(MockitoJUnitRunner.class)
public class TimeManagerIsArrivedAtSceneTest {

	private static final int ONE_HOUR_IN_MS = (1000 * 60 * 60);

	@InjectMocks
	private TimeManager timeManager;

	private Route route;
	private LocalDateTime now;

	@Before
	public void setup() {
		now = LocalDateTime.now();
		route = new Route(now, 10.0, 10, 10);
		route.setArrivalTime(now.minusMinutes(1));
	}

	@Test
	public void expectArrivedAtSceneIfArrivalTimeBeforeCurrentTime() {
		boolean arrivedAtScene = timeManager.hasVehicleArrivedAtScene(route);
		assertThat(arrivedAtScene).isTrue();
	}

	@Test
	public void expectArrivedAtSceneIfArrivalTimeAfterCurrentTime() {
		route.setArrivalTime(now.plusMinutes(1));
		boolean arrivedAtScene = timeManager.hasVehicleArrivedAtScene(route);
		assertThat(arrivedAtScene).isFalse();
	}
}