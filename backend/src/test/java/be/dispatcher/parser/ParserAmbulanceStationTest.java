package be.dispatcher.parser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.dispatcher.DispatcherSpringJunit4Test;
import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.repositories.BaseRespository;

public class ParserAmbulanceStationTest extends DispatcherSpringJunit4Test{

	private static final int AMBICARE_BORGLOON = 400;

	@Autowired
	private Parser parser;

	@Autowired
	private BaseRespository baseRespository;

	@Test
	public void expectFireDepartmentParsed() {
		parser.parseBase("init/ambulance_stations.csv", parser.csvToAmbulanceStationFunction);

		assertAmbulanceStationData();
	}

	private void assertAmbulanceStationData() {
		Base ambulanceStation = baseRespository.getById(AMBICARE_BORGLOON);
		assertThat(ambulanceStation.getLocation().getLon()).isEqualTo(5.332151);
		assertThat(ambulanceStation.getLocation().getLat()).isEqualTo(50.801867);
		assertThat(ambulanceStation.getName()).isEqualTo("Ambicare Borgloon");
	}

}