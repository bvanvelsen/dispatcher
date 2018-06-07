package be.dispatcher.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.incident.IncidentFactory;
import be.dispatcher.repositories.IncidentRepository;

@RestController
@RequestMapping("api/incidents/")
public class IncidentController {

	@Autowired
	private IncidentFactory incidentFactory;

	@Autowired
	private IncidentRepository incidentRepository;

	@RequestMapping(value = "allIncidents", method = RequestMethod.GET)
	public List<Incident> getAllIncidents() {
		return incidentRepository.getAllIncidents();
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public Incident createIncident() {
		return incidentFactory.createIncident();
	}
}