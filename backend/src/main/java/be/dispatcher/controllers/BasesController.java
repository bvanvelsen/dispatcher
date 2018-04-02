package be.dispatcher.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.repositories.BaseRespository;

@RestController
@RequestMapping("api/bases/")
public class BasesController {

	@Autowired
	private BaseRespository baseRespository;

	@RequestMapping(value = "hospitals", method = RequestMethod.GET)
	public List<Base> getAllHospitals() {
		return baseRespository.getAllHospitals();
	}

	@RequestMapping(value = "fire_departments", method = RequestMethod.GET)
	public List<Base> getAllFireDepartments() {
		return baseRespository.getAllFireDepartments();
	}

	@RequestMapping(value = "police_stations", method = RequestMethod.GET)
	public List<Base> getAllPoliceStations() {
		return baseRespository.getAllPoliceStations();
	}
}