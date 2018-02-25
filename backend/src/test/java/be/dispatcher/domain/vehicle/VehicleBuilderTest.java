package be.dispatcher.domain.vehicle;

import static org.junit.Assert.fail;

import org.junit.Test;

import be.dispatcher.exceptions.NoLocationException;

public class VehicleBuilderTest {

	@Test(expected = NoLocationException.class)
	public void expectExceptionWhenNoLocationGiven() {
		VehicleBuilder.aVehicle().withVehicleType(VehicleType.AMBULANCE).build();
		fail("exception should have been thrown because no location has been entered");
	}
}