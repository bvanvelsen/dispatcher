package be.dispatcher.world;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.dispatcher.domain.vehicle.VehicleStatus;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.Ticks;
import be.dispatcher.repositories.IncidentRepository;

@Component
public class World {

	@Autowired
	private IncidentRepository incidentRepository;

	private List<Ticks> tickableObjects = new ArrayList<>();

	public void startWorldTicking() {
		new Thread(() -> {
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(1);
					tickableObjects.forEach(Ticks::tick);
					removeFinishedIncidents();
					//					System.out.println("the world ticked");
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void removeFinishedIncidents() {
		incidentRepository.removeFinishedIncidents();
	}

	public void addObjectToWorld(Ticks livingObject) {
		tickableObjects.add(livingObject);
	}
}
