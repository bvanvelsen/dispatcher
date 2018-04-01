package be.dispatcher.domain.incident;

import java.util.List;

import be.dispatcher.domain.Ticks;
import be.dispatcher.domain.people.Victim;

public interface MedicalTasks {

	List<Victim> getVictims();
}
