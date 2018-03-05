package be.dispatcher.managers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.incident.IncidentBuilder;
import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.location.LocationBuilder;
import be.dispatcher.domain.people.Victim;
import be.dispatcher.domain.people.VictimBuilder;
import be.dispatcher.domain.vehicle.Ambulance;

@RunWith(MockitoJUnitRunner.class)
public class IncidentSceneManagerTest {

	@InjectMocks
	private IncidentSceneManager incidentSceneManager;

	private Ambulance ambulance;

	private Incident incident;

	private Victim victim;

	@Before
	public void setup() {
		Location location = LocationBuilder.aLocation()
				.withX(0)
				.withY(0)
				.build();
		ambulance = new Ambulance(location, 1,1,1.0);
		victim = VictimBuilder.aVictim().withHealth(5d).build();
		incident = IncidentBuilder.anIncident()
				.withLocation(location)
				.addVictim(VictimBuilder.aVictim().withHealth(30d).build())
				.addVictim(victim)
				.addVictim(VictimBuilder.aVictim().withHealth(25d).build())
				.build();
		ambulance.setIncident(incident);
	}

	@Test
	public void expectVictimWithLowestHealthFoundOnIncidentLocation() {
		assertThat(incidentSceneManager.getVictimFor(ambulance)).isEqualTo(victim);
	}

	@Test
	public void expectMulitpleCallsToGetVictimForReturnTheSameVictim() {
		assertThat(incidentSceneManager.getVictimFor(ambulance)).isEqualTo(victim);
		assertThat(incidentSceneManager.getVictimFor(ambulance)).isEqualTo(victim);
	}

	@Test
	public void expectVictimReadyForTransportRemocesItFromToBeTreatedVictimList() {
		Victim victimFor = incidentSceneManager.getVictimFor(ambulance);
		assertThat(victimFor).isNotNull();

		incidentSceneManager.notifyVictimReadyForTransport(ambulance);
		victimFor = incidentSceneManager.getVictimFor(ambulance);

		assertThat(victimFor).isNull();
	}
}