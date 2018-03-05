package be.dispatcher.domain.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import be.dispatcher.domain.DispatchFactory;
import be.dispatcher.domain.location.LocationFactory;
import be.dispatcher.repositories.VehicleRepository;

@Component
public class VehicleFactory extends DispatchFactory {

	@Value("${ambulance.dgh.healthgain}")
	private double ambulanceDghHealthGain;

	@Value("${ambulance.dgh.basicAmbulanceSittingPlaces}")
	private int basicAmbulanceSittingPlaces;

	@Value("${ambulance.dgh.basicAmbulanceLayingPlaces}")
	private int basicAmbulanceLayingPlaces;


	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private LocationFactory locationFactory;

//	public Vehicle createVehicle(VehicleType vehicleType) {
//		Vehicle vehicle = null;
//		if (VehicleType.AMBULANCE.equals(vehicleType)) {
//			vehicle = new Ambulance(vehicleType.getSpeed(), locationFactory.createDefaultVehicleLocation());
//		}
//		vehicleRepository.addVehicleToRepository(vehicle);
//		super.addToWorld(vehicle);
//		return vehicle;
//	}

	public Vehicle createBasicAmbulance() {
		Vehicle vehicle = new Ambulance(locationFactory.createDefaultVehicleLocation(), basicAmbulanceSittingPlaces, basicAmbulanceLayingPlaces, ambulanceDghHealthGain);
		vehicleRepository.addVehicleToRepository(vehicle);
		super.addToWorld(vehicle);
		return vehicle;
	}
}
