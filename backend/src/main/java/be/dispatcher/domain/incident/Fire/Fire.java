package be.dispatcher.domain.incident.Fire;

class Fire {

	private int amount;
	private int increasePerTick;
	private boolean isBurning;

	public Fire(int amount, int increasePerTick) {
		this.amount = amount;
		this.increasePerTick = increasePerTick;
		isBurning = amount > 0;
	}

	void extinguish(int extinguishAmount) {
		amount -= extinguishAmount;
		amount = amount <= 0 ? 0 : amount;
		isBurning = amount > 0;
	}

	void burn() {
		if (isBurning) {
			amount += increasePerTick;
		}
	}

	int getAmount() {
		return amount;
	}

	int getIncreasePerTick() {
		return increasePerTick;
	}

	boolean isBurning() {
		return isBurning;
	}
}
