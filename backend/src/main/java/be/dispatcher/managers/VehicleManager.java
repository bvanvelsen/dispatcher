package be.dispatcher.managers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.VehicleFactory;
import be.dispatcher.domain.vehicle.VehicleType;
import be.dispatcher.repositories.BaseRespository;
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

	@Autowired
	private BaseRespository baseRespository;

	public List<Vehicle> getAllVehicles() {
		return vehicleRepository.getVehicles();
	}

	public Vehicle sendVehicleToIncident(int vehicleId, String incidentId) {
		Vehicle vehicle = vehicleRepository.getVehicleById(vehicleId);
		Incident incident = incidentRepository.getIncidentById(incidentId);
		vehicle.goToIncident(incident);
		return vehicle;
	}

	public void sendVehicleToNearestHospital(int vehicleId) {
		Vehicle vehicle = vehicleRepository.getVehicleById(vehicleId);
		Base closestHospital = baseRespository.getClosestHospital(vehicle.getLocation());
		vehicle.goToDropoffLocation(closestHospital);
	}
}
