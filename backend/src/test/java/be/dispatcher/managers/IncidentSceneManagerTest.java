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
import be.dispatcher.managers.incidentscene.IncidentSceneManager;

@RunWith(MockitoJUnitRunner.class)
public class IncidentSceneManagerTest {

	@InjectMocks
	private IncidentSceneManager incidentSceneManager;

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
		ambulance = new Ambulance(location, 1,1,1.0);
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