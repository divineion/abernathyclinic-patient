package com.medilabo.abernathyclinic.patient.config;

import com.medilabo.abernathyclinic.patient.entity.City;
import com.medilabo.abernathyclinic.patient.entity.Street;
import com.medilabo.abernathyclinic.patient.repository.CityRepository;
import com.medilabo.abernathyclinic.patient.repository.StreetRepository;

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
	
	DataInitializer(CityRepository cityRepository, StreetRepository streetRepository) {
		this.cityRepository = cityRepository;
		this.streetRepository = streetRepository;
	}

	@Bean
	CommandLineRunner insertDevData() {
		return _ -> {
			initCities();
			initStreets();
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
}
