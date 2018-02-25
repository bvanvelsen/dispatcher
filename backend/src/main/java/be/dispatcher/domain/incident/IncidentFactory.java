package be.dispatcher.domain.incident;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.dispatcher.DispatcherProperties;
import be.dispatcher.domain.DispatchFactory;
import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.location.LocationFactory;
import be.dispatcher.repositories.IncidentRepository;

@Component
public class IncidentFactory extends DispatchFactory {

	@Autowired
	private IncidentRepository incidentRepository;

	@Autowired
	private LocationFactory locationFactory;

	public Incident createIncident() {
		Incident incident = IncidentBuilder.anIncident()
				.withLocation(locationFactory.createIncidentLocation())
				.build();
		incidentRepository.addIncidentToRepository(incident);
		super.addToWorld(incident);
		return incident;
	}
}
