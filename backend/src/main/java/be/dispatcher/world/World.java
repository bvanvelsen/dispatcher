package be.dispatcher.world;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import be.dispatcher.domain.vehicle.VehicleStatus;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.Ticks;

@Component
public class World {

	private List<Ticks> tickableObjects = new ArrayList<>();

	public void startWorldTicking() {
		new Thread(() -> {
			try {
				while (true) {
					TimeUnit.SECONDS.sleep(1);
					tickableObjects.forEach(Ticks::tick);
//					System.out.println("the world ticked");
				}
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}

	public void addObjectToWorld(Ticks livingObject) {
		tickableObjects.add(livingObject);
	}

	public void getObjectAndSetToStatus(String status) {
		Vehicle vehicle = (Vehicle) tickableObjects.get(0);
		vehicle.setVehicleStatus(VehicleStatus.valueOf(status));
	}
}
