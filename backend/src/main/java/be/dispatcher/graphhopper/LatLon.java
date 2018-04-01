package be.dispatcher.graphhopper;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class LatLon {

	private final Double lat;
	private final Double lon;
	private String display_name;

	public LatLon(Double lat, Double lon, String display_name) {
		this.lat = lat;
		this.lon = lon;
		this.display_name = display_name;
	}

	public LatLon(Double lat, Double lon) {
		this.lat = lat;
		this.lon = lon;
	}

	public Double getLat() {
		return lat;
	}

	public Double getLon() {
		return lon;
	}

	public String getDisplay_name() {
		return display_name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		LatLon latLon = (LatLon) o;

		return new EqualsBuilder()
				.append(lat, latLon.lat)
				.append(lon, latLon.lon)
				.append(display_name, latLon.display_name)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(lat)
				.append(lon)
				.append(display_name)
				.toHashCode();
	}

	public String toPoint() {
		return lat.toString() + "," + lon.toString();
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("LatLon{");
		sb.append("lat=").append(lat);
		sb.append(", lon=").append(lon);
		sb.append(", display_name='").append(display_name).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
