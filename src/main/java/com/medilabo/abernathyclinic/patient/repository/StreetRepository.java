package com.medilabo.abernathyclinic.patient.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.medilabo.abernathyclinic.patient.entity.City;
import com.medilabo.abernathyclinic.patient.entity.Street;

public interface StreetRepository extends JpaRepository<Street, Long> {
	
	@Query("select s from Street s where s.name = :name and s.city = :city")
	public Optional<Street> findByNameAndCity(String name, City city);
}
