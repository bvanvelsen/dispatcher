package be.dispatcher.domain.incident;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.dispatcher.DistributedRandomNumberGenerator;
import be.dispatcher.domain.people.Criminal;
import be.dispatcher.domain.people.InjuryLevel;
import be.dispatcher.domain.people.Victim;
import be.dispatcher.graphhopper.LatLon;
import be.dispatcher.graphhopper.location.PointGenerator;
import be.dispatcher.repositories.IncidentRepository;

@Component
public class IncidentFactory {

	@Autowired
	private IncidentRepository incidentRepository;

	@Autowired
	private PointGenerator pointGenerator;

	public Incident createIncident() {
		LatLon latLon = pointGenerator.generateRandomIncidentLocationSnappedToNearestLocation();

		Incident incident = new Incident(latLon);
		generateMedicalTasks(incident);
		generateFireTaks(incident);
		generatePoliceTaks(incident);

		incidentRepository.addIncidentToRepository(incident);
		return incident;
	}

	private void generatePoliceTaks(Incident incident) {
		if (shouldGeneratePoliceTasks(100)) {
			int criminalCount = createRandomVictimCount();
			List<Criminal> criminals = new ArrayList<>();
			if (criminalCount != 0) {
				for (int i = 0; i < criminalCount; i++) {
					int arrestCountdown = getRandomArrestCountdown();
					criminals.add(new Criminal(arrestCountdown));
				}
				PoliceTasksImpl policeTasks = new PoliceTasksImpl(criminals);
				incident.setPoliceTasks(policeTasks);
			}
		}
	}

	private void generateFireTaks(Incident incident) {
		if (shouldGenerateFireTasks(100)) {
			incident.setFireTasks(new FireTasksImpl(createRandomFireCountdown(), createRandomTechnicalCountdown()));
		}
	}

	private boolean shouldGenerateFireTasks(int changeThatFireDepartmentIsRequired) {
		return new Random().ints(0, 101).findFirst().getAsInt() < changeThatFireDepartmentIsRequired;
	}

	private boolean shouldGeneratePoliceTasks(int changeThatPoliceIsRequired) {
		return new Random().ints(0, 101).findFirst().getAsInt() < changeThatPoliceIsRequired;
	}

	private void generateMedicalTasks(Incident incident) {
		int victimCount = createRandomVictimCount();
		List<Victim> victims = new ArrayList<>();
		if (victimCount != 0) {
			for (int i = 0; i < victimCount; i++) {
				InjuryLevel randomInjuryLevel = getRandomInjuryLevel();
				int healCountdown = getRandomHealCountdown(randomInjuryLevel);
				victims.add(new Victim(randomInjuryLevel, healCountdown));
			}
			incident.setMedicalTasks(new MedicalTasksImpl(victims));
		}
	}

	private int getRandomHealCountdown(InjuryLevel randomInjuryLevel) {
		switch (randomInjuryLevel) {
		case MINOR:
			return new Random().ints(1000, 2000).findFirst().getAsInt();
		case MEDIOCRE:
			return new Random().ints(2000, 4000).findFirst().getAsInt();
		default:
			return new Random().ints(4000, 10000).findFirst().getAsInt();
		}
	}

	private int getRandomArrestCountdown() {
		return new Random().ints(10, 2000).findFirst().getAsInt();
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

	private int createRandomFireCountdown() {
		return new Random().ints(1, 100).findFirst().getAsInt();
	}

	private int createRandomTechnicalCountdown() {
		return new Random().ints(1, 100).findFirst().getAsInt();
	}
}
