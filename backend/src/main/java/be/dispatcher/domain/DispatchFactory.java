package be.dispatcher.domain;

import org.springframework.beans.factory.annotation.Autowired;

import be.dispatcher.world.World;

public class DispatchFactory {

	@Autowired
	private World world;

	protected final void addToWorld(Ticks tick) {
		world.addObjectToWorld(tick);
	}
}
