package be.dispatcher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DispatcherProperties {

	@Value("${world.boundingbox.min.lat}")
	private Double worldBoundingboxMinLat;

	@Value("${world.boundingbox.max.lat}")
	private Double worldBoundingboxMaxLat;

	@Value("${world.boundingbox.min.lon}")
	private Double worldBoundingboxMinLon;

	@Value("${world.boundingbox.max.lon}")
	private Double worldBoundingboxMaxLon;

	public Double getWorldBoundingboxMinLat() {
		return worldBoundingboxMinLat;
	}

	public Double getWorldBoundingboxMaxLat() {
		return worldBoundingboxMaxLat;
	}

	public Double getWorldBoundingboxMinLon() {
		return worldBoundingboxMinLon;
	}

	public Double getWorldBoundingboxMaxLon() {
		return worldBoundingboxMaxLon;
	}
}
