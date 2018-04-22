package be.dispatcher.domain.incident;

import java.util.List;

import be.dispatcher.domain.people.Criminal;

public class PoliceTasksImpl implements PoliceTasks {

	private List<Criminal> criminals;


	public PoliceTasksImpl(List<Criminal> criminals) {
		this.criminals = criminals;
	}

	@Override
	public List<Criminal> getCriminals() {
		return criminals;
	}

	@Override
	public boolean allTasksCompleted() {
		return criminals.isEmpty();
	}
}
