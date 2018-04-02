package be.dispatcher.domain.vehicle.medical;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.vehicle.VehicleType;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public class Ambulance extends MedicalTransportVehicle{


	public Ambulance(int id, String name, Base base, int healthGainPerTick) {
		super(id, name, base, healthGainPerTick);
		vehicleType = VehicleType.AMBULANCE;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("name", name)
				.append("vehicleType", vehicleType)
				.append("base", base)
				.append("location", location)
				.toString();
	}
}
