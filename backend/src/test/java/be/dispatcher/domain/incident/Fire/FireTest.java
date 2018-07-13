package be.dispatcher.domain.incident.Fire;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class FireTest {

	private static final int AMOUNT = 10;
	private static final int INCREASE_PER_TICK = 20;
	private static final int EXTINGUISH_AMOUNT = 5;
	private Fire fire;

	@Before
	public void setup() {
		fire = new Fire(AMOUNT, INCREASE_PER_TICK);
	}

	@Test
	public void expectBurnIncreasesFire() {
		fire.burn();

		assertThat(fire.getAmount()).isEqualTo(AMOUNT + INCREASE_PER_TICK);
	}

	@Test
	public void expectExtinguishDecreasesAmount() {
		fire.extinguish(EXTINGUISH_AMOUNT);

		assertThat(fire.getAmount()).isEqualTo(AMOUNT - EXTINGUISH_AMOUNT);
	}

	@Test
	public void expectAmountNeverLessThenZero() {
		fire.extinguish(100);

		assertThat(fire.getAmount()).isZero();
	}

	@Test
	public void expectFireIsBurning() {
		assertThat(fire.isBurning()).isTrue();
	}

	@Test
	public void expectFireNoLongerBurningIfAmountIsZero() {
		fire.extinguish(50);

		assertThat(fire.isBurning()).isFalse();
	}

}