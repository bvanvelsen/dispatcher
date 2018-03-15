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
	private final String shortDescription;
	private Location location;
	private List<Victim> unstabilizedVictims;
	private List<Victim> stabilizedVictims = new ArrayList<>();


	private boolean markIncidentFinished = false;

	public Incident(Location location, List<Victim> unstabilizedVictims) {
		this.id = UUID.randomUUID().toString();
		this.location = location;
		this.unstabilizedVictims = unstabilizedVictims;
		shortDescription = String.format("Verkeersongeval op x:%d y:%d",location.getX(), location.getY());
	}

	@Override
	public void tick() {
		unstabilizedVictims.forEach(victim -> victim.tick());
		checkIncidentFinished();
	}

	private void checkIncidentFinished() {
		markIncidentFinished = unstabilizedVictims.isEmpty() && stabilizedVictims.isEmpty();
	}

	public String getId() {
		return id;
	}

	public Location getLocation() {
		return location;
	}

	public String getShortDescription() {
		return shortDescription;
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

	public boolean isMarkIncidentFinished() {
		return markIncidentFinished;
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
