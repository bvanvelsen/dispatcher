package be.dispatcher.parser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.dispatcher.DispatcherSpringJunit4Test;
import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.repositories.BaseRespository;

public class ParserPoliceStationTest extends DispatcherSpringJunit4Test{

	private static final int LRH_HASSELT = 300;

	@Autowired
	private Parser parser;

	@Autowired
	private BaseRespository baseRespository;

	@Test
	public void expectFireDepartmentParsed() {
		parser.parseBase("init/police_stations.csv", parser.csvToPoliceStationFunction);

		assertPoliceStationData();
	}

	private void assertPoliceStationData() {
		Base policeStation = baseRespository.getById(LRH_HASSELT);
		assertThat(policeStation.getLocation().getLon()).isEqualTo(5.333729);
		assertThat(policeStation.getLocation().getLat()).isEqualTo(50.931738);
		assertThat(policeStation.getName()).isEqualTo("Politie LRH Hasselt");
	}

}