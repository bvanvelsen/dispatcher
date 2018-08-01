package be.dispatcher.domain.people;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public class Victim implements Person {

	private final InjuryLevel injuryLevel;
	private int healCountdown;
	private int trappedCountdown;
	private boolean trapped;

	public Victim(InjuryLevel injuryLevel, int healCountdown) {
		this.injuryLevel = injuryLevel;
		this.healCountdown = healCountdown;
		trapped = false;
	}

	public Victim(InjuryLevel injuryLevel, int healCountdown, int trappedCountdown) {
		this(injuryLevel, healCountdown);
		this.trappedCountdown = trappedCountdown;
		trapped = true;
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

	public boolean heal(int healAmount) {
		if (!isTrapped()) {
			healCountdown -= healAmount;
			healCountdown = healCountdown <= 0 ? 0 : healCountdown;
		}
		return isTransportable();
	}

	public boolean extract(int trappedAmount) {
		trappedCountdown -= trappedAmount;
		trappedCountdown = trappedCountdown <= 0 ? 0 : trappedCountdown;
		if (trappedCountdown == 0) {
			trapped = false;
			return true;
		}
		return false;
	}

	@Override
	public boolean isTrapped() {
		return trapped;
	}

	public int getTrappedCountdown() {
		return trappedCountdown;
	}
}
