package be.dispatcher.domain.location.emergencybases;

import static be.dispatcher.domain.location.emergencybases.BaseType.FIRE_DEPARTMENT;

import be.dispatcher.graphhopper.LatLon;

public class FireDepartment implements Base {

	private LatLon location;
	private final String name;
	private final int id;

	public FireDepartment(int id, String name, LatLon location) {
		this.id = id;
		this.name = name;
		this.location = location;
	}

	@Override
	public LatLon getLocation() {
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

	@Override
	public BaseType getBaseType() {
		return FIRE_DEPARTMENT;
	}

	@Override
	public String toString() {
		return name;
	}
}
