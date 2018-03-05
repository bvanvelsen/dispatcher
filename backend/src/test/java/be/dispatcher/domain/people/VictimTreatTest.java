package be.dispatcher.domain.people;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import be.dispatcher.DispatcherSpringJunit4Test;

public class VictimTreatTest extends DispatcherSpringJunit4Test {

	private static final double HEALTH_GAIN = 0.015;
	private static final double INITIAL_HEALTH = 80.0;
	private Victim victim;

	@Before
	public void setup() {
		victim = VictimBuilder.aVictim()
				.withInjuryLevel(InjuryLevel.MINOR)
				.withHealth(INITIAL_HEALTH)
				.build();
	}

	@Test
	public void expectVictimGainsHealthWhenTreated() {
		victim.treat(HEALTH_GAIN);
		assertThat(victim.getHealth()).isEqualTo(INITIAL_HEALTH + HEALTH_GAIN);
	}

	@Test
	public void expectVictimCannotGetMoreHealthThenMaxOfInjuryLevel() {
		victim = VictimBuilder.aVictim()
				.withInjuryLevel(InjuryLevel.MEDIOCRE)
				.withHealth(60.0)
				.build();
		victim.treat(200);
		assertThat(victim.getHealth()).isEqualTo(InjuryLevel.MEDIOCRE.getMaxHealth());
	}

	@Test
	public void expectVictimBecomsTransportableWhenMaxOfMinorInjuryLevelReached() {
		victim = VictimBuilder.aVictim()
				.withInjuryLevel(InjuryLevel.MINOR)
				.withHealth(800.0)
				.build();
		assertThat(victim.isTransportable()).isFalse();

		victim.treat(200);

		assertThat(victim.getHealth()).isEqualTo(InjuryLevel.MINOR.getMaxHealth());
		assertThat(victim.isTransportable()).isTrue();
	}

	@Test
	public void expectVictimBecomsTransportableWhenMaxOfMediocreInjuryLevelReached() {
		victim = VictimBuilder.aVictim()
				.withInjuryLevel(InjuryLevel.MEDIOCRE)
				.withHealth(60.0)
				.build();
		assertThat(victim.isTransportable()).isFalse();

		victim.treat(200);

		assertThat(victim.getHealth()).isEqualTo(InjuryLevel.MEDIOCRE.getMaxHealth());
		assertThat(victim.isTransportable()).isTrue();
	}

	@Test
	public void expectVictimBecomsTransportableWhenMaxOfSevereInjuryLevelReached() {
		victim = VictimBuilder.aVictim()
				.withInjuryLevel(InjuryLevel.SEVERE)
				.withHealth(40.0)
				.build();
		assertThat(victim.isTransportable()).isFalse();

		victim.treat(200);

		assertThat(victim.getHealth()).isEqualTo(InjuryLevel.SEVERE.getMaxHealth());
		assertThat(victim.isTransportable()).isTrue();
	}

	@Test
	public void expectVictimBecomsTransportableWhenMaxOfDeadInjuryLevelReached() {
		victim = VictimBuilder.aVictim()
				.withInjuryLevel(InjuryLevel.DEAD)
				.withHealth(-5.0)
				.build();
		assertThat(victim.isTransportable()).isFalse();

		victim.treat(200);

		assertThat(victim.getHealth()).isEqualTo(InjuryLevel.SEVERE.getMaxHealth());
		assertThat(victim.isTransportable()).isTrue();
	}

}