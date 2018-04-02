package be.dispatcher.domain.vehicle.fire;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import be.dispatcher.domain.incident.FireTasks;
import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.VehicleType;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public class FireTruck extends Vehicle {

	private final int fireGainPerTick;
	private final int technicalPerTick;

	public FireTruck(int id, String name, Base base, VehicleType vehicleType, int fireGainPerTick, int technicalPerTick) {
		super(id, name, base);
		this.vehicleType = vehicleType;
		this.fireGainPerTick = fireGainPerTick;
		this.technicalPerTick = technicalPerTick;
	}

	@Override
	public void tick() {
		super.tick();
		switch (vehicleStatus) {
		case AT_INCIDENT:
			FireTasks fireTasks = getIncident().getFireTasks();
			if (fireTasks != null) {
				fireTasks.extinguishFire(fireGainPerTick);
				fireTasks.workOnTechnicalDetails(technicalPerTick);
				if (fireTasks.allTasksCompleted()) {
					vehicleManager.sendVehicleToBase(this);
				}
			} else {
				vehicleManager.sendVehicleToBase(this);
			}

			break;
		}
	}
}
