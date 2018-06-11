package be.dispatcher.parser;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.dispatcher.DispatcherSpringJunit4Test;

public class ParserExceptionTest extends DispatcherSpringJunit4Test {

	@Autowired
	private Parser parser;

	@Test(expected = RuntimeException.class)
	public void expectRuntimeExceptionWhenNoValdidFile() {
		parser.parseBase("init/not_existing.csv", parser.csvToHospitalFunction);
	}
}