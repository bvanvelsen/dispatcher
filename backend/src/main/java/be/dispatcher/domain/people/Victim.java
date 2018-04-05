package be.dispatcher.domain.people;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public class Victim implements Person {

	private final InjuryLevel injuryLevel;
	private int healCountdown;

	public Victim(InjuryLevel injuryLevel, int healCountdown) {
		this.injuryLevel = injuryLevel;
		this.healCountdown = healCountdown;
	}

	public InjuryLevel getInjuryLevel() {
		return injuryLevel;
	}

	public int getHealCountdown() {
		return healCountdown;
	}

	@Override
	public boolean isTransportable() {
		return healCountdown == 0;
	}

	@Override
	public boolean isTrapped() {
		return false;
	}

	public boolean heal(int healAmount) {
		healCountdown -=healAmount;
		healCountdown = healCountdown <= 0 ? 0 : healCountdown;
		return isTransportable();
	}

	public int getTrappedCountdown() {
		return 0;
	}
}
