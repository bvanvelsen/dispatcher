package be.dispatcher.graphhopper;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class LatLonAtTime {

	private LatLon latLon;
	private LocalDateTime localDateTime;

	public LatLonAtTime(LatLon latLon, LocalDateTime localDateTime) {
		this.latLon = latLon;
		this.localDateTime = localDateTime;
	}

	public LatLon getLatLon() {
		return latLon;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("LatLonAtTime{");
		sb.append("latLon=").append(latLon);
		sb.append(", localDateTime=").append(localDateTime);
		sb.append('}');
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		LatLonAtTime that = (LatLonAtTime) o;

		return new EqualsBuilder()
				.append(latLon, that.latLon)
				.append(localDateTime, that.localDateTime)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(latLon)
				.append(localDateTime)
				.toHashCode();
	}
}
