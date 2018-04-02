package be.dispatcher.domain.incident;

public interface FireTasks {

	void extinguishFire(int extinguishAmount);

	void workOnTechnicalDetails(int technicalAmount);

	boolean allTasksCompleted();
}
