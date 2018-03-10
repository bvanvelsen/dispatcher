package be.dispatcher.domain.route;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Route {
	private final int distanceInMeters;
	private final int verticalDistance;
	private final int horizontalDistance;
	private final Direction travelDirection;

	public Route(int distanceInMeters, int verticalDistance, int horizontalDistance, Direction travelDirection) {
		this.distanceInMeters = distanceInMeters;
		this.verticalDistance = verticalDistance;
		this.horizontalDistance = horizontalDistance;
		this.travelDirection = travelDirection;
	}

	public int getDistanceInMeters() {
		return distanceInMeters;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("distanceInMeters", distanceInMeters)
				.append("verticalDistance", verticalDistance)
				.append("horizontalDistance", horizontalDistance)
				.append("travelDirection", travelDirection)
				.toString();
	}

	public int getHorizontalDistance() {
		return horizontalDistance;
	}

	public int getVerticalDistance() {
		return verticalDistance;
	}

	public int getTotalDistance() {
		return verticalDistance + horizontalDistance;
	}

	public Direction getTravelDirection() {
		return travelDirection;
	}
}
