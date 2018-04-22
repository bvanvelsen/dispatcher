package be.dispatcher.parser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.dispatcher.DispatcherSpringJunit4Test;
import be.dispatcher.domain.location.emergencybases.BaseType;
import be.dispatcher.repositories.BaseRespository;

public class ParserHospitalTest extends DispatcherSpringJunit4Test{

	@Autowired
	private Parser parser;

	@Autowired
	private BaseRespository baseRespository;

	@Test
	public void expectJessaHospitalParsed() {
		parser.parseBase("init/hospitals.csv", parser.csvToHospitalFunction);

		assertThat(baseRespository.getAll(BaseType.HOSPITAL)).isNotEmpty();
		assertThat(baseRespository.getById(100).getLocation().getLon()).isEqualTo(5.342462);
		assertThat(baseRespository.getById(100).getLocation().getLat()).isEqualTo(50.92669);
	}

}