package be.dispatcher.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import be.dispatcher.domain.vehicle.Vehicle;

@Component
public class VehicleRepository {

	private List<Vehicle> vehicles = new ArrayList<>();

	public void addVehicleToRepository(Vehicle vehicle) {
		vehicles.add(vehicle);
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public Vehicle getVehicleById(int id) {
		return vehicles.stream ()
				.filter(vehicle -> vehicle.getId() == id)
				.findFirst()
				.get();
	}

}
