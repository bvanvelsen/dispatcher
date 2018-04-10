package be.dispatcher.world;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Stopwatch;

import be.dispatcher.domain.Ticks;
import be.dispatcher.init.Parser;

@Component
public class World {

	@Autowired
	private Parser parser;

	private List<Ticks> tickableObjects = new ArrayList<>();

	public void startWorldTicking() {
		parser.hospitalParser();
		parser.fireDepartmentParser();
		parser.policeStationParser();
		parser.ambulanceStationParser();
		parser.medicalVehicleParser();
		parser.fireTruckParser();
		parser.policeVehicleParser();
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(helloRunnable, 0, 1, TimeUnit.SECONDS);
	}

	public void addObjectToWorld(Ticks livingObject) {
		tickableObjects.add(livingObject);
	}

	public void removeObjectFromWorld(Ticks livingObject) {
		tickableObjects.remove(livingObject);
	}

	Runnable helloRunnable = new Runnable() {
		public void run() {
			try {
				Stopwatch s = Stopwatch.createStarted();
				tickableObjects.forEach(Ticks::tick);
				System.out.println(TimeUnit.NANOSECONDS.toMillis(s.elapsed().getNano()));
				s.stop();
				s = null;
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	};

}
