package be.dispatcher.domain.incident;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.dispatcher.DistributedRandomNumberGenerator;
import be.dispatcher.domain.DispatchFactory;
import be.dispatcher.domain.people.InjuryLevel;
import be.dispatcher.domain.people.Victim;
import be.dispatcher.graphhopper.LatLon;
import be.dispatcher.graphhopper.location.PointGenerator;
import be.dispatcher.repositories.IncidentRepository;

@Component
public class IncidentFactory extends DispatchFactory {

	@Autowired
	private IncidentRepository incidentRepository;

	@Autowired
	private PointGenerator pointGenerator;

	public Incident createIncident() {
		LatLon latLon = pointGenerator.generateRandomIncidentLocationSnappedToNearestLocation();

		int victimCount = createRandomVictimCount();
		List<Victim> victims = new ArrayList<>();
		for (int i = 0; i < victimCount; i++) {
			InjuryLevel randomInjuryLevel = getRandomInjuryLevel();
			int healCountdown = getRandomHealCountdown(randomInjuryLevel);
			victims.add(new Victim(randomInjuryLevel, healCountdown));
		}

		Incident incident = new Incident(latLon);
		incident.setMedicalTasks(new MedicalTasksImpl(victims));
		incidentRepository.addIncidentToRepository(incident);
		super.addToWorld(incident);
		return incident;
	}

	private int getRandomHealCountdown(InjuryLevel randomInjuryLevel) {
		switch (randomInjuryLevel) {
		case MINOR:
		return new Random().ints(1000,2000).findFirst().getAsInt();
		case MEDIOCRE:
			return new Random().ints(2000,4000).findFirst().getAsInt();
		default:
			return new Random().ints(4000,10000).findFirst().getAsInt();
		}
	}

	private InjuryLevel getRandomInjuryLevel() {
		DistributedRandomNumberGenerator distributedRandomNumberGenerator = new DistributedRandomNumberGenerator();
		distributedRandomNumberGenerator.addNumber(0, 0.4);
		distributedRandomNumberGenerator.addNumber(1, 0.5);
		distributedRandomNumberGenerator.addNumber(2, 0.1);
		return InjuryLevel.values()[distributedRandomNumberGenerator.getDistributedRandomNumber()];
	}

	private int createRandomVictimCount() {
		DistributedRandomNumberGenerator distributedRandomNumberGenerator = new DistributedRandomNumberGenerator();
		distributedRandomNumberGenerator.addNumber(1, 0.8);
		distributedRandomNumberGenerator.addNumber(2, 0.5);
		distributedRandomNumberGenerator.addNumber(3, 0.03);
		distributedRandomNumberGenerator.addNumber(4, 0.02);
		return distributedRandomNumberGenerator.getDistributedRandomNumber();
	}

}
