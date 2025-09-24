package com.medilabo.abernathyclinic.patient.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.medilabo.abernathyclinic.patient.entity.City;

public interface CityRepository extends JpaRepository<City, Integer> {
	@Query("select c from City c where c.name = ?1 and c.zip = ?2")
	Optional<City> findByNameAndZip(String name, String zip);
}
