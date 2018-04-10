package be.dispatcher.domain.incident;

public class FireTasksImpl implements FireTasks {

	private int fireCountdown;
	private int technicalCountdown;
	private int fireIncreasePerTick;

	public FireTasksImpl(int fireCountdown, int technicalCountdown, int fireIncreasePerTick) {
		this.fireCountdown = fireCountdown;
		this.technicalCountdown = technicalCountdown;
		this.fireIncreasePerTick = fireIncreasePerTick;
	}

	@Override
	public void tick() {
		if (fireCountdown > 0) {
			fireCountdown += fireIncreasePerTick;
		}
	}

	@Override
	public void extinguishFire(int extinguishAmount) {
		fireCountdown -= extinguishAmount;
		fireCountdown = fireCountdown <= 0 ? 0 : fireCountdown;
	}

	@Override
	public void workOnTechnicalDetails(int technicalAmount) {
		technicalCountdown -= technicalAmount;
		technicalCountdown = technicalCountdown <= 0 ? 0 : technicalCountdown;
	}

	@Override
	public boolean allTasksCompleted() {
		if (fireCountdown == 0) {
			fireIncreasePerTick = 0;
		}
		return technicalCountdown == 0 && fireCountdown == 0;
	}

	public int getFireCountdown() {
		return fireCountdown;
	}

	public int getTechnicalCountdown() {
		return technicalCountdown;
	}
}
