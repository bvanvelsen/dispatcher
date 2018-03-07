package be.dispatcher.domain.incident;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import be.dispatcher.domain.people.Victim;
import be.dispatcher.domain.people.VictimBuilder;

public class IncidentNotifyVictimStabilizedTest {

	private Incident incident;
	private Victim victim;

	@Before
	public void setup() {
		victim = VictimBuilder.aVictim().build();
		incident = IncidentBuilder.anIncident()
				.addVictim(victim)
				.build();
	}

	@Test
	public void expectStabilizedVictimIsRemovedFromUnstableVictimListAndAddedToStableVictimList() {
		assertThat(incident.getUnstabilizedVictims()).containsExactly(victim);

		incident.notifyVictimStabilized(victim);

		assertThat(incident.getUnstabilizedVictims()).doesNotContain(victim);
	}
}