package com.medilabo.abernathyclinic.patient.config;

import com.medilabo.abernathyclinic.patient.entity.City;
import com.medilabo.abernathyclinic.patient.repository.CityRepository;

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

	DataInitializer(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}

	@Bean
	CommandLineRunner insertDevData() {
		return _ -> {
			initCities();
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
}
