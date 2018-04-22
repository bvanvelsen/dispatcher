package be.dispatcher.repositories;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.location.emergencybases.BaseType;
import be.dispatcher.domain.location.emergencybases.Hospital;
import be.dispatcher.domain.location.emergencybases.PoliceStation;
import be.dispatcher.graphhopper.LatLon;
import be.dispatcher.graphhopper.external_router.RetrofitRouteCaller;
import be.dispatcher.graphhopper.external_router.RouteInfoEnriched;
import be.dispatcher.graphhopper.external_router.RouteInput;

@Component
public class BaseRespository {

	private List<Base> bases = new ArrayList<>();

	@Autowired
	private RetrofitRouteCaller retrofitRouteCaller;

	public void addBaseToRepository(Base base) {
		bases.add(base);
	}

	public Base getById(int id) {
		return bases.stream()
				.filter(base -> id == base.getId())
				.findFirst().get();
	}

	public Base getClosestHospital(double speedProfile, LatLon vehicleLocation) {
		List<Base> hospitals = bases.stream()
				.filter(base -> base instanceof Hospital)
				.collect(Collectors.toList());

		Base closestHospital = null;
		int closestInTime = 0;
		for (Base hospital : hospitals) {
			RouteInfoEnriched routeInfoEnriched = retrofitRouteCaller.doCall(new RouteInput(speedProfile, vehicleLocation, hospital.getLocation()));
			int travelTimeToCurrentHospital = routeInfoEnriched.getTime();
			if (closestHospital == null || closestInTime > travelTimeToCurrentHospital) {
				closestHospital = hospital;
				closestInTime = travelTimeToCurrentHospital;
			}
		}
		return closestHospital;
	}

	public Base getClosestPolicestation(double speedProfile, LatLon vehicleLocation) {
		List<Base> policeStations = bases.stream()
				.filter(base -> base instanceof PoliceStation)
				.collect(Collectors.toList());

		Base closestPoliceStation = null;
		int closestInTime = 0;
		for (Base policeStation : policeStations) {
			RouteInfoEnriched routeInfoEnriched = retrofitRouteCaller.doCall(new RouteInput(speedProfile, vehicleLocation, policeStation.getLocation()));
			int travelTimeToCurrentPoliceStation = routeInfoEnriched.getTime();
			if (closestPoliceStation == null || closestInTime > travelTimeToCurrentPoliceStation) {
				closestPoliceStation = policeStation;
				closestInTime = travelTimeToCurrentPoliceStation;
			}
		}
		return closestPoliceStation;
	}

	public List<Base> getAll(BaseType baseType) {
		return bases.stream()
				.filter(base -> baseType.equals(base.getBaseType()))
				.collect(toList());
	}
}
