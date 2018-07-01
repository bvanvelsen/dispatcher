package be.dispatcher.graphhopper.external_router.routeinfojson;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Path {

	@SerializedName("instructions")
	@Expose
	private List<Instruction> instructions = null;

	@SerializedName("descend")
	@Expose
	private Double descend;

	@SerializedName("ascend")
	@Expose
	private Double ascend;

	@SerializedName("distance")
	@Expose
	private Double distance;

	@SerializedName("bbox")
	@Expose
	private List<Double> bbox = null;

	@SerializedName("weight")
	@Expose
	private Double weight;

	@SerializedName("points_encoded")
	@Expose
	private Boolean pointsEncoded;

	@SerializedName("points")
	@Expose
	private Points points;

	@SerializedName("details")
	@Expose
	private Details details;

	@SerializedName("transfers")
	@Expose
	private Integer transfers;

	@SerializedName("legs")
	@Expose
	private List<Object> legs = null;

	@SerializedName("time")
	@Expose
	private Integer time;

	@SerializedName("snapped_waypoints")
	@Expose
	private SnappedWaypoints snappedWaypoints;

	public List<Instruction> getInstructions() {
		return instructions;
	}

	public void setInstructions(List<Instruction> instructions) {
		this.instructions = instructions;
	}

	public Double getDescend() {
		return descend;
	}

	public void setDescend(Double descend) {
		this.descend = descend;
	}

	public Double getAscend() {
		return ascend;
	}

	public void setAscend(Double ascend) {
		this.ascend = ascend;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public List<Double> getBbox() {
		return bbox;
	}

	public void setBbox(List<Double> bbox) {
		this.bbox = bbox;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Boolean getPointsEncoded() {
		return pointsEncoded;
	}

	public void setPointsEncoded(Boolean pointsEncoded) {
		this.pointsEncoded = pointsEncoded;
	}

	public Points getPoints() {
		return points;
	}

	public void setPoints(Points points) {
		this.points = points;
	}

	public Integer getTransfers() {
		return transfers;
	}

	public void setTransfers(Integer transfers) {
		this.transfers = transfers;
	}

	public List<Object> getLegs() {
		return legs;
	}

	public void setLegs(List<Object> legs) {
		this.legs = legs;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public SnappedWaypoints getSnappedWaypoints() {
		return snappedWaypoints;
	}

	public void setSnappedWaypoints(SnappedWaypoints snappedWaypoints) {
		this.snappedWaypoints = snappedWaypoints;
	}

	public Details getDetails() {
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}
}