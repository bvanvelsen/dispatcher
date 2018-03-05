package be.dispatcher.domain.people;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.dispatcher.DispatcherSpringJunit4Test;

public class VictimFactoryLightWoundedVictimTest extends DispatcherSpringJunit4Test {

	@Autowired
	private VictimFactory victimFactory;

	@Test
	public void expectLightWoundedVictimCanBeCreated() {
		Victim lightWounedVictim = victimFactory.createLightWounedVictim();
		assertThat(lightWounedVictim).isNotNull();
	}

	@Test
	public void expectLightWoundedVictimHasPropertiesSet() {
		Victim lightWounedVictim = victimFactory.createLightWounedVictim();
		assertThat(lightWounedVictim.getMaxHealth()).isEqualTo(100);
		assertThat(lightWounedVictim.getHealth()).isBetween(70.0, 100.0);
		assertThat(lightWounedVictim.getHealthLossPerTick()).isEqualTo(0.01);
		System.out.println(lightWounedVictim);
	}

}