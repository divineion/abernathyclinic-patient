package com.medilabo.abernathyclinic.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medilabo.abernathyclinic.patient.entity.Street;

public interface StreetRepository extends JpaRepository<Street, Integer> {

}
