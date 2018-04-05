package be.dispatcher.graphhopper.external_router;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import be.dispatcher.LatLonMapper;
import be.dispatcher.graphhopper.LatLon;
import be.dispatcher.graphhopper.LatLonAtTime;

public class RouteInfoEnriched {

	private LocalDateTime startTime;

	private LocalDateTime arrivalTime;

	private List<LatLonAtTime> latLonAtTimeList = new ArrayList();
	private RouteInfoWithSpeedProfile routeInfo;

	public RouteInfoEnriched(RouteInfoWithSpeedProfile routeInfoWithSpeedProfile) {
		this.routeInfo = routeInfoWithSpeedProfile;
	}

	public Integer getTime() {
		return routeInfo.getTime();
	}

	public void enrichRouteInfo() {
		startTime = LocalDateTime.now();
		int travelTimeSoFarInMs = 0;

		for (List<Double> doubles : routeInfo.getTimes()) {
			enrichPointsWithTimeInformation(getCoordinateListFor(doubles), doubles.get(2).intValue(), travelTimeSoFarInMs);
			travelTimeSoFarInMs += doubles.get(2).intValue();
		}
		arrivalTime = startTime.plusSeconds(getTime() / 1000);
	}

	public List<LatLonAtTime> getLatLonAtTimeList() {
		return latLonAtTimeList;
	}

	public void addLatLonAtTime(LatLonAtTime latLonAtTime) {
		latLonAtTimeList.add(latLonAtTime);
	}

	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public LatLon getDestination() {
		return new LatLon(routeInfo.getDestination().get(1), routeInfo.getDestination().get(0));
	}


	private List<List<Double>> getCoordinateListFor(List<Double> intervals) {
		List<List<Double>> subList = routeInfo.getCoordinates().subList(intervals.get(0).intValue(), intervals.get(1).intValue()+1);
		if (subList.isEmpty()) {//last item
			subList = Collections.singletonList(routeInfo.getCoordinates().get(intervals.get(0).intValue()));
		}
		return subList;
	}

	private void enrichPointsWithTimeInformation(List<List<Double>> points, Integer timeForInstructionInMs, int travelTimeSoFarInMs) {
		if (!points.isEmpty()) {
			List<LatLon> latLons = points.stream().map(point -> new LatLon(point.get(1), point.get(0))).collect(Collectors.toList());

			List<LatLon> latLonsWith10MetersInBetween = LatLonMapper.calculateLatLonsInBetween(latLons.stream().findFirst().get(), latLons.stream().reduce((first, second) -> second).get(), timeForInstructionInMs);
			double travelTimeBetweenPointsInInstruction = timeForInstructionInMs / latLonsWith10MetersInBetween.size();
			for (LatLon latLon : latLonsWith10MetersInBetween) {
				long timeAtPoint = (startTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() + msAfterStart(latLonsWith10MetersInBetween, travelTimeSoFarInMs, travelTimeBetweenPointsInInstruction, latLon));
				LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeAtPoint), ZoneId.systemDefault());
				addLatLonAtTime(new LatLonAtTime(latLon, date));
			}
		}

	}

	private LatLon createLatLanForPoint(List<Double> point) {
		return new LatLon(point.get(1), point.get(0));
	}

	private int msAfterStart(List<LatLon> points, int travelTimeSoFarInMs, double travelTimeBetweenPointsInInstruction, LatLon point) {
		return (int) (travelTimeSoFarInMs + (travelTimeBetweenPointsInInstruction * (points.indexOf(point) + 1)));
	}

	public LatLon getLocationForCurrentTime(LocalDateTime localDateTime) {
		if (latLonAtTimeList.isEmpty()) {
			return null;
		}
		LatLonAtTime latLonAtTime1 = latLonAtTimeList.stream()
				.filter(latLonAtTime -> localDateTime.isAfter(latLonAtTime.getLocalDateTime()))
				.sorted(Comparator.comparing(LatLonAtTime::getLocalDateTime).reversed())
				.findFirst().orElse(latLonAtTimeList.get(0));
		return latLonAtTime1.getLatLon();
	}
}