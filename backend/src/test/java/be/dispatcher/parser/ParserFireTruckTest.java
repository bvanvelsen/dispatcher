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
import be.dispatcher.domain.vehicle.fire.FireTruck;
import be.dispatcher.repositories.BaseRespository;
import be.dispatcher.repositories.VehicleRepository;

@RunWith(MockitoJUnitRunner.class)
public class ParserFireTruckTest {

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
	public void expectFDAutopompParsed() {
		parser.parseVehicles("init/fire_trucks.csv", parser.csvToFireTruckFunction);

		FireTruck vehicle = vehicleRepository.getVehicleById(2000, FireTruck.class);
		assertThat(vehicle.getName()).isEqualTo("11 Halfzware autopomp Hasselt");
		assertThat(vehicle.getVehicleType()).isEqualTo(VehicleType.FD_AUTOPOMP);
		assertThat(vehicle.getExtinguishRatePerTick()).isEqualTo(40);
		assertThat(vehicle.getTechnicalWorkPerTick()).isEqualTo(0);
		assertThat(vehicle.getVehicleImagePath()).isEqualTo("images/vehicles/fd/hasselt/11.jpg");
	}
}