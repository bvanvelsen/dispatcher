package be.dispatcher.init;

import static java.util.stream.Collectors.toList;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.dispatcher.domain.location.emergencybases.AmbulanceStation;
import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.location.emergencybases.FireDepartment;
import be.dispatcher.domain.location.emergencybases.Hospital;
import be.dispatcher.domain.location.emergencybases.PoliceStation;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.VehicleType;
import be.dispatcher.domain.vehicle.fire.FireTruck;
import be.dispatcher.domain.vehicle.medical.Ambulance;
import be.dispatcher.domain.vehicle.medical.Mug;
import be.dispatcher.domain.vehicle.police.PoliceVehicle;
import be.dispatcher.graphhopper.LatLon;
import be.dispatcher.repositories.BaseRespository;
import be.dispatcher.repositories.VehicleRepository;

@Component
public class Parser {

	@Autowired
	private BaseRespository baseRespository;

	@Autowired
	private VehicleRepository vehicleRepository;

	private Function<CSVRecord, Hospital> csvToHospitalFunction = csvRecord -> {
		int id = Integer.parseInt(csvRecord.get(0));
		String name = csvRecord.get(1);
		double lat = Double.parseDouble(csvRecord.get(2));
		double lon = Double.parseDouble(csvRecord.get(3));
		LatLon latLon = new LatLon(lat, lon);
		return new Hospital(id, name, latLon);
	};

	private Function<CSVRecord, Vehicle> csvToMedicalVehicleFunction = csvRecord -> {
		int id = Integer.parseInt(csvRecord.get(0));
		Base base = baseRespository.getById(Integer.parseInt(csvRecord.get(1)));
		String name = csvRecord.get(2);
		VehicleType vehicleType = VehicleType.valueOf(csvRecord.get(3));
		int healthGainPerTick = Integer.parseInt(csvRecord.get(4));
		switch (vehicleType) {
		case AMBULANCE:
			return new Ambulance(id, name, base, healthGainPerTick);
		case MUG:
			return new Mug(id, name, base, healthGainPerTick);
		}
		return null;
	};

	private Function<CSVRecord, Vehicle> csvToFireTruckFunction = csvRecord -> {
		int id = Integer.parseInt(csvRecord.get(0));
		Base base = baseRespository.getById(Integer.parseInt(csvRecord.get(1)));
		String name = csvRecord.get(2);
		VehicleType vehicleType = VehicleType.valueOf(csvRecord.get(3));
		int fireGainPerTick = Integer.parseInt(csvRecord.get(4));
		int technicalPerTick = Integer.parseInt(csvRecord.get(5));
		boolean volunteer = Boolean.parseBoolean(csvRecord.get(6));
		return new FireTruck(id, name, base, vehicleType, fireGainPerTick, technicalPerTick, volunteer);
	};

	private Function<CSVRecord, Vehicle> csvToPoliceVehicleFunction = csvRecord -> {
		int id = Integer.parseInt(csvRecord.get(0));
		Base base = baseRespository.getById(Integer.parseInt(csvRecord.get(1)));
		String name = csvRecord.get(2);
		VehicleType vehicleType = VehicleType.valueOf(csvRecord.get(3));
		int arrestGainPerTick = Integer.parseInt(csvRecord.get(4));
		return new PoliceVehicle(id, name, base, arrestGainPerTick, vehicleType);
	};

	private Function<CSVRecord, FireDepartment> csvToFireDepartmentFunction = csvRecord -> {
		int id = Integer.parseInt(csvRecord.get(0));
		String name = csvRecord.get(1);
		double lat = Double.parseDouble(csvRecord.get(2));
		double lon = Double.parseDouble(csvRecord.get(3));
		LatLon latLon = new LatLon(lat, lon);
		return new FireDepartment(id, name, latLon);
	};

	private Function<CSVRecord, AmbulanceStation> csvToAmbulanceStationFunction = csvRecord -> {
		int id = Integer.parseInt(csvRecord.get(0));
		String name = csvRecord.get(1);
		double lat = Double.parseDouble(csvRecord.get(2));
		double lon = Double.parseDouble(csvRecord.get(3));
		LatLon latLon = new LatLon(lat, lon);
		return new AmbulanceStation(id, name, latLon);
	};

	private Function<CSVRecord, PoliceStation> csvToPoliceStationFunction = csvRecord -> {
		int id = Integer.parseInt(csvRecord.get(0));
		String name = csvRecord.get(1);
		double lat = Double.parseDouble(csvRecord.get(2));
		double lon = Double.parseDouble(csvRecord.get(3));
		LatLon latLon = new LatLon(lat, lon);
		return new PoliceStation(id, name, latLon);
	};

	public void hospitalParser() {
		try {
			Reader in = new FileReader(getClass().getClassLoader().getResource("init/hospitals.csv").getFile());
			Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in).getRecords();
			List<Hospital> hospitals = IterableUtils.toList(records).stream()
					.map(csvToHospitalFunction)
					.collect(toList());
			hospitals.forEach(hospital -> baseRespository.addBaseToRepository(hospital));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void medicalVehicleParser() {
		try {
			Reader in = new FileReader(getClass().getClassLoader().getResource("init/ambulances.csv").getFile());
			Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in).getRecords();
			List<Vehicle> ambulances = IterableUtils.toList(records).stream()
					.map(csvToMedicalVehicleFunction)
					.collect(toList());
			ambulances.forEach(ambulance -> vehicleRepository.addVehicleToRepository(ambulance));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void fireTruckParser() {
		try {
			Reader in = new FileReader(getClass().getClassLoader().getResource("init/fire_trucks.csv").getFile());
			Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in).getRecords();
			List<Vehicle> fireTrucks = IterableUtils.toList(records).stream()
					.map(csvToFireTruckFunction)
					.collect(toList());
			fireTrucks.forEach(fireTruck -> vehicleRepository.addVehicleToRepository(fireTruck));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void policeVehicleParser() {
		try {
			Reader in = new FileReader(getClass().getClassLoader().getResource("init/police_vehicles.csv").getFile());
			Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in).getRecords();
			List<Vehicle> fireTrucks = IterableUtils.toList(records).stream()
					.map(csvToPoliceVehicleFunction)
					.collect(toList());
			fireTrucks.forEach(fireTruck -> vehicleRepository.addVehicleToRepository(fireTruck));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void fireDepartmentParser() {
		try {
			Reader in = new FileReader(getClass().getClassLoader().getResource("init/fire_department.csv").getFile());
			Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in).getRecords();
			List<FireDepartment> fireDepartments = IterableUtils.toList(records).stream()
					.map(csvToFireDepartmentFunction)
					.collect(toList());
			fireDepartments.forEach(fireDepartment -> baseRespository.addBaseToRepository(fireDepartment));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void ambulanceStationParser() {
		try {
			Reader in = new FileReader(getClass().getClassLoader().getResource("init/ambulance_stations.csv").getFile());
			Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in).getRecords();
			List<AmbulanceStation> ambulanceStations = IterableUtils.toList(records).stream()
					.map(csvToAmbulanceStationFunction)
					.collect(toList());
			ambulanceStations.forEach(fireDepartment -> baseRespository.addBaseToRepository(fireDepartment));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void policeStationParser() {
		try {
			Reader in = new FileReader(getClass().getClassLoader().getResource("init/police_stations.csv").getFile());
			Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in).getRecords();
			List<PoliceStation> policeStations = IterableUtils.toList(records).stream()
					.map(csvToPoliceStationFunction)
					.collect(toList());
			policeStations.forEach(policeStation -> baseRespository.addBaseToRepository(policeStation));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
