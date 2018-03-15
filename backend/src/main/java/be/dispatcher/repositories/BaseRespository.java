package be.dispatcher.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.dispatcher.DispatcherProperties;
import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.location.emergencybases.Hospital;
import be.dispatcher.managers.LocationManager;

@Component
public class BaseRespository {

	@Autowired
	private DispatcherProperties dispatcherProperties;

	@Autowired
	private LocationManager locationManager;

	private List<Base> bases = new ArrayList<>();

	public void addBaseToRepository(Base base) {
		bases.add(base);
	}

	public List<Base> getBases() {
		return bases;
	}

	public Base getById(int id) {
		return bases.stream()
				.filter(base -> id == base.getId())
				.findFirst().get();
	}

	public Base getClosestHospital(Location vehicleLocation) {
		List<Base> hospitals = bases.stream()
				.filter(base -> base instanceof Hospital)
				.collect(Collectors.toList());

		Base closestHospital = null;
		int closestInMeters = 0;
		for (Base hospital : hospitals) {
			int distanceInMeters = locationManager.getRouteBetweenLocations(vehicleLocation, hospital.getLocation()).getDistanceInMeters();
			if (closestHospital == null || closestInMeters > distanceInMeters) {
				closestHospital = hospital;
				closestInMeters = distanceInMeters;
			}
		}
		return closestHospital;
	}

}
