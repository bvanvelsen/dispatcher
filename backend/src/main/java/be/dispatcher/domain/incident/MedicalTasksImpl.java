package be.dispatcher.domain.incident;

import java.util.ArrayList;
import java.util.List;

import be.dispatcher.domain.people.Victim;

public class MedicalTasksImpl implements MedicalTasks {

	private List<Victim> unstabilizedVictims;
	private List<Victim> stabilizedVictims = new ArrayList<>();

	public MedicalTasksImpl(List<Victim> unstabilizedVictims) {
		this.unstabilizedVictims = unstabilizedVictims;
	}

	@Override
	public void tick() {
		unstabilizedVictims.forEach(victim -> victim.tick());
	}

	@Override
	public boolean areAllTasksCompleted() {
		return unstabilizedVictims.isEmpty() && stabilizedVictims.isEmpty();
	}

	@Override
	public List<Victim> getStabilizedVictims() {
		return stabilizedVictims;
	}

	@Override
	public List<Victim> getUnstabilizedVictims() {
		return unstabilizedVictims;
	}

	@Override
	public void notifyVictimStabilized(Victim victim) {
		if (unstabilizedVictims.remove(victim)) {
			stabilizedVictims.add(victim);
		}
	}
}
