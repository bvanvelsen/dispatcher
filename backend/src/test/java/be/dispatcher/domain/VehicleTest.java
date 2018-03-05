package be.dispatcher.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.dispatcher.DispatcherSpringJunit4Test;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.VehicleFactory;
import be.dispatcher.domain.vehicle.VehicleStatus;
import be.dispatcher.domain.vehicle.VehicleType;

public class VehicleTest extends DispatcherSpringJunit4Test {

	@Autowired
	private VehicleFactory vehicleFactory;

	private Vehicle vehicle;

	@Before
	public void setup() {
		vehicle = vehicleFactory.createBasicAmbulance();
	}

	@Test
	public void expectVehicleTypeIsCorrectlySet() {
		assertThat(vehicle.getVehicleType()).isEqualTo(VehicleType.AMBULANCE);
	}

	@Test
	public void expectVehicleStatusIsCorrectlySet() {
		assertThat(vehicle.getVehicleStatus()).isEqualTo(VehicleStatus.AT_BASE);
	}

	@Test
	public void expectVehicleHasAGeneratedId() {
		assertThat(vehicle.getId()).isNotEmpty();
	}
}