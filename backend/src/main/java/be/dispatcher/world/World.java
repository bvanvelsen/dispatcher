package be.dispatcher.world;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
		parser.medicalVehicleParser();
		parser.fireTruckParser();
		parser.policeVehicleParser();
		new Thread(() -> {
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(1);
					tickableObjects.forEach(Ticks::tick);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void addObjectToWorld(Ticks livingObject) {
		tickableObjects.add(livingObject);
	}

	public void removeObjectFromWorld(Ticks livingObject) {
		tickableObjects.remove(livingObject);
	}
}
