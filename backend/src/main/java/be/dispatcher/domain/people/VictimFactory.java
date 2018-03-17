package be.dispatcher.domain.people;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VictimFactory {

	@Value("${victim.maxhealth}")
	private Double maxHealth;

	@Value("${victim.lightwounded.minhealth}")
	private Double lightWoundedMinhealth;

	private double getBoundedNumber(Double min, Double max) {
		return ThreadLocalRandom.current().doubles(min, max).findFirst().getAsDouble();
	}

	public Victim createLightWounedVictim() {
		return VictimBuilder.aVictim()
				.withHealth(getBoundedNumber(lightWoundedMinhealth, maxHealth))
				.withInjuryLevel(InjuryLevel.MINOR)
				.build();
	}
}
