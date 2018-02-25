package be.dispatcher.domain.vehicle.statushandlers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import be.dispatcher.domain.location.LocationBuilder;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.VehicleBuilder;
import be.dispatcher.domain.vehicle.VehicleStatus;
import be.dispatcher.domain.vehicle.VehicleType;

public class StatusHandlerFactoryTest {

	@Test
	public void expectRespondingStatusHandlerWhenVehicleRespondingToIncident() {
		Vehicle vehicle = VehicleBuilder.aVehicle()
				.withVehicleStatus(VehicleStatus.RESPONDING)
				.withVehicleType(VehicleType.AMBULANCE)
				.withLocation(LocationBuilder.aLocation().build())
				.build();
		Optional<StatusHandler> statusHandler = new StatusHandlerFactory().getStatusHandler(vehicle);
		assertThat(statusHandler.get()).isInstanceOf(RespondingStatusHandler.class);
	}

}