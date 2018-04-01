package be.dispatcher.domain.incident;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import be.dispatcher.domain.Ticks;
import be.dispatcher.domain.people.Victim;
import be.dispatcher.graphhopper.LatLon;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public class Incident implements Ticks {

	private static int incidentCounter = 0;

	private final int id;
	private LatLon location;
	private MedicalTasks medicalTasks;

	public Incident(LatLon location) {
		this.id = incidentCounter++;
		this.location = location;
	}

	@Override
	public void tick() {
	}

	public int getId() {
		return id;
	}

	public LatLon getLocation() {
		return location;
	}

	public MedicalTasks getMedicalTasks() {
		return medicalTasks;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("location", location)
				.toString();
	}

	public void setMedicalTasks(MedicalTasks medicalTasks) {
		this.medicalTasks = medicalTasks;
	}
}
