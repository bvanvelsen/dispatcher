package be.dispatcher.world;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.dispatcher.domain.Ticks;
import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.parser.Parser;

@Component
public class World {

	private final Parser parser;

	private List<Ticks> tickableObjects = new ArrayList<>();

	@Autowired
	public World(Parser parser) {
		this.parser = parser;
	}

	public void startWorldTicking() {
		parser.parseBase("init/hospitals.csv", parser.csvToHospitalFunction);
		parser.parseBase("init/fire_department.csv", parser.csvToFireDepartmentFunction);
		parser.parseBase("init/police_stations.csv", parser.csvToPoliceStationFunction);
		parser.parseBase("init/ambulance_stations.csv", parser.csvToAmbulanceStationFunction);
		List<Vehicle> medicalVehicles = parser.parseVehicles("init/ambulances.csv", parser.csvToMedicalVehicleFunction);
		List<Vehicle> fireTrucks = parser.parseVehicles("init/fire_trucks.csv", parser.csvToFireTruckFunction);
		List<Vehicle> policeVehicles = parser.parseVehicles("init/police_vehicles.csv", parser.csvToPoliceVehicleFunction);

		medicalVehicles.forEach(this::addVehicleToWorld);
		fireTrucks.forEach(this::addVehicleToWorld);
		policeVehicles.forEach(this::addVehicleToWorld);
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(helloRunnable, 0, 1, TimeUnit.SECONDS);
	}

	private void addVehicleToWorld(Vehicle vehicle) {
		tickableObjects.add(vehicle);
	}

	public void addIncidentToWorld(Incident incident) {
		tickableObjects.add(incident);
	}

	public void removeIncidentFromWorld(Incident livingObject) {
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
