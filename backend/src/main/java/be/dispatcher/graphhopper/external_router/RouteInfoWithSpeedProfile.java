package be.dispatcher.graphhopper.external_router;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class RouteInfoWithSpeedProfile {
	private RouteInfo routeInfo;
	private double speedFactor;

	RouteInfoWithSpeedProfile(RouteInfo routeInfo, double speedFactor) {
		this.routeInfo = routeInfo;
		this.speedFactor = speedFactor;
	}

	public Integer getTime() {
		return (int)(routeInfo.getTime() * (1-speedFactor));
	}

	public List<List<Double>> getTimes() {
		List<List<Double>> times = routeInfo.getTimes();
		List<List<Double>> collect = times.stream().map(list -> fillList(list)).collect(Collectors.toList());
		return collect;
	}

	private ArrayList<Double> fillList(List<Double> list) {
		ArrayList<Double> doubles = new ArrayList<>();
		doubles.add(list.get(0));
		doubles.add(list.get(1));
		doubles.add(Math.floor(list.get(2) * (1-speedFactor)));
		return doubles;
	}

	public List<Double> getDestination() {
		return routeInfo.getDestination();
	}

	public List<List<Double>> getCoordinates() {
		return routeInfo.getCoordinates();
	}
}
