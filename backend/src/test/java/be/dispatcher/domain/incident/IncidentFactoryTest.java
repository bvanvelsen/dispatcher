package be.dispatcher.domain.incident;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.location.LocationFactory;
import be.dispatcher.domain.people.VictimFactory;
import be.dispatcher.repositories.IncidentRepository;
import be.dispatcher.world.World;

@RunWith(MockitoJUnitRunner.class)
public class IncidentFactoryTest {

	@Mock
	private IncidentRepository incidentRepository;

	@Mock
	private World world;

	@Mock
	private LocationFactory locationFactory;

	@Mock
	private Location location;

	@Mock
	private VictimFactory victimFactory;

	@InjectMocks
	private IncidentFactory incidentFactory;

	private Incident incident;

	@Before
	public void setup() {
		incident = incidentFactory.createIncident();
	}

	@Test
	public void expectVehicleAddedToRepository() {
		verify(incidentRepository).addIncidentToRepository(incident);
	}

	@Test
	public void expectVehicleAddedToWorld() {
		verify(world).addObjectToWorld(incident);
	}

}