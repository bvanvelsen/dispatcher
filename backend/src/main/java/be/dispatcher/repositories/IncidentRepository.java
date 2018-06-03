package be.dispatcher.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.dispatcher.domain.incident.Incident;
import be.dispatcher.world.World;

@Component
public class IncidentRepository {

	@Autowired
	private World world;

	private List<Incident> incidents = new ArrayList<>();

	public void addIncidentToRepository(Incident incident) {
		world.addIncidentToWorld(incident);
		incidents.add(incident);
	}

	public void removeIncidentFromRepository(Incident incident) {
		world.removeIncidentFromWorld(incident);
		incidents.remove(incident);
	}

	public List<Incident> getIncidents() {
		return incidents;
	}

	public Incident getIncidentById(int id) {
		return incidents.stream ()
				.filter(incident -> incident.getId() == id)
				.findFirst()
				.get();
	}

}
