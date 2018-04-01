package be.dispatcher.graphhopper.external_router;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import be.dispatcher.graphhopper.LatLon;
import be.dispatcher.graphhopper.LatLonAtTime;
import be.dispatcher.graphhopper.external_router.reouteinfojson.RouteInfo;
import be.dispatcher.graphhopper.external_router.reouteinfojson.RouteInfoEnriched;

@RunWith(MockitoJUnitRunner.class)
public class RetrofitRouteCachedCallerTest {

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
	public void expectStartTimeSetToCurrentTime() {
		retrofitRouteCaller.getCache().put(routeInput, getRouteInfo());
		RouteInfoEnriched routeInfo = retrofitRouteCaller.doCall(routeInput);
		assertThat(routeInfo).isNotNull();
		assertThat(routeInfo.getStartTime()).isBetween(LocalDateTime.now().minusSeconds(1), LocalDateTime.now().plusSeconds(1));
	}

	@Test
	public void expectArrivalTimeIsEqualToStartTimePlusTravelTime() {
		retrofitRouteCaller.getCache().put(routeInput, getRouteInfo());
		RouteInfoEnriched routeInfo = retrofitRouteCaller.doCall(routeInput);
		assertThat(routeInfo).isNotNull();
		assertThat(routeInfo.getArrivalTime()).isEqualTo(routeInfo.getStartTime().plusSeconds(routeInfo.getTime()/1000));
		assertThat(routeInfo.getArrivalTime()).isBetween(Iterables.getLast(routeInfo.getLatLonAtTimeList()).getLocalDateTime().minusSeconds(1),Iterables.getLast(routeInfo.getLatLonAtTimeList()).getLocalDateTime().plusSeconds(1));
	}


//	5.341774,
//			50.914074
	@Test
	public void expectClosestLocationIsLastNodeWhenTimeHasPassed() {
		retrofitRouteCaller.getCache().put(routeInput, getRouteInfo());
		RouteInfoEnriched routeInfo = retrofitRouteCaller.doCall(routeInput);
		assertThat(routeInfo).isNotNull();
		assertThat(routeInfo.getLocationForCurrentTime(LocalDateTime.now())).isNotNull();

		routeInfo.getLatLonAtTimeList().stream().forEach(t -> System.out.println(String.format("%d: %s", routeInfo.getLatLonAtTimeList().indexOf(t), t)));

		for (int i = 0; i < 1000; i++) {
			LatLon locationForCurrentTime = routeInfo.getLocationForCurrentTime(LocalDateTime.now().plusSeconds(1));
//			System.out.println(locationForCurrentTime);

		}

		assertThat(routeInfo.getLocationForCurrentTime(LocalDateTime.now().plusHours(1))).isEqualTo(Iterables.getLast(routeInfo.getLatLonAtTimeList()).getLatLon());
	}

	private RouteInfo getRouteInfo() {
		try {
			Type REVIEW_TYPE = new TypeToken<RouteInfo>() {}.getType();
			JsonReader reader = new JsonReader(new FileReader("/Users/berndvanvelsen/IdeaProjects/dispatcher/backend/src/test/resources/testdata/route3.json"));
			return new Gson().fromJson(reader, REVIEW_TYPE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}