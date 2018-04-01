package be.dispatcher.graphhopper.reverse.geocode;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import be.dispatcher.graphhopper.LatLon;

@RunWith(MockitoJUnitRunner.class)
public class RetrofitReverseGeocoderCallerTest {

	@InjectMocks
	RetrofitReverseGeocoderCaller retrofitReverseGeocoderCaller;

	@Test
	public void expectCoordinatesReverseGeocoded() {
		ReverseGeocode reverseGeocode = retrofitReverseGeocoderCaller.doCall(new LatLon(50.937506, 5.216248));
		assertThat(reverseGeocode.getDisplay_name()).isEqualTo("Laarbeekstraat, Spalbeek, Hasselt, Limburg, Flanders, 3510, Belgium");
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