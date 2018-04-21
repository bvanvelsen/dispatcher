package be.dispatcher;

import java.util.ArrayList;
import java.util.List;

import be.dispatcher.graphhopper.LatLon;

public class LatLonCalculator {

	public static List<LatLon> calculateLatLonsInBetween(LatLon start, LatLon destnation, int timeForInstructionInMs) {
		double interval = 10.0;
		Double lat1 = start.getLat();
		Double lng1 = start.getLon();
		Double lat2 = destnation.getLat();
		Double lng2 = destnation.getLon();
		double azimuth = calculateBearing(lat1, lng1, lat2, lng2);
		return main(interval, azimuth, lat1, lng1, lat2, lng2);
	}

	private static int distanceForInstructionInMeter(LatLon start, LatLon destnation) {
		double interval = 1.0;
		Double lat1 = start.getLat();
		Double lng1 = start.getLon();
		Double lat2 = destnation.getLat();
		Double lng2 = destnation.getLon();
		double azimuth = calculateBearing(lat1, lng1, lat2, lng2);
		double d = getPathLength(lat1, lng1, lat2, lng2);
		double[] doubles = myModf(d / interval);
		return new Double(doubles[0]).intValue();
	}

	//		'''returns every coordinate pair inbetween two coordinate pairs given the desired interval'''
	private static List<LatLon> main(double interval, double azimuth, double lat1, double lng1, double lat2, double lng2) {

		double d = getPathLength(lat1, lng1, lat2, lng2);
		double[] doubles = myModf(d / interval);
		List<LatLon> coords = new ArrayList<>();
		coords.add(new LatLon(lat1, lng1));
		double distance = doubles[0] * 10;
		for (int x = 0; x < distance; x += interval) {
			LatLon coord = getDestinationLatLong(lat1, lng1, azimuth, x);
			coords.add(coord);
		}
		coords.add(new LatLon(lat2, lng2));
		return coords;
	}

	//		'''returns the lat an long of destination point given the start lat, long, aziuth, and distance'''
	private static LatLon getDestinationLatLong(double lat, double lng, double azimuth, double distance) {
		double R = 6378.1; //#Radius of the Earth in km
		double brng = Math.toRadians(azimuth);
		double d = distance / 1000;
		double lat1 = Math.toRadians(lat);
		double lon1 = Math.toRadians(lng);
		double lat2 = Math.asin(Math.sin(lat1) * Math.cos(d / R) + Math.cos(lat1) * Math.sin(d / R) * Math.cos(brng));
		double lon2 = lon1 + Math.atan2(Math.sin(brng) * Math.sin(d / R) * Math.cos(lat1), Math.cos(d / R) - Math.sin(lat1) * Math.sin(lat2));
		//    #convert back to degrees
		lat2 = Math.toDegrees(lat2);
		lon2 = Math.toDegrees(lon2);
		return new LatLon(lat2, lon2);
	}

	private static double[] myModf(double fullDouble) {
		int intVal = (int) fullDouble;
		double remainder = fullDouble - intVal;

		double[] retVal = new double[2];
		retVal[0] = intVal;
		retVal[1] = remainder;
		return retVal;
	}

	//		'''calculates the distance between two lat, long coordinate pairs'''
	private static double getPathLength(double lat1, double lng1, double lat2, double lng2) {
		double R = 6371000; //# radius of earth in m
		double lat1rads = Math.toRadians(lat1);
		double lat2rads = Math.toRadians(lat2);
		double deltaLat = Math.toRadians((lat2 - lat1));
		double deltaLng = Math.toRadians((lng2 - lng1));
		double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) + Math.cos(lat1rads) * Math.cos(lat2rads) * Math.sin(deltaLng / 2) * Math.sin(deltaLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return R * c;
	}

	//calculates the azimuth in degrees from start point to end point
	private static double calculateBearing(double lat1, double lng1, double lat2, double lng2) {
		double startLat = Math.toRadians(lat1);
		double startLong = Math.toRadians(lng1);
		double endLat = Math.toRadians(lat2);
		double endLong = Math.toRadians(lng2);
		double dLong = endLong - startLong;
		double dPhi = Math.log(Math.tan(endLat / 2.0 + Math.PI / 4.0) / Math.tan(startLat / 2.0 + Math.PI / 4.0));
		if (Math.abs(dLong) > Math.PI) {
			if (dLong > 0) {
				dLong = -(2.0 * Math.PI - dLong);
			} else {
				dLong = (2.0 * Math.PI + dLong);
			}
		}
		return (Math.toDegrees(Math.atan2(dLong, dPhi)) + 360.0) % 360.0;
	}
}
