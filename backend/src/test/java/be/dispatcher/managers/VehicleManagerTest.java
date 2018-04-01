package be.dispatcher.managers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.vehicle.Ambulance;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.VehicleStatus;
import be.dispatcher.graphhopper.LatLon;
import be.dispatcher.graphhopper.external_router.RetrofitRouteCaller;
import be.dispatcher.graphhopper.external_router.RouteInput;
import be.dispatcher.repositories.IncidentRepository;
import be.dispatcher.repositories.VehicleRepository;

@RunWith(MockitoJUnitRunner.class)
public class VehicleManagerTest {

	private static final int VEHICLE_ID = 1;
	private static final int HEALTH_GAIN_PER_TICK = 10;
	@InjectMocks
	private VehicleManager vehicleManager;

	@Mock
	VehicleRepository vehicleRepository;

	@Mock
	IncidentRepository incidentRepository;

	@Mock
	private RetrofitRouteCaller retrofitRouteCaller;

	@Mock
	private Base base;
	private Vehicle vehicle;
	private Incident incident;

	@Before
	public void setup() {
		when(base.getLocation()).thenReturn(new LatLon(2.0,2.0));
		vehicle = new Ambulance(VEHICLE_ID,"Ambu", base, HEALTH_GAIN_PER_TICK);
		incident = new Incident(new LatLon(1.0,1.0));
		when(vehicleRepository.getVehicleById(vehicle.getId())).thenReturn(vehicle);
		when(incidentRepository.getIncidentById(anyInt())).thenReturn(incident);
	}

	@Test
	public void expect() {
		vehicleManager.sendVehicleToIncident(VEHICLE_ID,0);

		verify(retrofitRouteCaller).doCall(any(RouteInput.class));
		assertThat(vehicle.getVehicleStatus()).isEqualTo(VehicleStatus.RESPONDING);
		assertThat(vehicle.getIncident()).isEqualTo(incident);
	}

}