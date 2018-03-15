package be.dispatcher.domain.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import be.dispatcher.domain.DispatchFactory;
import be.dispatcher.repositories.BaseRespository;
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
	private BaseRespository baseRespository;

	public Vehicle createBasicAmbulance(Vehicle vehicle) {
		//		Vehicle vehicle = new Ambulance(basicAmbulanceSittingPlaces, basicAmbulanceLayingPlaces, ambulanceDghHealthGain, baseRespository.getBases().get(0));
		vehicleRepository.addVehicleToRepository(vehicle);
		super.addToWorld(vehicle);
		return vehicle;
	}

	public Vehicle createBasicAmbulance() {
		Vehicle vehicle = new Ambulance(1, "", basicAmbulanceSittingPlaces, basicAmbulanceLayingPlaces, ambulanceDghHealthGain, baseRespository.getBases().get(0));
		vehicleRepository.addVehicleToRepository(vehicle);
		super.addToWorld(vehicle);
		return vehicle;
	}
}
