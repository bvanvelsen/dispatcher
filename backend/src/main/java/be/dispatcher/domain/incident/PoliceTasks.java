package be.dispatcher.domain.incident;

import java.util.List;

import be.dispatcher.domain.incident.police.TrafficDuty;
import be.dispatcher.domain.people.Criminal;
import be.dispatcher.domain.vehicle.police.PoliceVehicle;

public interface PoliceTasks {

	List<Criminal> getCriminals();

	TrafficDuty getTrafficDuty();

	void informNoTrafficDutyRequiredAnymore();

	boolean allTasksCompleted();

	void performTrafficDuty(PoliceVehicle policeVehicle);

	boolean isTrafficDutyStillRequired();
}
