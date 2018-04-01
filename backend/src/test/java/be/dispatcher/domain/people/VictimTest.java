package be.dispatcher.domain.people;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class VictimTest {

	private static final int HEAL_COUNTDOWN = 10;
	private Victim victim;

	@Before
	public void setup() {
		victim = new Victim(InjuryLevel.MINOR, HEAL_COUNTDOWN);
	}

	@Test
	public void expectVictimNotTransportableWhenNotHealed() {
		assertThat(victim.isTransportable()).isFalse();
	}

	@Test
	public void expectVictimTransportableWhenHealed() {
		victim.heal(HEAL_COUNTDOWN + 1);
		assertThat(victim.isTransportable()).isTrue();
	}

	@Test
	public void expectVictimGetsHealed() {
		assertThat(victim.getHealCountdown()).isEqualTo(HEAL_COUNTDOWN);
		victim.heal(5);
		assertThat(victim.getHealCountdown()).isEqualTo(5);
	}

	@Test
	public void expectVictimHealthCannotGetBelowZero() {
		victim.heal(1000);
		assertThat(victim.getHealCountdown()).isZero();
	}

}