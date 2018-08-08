package be.dispatcher.graphhopper.location;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.dispatcher.graphhopper.LatLon;
import be.dispatcher.graphhopper.reverse.geocode.RetrofitReverseGeocoderCaller;
import be.dispatcher.graphhopper.reverse.geocode.ReverseGeocode;

@Component
public class PointGenerator {

	@Autowired
	private RetrofitReverseGeocoderCaller retrofitReverseGeocoderCaller;

	public LatLon generateRandomIncidentLocationSnappedToNearestLocation(double minLat, double maxLat, double minLon, double maxLon) {
		Random latRandom = new Random();
		Random lonRandom = new Random();
		double randomLat = latRandom.doubles(minLat, maxLat).findFirst().getAsDouble();
		double randomLon = lonRandom.doubles(minLon, maxLon).findFirst().getAsDouble();
		randomLat = Double.parseDouble(String.format("%.6f", randomLat));
		randomLon = Double.parseDouble(String.format("%.6f", randomLon));
		LatLon latLon = new LatLon(randomLat, randomLon);
		ReverseGeocode reverseGeocode = retrofitReverseGeocoderCaller.doCall(latLon);
		return new LatLon(reverseGeocode.getLat(), reverseGeocode.getLon(), reverseGeocode.getAddress());
	}

}
