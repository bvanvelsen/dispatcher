package be.dispatcher.domain.vehicle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.people.InjuryLevel;
import be.dispatcher.domain.people.Victim;
import be.dispatcher.managers.IncidentSceneManager;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public class Ambulance extends Vehicle implements MedicalVehicle {

	private int maxSittingPlaces;

	private int maxlayingPlaces;

	private double healthGain;

	List<Victim> sittingVictims = new ArrayList<>();

	List<Victim> layingVictims = new ArrayList<>();

	@Autowired
	private IncidentSceneManager incidentSceneManager;

	public Ambulance(Location location, int maxSittingPlaces, int maxlayingPlace, double healthGain) {
		super(location, VehicleType.AMBULANCE);
		this.maxlayingPlaces = maxlayingPlace;
		this.maxSittingPlaces = maxSittingPlaces;
		this.healthGain = healthGain;
	}

	@Override
	public boolean hasEmptySpaces() {
		return hasFreeSittingsPlaces() || hasFreeLayingPlaces();
	}

	private boolean hasFreeLayingPlaces() {
		return layingVictims.size() < maxlayingPlaces;
	}

	private boolean hasFreeSittingsPlaces() {
		return sittingVictims.size() < maxSittingPlaces;
	}

	@Override
	public boolean canTransport(Victim victim) {
		if (victim.getInjuryLevel().equals(InjuryLevel.MINOR)) {
			return sittingVictims.size() < maxSittingPlaces;
		} else {
			return layingVictims.size() < maxlayingPlaces;
		}
	}

	@Override
	public void performJobAtIncidentLocation() {
		Victim victim = incidentSceneManager.getVictimFor(this);
		if (victim != null) {
			treatVictim(victim);
			if (victim.isTransportable()) {
				incidentSceneManager.notifyVictimReadyForTransport(this);
			}
		}
	}

	private void treatVictim(Victim victim) {
		victim.treat(healthGain);
	}
}
