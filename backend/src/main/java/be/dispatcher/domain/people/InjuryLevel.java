package be.dispatcher.domain.people;

public enum InjuryLevel {

	MINOR(99, 0.05), MEDIOCRE(70, 0.1), SEVERE(50, 0.5), DEAD(0, 5);

	private double maxHealth;
	private double healthLossPerTick;

	InjuryLevel(double maxHealth, double healthLossPerTick) {
		this.maxHealth = maxHealth;
		this.healthLossPerTick = healthLossPerTick;
	}

	public double getMaxHealth() {
		return maxHealth;
	}

	public double getHealthLossPerTick() {
		return healthLossPerTick;
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
