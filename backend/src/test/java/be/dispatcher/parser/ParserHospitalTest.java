package be.dispatcher.parser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.dispatcher.DispatcherSpringJunit4Test;
import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.repositories.BaseRespository;

public class ParserHospitalTest extends DispatcherSpringJunit4Test{

	private static final int JESSA = 100;

	@Autowired
	private Parser parser;

	@Autowired
	private BaseRespository baseRespository;

	@Test
	public void expectHospitalsParsed() {
		parser.parseBase("init/hospitals.csv", parser.csvToHospitalFunction);

		assertHospitalData();
	}

	private void assertHospitalData() {
		Base hospital = baseRespository.getById(JESSA);
		assertThat(hospital.getLocation().getLon()).isEqualTo(5.342462);
		assertThat(hospital.getLocation().getLat()).isEqualTo(50.92669);
		assertThat(hospital.getName()).isEqualTo("Jessa Ziekenhuis");
	}

}