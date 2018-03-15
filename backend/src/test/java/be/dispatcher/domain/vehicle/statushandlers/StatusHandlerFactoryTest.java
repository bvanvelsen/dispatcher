package be.dispatcher.domain.vehicle.statushandlers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.dispatcher.DispatcherSpringJunit4Test;
import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.incident.IncidentFactory;
import be.dispatcher.domain.vehicle.Ambulance;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.VehicleFactory;
import be.dispatcher.domain.vehicle.VehicleStatus;

public class StatusHandlerFactoryTest extends DispatcherSpringJunit4Test {

	@Autowired
	private VehicleFactory vehicleFactory;

	@Autowired
	private StatusHandlerFactory statusHandlerFactory;

	@Autowired
	private IncidentFactory incidentFactory;

	@Test
	public void expectRespondingStatusHandlerWhenVehicleRespondingToIncident() {
		Vehicle vehicle = vehicleFactory.createBasicAmbulance();
		vehicle.setVehicleStatus(VehicleStatus.RESPONDING);
		Incident incident = incidentFactory.createIncident();
		vehicle.setIncident(incident);

		Optional<StatusHandler> statusHandler = statusHandlerFactory.getStatusHandler(vehicle);

		assertThat(statusHandler.get()).isInstanceOf(RespondingStatusHandler.class);
	}

}