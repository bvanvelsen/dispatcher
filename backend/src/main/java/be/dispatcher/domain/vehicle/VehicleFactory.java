package be.dispatcher.domain.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.dispatcher.domain.DispatchFactory;
import be.dispatcher.domain.location.LocationFactory;
import be.dispatcher.repositories.VehicleRepository;

@Component
public class VehicleFactory extends DispatchFactory {

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private LocationFactory locationFactory;

	public Vehicle createVehicle(VehicleType vehicleType) {

		Vehicle vehicle = VehicleBuilder.aVehicle()
				.withVehicleType(vehicleType)
				.withVehicleStatus(VehicleStatus.AT_BASE)
				.withLocation(locationFactory.createDefaultVehicleLocation())
				.build();
		vehicleRepository.addVehicleToRepository(vehicle);
		super.addToWorld(vehicle);
		return vehicle;
	}
}
