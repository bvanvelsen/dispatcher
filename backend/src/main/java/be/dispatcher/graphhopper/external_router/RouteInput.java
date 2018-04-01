package be.dispatcher.graphhopper.external_router;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import be.dispatcher.graphhopper.LatLon;

public class RouteInput {

	private String speedprofile;
	private LatLon startLocation;
	private LatLon destinationLocation;

	public RouteInput(String speedprofile, LatLon startLocation, LatLon destinationLocation) {
		this.speedprofile = speedprofile;
		this.startLocation = startLocation;
		this.destinationLocation = destinationLocation;
	}

	public LatLon getStartLocation() {
		return startLocation;
	}

	public LatLon getDestinationLocation() {
		return destinationLocation;
	}

	public String getSpeedprofile() {
		return speedprofile;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		RouteInput routeInput = (RouteInput) o;

		return new EqualsBuilder()
				.append(startLocation, routeInput.startLocation)
				.append(destinationLocation, routeInput.destinationLocation)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(startLocation)
				.append(destinationLocation)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("startLocation", startLocation)
				.append("destinationLocation", destinationLocation)
				.toString();
	}

}
