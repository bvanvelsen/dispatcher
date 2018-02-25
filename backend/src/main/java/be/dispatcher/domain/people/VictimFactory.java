package be.dispatcher.domain.people;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VictimFactory {

	@Value("${victim.maxhealth}")
	private Long maxHealth;


	@Value("${victim.lightwounded.minhealth}")
	private Long lightWoundedMinhealth;

	public Victim createLightWounedVictim() {
		Victim victim = VictimBuilder.aVictim()
				.withHealth(getBoundedNumber(lightWoundedMinhealth, maxHealth))
				.build();
	}

	private long getBoundedNumber(long min, long max) {
		return ThreadLocalRandom.current().longs(min,max).findFirst().getAsLong();
	}
}
