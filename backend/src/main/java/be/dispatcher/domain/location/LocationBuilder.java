package be.dispatcher.domain.location;

public final class LocationBuilder {
	private int x;
	private int y;

	private LocationBuilder() {
	}

	public static LocationBuilder aLocation() {
		return new LocationBuilder();
	}

	public LocationBuilder withX(int x) {
		this.x = x;
		return this;
	}

	public LocationBuilder withY(int y) {
		this.y = y;
		return this;
	}

	public Location build() {
		return new Location(x, y);
	}
}
