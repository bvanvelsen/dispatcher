package be.dispatcher.managers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.incident.IncidentBuilder;
import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.location.LocationBuilder;
import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.people.Victim;
import be.dispatcher.domain.people.VictimBuilder;
import be.dispatcher.domain.vehicle.Ambulance;
import be.dispatcher.managers.incidentscene.IncidentSceneManager;

@RunWith(MockitoJUnitRunner.class)
public class IncidentSceneManagerTest {

	@InjectMocks
	private IncidentSceneManager incidentSceneManager;

	@Mock
	private Base base;

	private Ambulance ambulance;

	private Incident incident;

	private Victim victim;
	private Victim victim2;

	@Before
	public void setup() {
		Location location = LocationBuilder.aLocation()
				.withX(0)
				.withY(0)
				.build();
		when(base.getLocation()).thenReturn(location);

		ambulance = new Ambulance(1, "", 1, 1, 1.0, base);
		victim = VictimBuilder.aVictim().withHealth(5d).build();
		victim2 = VictimBuilder.aVictim().withHealth(25d).build();
		incident = IncidentBuilder.anIncident()
				.withLocation(location)
				.addVictim(VictimBuilder.aVictim().withHealth(30d).build())
				.addVictim(victim)
				.addVictim(victim2)
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
	public void expectVictimReadyForTransportRemovesItFromToBeTreatedVictimList() {
		Victim victim = incidentSceneManager.getVictimFor(ambulance);
		assertThat(victim).isEqualTo(this.victim);

		incidentSceneManager.notifyVictimReadyForTransport(ambulance);
		Victim victim2 = incidentSceneManager.getVictimFor(ambulance);

		assertThat(victim2).isEqualTo(this.victim2);
		assertThat(victim).isNotEqualTo(victim2);
	}
}