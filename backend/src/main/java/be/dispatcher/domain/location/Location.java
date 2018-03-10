package be.dispatcher.domain.location;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Location {

	private int x;
	private int y;

	Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("x", x)
				.append("y", y)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Location location = (Location) o;

		return new EqualsBuilder()
				.append(x, location.x)
				.append(y, location.y)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(x)
				.append(y)
				.toHashCode();
	}
}
