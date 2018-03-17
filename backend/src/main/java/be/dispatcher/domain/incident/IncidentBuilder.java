package be.dispatcher.domain.incident;

import java.util.ArrayList;
import java.util.List;

import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.people.Victim;

public final class IncidentBuilder {
	private Location location;
	private List<Victim> victims = new ArrayList<>();
	private List<Victim> stabilizedVictims = new ArrayList<>();

	private IncidentBuilder() {
	}

	public static IncidentBuilder anIncident() {
		return new IncidentBuilder();
	}

	public IncidentBuilder withLocation(Location location) {
		this.location = location;
		return this;
	}

	public IncidentBuilder addVictim(Victim victim) {
		this.victims.add(victim);
		return this;
	}

	public IncidentBuilder addStabilizedVictim(Victim victim) {
		this.stabilizedVictims.add(victim);
		return this;
	}

	public Incident build() {
		Incident incident = new Incident(location, new MedicalTasksImpl(victims));
		incident.getMedicalTasks().getStabilizedVictims().addAll(stabilizedVictims);
		return incident;
	}
}
