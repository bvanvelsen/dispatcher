package be.dispatcher.domain.people;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public class TrappedVictim extends Victim {

	private int trappedCountdown;
	private boolean trapped;

	public TrappedVictim(InjuryLevel injuryLevel, int healCountdown, int trappedCountdown) {
		super(injuryLevel, healCountdown);
		this.trappedCountdown = trappedCountdown;
		trapped = true;
	}

	public boolean heal(int healAmount) {
		if (!isTrapped()) {
			return super.heal(healAmount);
		}
		return false;
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

	@Override
	public int getTrappedCountdown() {
		return trappedCountdown;
	}
}
