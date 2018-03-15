package be.dispatcher.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import be.dispatcher.DispatcherSpringJunit4Test;
import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.location.LocationBuilder;
import be.dispatcher.domain.location.emergencybases.Hospital;

public class BaseRespositoryTest extends DispatcherSpringJunit4Test {

	private static final Location VEHICLE_LOCATION = LocationBuilder.aLocation().withX(10).withY(10).build();
	private static final Hospital HOSPITAL_1 = new Hospital(1, "", LocationBuilder.aLocation().withY(1).withX(5).build());
	private static final Hospital HOSPITAL_2 = new Hospital(2, "", LocationBuilder.aLocation().withY(5).withX(5).build());
	private static final Hospital HOSPITAL_3 = new Hospital(3, "", LocationBuilder.aLocation().withY(2).withX(4).build());

	@Autowired
	private BaseRespository baseRespository;

	@Before
	public void setup() {
		baseRespository.getBases().add(HOSPITAL_1);
		baseRespository.getBases().add(HOSPITAL_2);
		baseRespository.getBases().add(HOSPITAL_3);
	}

	@Test
	public void expectClosestHospitalFound() {
		assertThat(baseRespository.getClosestHospital(VEHICLE_LOCATION)).isEqualTo(HOSPITAL_2);
	}

}