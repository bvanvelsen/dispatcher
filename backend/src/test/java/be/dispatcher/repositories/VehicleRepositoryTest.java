package be.dispatcher.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.dispatcher.DispatcherSpringJunit4Test;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.VehicleType;
import be.dispatcher.domain.vehicle.VehicleFactory;

public class VehicleRepositoryTest extends DispatcherSpringJunit4Test {

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private VehicleFactory vehicleFactory;

	private Vehicle ambulance;

	@Before
	public void setup() {
		ambulance = vehicleFactory.createVehicle(VehicleType.AMBULANCE);
	}

	@Test
	public void expectRepositoryContainsCreatedVehicles() {
		assertThat(vehicleRepository.getVehicles()).containsOnly(ambulance);
	}

	@Test
	public void expectRepositoryReturnsVehicleById() {
		assertThat(vehicleRepository.getVehicleById(ambulance.getId())).isEqualTo(ambulance);
	}
}