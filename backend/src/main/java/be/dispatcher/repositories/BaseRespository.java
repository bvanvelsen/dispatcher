package be.dispatcher.repositories;

import static be.dispatcher.domain.location.emergencybases.BaseType.*;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.location.emergencybases.BaseType;
import be.dispatcher.domain.location.emergencybases.Hospital;
import be.dispatcher.graphhopper.LatLon;
import be.dispatcher.graphhopper.external_router.RetrofitRouteCaller;
import be.dispatcher.graphhopper.external_router.RouteInput;
import be.dispatcher.graphhopper.external_router.reouteinfojson.RouteInfoEnriched;

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

	public List<Base> getAllHospitals() {
		return bases.stream()
				.filter(base -> HOSPITAL.equals(base.getBaseType()))
				.collect(toList());
	}

	public Base getClosestHospital(String speedProfile, LatLon vehicleLocation) {
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

	public List<Base> getAllFireDepartments() {
		return bases.stream()
				.filter(base -> FIRE_DEPARTMENT.equals(base.getBaseType()))
				.collect(toList());
	}
}
