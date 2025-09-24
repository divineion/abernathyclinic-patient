package com.medilabo.abernathyclinic.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medilabo.abernathyclinic.patient.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
