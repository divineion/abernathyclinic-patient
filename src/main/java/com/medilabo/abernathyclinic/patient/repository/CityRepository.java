package com.medilabo.abernathyclinic.patient.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.medilabo.abernathyclinic.patient.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {
	@Query("select c from City c where c.name = :name and c.zip = :zip")
	Optional<City> findByNameAndZip(String name, String zip);
}
