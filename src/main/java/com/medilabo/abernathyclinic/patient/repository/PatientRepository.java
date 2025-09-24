package com.medilabo.abernathyclinic.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medilabo.abernathyclinic.patient.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
