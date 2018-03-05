package be.dispatcher.domain.factories;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.location.LocationFactory;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.VehicleFactory;
import be.dispatcher.domain.vehicle.VehicleType;
import be.dispatcher.repositories.VehicleRepository;
import be.dispatcher.world.World;

@RunWith(MockitoJUnitRunner.class)
public class VehicleFactoryTest {

	@Mock
	private VehicleRepository vehicleRepository;

	@Mock
	private World world;

	@Mock
	private LocationFactory locationFactory;

	@Mock
	private Location location;

	@InjectMocks
	private VehicleFactory vehicleFactory;


	private Vehicle ambulance;

	@Before
	public void setup() {
		when(locationFactory.createDefaultVehicleLocation()).thenReturn(location);
		ambulance = vehicleFactory.createBasicAmbulance();
	}

	@Test
	public void expectVehicleAddedToRepository() {
		verify(vehicleRepository).addVehicleToRepository(ambulance);
	}

	@Test
	public void expectVehicleAddedToWorld() {
		verify(world).addObjectToWorld(ambulance);
	}

}