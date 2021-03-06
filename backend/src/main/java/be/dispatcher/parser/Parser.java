package be.dispatcher.parser;

import static java.util.stream.Collectors.toList;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.dispatcher.domain.location.emergencybases.AmbulanceStation;
import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.location.emergencybases.BaseFactory;
import be.dispatcher.domain.location.emergencybases.FireDepartment;
import be.dispatcher.domain.location.emergencybases.Hospital;
import be.dispatcher.domain.location.emergencybases.PoliceStation;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.VehicleType;
import be.dispatcher.domain.vehicle.fire.FireTruck;
import be.dispatcher.domain.vehicle.fire.FireTruckTrafficDutyVehicle;
import be.dispatcher.domain.vehicle.medical.Ambulance;
import be.dispatcher.domain.vehicle.medical.Mug;
import be.dispatcher.domain.vehicle.police.PoliceVehicle;
import be.dispatcher.repositories.BaseRespository;
import be.dispatcher.repositories.VehicleRepository;

@Component
public class Parser {

	private static final Logger LOGGER = LoggerFactory.getLogger(Parser.class);

	@Autowired
	private BaseRespository baseRespository;

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private BaseFactory baseFactory;

	public Function<CSVRecord, Vehicle> csvToMedicalVehicleFunction = csvRecord -> {
		int id = Integer.parseInt(csvRecord.get(0));
		Base base = baseRespository.getById(Integer.parseInt(csvRecord.get(1)));
		String name = csvRecord.get(2);
		VehicleType vehicleType = VehicleType.valueOf(csvRecord.get(3));
		int healthGainPerTick = Integer.parseInt(csvRecord.get(4));
		String vehicleImagePath = null;
		try {
			vehicleImagePath = csvRecord.get(5);
		} catch (Exception exception) {
			LOGGER.warn(String.format("Geen image kunnen laden voor medical vehicle met id: %s", id), exception);
		}
		if (VehicleType.AMBULANCE.equals(vehicleType)) {
			return new Ambulance(id, name, base, healthGainPerTick, vehicleImagePath);
		} else if (VehicleType.MUG.equals(vehicleType)) {
			return new Mug(id, name, base, healthGainPerTick, vehicleImagePath);
		}
		return null;
	};

	public Function<CSVRecord, Vehicle> csvToFireTruckFunction = csvRecord -> {
		int id = Integer.parseInt(csvRecord.get(0));
		Base base = baseRespository.getById(Integer.parseInt(csvRecord.get(1)));
		String name = csvRecord.get(2);
		VehicleType vehicleType = VehicleType.valueOf(csvRecord.get(3));
		int fireGainPerTick = Integer.parseInt(csvRecord.get(4));
		int technicalPerTick = Integer.parseInt(csvRecord.get(5));
		String vehicleImagePath = null;
		try {
			vehicleImagePath = csvRecord.get(6);
		} catch (Exception exception) {
			LOGGER.warn(String.format("Geen image kunnen laden voor fire truck met id: %s", id), exception);
		}

		switch (vehicleType) {
		case SIGNALISATIEWAGEN:
			return new FireTruckTrafficDutyVehicle(id, name, base, vehicleType, fireGainPerTick, technicalPerTick, vehicleImagePath);
		default:
			return new FireTruck(id, name, base, vehicleType, fireGainPerTick, technicalPerTick, vehicleImagePath);

		}
	};

	public Function<CSVRecord, Vehicle> csvToPoliceVehicleFunction = csvRecord -> {
		int id = Integer.parseInt(csvRecord.get(0));
		Base base = baseRespository.getById(Integer.parseInt(csvRecord.get(1)));
		String name = csvRecord.get(2);
		VehicleType vehicleType = VehicleType.valueOf(csvRecord.get(3));
		int arrestGainPerTick = Integer.parseInt(csvRecord.get(4));
		String vehicleImagePath = null;
		try {
			vehicleImagePath = csvRecord.get(5);
		} catch (Exception exception) {
			LOGGER.warn(String.format("Geen image kunnen laden voor police vehicle met id: %s", id), exception);
		}
		return new PoliceVehicle(id, name, base, arrestGainPerTick, vehicleType, vehicleImagePath);
	};

	public Function<CSVRecord, Base> csvToHospitalFunction = csvRecord -> {
		int id = Integer.parseInt(csvRecord.get(0));
		String name = csvRecord.get(1);
		double lat = Double.parseDouble(csvRecord.get(2));
		double lon = Double.parseDouble(csvRecord.get(3));
		return baseFactory.getBase(id, lat, lon, name, Hospital.class);
	};

	public Function<CSVRecord, Base> csvToFireDepartmentFunction = csvRecord -> {
		int id = Integer.parseInt(csvRecord.get(0));
		String name = csvRecord.get(1);
		double lat = Double.parseDouble(csvRecord.get(2));
		double lon = Double.parseDouble(csvRecord.get(3));
		return baseFactory.getBase(id, lat, lon, name, FireDepartment.class);
	};

	public Function<CSVRecord, Base> csvToAmbulanceStationFunction = csvRecord -> {
		int id = Integer.parseInt(csvRecord.get(0));
		String name = csvRecord.get(1);
		double lat = Double.parseDouble(csvRecord.get(2));
		double lon = Double.parseDouble(csvRecord.get(3));
		return baseFactory.getBase(id, lat, lon, name, AmbulanceStation.class);
	};

	public Function<CSVRecord, Base> csvToPoliceStationFunction = csvRecord -> {
		int id = Integer.parseInt(csvRecord.get(0));
		String name = csvRecord.get(1);
		double lat = Double.parseDouble(csvRecord.get(2));
		double lon = Double.parseDouble(csvRecord.get(3));
		return baseFactory.getBase(id, lat, lon, name, PoliceStation.class);
	};

	public void parseBase(String baseFile, Function<CSVRecord, Base> function) {
		Iterable<CSVRecord> records = getCsvRecords(baseFile);
		List<? extends Base> bases = IterableUtils.toList(records).stream()
				.map(function)
				.collect(toList());
		bases.forEach(base -> baseRespository.addBaseToRepository(base));
	}

	public List<Vehicle> parseVehicles(String vehiclesFile, Function<CSVRecord, Vehicle> function) {
		Iterable<CSVRecord> records = getCsvRecords(vehiclesFile);
		List<Vehicle> vehicles = IterableUtils.toList(records).stream()
				.map(function)
				.collect(toList());
		vehicles.forEach(this::addToReposity);
		return vehicles;
	}

	private void addToReposity(Vehicle vehicle) {
		vehicleRepository.addVehicleToRepository(vehicle);
	}

	private Iterable<CSVRecord> getCsvRecords(String baseFile) {
		try {
			Reader in = new FileReader(Objects.requireNonNull(getClass().getClassLoader().getResource(baseFile)).getFile());
			return CSVFormat.RFC4180.withCommentMarker('#').parse(in).getRecords();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
