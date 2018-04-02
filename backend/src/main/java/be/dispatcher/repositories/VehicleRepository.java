package be.dispatcher.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.VehicleType;
import be.dispatcher.domain.vehicle.fire.FireTruck;
import be.dispatcher.domain.vehicle.medical.MedicalVehicle;
import be.dispatcher.domain.vehicle.police.PoliceVehicle;
import be.dispatcher.world.World;

@Component
public class VehicleRepository {

	@Autowired
	private World world;

	private List<Vehicle> vehicles = new ArrayList<>();

	public void addVehicleToRepository(Vehicle vehicle) {
		world.addObjectToWorld(vehicle);
		vehicles.add(vehicle);
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public List<Vehicle> getVehicles(VehicleType vehicleType) {
		return vehicles.stream ()
				.filter(vehicle -> vehicleType.equals(vehicle.getVehicleType()))
				.collect(Collectors.toList());
	}

	public Vehicle getVehicleById(int id) {
		return vehicles.stream ()
				.filter(vehicle -> vehicle.getId() == id)
				.findFirst()
				.get();
	}

	public List<? extends MedicalVehicle> getAllMedicalVehicles() {
		return vehicles.stream ()
				.filter(vehicle -> vehicle instanceof MedicalVehicle)
				.map(vehicle -> (MedicalVehicle)vehicle)
				.collect(Collectors.toList());
	}

	public List<FireTruck> getAllFireTrucks() {
		return vehicles.stream ()
				.filter(vehicle -> vehicle instanceof FireTruck)
				.map(vehicle -> (FireTruck)vehicle)
				.collect(Collectors.toList());
	}

	public List<PoliceVehicle> getAllPoliceVehicles() {
		return vehicles.stream ()
				.filter(vehicle -> vehicle instanceof PoliceVehicle)
				.map(vehicle -> (PoliceVehicle)vehicle)
				.collect(Collectors.toList());
	}
}
