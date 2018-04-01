package be.dispatcher.graphhopper.reverse.geocode;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ReverseGeocode {

	private String place_id;
	private String osm_type;
	private Double lat;
	private Double lon;
	private String display_name;

	public String getPlace_id() {
		return place_id;
	}

	public String getOsm_type() {
		return osm_type;
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
	public String toString() {
		return new ToStringBuilder(this)
				.append("place_id", place_id)
				.append("osm_type", osm_type)
				.append("lat", lat)
				.append("lon", lon)
				.append("display_name", display_name)
				.toString();
	}
}
