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
import be.dispatcher.domain.vehicle.medical.Ambulance;
import be.dispatcher.domain.vehicle.medical.Mug;
import be.dispatcher.repositories.BaseRespository;
import be.dispatcher.repositories.VehicleRepository;

@RunWith(MockitoJUnitRunner.class)
public class ParserMedicalVehicleTest {

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
	public void expectAmbulanceParsed() {
		parser.parseVehicles("init/ambulances.csv", parser.csvToMedicalVehicleFunction);

		Ambulance vehicle = vehicleRepository.getVehicleById(1000, Ambulance.class);
		assertThat(vehicle.getName()).isEqualTo("Ziekenwagen Jessa PIT");
		assertThat(vehicle.getVehicleType()).isEqualTo(VehicleType.AMBULANCE);
		assertThat(vehicle.getHealthGainPerTick()).isEqualTo(20);
		assertThat(vehicle.getVehicleImagePath()).isEqualTo("images/vehicles/medical/hasselt/jessa.jpg");
	}

	@Test
	public void expectMugParsed() {
		parser.parseVehicles("init/ambulances.csv", parser.csvToMedicalVehicleFunction);

		Mug vehicle = vehicleRepository.getVehicleById(1001, Mug.class);
		assertThat(vehicle.getName()).isEqualTo("MUG Jessa");
		assertThat(vehicle.getVehicleType()).isEqualTo(VehicleType.MUG);
		assertThat(vehicle.getHealthGainPerTick()).isEqualTo(40);
		assertThat(vehicle.getVehicleImagePath()).isEqualTo("images/vehicles/medical/hasselt/mug.jpg");
	}

}