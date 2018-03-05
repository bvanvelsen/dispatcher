package be.dispatcher.domain.incident;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.dispatcher.DistributedRandomNumberGenerator;
import be.dispatcher.domain.DispatchFactory;
import be.dispatcher.domain.location.LocationFactory;
import be.dispatcher.domain.people.VictimFactory;
import be.dispatcher.repositories.IncidentRepository;

@Component
public class IncidentFactory extends DispatchFactory {

	@Autowired
	private IncidentRepository incidentRepository;

	@Autowired
	private VictimFactory victimFactory;

	@Autowired
	private LocationFactory locationFactory;

	public Incident createIncident() {
		IncidentBuilder incidentBuilder = IncidentBuilder.anIncident();

		int victimCount = createRandomVictimCount();
		for (int i = 0; i < victimCount; i++) {
			incidentBuilder.addVictim(victimFactory.createLightWounedVictim());
		}

		Incident incident = incidentBuilder
				.withLocation(locationFactory.createIncidentLocation())
				.build();

		incidentRepository.addIncidentToRepository(incident);
		super.addToWorld(incident);
		return incident;
	}

	private int createRandomVictimCount() {
		DistributedRandomNumberGenerator distributedRandomNumberGenerator = new DistributedRandomNumberGenerator();
		distributedRandomNumberGenerator.addNumber(1, 0.8);
		distributedRandomNumberGenerator.addNumber(2, 0.1);
		distributedRandomNumberGenerator.addNumber(3, 0.07);
		distributedRandomNumberGenerator.addNumber(4, 0.03);
		return distributedRandomNumberGenerator.getDistributedRandomNumber();
	}
}
