package be.dispatcher.domain.vehicle.police;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.VehicleType;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public class PoliceVehicle extends Vehicle {

	public PoliceVehicle(int id, String name, Base base, VehicleType vehicleType) {
		super(id, name, base);
		this.vehicleType = vehicleType;
	}
}
