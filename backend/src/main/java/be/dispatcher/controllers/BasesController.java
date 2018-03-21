package be.dispatcher.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.managers.VehicleManager;
import be.dispatcher.repositories.BaseRespository;

@RestController
@RequestMapping("api/")
public class BasesController {

	@Autowired
	private BaseRespository vehicleManager;

	@RequestMapping(value = "bases", method = RequestMethod.GET)
	public List<? extends Base> getAllVehicles() {
		return vehicleManager.getBases();
	}

}