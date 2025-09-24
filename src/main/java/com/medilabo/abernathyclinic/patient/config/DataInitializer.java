package com.medilabo.abernathyclinic.patient.config;

import com.medilabo.abernathyclinic.patient.entity.Address;
import com.medilabo.abernathyclinic.patient.entity.City;
import com.medilabo.abernathyclinic.patient.entity.Patient;
import com.medilabo.abernathyclinic.patient.entity.Street;
import com.medilabo.abernathyclinic.patient.repository.AddressRepository;
import com.medilabo.abernathyclinic.patient.repository.CityRepository;
import com.medilabo.abernathyclinic.patient.repository.PatientRepository;
import com.medilabo.abernathyclinic.patient.repository.StreetRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
public class DataInitializer {

	private final CityRepository cityRepository;
	private final StreetRepository streetRepository;
    private final AddressRepository addressRepository;
    private final PatientRepository patientRepository;
    
	DataInitializer(CityRepository cityRepository, StreetRepository streetRepository, AddressRepository addressRepository, PatientRepository patientRepository) {
		this.cityRepository = cityRepository;
		this.streetRepository = streetRepository;
		this.addressRepository = addressRepository;
		this.patientRepository = patientRepository;
	}

	@Bean
	CommandLineRunner insertDevData() {
		return _ -> {
			initCities();
			initStreets();
			initAddresses();
			initPatients();
		};
	}

	private void initCities() {
		List<City> newCities = new ArrayList<>();

		City newCity1 = new City("Cambridge", "10139");
		newCities.add(newCity1);

		City newCity2 = new City("North Platte", "20139");
		newCities.add(newCity2);

		City newCity3 = new City("San Diego", "30139");
		newCities.add(newCity3);

		City newCity4 = new City("Bethia", "40139");
		newCities.add(newCity4);

		newCities.forEach(city -> {
			if (cityRepository.findByNameAndZip(city.getName(), city.getZip()).isEmpty()) {
				cityRepository.save(city);
			}
		});
	}
		
	private void initStreets() {
		List<Street> streets = new ArrayList<>();
	
		City city1 = cityRepository.findByNameAndZip("Cambridge", "10139").get();
		Street street1 = new Street("Brookside St", city1);
		streets.add(street1);
	
		City city2 = cityRepository.findByNameAndZip("North Platte", "20139").get();
		Street street2 = new Street("High St", city2);
		streets.add(street2);
	
		City city3 = cityRepository.findByNameAndZip("San Diego", "30139").get();
		Street street3 = new Street("Club Road", city3);
		streets.add(street3);
	
		City city4 = cityRepository.findByNameAndZip("Bethia", "40139").get();
		Street street4 = new Street("Valley Dr", city4);
		streets.add(street4);
	
		streets.forEach(street -> {
			if (streetRepository.findByNameAndCity(street.getName(), street.getCity()).isEmpty()) {
				streetRepository.save(street);
			}
		});
	}
	
	private void initAddresses() {
		List<Address> addresses = new ArrayList<>();
	
		City city1 = cityRepository.findByNameAndZip("Cambridge", "10139").get();
		Street street1 = streetRepository.findByNameAndCity("Brookside St", city1).get();
		Address address1 = new Address("1", street1);
		addresses.add(address1);
	
		City city2 = cityRepository.findByNameAndZip("North Platte", "20139").get();
		Street street2 = streetRepository.findByNameAndCity("High St", city2).get();
		Address address2 = new Address("2", street2);
		addresses.add(address2);
	
		City city3 = cityRepository.findByNameAndZip("San Diego", "30139").get();
		Street street3 = streetRepository.findByNameAndCity("Club Road", city3).get();
		Address address3 = new Address("3", street3);
		addresses.add(address3);
	
		City city4 = cityRepository.findByNameAndZip("Bethia", "40139").get();
		Street street4 = streetRepository.findByNameAndCity("Valley Dr", city4).get();
		Address address4 = new Address("4", street4);
		addresses.add(address4);
	
		addresses.forEach(address -> {
			if (addressRepository.findByNumberAndStreet(address.getStreetNumber(), address.getStreet()).isEmpty()) {
				addressRepository.save(address);
			}
		});
	}
	
	private void initPatients() {
		List<Patient> patients = new ArrayList<>();
	
		City city1 = cityRepository.findByNameAndZip("Cambridge", "10139").get();
		Street street1 = streetRepository.findByNameAndCity("Brookside St", city1).get();
		Address address1 = addressRepository.findByNumberAndStreet("1", street1).get();
		Patient patient1 = new Patient("TestNone", "Test", LocalDate.of(1966, 12, 31), "F", address1, "100-222-3333");
		patients.add(patient1);
	
		City city2 = cityRepository.findByNameAndZip("North Platte", "20139").get();
		Street street2 = streetRepository.findByNameAndCity("High St", city2).get();
		Address address2 = addressRepository.findByNumberAndStreet("2", street2).get();
		Patient patient2 = new Patient("TestBorderline", "Test", LocalDate.of(1945, 06, 24), "M", address2,
				"200-333-4444");
		patients.add(patient2);
	
		City city3 = cityRepository.findByNameAndZip("San Diego", "30139").get();
		Street street3 = streetRepository.findByNameAndCity("Club Road", city3).get();
		Address address3 = addressRepository.findByNumberAndStreet("3", street3).get();
		Patient patient3 = new Patient("TestInDanger", "Test", LocalDate.of(2004, 06, 18), "M", address3,
				"300-444-5555");
		patients.add(patient3);
	
		City city4 = cityRepository.findByNameAndZip("Bethia", "40139").get();
		Street street4 = streetRepository.findByNameAndCity("Valley Dr", city4).get();
		Address address4 = addressRepository.findByNumberAndStreet("4", street4).get();
		Patient patient4 = new Patient("TestEarlyOnset", "Test", LocalDate.of(2002, 06, 28), "F", address4,
				"400-555-666");
		patients.add(patient4);
	
		patients.forEach(patient -> {
			if (patientRepository
					.findByNameAndBirthDate(patient.getLastName(), patient.getFirstName(), patient.getBirthDate())
					.isEmpty()) {
				patientRepository.save(patient);
			}
		});
	}
}
