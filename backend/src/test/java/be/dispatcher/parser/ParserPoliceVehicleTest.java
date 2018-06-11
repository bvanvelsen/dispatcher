package be.dispatcher.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.vehicle.VehicleType;
import be.dispatcher.domain.vehicle.police.PoliceVehicle;
import be.dispatcher.repositories.BaseRespository;
import be.dispatcher.repositories.VehicleRepository;

@RunWith(MockitoJUnitRunner.class)
public class ParserPoliceVehicleTest {

	@InjectMocks
	private Parser parser;

	@Spy
	private VehicleRepository vehicleRepository;

	@Mock
	private BaseRespository baseRespository;

	@Mock
	private Base base;

	@Before
	public void setup() {
		when(baseRespository.getById(anyInt())).thenReturn(base);
	}

	@Test
	public void expectPoliceVehicleParsed() {
		parser.parseVehicles("init/police_vehicles.csv", parser.csvToPoliceVehicleFunction);

		PoliceVehicle vehicle = vehicleRepository.getVehicleById(3000, PoliceVehicle.class);
		assertThat(vehicle.getName()).isEqualTo("Interventieploeg Hasselt 1");
		assertThat(vehicle.getVehicleType()).isEqualTo(VehicleType.COMBI);
		assertThat(vehicle.getArrestGainPerTick()).isEqualTo(20);
		assertThat(vehicle.getVehicleImagePath()).isEqualTo("images/vehicles/police/combi.jpg");
	}
}