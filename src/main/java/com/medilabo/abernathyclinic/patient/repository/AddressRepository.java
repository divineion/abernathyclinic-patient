package com.medilabo.abernathyclinic.patient.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.medilabo.abernathyclinic.patient.entity.Address;
import com.medilabo.abernathyclinic.patient.entity.Street;

public interface AddressRepository extends JpaRepository<Address, Integer> {
	@Query("select a from Address a where a.streetNumber = :streetNumber and a.street = :street")
	public Optional<Address> findByNumberAndStreet(String streetNumber, Street street);
}
