package be.dispatcher.graphhopper.external_router.routeinfojson;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RouteInfo {

	@SerializedName("paths")
	@Expose
	private List<Path> paths = null;

	public Integer getTime() {
		return paths.get(0).getTime();
	}

	public Double getDistance() {
		return paths.get(0).getDistance();
	}

	public List<List<Double>> getTimes() {
		return paths.get(0).getDetails().getTimes();
	}

	public List<Instruction> getInstructions() {
		return paths.get(0).getInstructions();
	}

	public List<List<Double>> getCoordinates() {
		return paths.get(0).getPoints().getCoordinates();
	}

	public List<Double> getDestination() {
		return paths.get(0).getSnappedWaypoints().getCoordinates().get(1);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("paths", paths)
				.toString();
	}

}