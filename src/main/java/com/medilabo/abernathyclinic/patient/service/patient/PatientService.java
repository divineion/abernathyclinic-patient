package com.medilabo.abernathyclinic.patient.service.patient;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medilabo.abernathyclinic.patient.dto.MinimalPatientDto;
import com.medilabo.abernathyclinic.patient.dto.PatientDto;
import com.medilabo.abernathyclinic.patient.entity.Patient;
import com.medilabo.abernathyclinic.patient.exception.PatientNotFoundException;
import com.medilabo.abernathyclinic.patient.repository.PatientRepository;

@Service
public class PatientService {
	private final PatientRepository patientRepository;
	private final PatientMapper patientMapper;
	
	public PatientService(PatientRepository patientRepository, PatientMapper patientMapper) {
		this.patientRepository = patientRepository;
		this.patientMapper = patientMapper;
	}
	
	public PatientDto findById(Long id) throws PatientNotFoundException {
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new PatientNotFoundException("No patient found with id " + id));
		
		return patientMapper.patientToPatientDto(patient);
	}

	public List<MinimalPatientDto> findAllPatient() {
		List<Patient> patientsList = patientRepository.findAll();
		
		List<MinimalPatientDto> dtoList = patientMapper.patientsListToMinimalPatientDtoList(patientsList);
		
		return dtoList;
	}
}
