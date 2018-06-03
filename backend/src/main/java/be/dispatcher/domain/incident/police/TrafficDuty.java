package be.dispatcher.domain.incident.police;

import java.util.HashSet;
import java.util.Set;

import be.dispatcher.domain.vehicle.police.PoliceVehicle;

public class TrafficDuty {

	private Integer requiredUnits;
	private Set<PoliceVehicle> coupledPoliceVehicles = new HashSet<>();
	private boolean stillNeeded;

	public TrafficDuty(Integer requiredUnits) {
		stillNeeded = true;
		this.requiredUnits = requiredUnits;
	}

	public Integer getRequiredUnits() {
		return requiredUnits;
	}

	public Integer getAmountOfUnitsPerformingTrafficDuty() {
		return coupledPoliceVehicles.size();
	}

	public void informNoTrafficDutyRequiredAnymore() {
		stillNeeded = false;
	}

	public boolean isStillNeeded() {
		return stillNeeded;
	}

	public void performTrafficDuty(PoliceVehicle policeVehicle) {
		coupledPoliceVehicles.add(policeVehicle);
	}
}
