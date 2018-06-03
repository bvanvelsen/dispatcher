package be.dispatcher.domain.incident;

import java.util.List;

import be.dispatcher.domain.incident.police.TrafficDuty;
import be.dispatcher.domain.people.Criminal;
import be.dispatcher.domain.vehicle.police.PoliceVehicle;

public class PoliceTasksImpl implements PoliceTasks {

	private List<Criminal> criminals;
	private TrafficDuty trafficDuty;

	public PoliceTasksImpl(List<Criminal> criminals) {
		this.criminals = criminals;
	}

	public PoliceTasksImpl(TrafficDuty trafficDuty) {
		this.trafficDuty = trafficDuty;
	}

	public PoliceTasksImpl(List<Criminal> criminals, TrafficDuty trafficDuty) {
		this.criminals = criminals;
		this.trafficDuty = trafficDuty;
	}

	@Override
	public List<Criminal> getCriminals() {
		return criminals;
	}

	@Override
	public TrafficDuty getTrafficDuty() {
		return trafficDuty;
	}

	@Override
	public void informNoTrafficDutyRequiredAnymore() {
		trafficDuty.informNoTrafficDutyRequiredAnymore();
	}

	@Override
	public boolean allTasksCompleted() {
		return (criminals == null || criminals.isEmpty()) && (trafficDuty == null || trafficDuty.isStillNeeded());
	}

	@Override
	public void performTrafficDuty(PoliceVehicle policeVehicle) {
		trafficDuty.performTrafficDuty(policeVehicle);
	}

	@Override
	public boolean isTrafficDutyStillRequired() {
		return trafficDuty != null && trafficDuty.isStillNeeded();
	}
}
