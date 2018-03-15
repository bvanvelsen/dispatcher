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

import be.dispatcher.domain.location.Location;
import be.dispatcher.domain.location.LocationBuilder;
import be.dispatcher.domain.location.emergencybases.FireDepartment;
import be.dispatcher.domain.location.emergencybases.Hospital;
import be.dispatcher.domain.vehicle.Ambulance;
import be.dispatcher.domain.vehicle.VehicleFactory;
import be.dispatcher.repositories.BaseRespository;
import be.dispatcher.repositories.VehicleRepository;

@Component
public class Parser {

	@Autowired
	private BaseRespository baseRespository;

	@Autowired
	private VehicleFactory vehicleFactory;

	private Function<CSVRecord, Hospital> csvToHospitalFunction = csvRecord -> {
		Location location = LocationBuilder.aLocation()
				.withX(Integer.parseInt(csvRecord.get(2)))
				.withY(Integer.parseInt(csvRecord.get(3)))
				.build();
		return new Hospital(Integer.parseInt(csvRecord.get(0)), csvRecord.get(1), location);
	};

	private Function<CSVRecord, Ambulance> csvToAmbulanceFunction = csvRecord -> {
		return new Ambulance(Integer.parseInt(csvRecord.get(1)), csvRecord.get(2), Integer.parseInt(csvRecord.get(4)), Integer.parseInt(csvRecord.get(5)), Double.parseDouble(csvRecord.get(3)),
				baseRespository.getById(Integer.parseInt(csvRecord.get(1))));
	};
	private Function<CSVRecord, FireDepartment> csvToFireDepartmentFunction = csvRecord -> {
		Location location = LocationBuilder.aLocation()
				.withX(Integer.parseInt(csvRecord.get(2)))
				.withY(Integer.parseInt(csvRecord.get(3)))
				.build();
		return new FireDepartment(Integer.parseInt(csvRecord.get(0)), csvRecord.get(1), location);
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

	public void ambulanceParser() {
		try {
			Reader in = new FileReader(getClass().getClassLoader().getResource("init/ambulances.csv").getFile());
			Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in).getRecords();
			List<Ambulance> ambulances = IterableUtils.toList(records).stream()
					.map(csvToAmbulanceFunction)
					.collect(toList());
			ambulances.forEach(ambulance -> vehicleFactory.createBasicAmbulance(ambulance));
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
