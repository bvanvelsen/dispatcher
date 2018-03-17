package be.dispatcher.domain.vehicle;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.people.InjuryLevel;
import be.dispatcher.domain.people.Victim;
import be.dispatcher.managers.incidentscene.IncidentSceneMedicalTasksManager;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public class Ambulance extends Vehicle implements MedicalVehicle {

	private int maxSittingPlaces;

	private int maxlayingPlaces;

	private double healthGain;

	private List<Victim> sittingVictims = new ArrayList<>();

	private List<Victim> layingVictims = new ArrayList<>();

	private boolean mug;

	@Autowired
	private IncidentSceneMedicalTasksManager incidentSceneMedicalTasksManager;

	public Ambulance(int id, String name,  int maxSittingPlaces, int maxlayingPlace, double healthGain, Base base) {
		super(id, name, VehicleType.AMBULANCE, base);
		this.maxlayingPlaces = maxlayingPlace;
		this.maxSittingPlaces = maxSittingPlaces;
		this.healthGain = healthGain;
		this.mug = StringUtils.startsWithIgnoreCase(name, "MUG");
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
	public int getMaxlayingPlaces() {
		return maxlayingPlaces;
	}

	@Override
	public int getMaxSittingPlaces() {
		return maxSittingPlaces;
	}

	@Override
	public boolean isMug() {
		return mug;
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
			incidentSceneMedicalTasksManager.fillEmptySlotsWithStableVictims(this);
		}
	}

	private void treatVictimAndNotifyTransportableIfPossible() {
		Victim victim = incidentSceneMedicalTasksManager.getVictimFor(this);
		if (victim != null) {
			treatVictim(victim);
			if (victim.isTransportable()) {
				incidentSceneMedicalTasksManager.notifyVictimReadyForTransport(this);
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
