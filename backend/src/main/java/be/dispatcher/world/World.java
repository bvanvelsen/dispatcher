package be.dispatcher.world;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import be.dispatcher.domain.Ticks;

@Component
public class World {

	private List<Ticks> tickableObjects = new ArrayList<>();

	public void startWorldTicking() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(helloRunnable, 0, 1, TimeUnit.SECONDS);
	}

	public void addObjectToWorld(Ticks livingObject) {
		tickableObjects.add(livingObject);
	}

	public void removeObjectFromWorld(Ticks livingObject) {
		tickableObjects.remove(livingObject);
	}

	private Runnable helloRunnable = () -> {
		try {
			tickableObjects.forEach(Ticks::tick);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	};

}
