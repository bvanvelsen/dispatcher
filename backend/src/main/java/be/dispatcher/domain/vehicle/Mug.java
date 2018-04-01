package be.dispatcher.domain.vehicle;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import be.dispatcher.domain.incident.Incident;
import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.graphhopper.LatLon;
import be.dispatcher.graphhopper.external_router.reouteinfojson.RouteInfo;
import be.dispatcher.graphhopper.external_router.reouteinfojson.RouteInfoEnriched;

@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE)
public class Mug extends MedicalVehicle {

	public Mug(int id, String name, Base base, int healthGainPerTick) {
		super(id, name, base, healthGainPerTick);
		vehicleType = VehicleType.MUG;
	}

	@Override
	public void tick() {
		super.tick();
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
