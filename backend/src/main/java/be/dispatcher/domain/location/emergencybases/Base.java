package be.dispatcher.domain.location.emergencybases;

import be.dispatcher.graphhopper.LatLon;

public interface Base {
	LatLon getLocation();

	String getName();

	int getId();

	BaseType getBaseType();
}
