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

import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.location.emergencybases.FireDepartment;
import be.dispatcher.domain.location.emergencybases.Hospital;
import be.dispatcher.domain.vehicle.Ambulance;
import be.dispatcher.domain.vehicle.Mug;
import be.dispatcher.domain.vehicle.Vehicle;
import be.dispatcher.domain.vehicle.VehicleType;
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

		private Function<CSVRecord, FireDepartment> csvToFireDepartmentFunction = csvRecord -> {
			int id = Integer.parseInt(csvRecord.get(0));
			String name = csvRecord.get(1);
			double lat = Double.parseDouble(csvRecord.get(2));
			double lon = Double.parseDouble(csvRecord.get(3));
			LatLon latLon = new LatLon(lat, lon);
			return new FireDepartment(id, name, latLon);
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

		public void fireDepartmentParser() {
			try {
				Reader in = new FileReader(getClass().getClassLoader().getResource("init/fire_department.csv").getFile());
				Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in).getRecords();
				List<FireDepartment> fireDepartments = IterableUtils.toList(records).stream()
						.map(csvToFireDepartmentFunction)
						.collect(toList());
				fireDepartments.forEach(ambulance -> baseRespository.addBaseToRepository(ambulance));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
}
