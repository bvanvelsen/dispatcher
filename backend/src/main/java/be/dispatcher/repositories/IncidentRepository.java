package be.dispatcher.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import be.dispatcher.domain.incident.Incident;

@Component
public class IncidentRepository {

	private List<Incident> incidents = new ArrayList<>();

	public void addIncidentToRepository(Incident incident) {
		incidents.add(incident);
	}

	public List<Incident> getIncidents() {
		return incidents;
	}

	public Incident getIncidentById(String id) {
		return incidents.stream ()
				.filter(incident -> incident.getId().equals(id))
				.findFirst()
				.get();
	}
}
