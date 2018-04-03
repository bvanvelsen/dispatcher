package be.dispatcher.managers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.people.Criminal;
import be.dispatcher.domain.people.Victim;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.VehicleStatus;
import be.dispatcher.domain.vehicle.police.PoliceVehicle;
import be.dispatcher.graphhopper.external_router.RetrofitRouteCaller;
import be.dispatcher.graphhopper.external_router.RouteInput;
import be.dispatcher.graphhopper.external_router.reouteinfojson.RouteInfoEnriched;
import be.dispatcher.managers.incidentscene.IncidentSceneMedicalTasksManager;
import be.dispatcher.repositories.BaseRespository;
import be.dispatcher.repositories.IncidentRepository;
import be.dispatcher.repositories.VehicleRepository;

@Component
public class VehicleManager {

//	@Autowired
//	private VehicleFactory vehicleFactory;

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private IncidentRepository incidentRepository;

	@Autowired
	private RetrofitRouteCaller retrofitRouteCaller;

	@Autowired
	private BaseRespository baseRespository;

	@Autowired
	private IncidentSceneMedicalTasksManager incidentSceneMedicalTasksManager;

	public List<Vehicle> getAllVehicles() {
		return vehicleRepository.getVehicles();
	}

	public Vehicle sendVehicleToIncident(int vehicleId, int incidentId) {
		Vehicle vehicle = vehicleRepository.getVehicleById(vehicleId);
		Incident incident = incidentRepository.getIncidentById(incidentId);
		RouteInfoEnriched routeInfo = retrofitRouteCaller.doCall(new RouteInput(vehicle.getVehicleType().getSpeedProfilePrioriy(), vehicle.getLocation(), incident.getLocation()));
		vehicle.setRouteInfo(routeInfo);
		vehicle.setVehicleStatus(VehicleStatus.RESPONDING);
		vehicle.setIncident(incident);
		return vehicle;
	}

	public void sendVehicleToNearestHospital(Vehicle vehicle) {
		Victim victim = incidentSceneMedicalTasksManager.notifyVehicleLeavingSoRemoveCoupling(vehicle);
		vehicle.getIncident().getMedicalTasks().getVictims().remove(victim);
		vehicle.setFilled(true);
		Base closestHospital = baseRespository.getClosestHospital(vehicle.getVehicleType().getSpeedProfilePrioriy(), vehicle.getLocation());
		RouteInfoEnriched routeInfoEnriched = retrofitRouteCaller.doCall(new RouteInput(vehicle.getVehicleType().getSpeedProfilePrioriy(), vehicle.getLocation(), closestHospital.getLocation()));
		vehicle.setRouteInfo(routeInfoEnriched);
		vehicle.setVehicleStatus(VehicleStatus.GO_TO_DROPOFF);
	}

	public void sendVehicleToBase(Vehicle vehicle) {
		RouteInfoEnriched routeInfoEnriched = retrofitRouteCaller.doCall(new RouteInput(vehicle.getVehicleType().getSpeedProfileSecundary(), vehicle.getLocation(), vehicle.getBase().getLocation()));
		vehicle.setRouteInfo(routeInfoEnriched);
		vehicle.setVehicleStatus(VehicleStatus.GO_TO_BASE);
	}

	public void sendVehicleToNearestPoliceStation(PoliceVehicle policeVehicle) {
		Criminal victim = incidentSceneMedicalTasksManager.notifyVehicleLeavingSoRemovePoliceVehicleCoupling(policeVehicle);
		policeVehicle.getIncident().getPoliceTasks().getCriminals().remove(victim);
		policeVehicle.setFilled(true);
		Base closestPolicestation = baseRespository.getClosestPolicestation(policeVehicle.getVehicleType().getSpeedProfileSecundary(), policeVehicle.getLocation());
		RouteInfoEnriched routeInfoEnriched = retrofitRouteCaller.doCall(new RouteInput(policeVehicle.getVehicleType().getSpeedProfileSecundary(), policeVehicle.getLocation(), closestPolicestation.getLocation()));
		policeVehicle.setRouteInfo(routeInfoEnriched);
		policeVehicle.setVehicleStatus(VehicleStatus.GO_TO_DROPOFF);
	}
}
