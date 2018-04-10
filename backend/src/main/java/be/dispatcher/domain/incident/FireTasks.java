package be.dispatcher.domain.incident;

import be.dispatcher.domain.Ticks;

public interface FireTasks extends Ticks {

	void extinguishFire(int extinguishAmount);

	void workOnTechnicalDetails(int technicalAmount);

	boolean allTasksCompleted();
}
