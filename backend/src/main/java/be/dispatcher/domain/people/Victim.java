package be.dispatcher.domain.people;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import be.dispatcher.domain.vehicle.Vehicle;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public class Victim implements Person {

	private final double maxHealth = 100;
	private Double health;
	private Double healthLossPerTick;
	private InjuryLevel injuryLevel;
	private Vehicle vehicle;
	private boolean transportable = false;
	private boolean needsDoctor;

	public Victim(Double health, InjuryLevel injuryLevel) {
		this.health = health;
		this.healthLossPerTick = injuryLevel.getHealthLossPerTick();
		this.injuryLevel = injuryLevel;
		needsDoctor = InjuryLevel.DEAD.equals(injuryLevel);
	}

	public double getMaxHealth() {
		return maxHealth;
	}

	public Double getHealth() {
		return health;
	}

	public Double getHealthLossPerTick() {
		return healthLossPerTick;
	}

	public InjuryLevel getInjuryLevel() {
		return injuryLevel;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	@Override
	public void tick() {
		health -= healthLossPerTick;
		setInjuryLevelBasedOnCurrentHealth();
	}

	private void setInjuryLevelBasedOnCurrentHealth() {
		switch (injuryLevel) {
		case MINOR:
			injuryLevel = health < InjuryLevel.MEDIOCRE.getMaxHealth() ? InjuryLevel.getNextLowerInjuryLevel(injuryLevel) : InjuryLevel.MINOR;
			break;
		case MEDIOCRE:
			injuryLevel = health < InjuryLevel.SEVERE.getMaxHealth() ? InjuryLevel.getNextLowerInjuryLevel(injuryLevel) : InjuryLevel.MEDIOCRE;
			break;
		case SEVERE:
			injuryLevel = health < InjuryLevel.DEAD.getMaxHealth() ? InjuryLevel.getNextLowerInjuryLevel(injuryLevel) : InjuryLevel.SEVERE;
			break;
		}
			healthLossPerTick = injuryLevel.getHealthLossPerTick();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("maxHealth", maxHealth)
				.append("health", health)
				.append("healthLossPerTick", healthLossPerTick)
				.append("injuryLevel", injuryLevel)
				.toString();
	}

	public void treat(double healthGain) {
		if (!needsDoctor) {
			health += healthGain;
			setHealthToMaxOfInjuryLevelIfCurrentHealthOverInjuryLevelMax();
		}
	}

	private void setHealthToMaxOfInjuryLevelIfCurrentHealthOverInjuryLevelMax() {
		if (health >= injuryLevel.getMaxHealth()) {
			victimFullyTreatedAndMakeTransportable();
		}
	}

	private void victimFullyTreatedAndMakeTransportable() {
		healthLossPerTick = 0.0;
		health = injuryLevel.getMaxHealth();
		transportable = true;
	}

	public boolean isTransportable() {
		return transportable;
	}
}
