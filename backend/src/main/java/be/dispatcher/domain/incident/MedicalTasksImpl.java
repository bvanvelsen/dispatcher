package be.dispatcher.domain.incident;

import java.util.ArrayList;
import java.util.List;

import be.dispatcher.domain.people.Victim;

public class MedicalTasksImpl implements MedicalTasks {

	private List<Victim> victims;

	public MedicalTasksImpl(List<Victim> victims) {
		this.victims = victims;
	}

	@Override
	public List<Victim> getVictims() {
		return victims;
	}
}
