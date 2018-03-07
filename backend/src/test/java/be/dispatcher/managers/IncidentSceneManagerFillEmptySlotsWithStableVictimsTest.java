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
import be.dispatcher.domain.people.InjuryLevel;
import be.dispatcher.domain.people.Victim;
import be.dispatcher.domain.people.VictimBuilder;
import be.dispatcher.domain.vehicle.Ambulance;
import be.dispatcher.managers.incidentscene.IncidentSceneManager;

@RunWith(MockitoJUnitRunner.class)
public class IncidentSceneManagerFillEmptySlotsWithStableVictimsTest {

	@InjectMocks
	private IncidentSceneManager incidentSceneManager;

	private Ambulance ambulance;

	private Incident incident;

	private Victim victimMediocre1;
	private Victim victimMinor1;
	private Victim victimMinor2;

	@Before
	public void setup() {
		Location location = LocationBuilder.aLocation()
				.withX(0)
				.withY(0)
				.build();
		ambulance = new Ambulance(location, 2,1,1.0);
		victimMediocre1 = VictimBuilder.aVictim()
				.withInjuryLevel(InjuryLevel.MEDIOCRE)
				.withHealth(5d).build();
		victimMinor1 = VictimBuilder.aVictim()
				.withInjuryLevel(InjuryLevel.MINOR)
				.withHealth(5d).build();
		victimMinor2 = VictimBuilder.aVictim()
				.withInjuryLevel(InjuryLevel.MINOR)
				.withHealth(5d).build();
		incident = IncidentBuilder.anIncident()
				.withLocation(location)
				.addStabilizedVictim(victimMinor1)
				.addStabilizedVictim(victimMinor2)
				.addStabilizedVictim(victimMediocre1)
				.build();
		ambulance.setIncident(incident);
	}

	@Test
	public void expectStableVictimForAmbulanceFound() {
		incidentSceneManager.fillEmptySlotsWithStableVictims(ambulance);

		assertThat(ambulance.getLayingVictims()).containsExactly(victimMediocre1);
		assertThat(ambulance.getSittingVictims()).containsExactly(victimMinor1,victimMinor2);
	}

	@Test
	public void expectAmbulanceFull() {
		assertThat(ambulance.hasEmptySpaces()).isTrue();

		incidentSceneManager.fillEmptySlotsWithStableVictims(ambulance);

		assertThat(ambulance.hasEmptySpaces()).isFalse();
	}
}