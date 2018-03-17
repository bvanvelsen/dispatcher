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
import be.dispatcher.managers.incidentscene.IncidentSceneMedicalTasksManager;

@RunWith(MockitoJUnitRunner.class)
public class IncidentSceneMedicalTasksManagerTest {

	@InjectMocks
	private IncidentSceneMedicalTasksManager incidentSceneMedicalTasksManager;

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
		assertThat(incidentSceneMedicalTasksManager.getVictimFor(ambulance)).isEqualTo(victim);
	}

	@Test
	public void expectMulitpleCallsToGetVictimForReturnTheSameVictim() {
		assertThat(incidentSceneMedicalTasksManager.getVictimFor(ambulance)).isEqualTo(victim);
		assertThat(incidentSceneMedicalTasksManager.getVictimFor(ambulance)).isEqualTo(victim);
	}

	@Test
	public void expectVictimReadyForTransportRemovesItFromToBeTreatedVictimList() {
		Victim victim = incidentSceneMedicalTasksManager.getVictimFor(ambulance);
		assertThat(victim).isEqualTo(this.victim);

		incidentSceneMedicalTasksManager.notifyVictimReadyForTransport(ambulance);
		Victim victim2 = incidentSceneMedicalTasksManager.getVictimFor(ambulance);

		assertThat(victim2).isEqualTo(this.victim2);
		assertThat(victim).isNotEqualTo(victim2);
	}
}