package com.medilabo.abernathyclinic.patient.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.abernathyclinic.patient.dto.MinimalPatientDto;
import com.medilabo.abernathyclinic.patient.dto.PatientDto;
import com.medilabo.abernathyclinic.patient.exception.PatientNotFoundException;
import com.medilabo.abernathyclinic.patient.service.patient.PatientService;

@RestController
public class PatientController {
    private final PatientService patientService;

    PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
    
	@GetMapping("/api/patient/{id}")
	public ResponseEntity<PatientDto> getPatientById(@PathVariable Long id) throws PatientNotFoundException {	
		PatientDto patient = patientService.findPatientById(id);
		return ResponseEntity.ok(patient);
	}
	
	@GetMapping("/api/patients")
	public ResponseEntity<List<MinimalPatientDto>> getAllPatients() {
		List<MinimalPatientDto> list = patientService.findAllPatient();
		return ResponseEntity.ok(list);
	}
}
