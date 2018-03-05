package be.dispatcher.domain.incident;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import be.dispatcher.domain.Ticks;
import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.people.Victim;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
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
		victims.forEach(victim -> victim.tick());
	}

	public String getId() {
		return id;
	}

	public Location getLocation() {
		return location;
	}

	public List<Victim> getVictims() {
		return victims;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("location", location)
				.append("victims", victims)
				.toString();
	}
}
