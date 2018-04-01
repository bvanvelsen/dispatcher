package be.dispatcher.graphhopper.location;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.dispatcher.DispatcherProperties;
import be.dispatcher.DispatcherSpringJunit4Test;
import be.dispatcher.graphhopper.LatLon;

public class PointGeneratorTest extends DispatcherSpringJunit4Test {

	@Autowired
	private PointGenerator pointGenerator;

	@Autowired
	private DispatcherProperties dispatcherProperties;

	@Test
	public void expectPointGeneratedWithinBounds() {
		LatLon latLon = pointGenerator.generateRandomIncidentLocationSnappedToNearestLocation();

		assertThat(latLon).isNotNull();
		assertThat(latLon.getLat()).isBetween(dispatcherProperties.getWorldBoundingboxMinLat(), dispatcherProperties.getWorldBoundingboxMaxLat());
		assertThat(latLon.getLon()).isBetween(dispatcherProperties.getWorldBoundingboxMinLon(), dispatcherProperties.getWorldBoundingboxMaxLon());
	}

}