package be.dispatcher.domain.incident;

import java.util.List;

import be.dispatcher.domain.Ticks;
import be.dispatcher.domain.people.Victim;

public interface MedicalTasks extends Ticks {

	boolean areAllTasksCompleted();

	List<Victim> getStabilizedVictims();

	List<Victim> getUnstabilizedVictims();

	void notifyVictimStabilized(Victim victim);
}
