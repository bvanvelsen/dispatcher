package be.dispatcher.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Repeat;

import be.dispatcher.DispatcherSpringJunit4Test;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.VehicleFactory;
import be.dispatcher.domain.vehicle.VehicleStatus;
import be.dispatcher.domain.vehicle.VehicleType;

public class VehicleArriveAtDropOffTestTest extends DispatcherSpringJunit4Test {

	@Autowired
	private VehicleFactory vehicleFactory;

	private Vehicle vehicle;

	@Before
	public void setup() {
		vehicle = vehicleFactory.createBasicAmbulance();
	}


	@Test
	public void expectArrivedAtDropOffStatusSetWhenArrivingAtDropOff() {
		vehicle.arriveAtDropOff();

		assertThat(vehicle.getVehicleStatus()).isEqualTo(VehicleStatus.AT_DROP_OFF);
	}

	@Test
	@Repeat(10)
	public void expectRandomTimeGeneratedWhenArrivingAtDropOff() {
			vehicle.arriveAtDropOff();
			assertThat(vehicle.getDropOffEndTime()).isBetween(LocalDateTime.now(), LocalDateTime.now().plusMinutes(10));
	}

}