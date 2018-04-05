package be.dispatcher.graphhopper.external_router;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import be.dispatcher.graphhopper.LatLon;
import be.dispatcher.graphhopper.LatLonAtTime;
import be.dispatcher.graphhopper.external_router.routeinfojson.RouteInfoEnriched;

@RunWith(MockitoJUnitRunner.class)
public class RetrofitRouteRealCallerTest {

	@InjectMocks
	RetrofitRouteCaller retrofitRouteCaller;
	private RouteInput routeInput;

	@Before
	public void setup() {
		LatLon startLocation = new LatLon(50.92669, 5.342462);//jessa
		LatLon destinationLocation = new LatLon(50.937506, 5.316248);//brico grote ring
		routeInput = new RouteInput("car", startLocation, destinationLocation);
	}

	@Test
	public void expectCoordinatesReverseGeocoded() {
		RouteInfoEnriched routeInfo = retrofitRouteCaller.doCall(routeInput);
		assertThat(routeInfo).isNotNull();
		System.out.println(routeInfo);
		for (LatLonAtTime latLonAtTime : routeInfo.getLatLonAtTimeList()) {
			System.out.println(latLonAtTime);
		}
	}

	@After
	public void throttleTest() {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}