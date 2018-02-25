package be.dispatcher.domain.people;

public class Victim implements Person {

	private final long maxHealth = 100;
	private Long health;
	private Long healthLossPerTick;
	private Long transportableHealthTreshold;//indicates the health needed before victim is transpostable
	private Integer minimumWorkTimeInSecondsBeforeTransportable;//indicates how long paramedics minimally need to stabilize/investige this person before they can/will transport this person

	public Victim(long health, long healthLossPerTick, long transportableHealthTreshold, int minimumWorkTimeInSecondsBeforeTransportable) {
		this.health = health;
		this.healthLossPerTick = healthLossPerTick;
		this.transportableHealthTreshold = transportableHealthTreshold;
		this.minimumWorkTimeInSecondsBeforeTransportable = minimumWorkTimeInSecondsBeforeTransportable;
	}

	public long getMaxHealth() {
		return maxHealth;
	}

	public Long getHealth() {
		return health;
	}

	public Long getHealthLossPerTick() {
		return healthLossPerTick;
	}

	public Long getTransportableHealthTreshold() {
		return transportableHealthTreshold;
	}

	public Integer getMinimumWorkTimeInSecondsBeforeTransportable() {
		return minimumWorkTimeInSecondsBeforeTransportable;
	}

	@Override
	public void tick() {

	}
}
