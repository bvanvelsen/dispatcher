package be.dispatcher.domain.incident.police;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

import org.threeten.extra.Interval;

import be.dispatcher.domain.vehicle.TrafficDutyVehicle;

public class TrafficDuty {

	private Integer requiredUnits;
	private Set<TrafficDutyVehicle> trafficDutyVehicles = new HashSet<>();
	private boolean stillNeeded;

	public TrafficDuty(Integer requiredUnits) {
		stillNeeded = true;
		this.requiredUnits = requiredUnits;
	}

	public Integer getRequiredUnits() {
		return requiredUnits;
	}

	public Integer getAmountOfUnitsPerformingTrafficDuty() {
		return trafficDutyVehicles.size();
	}

	public void informNoTrafficDutyRequiredAnymore() {
		stillNeeded = false;
		trafficDutyVehicles.clear();
		requiredUnits = 0;
	}

	public boolean isStillNeeded() {
		return stillNeeded;
	}

	public void performTrafficDuty(TrafficDutyVehicle trafficDutyVehicle) {
		trafficDutyVehicles.add(trafficDutyVehicle);
	}

}
