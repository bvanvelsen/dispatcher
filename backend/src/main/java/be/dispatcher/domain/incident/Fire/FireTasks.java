package be.dispatcher.domain.incident.Fire;

import be.dispatcher.domain.Ticks;

public interface FireTasks extends Ticks {

	void extinguishFire(int extinguishAmount);

	boolean hasFire();

	void workOnTechnicalDetails(int technicalAmount);

	boolean hasTechnicalWork();

	boolean allTasksCompleted();
}
