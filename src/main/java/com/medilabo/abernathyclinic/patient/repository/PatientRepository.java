package com.medilabo.abernathyclinic.patient.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.medilabo.abernathyclinic.patient.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	
	@Query("select p from Patient p where p.lastName = :lastName and p.firstName = :firstName and p.birthDate = :birthDate")
	List<Patient> findByNameAndBirthDate(String lastName, String firstName, LocalDate birthDate);
	
	@Override
	@Query("select p from Patient p left join fetch p.address a left join fetch a.street s left join fetch s.city where p.id =:id")
	Optional<Patient> findById(Long id);

	@Query("select p from Patient p left join fetch p.address a left join fetch a.street s left join fetch s.city where p.uuid =:uuid")
	Optional<Patient> findByUuid(UUID uuid);
}
