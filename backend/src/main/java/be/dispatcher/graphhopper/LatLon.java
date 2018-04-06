package be.dispatcher.graphhopper;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import be.dispatcher.graphhopper.reverse.geocode.Address;

public class LatLon {

	private final Double lat;
	private final Double lon;
	private Address address;

	public LatLon(Double lat, Double lon, Address address) {
		this.lat = lat;
		this.lon = lon;
		this.address = address;
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
		if (address != null) {
			return address.getDisplayName();
		}
		return null;
	}

	public Address getAddress() {
		return address;
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
				.append(address, latLon.address)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(lat)
				.append(lon)
				.append(address)
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
		sb.append(", address=").append(address);
		sb.append('}');
		return sb.toString();
	}
}
