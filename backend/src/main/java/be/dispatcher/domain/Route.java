package be.dispatcher.domain;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Route {
	private final LocalDateTime startTime;
	private final Double distanceInKm;
	private final Integer verticalSteps;
	private final Integer horizontalSteps;
	private LocalDateTime arrivalTime;

	public Route(LocalDateTime startTime, Double distanceInKm, Integer verticalSteps, Integer horizontalSteps) {
		this.startTime = startTime;
		this.distanceInKm = distanceInKm;
		this.verticalSteps = verticalSteps;
		this.horizontalSteps = horizontalSteps;
	}

	public Double getDistanceInKm() {
		return distanceInKm;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("startTime", startTime)
				.append("distanceInKm", distanceInKm)
				.append("verticalSteps", verticalSteps)
				.append("horizontalSteps", horizontalSteps)
				.append("arrivalTime", arrivalTime)
				.toString();
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Long getArrivalTimeInMs() {
		return arrivalTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}

	public Integer getHorizontalSteps() {
		return horizontalSteps;
	}

	public Integer getVerticalSteps() {
		return verticalSteps;
	}

	public int getTotalSteps() {
		return verticalSteps + horizontalSteps;
	}
}
