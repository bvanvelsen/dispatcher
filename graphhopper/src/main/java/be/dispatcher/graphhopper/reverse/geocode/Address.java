package be.dispatcher.graphhopper.reverse.geocode;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

	@SerializedName("bus_stop")
	@Expose
	private String busStop;
	@SerializedName("road")
	@Expose
	private String road;
	@SerializedName("city_district")
	@Expose
	private String cityDistrict;
	@SerializedName("city")
	@Expose
	private String city;
	@SerializedName("town")
	@Expose
	private String town;
	@SerializedName("retail")
	@Expose
	private String retail;
	@SerializedName("village")
	@Expose
	private String village;
	@SerializedName("county")
	@Expose
	private String county;
	@SerializedName("state")
	@Expose
	private String state;
	@SerializedName("postcode")
	@Expose
	private String postcode;
	@SerializedName("country")
	@Expose
	private String country;
	@SerializedName("country_code")
	@Expose
	private String countryCode;
	@SerializedName("suburb")
	@Expose
	private String suburb;



	public String getBusStop() {
		return busStop;
	}

	public void setBusStop(String busStop) {
		this.busStop = busStop;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getCityDistrict() {
		return cityDistrict;
	}

	public void setCityDistrict(String cityDistrict) {
		this.cityDistrict = cityDistrict;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getRetail() {
		return retail;
	}

	public void setRetail(String retail) {
		this.retail = retail;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Address address = (Address) o;

		return new EqualsBuilder()
				.append(busStop, address.busStop)
				.append(road, address.road)
				.append(cityDistrict, address.cityDistrict)
				.append(retail, address.retail)
				.append(town, address.town)
				.append(county, address.county)
				.append(state, address.state)
				.append(postcode, address.postcode)
				.append(country, address.country)
				.append(countryCode, address.countryCode)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(busStop)
				.append(road)
				.append(cityDistrict)
				.append(town)
				.append(retail)
				.append(county)
				.append(state)
				.append(postcode)
				.append(country)
				.append(countryCode)
				.toHashCode();
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Address{");
		sb.append("busStop='").append(busStop).append('\'');
		sb.append(", road='").append(road).append('\'');
		sb.append(", cityDistrict='").append(cityDistrict).append('\'');
		sb.append(", town='").append(town).append('\'');
		sb.append(", county='").append(county).append('\'');
		sb.append(", state='").append(state).append('\'');
		sb.append(", postcode='").append(postcode).append('\'');
		sb.append(", country='").append(country).append('\'');
		sb.append(", countryCode='").append(countryCode).append('\'');
		sb.append('}');
		return sb.toString();
	}

	public String getDisplayName() {
		String gemeente;
		if(town != null) {
			gemeente = town;
		} else if (city != null) {
			gemeente = city;
		} else if (retail != null) {
			gemeente = retail;
		}
		else {
			gemeente = village;
		}
		return gemeente + ": " + road;
	}
}
