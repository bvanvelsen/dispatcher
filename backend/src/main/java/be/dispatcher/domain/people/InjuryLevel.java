package be.dispatcher.domain.people;

public enum InjuryLevel {

	MINOR(99), MEDIOCRE(70), SEVERE(50), DEAD(0);

	private double maxHealth;

	InjuryLevel(double maxHealth) {
		this.maxHealth = maxHealth;
	}

	public double getMaxHealth() {
		return maxHealth;
	}

	public static InjuryLevel getNextLowerInjuryLevel(InjuryLevel injuryLevel) {
		switch (injuryLevel) {
		case MINOR:
			return MEDIOCRE;
		case MEDIOCRE:
			return SEVERE;
		case SEVERE:
			return DEAD;
		}
		return DEAD;
	}
}
