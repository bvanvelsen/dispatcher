package be.dispatcher.domain.location.emergencybases;

import be.dispatcher.domain.location.Location;

public class FireDepartment implements Base {

	private Location location;
	private final String name;
	private final int id;

	public FireDepartment(int id, String name, Location location) {
		this.id = id;
		this.name = name;
		this.location = location;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getId() {
		return id;
	}
}
