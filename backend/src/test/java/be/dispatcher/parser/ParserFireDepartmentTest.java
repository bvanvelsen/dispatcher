package be.dispatcher.parser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.dispatcher.DispatcherSpringJunit4Test;
import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.repositories.BaseRespository;

public class ParserFireDepartmentTest extends DispatcherSpringJunit4Test{

	private static final int HASSELT = 200;

	@Autowired
	private Parser parser;

	@Autowired
	private BaseRespository baseRespository;

	@Test
	public void expectFireDepartmentParsed() {
		parser.parseBase("init/fire_department.csv", parser.csvToFireDepartmentFunction);

		assertFireDepartmentData();
	}

	private void assertFireDepartmentData() {
		Base fireDepartment = baseRespository.getById(HASSELT);
		assertThat(fireDepartment.getLocation().getLon()).isEqualTo(5.350546);
		assertThat(fireDepartment.getLocation().getLat()).isEqualTo(50.933903);
		assertThat(fireDepartment.getName()).isEqualTo("Brandweer Hasselt");
	}

}