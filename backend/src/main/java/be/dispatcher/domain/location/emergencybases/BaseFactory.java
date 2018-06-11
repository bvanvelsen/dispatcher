package be.dispatcher.domain.location.emergencybases;

import org.springframework.stereotype.Component;

import be.dispatcher.graphhopper.LatLon;

@Component
public class BaseFactory {

	public Base getBase(int id, double lat, double lon, String name, Class<? extends Base> type) {
		if (type.equals(AmbulanceStation.class)) {
			return new AmbulanceStation(id, name, new LatLon(lat, lon));
		} else if (type.equals(PoliceStation.class)) {
			return new PoliceStation(id, name, new LatLon(lat, lon));
		} else if (type.equals(Hospital.class)) {
			return new Hospital(id, name, new LatLon(lat, lon));
		} else {
			return new FireDepartment(id, name, new LatLon(lat, lon));
		}
	}
}
