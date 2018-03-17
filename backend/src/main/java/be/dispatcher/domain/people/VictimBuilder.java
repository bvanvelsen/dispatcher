package be.dispatcher.domain.people;

public final class VictimBuilder {

	private Double health;
	private InjuryLevel injuryLevel;

	private VictimBuilder() {
	}

	public static VictimBuilder aVictim() {
		return new VictimBuilder();
	}

	public VictimBuilder withHealth(Double health) {
		this.health = health;
		return this;
	}

	public VictimBuilder withInjuryLevel(InjuryLevel injuryLevel) {
		this.injuryLevel = injuryLevel;
		return this;
	}

	public Victim build() {
		return new Victim(health, injuryLevel);
	}
}
