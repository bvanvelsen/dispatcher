package be.dispatcher.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.VehicleType;
import be.dispatcher.init.Parser;
import be.dispatcher.managers.VehicleManager;

@RestController
@RequestMapping("api/vehicles/create/")
public class VehicleShopController {

	@Autowired
	private VehicleManager vehicleManager;

	@Autowired
	private Parser parser;

	@RequestMapping(value = "{vehicleType}", method = RequestMethod.POST)
	@ResponseBody void createVehicleOfType(@PathVariable("vehicleType") String vehicleType) {
		parser.hospitalParser();
		parser.fireDepartmentParser();
		parser.ambulanceParser();
	}

}