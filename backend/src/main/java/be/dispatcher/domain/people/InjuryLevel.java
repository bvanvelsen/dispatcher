package be.dispatcher.domain.people;

public enum InjuryLevel {

	MINOR(false), MEDIOCRE(false), SEVERE(true);

	private boolean requiresMUG;

	InjuryLevel(boolean requiresMUG) {
		this.requiresMUG = requiresMUG;
	}

	public boolean requiresMUG() {
		return requiresMUG;
	}
}
