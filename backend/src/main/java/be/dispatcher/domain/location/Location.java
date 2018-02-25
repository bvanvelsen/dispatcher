package be.dispatcher.domain.location;

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
}
