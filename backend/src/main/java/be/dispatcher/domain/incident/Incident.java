package be.dispatcher.domain.incident;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import be.dispatcher.domain.Ticks;
import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.people.Victim;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public class Incident implements Ticks {

	private final String id;
	private final String shortDescription;
	private Location location;
	private MedicalTasks medicalTasks;

	private boolean markIncidentFinished = false;

	public Incident(Location location, MedicalTasks medicalTasks) {
		this.id = UUID.randomUUID().toString();
		this.location = location;
		this.medicalTasks = medicalTasks;
		shortDescription = String.format("Verkeersongeval op x:%d y:%d",location.getX(), location.getY());
	}

	@Override
	public void tick() {
		medicalTasks.tick();
		checkIncidentFinished();
	}

	private void checkIncidentFinished() {
		markIncidentFinished = medicalTasks.areAllTasksCompleted();
	}

	public String getId() {
		return id;
	}

	public Location getLocation() {
		return location;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public MedicalTasks getMedicalTasks() {
		return medicalTasks;
	}

	public boolean isMarkIncidentFinished() {
		return markIncidentFinished;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("shortDescription", shortDescription)
				.append("location", location)
				.append("medicalTasks", medicalTasks)
				.append("markIncidentFinished", markIncidentFinished)
				.toString();
	}
}
