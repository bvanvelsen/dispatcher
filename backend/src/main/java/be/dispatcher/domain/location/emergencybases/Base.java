package be.dispatcher.domain.location.emergencybases;

import be.dispatcher.domain.location.Location;

public interface Base {

	Location getLocation();

	String getName();

	int getId();

	BaseType getBaseType();
}
