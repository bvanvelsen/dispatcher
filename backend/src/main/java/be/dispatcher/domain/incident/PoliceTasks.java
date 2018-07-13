package be.dispatcher.domain.incident;

import java.util.List;

import be.dispatcher.domain.people.Criminal;

public interface PoliceTasks {

	List<Criminal> getCriminals();

	boolean allTasksCompleted();
}
