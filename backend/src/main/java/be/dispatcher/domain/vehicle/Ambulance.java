package be.dispatcher.domain.vehicle;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.people.InjuryLevel;
import be.dispatcher.domain.people.Victim;
import be.dispatcher.managers.incidentscene.IncidentSceneManager;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public class Ambulance extends Vehicle implements MedicalVehicle {

	private int maxSittingPlaces;

	private int maxlayingPlaces;

	private double healthGain;

	List<Victim> sittingVictims = new ArrayList<>();

	List<Victim> layingVictims = new ArrayList<>();

	@Autowired
	private IncidentSceneManager incidentSceneManager;

	public Ambulance(int id, String name,  int maxSittingPlaces, int maxlayingPlace, double healthGain, Base base) {
		super(id, name, VehicleType.AMBULANCE, base);
		this.maxlayingPlaces = maxlayingPlace;
		this.maxSittingPlaces = maxSittingPlaces;
		this.healthGain = healthGain;
	}

	@Override
	public boolean hasEmptySpaces() {
		return hasFreeSittingsPlaces() || hasFreeLayingPlaces();
	}

	@Override
	public boolean hasFreeLayingPlaces() {
		return freeLayingPlaces() > 0;
	}

	@Override
	public boolean hasFreeSittingsPlaces() {
		return freeSittingsPlaces() > 0;
	}

	@Override
	public int freeLayingPlaces() {
		return maxlayingPlaces - layingVictims.size();
	}

	@Override
	public int freeSittingsPlaces() {
		return maxSittingPlaces - sittingVictims.size();
	}

	@Override
	public List<Victim> getLayingVictims() {
		return layingVictims;
	}

	@Override
	public List<Victim> getSittingVictims() {
		return sittingVictims;
	}

	@Override
	public boolean canTransport(Victim victim) {
		if (victim.getInjuryLevel().equals(InjuryLevel.MINOR)) {
			return sittingVictims.size() < maxSittingPlaces;
		} else {
			return layingVictims.size() < maxlayingPlaces;
		}
	}

	@Override //called in tick sequence
	public void performJobAtIncidentLocation() {
		checkIfThereAreTransportableVictimsAndFillVehicle();
		treatVictimAndNotifyTransportableIfPossible();
	}

	@Override
	protected void clearVehicle() {
		sittingVictims.clear();
		layingVictims.clear();
	}

	private void checkIfThereAreTransportableVictimsAndFillVehicle() {
		if (hasEmptySpaces()) {
			incidentSceneManager.fillEmptySlotsWithStableVictims(this);
		}
	}

	private void treatVictimAndNotifyTransportableIfPossible() {
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

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("maxSittingPlaces", maxSittingPlaces)
				.append("maxlayingPlaces", maxlayingPlaces)
				.append("healthGain", healthGain)
				.append("sittingVictims", sittingVictims)
				.append("layingVictims", layingVictims)
				.toString();
	}
}
