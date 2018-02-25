package be.dispatcher.domain.people;

public final class VictimBuilder {

	private Long health;
	private Long healthLossPerTick;
	private Long transportableHealthTreshold;
	private Integer minimumWorkTimeInSecondsBeforeTransportable;

	private VictimBuilder() {
	}

	public static VictimBuilder aVictim() {
		return new VictimBuilder();
	}

	public VictimBuilder withHealth(Long health) {
		this.health = health;
		return this;
	}

	public VictimBuilder withHealthLossPerTick(Long healthLossPerTick) {
		this.healthLossPerTick = healthLossPerTick;
		return this;
	}
	public VictimBuilder withTransportableHealthTreshold(Long transportableHealthTreshold) {
		this.transportableHealthTreshold = transportableHealthTreshold;
		return this;
	}
	public VictimBuilder withMinimumWorkTimeInSecondsBeforeTransportable(Integer minimumWorkTimeInSecondsBeforeTransportable) {
		this.minimumWorkTimeInSecondsBeforeTransportable = minimumWorkTimeInSecondsBeforeTransportable;
		return this;
	}

	public Victim build() {
		return new Victim(health, healthLossPerTick, transportableHealthTreshold, minimumWorkTimeInSecondsBeforeTransportable);
	}
}
