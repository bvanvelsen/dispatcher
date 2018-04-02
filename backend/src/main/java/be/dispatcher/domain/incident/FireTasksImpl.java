package be.dispatcher.domain.incident;

public class FireTasksImpl implements FireTasks {

	private int fireCountdown;
	private int technicalCountdown;

	public FireTasksImpl(int fireCountdown, int technicalCountdown) {
		this.fireCountdown = fireCountdown;
		this.technicalCountdown = technicalCountdown;
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
		return technicalCountdown == 0 && fireCountdown == 0;
	}

	public int getFireCountdown() {
		return fireCountdown;
	}

	public int getTechnicalCountdown() {
		return technicalCountdown;
	}
}
