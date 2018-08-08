package be.dispatcher.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.vehicle.TrafficDutyVehicle;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.VehicleType;
import be.dispatcher.domain.vehicle.fire.FireTruck;
import be.dispatcher.domain.vehicle.medical.MedicalVehicle;
import be.dispatcher.domain.vehicle.police.PoliceVehicle;
import be.dispatcher.managers.VehicleManager;
import be.dispatcher.repositories.VehicleRepository;

@RestController
@RequestMapping("api/vehicles/")
public class VehicleController {

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private VehicleManager vehicleManager;

	@RequestMapping(value = "ambulances/all", method = RequestMethod.GET)
	public List<? extends Vehicle> getAllAmbulances() {
		return vehicleRepository.getVehicles(VehicleType.AMBULANCE);
	}

	@RequestMapping(value = "mugs/all", method = RequestMethod.GET)
	public List<? extends Vehicle> getAllMugs() {
		return vehicleRepository.getVehicles(VehicleType.MUG);
	}

	@RequestMapping(value = "medical_vehicles", method = RequestMethod.GET)
	public List<? extends MedicalVehicle> getAllMedicalVehicles() {
		return vehicleRepository.getAllMedicalVehicles();
	}

	@RequestMapping(value = "medical_vehicles_per_station", method = RequestMethod.GET)
	public Map<Base, List<MedicalVehicle>> getAllMedicleVehiclesPerStation() {
		return vehicleRepository.getAllMedicalVehicles().stream().sorted().collect(Collectors.groupingBy(MedicalVehicle::getBase));
	}

	@RequestMapping(value = "police_vehicles", method = RequestMethod.GET)
	public List<PoliceVehicle> getAllPoliceVehicles() {
		return vehicleRepository.getAllPoliceVehicles();
	}

	@RequestMapping(value = "police_vehicles_per_station", method = RequestMethod.GET)
	public Map<Base, List<PoliceVehicle>> getAllPoliceVehiclesPerStation() {
		return vehicleRepository.getAllPoliceVehicles().stream().sorted().collect(Collectors.groupingBy(PoliceVehicle::getBase));
	}

	@RequestMapping(value = "fire_trucks", method = RequestMethod.GET)
	public List<FireTruck> getAllFireTrucks() {
		return vehicleRepository.getAllFireTrucks();
	}

	@RequestMapping(value = "fire_trucks_per_station", method = RequestMethod.GET)
	public Map<Base, List<FireTruck>> getAllFireTrucksPerStation() {
		return vehicleRepository.getAllFireTrucks().stream().sorted().collect(Collectors.groupingBy(FireTruck::getBase));
	}

	@RequestMapping(value = "{vehicleId}/sendTo/{incidentId}", method = RequestMethod.POST)
	@ResponseBody
	public Vehicle sendVehicleToIncident(@PathVariable("vehicleId") int vehicleId, @PathVariable("incidentId") int incidentId) {
		return vehicleManager.sendVehicleToIncident(vehicleId, incidentId);
	}

	@RequestMapping(value = "{vehicleId}/performTrafficDuty", method = RequestMethod.POST)
	@ResponseBody
	public void performTrafficDuty(@RequestBody boolean performTrafficDuty, @PathVariable("vehicleId") int vehicleId) {
		vehicleManager.getAllVehicles().stream().filter(vehicle -> vehicle.getId() == vehicleId).findFirst().map(vehicle -> (TrafficDutyVehicle)vehicle).get().setScheduledToPerformTrafficDuty(performTrafficDuty);
	}
}