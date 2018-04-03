package be.dispatcher.domain.people;

public class Criminal implements Person{

	private int arrestCountdown;

	public Criminal(int arrestCountdown) {
		this.arrestCountdown = arrestCountdown;
	}

	public int getArrestCountdown() {
		return arrestCountdown;
	}

	@Override
	public boolean isTransportable() {
		return arrestCountdown == 0;
	}

	public boolean arrest(int arrestAmount) {
		arrestCountdown -=arrestAmount;
		arrestCountdown = arrestCountdown <= 0 ? 0 : arrestCountdown;
		return isTransportable();
	}
}
