package be.dispatcher.domain.people;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import be.dispatcher.DispatcherSpringJunit4Test;

public class VictimSetInjuryLevelBasedOnCurrentHealthTest extends DispatcherSpringJunit4Test {

	private Victim victim;

	@Test
	public void expectVictimInjuryLevelDroppedFromMinorToMediocre() {
		victim = VictimBuilder.aVictim()
				.withInjuryLevel(InjuryLevel.MINOR)
				.withHealth(InjuryLevel.MEDIOCRE.getMaxHealth())
				.withHealthLossPerTick(1.0)
				.build();

		victim.tick();

		assertThat(victim.getInjuryLevel()).isEqualTo(InjuryLevel.MEDIOCRE);
	}

	@Test
	public void expectVictimInjuryLevelDroppedFromMediocreToSevere() {
		victim = VictimBuilder.aVictim()
				.withInjuryLevel(InjuryLevel.MEDIOCRE)
				.withHealth(InjuryLevel.SEVERE.getMaxHealth())
				.withHealthLossPerTick(1.0)
				.build();

		victim.tick();

		assertThat(victim.getInjuryLevel()).isEqualTo(InjuryLevel.SEVERE);
	}

	@Test
	public void expectVictimInjuryLevelDroppedFromSevereToDead() {
		victim = VictimBuilder.aVictim()
				.withHealthLossPerTick(1.0)
				.withInjuryLevel(InjuryLevel.SEVERE)
				.withHealth(InjuryLevel.DEAD.getMaxHealth())
				.build();

		victim.tick();

		assertThat(victim.getInjuryLevel()).isEqualTo(InjuryLevel.DEAD);
	}

}