package be.dispatcher.managers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.VehicleFactory;
import be.dispatcher.domain.vehicle.VehicleType;
import be.dispatcher.repositories.IncidentRepository;
import be.dispatcher.repositories.VehicleRepository;

@Component
public class VehicleManager {

	@Autowired
	private VehicleFactory vehicleFactory;

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private IncidentRepository incidentRepository;

	public Vehicle createVehicle(VehicleType vehicleType) {
		if (VehicleType.AMBULANCE.equals(vehicleType)) {
			return vehicleFactory.createBasicAmbulance();
		}
		return null;
	}

		public List<Vehicle> getAllVehicles () {
			return vehicleRepository.getVehicles();
		}

		public Vehicle sendVehicleToIncident (String vehicleId, String incidentId){
			Vehicle vehicle = vehicleRepository.getVehicleById(vehicleId);
			Incident incident = incidentRepository.getIncidentById(incidentId);
			vehicle.goToIncident(incident);
			return vehicle;
		}
	}
