package be.dispatcher.domain.incident.Fire;

public class FireTasksImpl implements FireTasks {

	private int technicalCountdown;
	private Fire fire;

	public FireTasksImpl(int fireCountdown, int technicalCountdown, int fireIncreasePerTick) {
		fire = new Fire(fireCountdown, fireIncreasePerTick);
		this.technicalCountdown = technicalCountdown;
	}

	@Override
	public void tick() {
		if (hasFire()) {
			fire.burn();
		}
	}

	@Override
	public void extinguishFire(int extinguishAmount) {
		if (hasFire()) {
			fire.extinguish(extinguishAmount);
			if (!fire.isBurning()) {
				fire = null;
			}
		}
	}

	@Override
	public boolean hasFire() {
		return fire != null;
	}

	@Override
	public void workOnTechnicalDetails(int technicalAmount) {
		technicalCountdown -= technicalAmount;
		technicalCountdown = technicalCountdown <= 0 ? 0 : technicalCountdown;
	}

	@Override
	public boolean hasTechnicalWork() {
		return technicalCountdown > 0;
	}

	@Override
	public boolean allTasksCompleted() {
		return technicalCountdown == 0 && fire == null;
	}

	public int getTechnicalCountdown() {
		return technicalCountdown;
	}
}
