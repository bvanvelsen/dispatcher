package be.dispatcher.world;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.vehicle.fire.FireTruck;
import be.dispatcher.domain.vehicle.medical.MedicalVehicle;
import be.dispatcher.domain.vehicle.police.PoliceVehicle;
import be.dispatcher.parser.Parser;

@RunWith(MockitoJUnitRunner.class)
public class WorldTest {

	@InjectMocks
	private World world;

	@Mock
	private Parser parser;

	@Mock
	private MedicalVehicle medicalVehicle;

	@Mock
	private FireTruck fireTruck;

	@Mock
	private PoliceVehicle policeVehicle;

	@Mock
	private Incident incident;

	@Test
	public void expectParserParsesBases() {
		world.startWorldTicking();

		verify(parser).parseBase("init/hospitals.csv", parser.csvToHospitalFunction);
		verify(parser).parseBase("init/fire_department.csv", parser.csvToFireDepartmentFunction);
		verify(parser).parseBase("init/police_stations.csv", parser.csvToPoliceStationFunction);
		verify(parser).parseBase("init/ambulance_stations.csv", parser.csvToAmbulanceStationFunction);
	}

	@Test
	public void expectParserParsesVehicles() {
		world.startWorldTicking();

		verify(parser).parseVehicles("init/ambulances.csv", parser.csvToMedicalVehicleFunction);
		verify(parser).parseVehicles("init/fire_trucks.csv", parser.csvToFireTruckFunction);
		verify(parser).parseVehicles("init/police_vehicles.csv", parser.csvToPoliceVehicleFunction);

	}

	@Test
	public void expectVehiclesAddedToWorld() {
		when(parser.parseVehicles("init/ambulances.csv", parser.csvToMedicalVehicleFunction)).thenReturn(Collections.singletonList(medicalVehicle));
		when(parser.parseVehicles("init/police_vehicles.csv", parser.csvToPoliceVehicleFunction)).thenReturn(Collections.singletonList(policeVehicle));
		when(parser.parseVehicles("init/fire_trucks.csv", parser.csvToFireTruckFunction)).thenReturn(Collections.singletonList(fireTruck));

		world.startWorldTicking();

		assertThat(world.getTickableObjects()).containsOnly(medicalVehicle, policeVehicle, fireTruck);
	}

	@Test
	public void expectIncidentAddedToWorld() {
		world.addIncidentToWorld(incident);

		assertThat(world.getTickableObjects()).containsOnly(incident);
	}

	@Test
	public void expectIncidentRemovedFromWorld() {
		world.addIncidentToWorld(incident);
		world.removeIncidentFromWorld(incident);

		assertThat(world.getTickableObjects()).isEmpty();
	}
}