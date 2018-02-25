package be.dispatcher.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.VehicleType;
import be.dispatcher.managers.VehicleManager;

@RestController
@RequestMapping("api/vehicles/")
public class VehicleController {

	@Autowired
	private VehicleManager vehicleManager;

	@RequestMapping(value = "all", method = RequestMethod.GET)
	public List<Vehicle> getAllVehicles() {
		return vehicleManager.getAllVehicles();
	}

	@RequestMapping(value = "{vehicleId}/sendTo/{incidentId}", method = RequestMethod.POST)
	@ResponseBody
	Vehicle sendVehicleToIncident(@PathVariable("vehicleId") String vehicleId, @PathVariable("incidentId") String incidentId) {
		return vehicleManager.sendVehicleToIncident(vehicleId, incidentId);
	}

}