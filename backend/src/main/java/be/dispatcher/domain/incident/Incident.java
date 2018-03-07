package be.dispatcher.domain.incident;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
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
	private List<Victim> unstabilizedVictims;
	private List<Victim> stabilizedVictims = new ArrayList<>();

	public Incident(Location location, List<Victim> unstabilizedVictims) {
		this.id = UUID.randomUUID().toString();
		this.location = location;
		this.unstabilizedVictims = unstabilizedVictims;
	}

	@Override
	public void tick() {
		unstabilizedVictims.forEach(victim -> victim.tick());
	}

	public String getId() {
		return id;
	}

	public Location getLocation() {
		return location;
	}

	public List<Victim> getUnstabilizedVictims() {
		return unstabilizedVictims;
	}

	public boolean hasStabilizedVictims() {
		return CollectionUtils.isNotEmpty(stabilizedVictims);
	}

	public List<Victim> getStabilizedVictims() {
		return stabilizedVictims;
	}

	public void notifyVictimStabilized(Victim victim) {
		unstabilizedVictims.remove(victim);
		stabilizedVictims.add(victim);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("location", location)
				.append("unstabilizedVictims", unstabilizedVictims)
				.append("stabilizedVictims", stabilizedVictims)
				.toString();
	}
}
