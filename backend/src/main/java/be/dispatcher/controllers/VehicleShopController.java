package be.dispatcher.controllers;

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
@RequestMapping("api/vehicles/create/")
public class VehicleShopController {

	@Autowired
	private VehicleManager vehicleManager;

	@RequestMapping(value = "{vehicleType}", method = RequestMethod.POST)
	@ResponseBody Vehicle createVehicleOfType(@PathVariable("vehicleType") String vehicleType) {
		return vehicleManager.createVehicle(VehicleType.valueOf(vehicleType.toUpperCase()));
	}

}