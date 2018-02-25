//package be.dispatcher.domain.vehicle.statushandlers;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import be.dispatcher.domain.Route;
//import be.dispatcher.domain.location.LocationBuilder;
//import be.dispatcher.domain.vehicle.Vehicle;
//import be.dispatcher.domain.vehicle.VehicleBuilder;
//import be.dispatcher.domain.vehicle.VehicleType;
//import be.dispatcher.managers.TimeManager;
//
//@RunWith(MockitoJUnitRunner.class)
//public class RespondingStatusHandlerTest {
//
//	@Mock
//	private TimeManager timeManager;
//
//	@InjectMocks
//	private RespondingStatusHandler respondingStatusHandler;
//
//	@Before
//	public void setup() {
//		when(timeManager.timeInMsPerStepOnRoute(any(Route.class))).thenReturn(10L);
//		Vehicle build = VehicleBuilder.aVehicle()
//				.withVehicleType(VehicleType.AMBULANCE)
//				.withLocation(LocationBuilder.aLocation().withX(10).withY(10).build())
//				.build();
//
//
//	}
//
//	@Test
//	public void expectTravel() {
//		respondingStatusHandler.handleStatus();
//		verify(timeManager).timeInMsPerStepOnRoute(any(Route.class));
//	}
//}