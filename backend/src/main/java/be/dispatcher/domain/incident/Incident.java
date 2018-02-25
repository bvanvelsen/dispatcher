package be.dispatcher.domain.incident;

import java.util.List;
import java.util.UUID;

import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.Ticks;
import be.dispatcher.domain.people.Victim;

public class Incident implements Ticks {

	private final String id;
	private Location location;
	private List<Victim> victims;


	public Incident(Location location, List<Victim> victims) {
		this.id = UUID.randomUUID().toString();
		this.location = location;
		this.victims = victims;
	}

	@Override
	public void tick() {
		System.out.println(this);
	}

	public String getId() {
		return id;
	}

	public Location getLocation() {
		return location;
	}

	@Override
	public String toString() {
		return new org.apache.commons.lang3.builder.ToStringBuilder(this)
				.append("id", id)
				.append("location", location)
				.toString();
	}
}
