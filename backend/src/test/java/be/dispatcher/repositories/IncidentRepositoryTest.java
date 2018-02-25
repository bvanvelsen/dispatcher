package be.dispatcher.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.dispatcher.DispatcherSpringJunit4Test;
import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.incident.IncidentFactory;

public class IncidentRepositoryTest extends DispatcherSpringJunit4Test {

	@Autowired
	private IncidentRepository incidentRepository;

	@Autowired
	private IncidentFactory incidentFactory;

	private Incident incident;

	@Before
	public void setup() {
		incident = incidentFactory.createIncident();
	}

	@Test
	public void expectRepositoryContainsIncident() {
		assertThat(incidentRepository.getIncidents()).containsOnly(incident);
	}

	@Test
	public void expectIncidentCanBeQueriedById() {
		assertThat(incidentRepository.getIncidentById(incident.getId())).isEqualTo(incident);
	}
}