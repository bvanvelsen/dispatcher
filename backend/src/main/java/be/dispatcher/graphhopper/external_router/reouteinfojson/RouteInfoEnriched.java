package be.dispatcher.graphhopper.external_router.reouteinfojson;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import be.dispatcher.graphhopper.LatLon;
import be.dispatcher.graphhopper.LatLonAtTime;

public class RouteInfoEnriched {

	private RouteInfo routeInfo;

	private LocalDateTime startTime;

	private LocalDateTime arrivalTime;

	private List<LatLonAtTime> latLonAtTimeList = new ArrayList();

	public Integer getTime() {
		return routeInfo.getTime();
	}

	public RouteInfoEnriched(RouteInfo routeInfo) {
		this.routeInfo = routeInfo;
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

	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public void enrichRouteInfo() {
		LocalDateTime startOfResponse = LocalDateTime.now();
		startTime = startOfResponse;
		int travelTimeSoFarInMs = 0;
		for (Instruction instruction : routeInfo.getInstructions()) {
			enrichPointsWithTimeInformation(getCoordinateListFor(instruction), instruction, travelTimeSoFarInMs,
					startOfResponse);
			travelTimeSoFarInMs += instruction.getTime();
		}
		arrivalTime = startTime.plusSeconds(travelTimeSoFarInMs / 1000);
	}

	private List<List<Double>> getCoordinateListFor(Instruction instruction) {
		List<List<Double>> subList = routeInfo.getCoordinates().subList(instruction.getInterval().get(0), instruction.getInterval().get(1));
		if (subList.isEmpty()) {//last item
			subList = Collections.singletonList(routeInfo.getCoordinates().get(instruction.getInterval().get(0)));
		}
		return subList;
	}

	private void enrichPointsWithTimeInformation(List<List<Double>> points, Instruction instruction, int travelTimeSoFarInMs, LocalDateTime startTimeOfRoute) {
		Integer timeForInstructionInMs = instruction.getTime();
		if (!points.isEmpty()) {
			double travelTimeBetweenPointsInInstruction = timeForInstructionInMs / points.size();
			for (List<Double> point : points) {
				LatLon latLon = createLatLanForPoint(point);
				long timeAtPoint = (startTimeOfRoute.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() + msAfterStart(points, travelTimeSoFarInMs, travelTimeBetweenPointsInInstruction, point));
				LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeAtPoint), ZoneId.systemDefault());
				addLatLonAtTime(new LatLonAtTime(latLon, date));
			}
		}

	}

	private LatLon createLatLanForPoint(List<Double> point) {
		return new LatLon(point.get(1), point.get(0));
	}

	private int msAfterStart(List<List<Double>> points, int travelTimeSoFarInMs, double travelTimeBetweenPointsInInstruction, List<Double> point) {
		return (int) (travelTimeSoFarInMs + (travelTimeBetweenPointsInInstruction * (points.indexOf(point) + 1)));
	}

	public LatLon getLocationForCurrentTime(LocalDateTime localDateTime) {
		LatLonAtTime latLonAtTime1 = latLonAtTimeList.stream()
				.filter(latLonAtTime -> localDateTime.isAfter(latLonAtTime.getLocalDateTime()))
				.sorted(Comparator.comparing(LatLonAtTime::getLocalDateTime).reversed())
				.findFirst().orElse(latLonAtTimeList.get(0));
		System.out.println(latLonAtTime1);
		return latLonAtTime1.getLatLon();
	}
}