package be.dispatcher.domain.incident;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import be.dispatcher.domain.Ticks;
import be.dispatcher.domain.incident.Fire.FireTasks;
import be.dispatcher.domain.incident.police.TrafficDuty;
import be.dispatcher.domain.vehicle.TrafficDutyVehicle;
import be.dispatcher.graphhopper.LatLon;
import be.dispatcher.repositories.IncidentRepository;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public class Incident implements Ticks {

	private static int incidentCounter = 0;

	@Autowired
	private IncidentRepository incidentRepository;

	private final int id;
	private LatLon location;
	private MedicalTasks medicalTasks;
	private FireTasks fireTasks;
	private PoliceTasks policeTasks;
	private TrafficDuty trafficDuty;

	public Incident(LatLon location) {
		this.id = incidentCounter++;
		this.location = location;
	}

	@Override
	public void tick() {
		if (fireTasks!= null) {
			fireTasks.tick();
		}
		if (isCompleted()) {
			trafficDuty.informNoTrafficDutyRequiredAnymore();
			incidentRepository.removeIncidentFromRepository(this);
		}
	}

	public boolean isTrafficDutyStillRequired(TrafficDutyVehicle trafficDutyVehicle) {
		return trafficDuty != null && trafficDutyVehicle.isScheduledToPerformTrafficDuty() && trafficDuty.isStillNeeded();
	}

	public void performTrafficDuty(TrafficDutyVehicle trafficDutyVehicle) {
		trafficDuty.performTrafficDuty(trafficDutyVehicle);
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

	public FireTasks getFireTasks() {
		return fireTasks;
	}

	public PoliceTasks getPoliceTasks() {
		return policeTasks;
	}

	public TrafficDuty getTrafficDuty() {
		return trafficDuty;
	}

	private boolean isCompleted() {
		boolean completed = true;
		if (medicalTasks != null) {
			completed &= medicalTasks.allTasksCompleted();
		}
		if (fireTasks != null) {
			completed &= fireTasks.allTasksCompleted();
		}
		if (policeTasks != null) {
			completed &= policeTasks.allTasksCompleted();
		}
		return completed;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("location", location)
				.toString();
	}

	public void setFireTasks(FireTasks fireTasks) {
		this.fireTasks = fireTasks;
	}

	public void setMedicalTasks(MedicalTasks medicalTasks) {
		this.medicalTasks = medicalTasks;
	}

	public void setPoliceTasks(PoliceTasks policeTasks) {
		this.policeTasks = policeTasks;
	}

	public void setTrafficDuty(TrafficDuty trafficDuty) {
		this.trafficDuty = trafficDuty;
	}
}
